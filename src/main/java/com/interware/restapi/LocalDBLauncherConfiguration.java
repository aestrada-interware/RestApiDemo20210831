package com.interware.restapi;

import com.tracktopell.dbutil.DBInstaller;
import com.tracktopell.dbutil.DerbyDBInstaller;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author Alfredo Estrada
 */
@Component
public class LocalDBLauncherConfiguration {
    private static final Logger logger = Logger.getLogger(LocalDBLauncherConfiguration.class);
    static String runMode;

	private static Connection conn = null;

    private static Properties configProperties;
    
    private static String HOME_DERBY_DB   = null;
    private static String ACTION_DERBY_DB = null;

    private static String SMART_QUEUE_SYSYTEM_STATUS = null;
    
    public static final String SYSTEM_READY     = "SYSTEM_READY";
    
    public static final String SYSTEM_ERROR     = "SYSTEM_ERROR";
    public static final String SYSTEM_NOT_READY = "SYSTEM_NOT_READY";
    
    private static final String ACTION_INSTALL_AND_WORK_DERBY_DB     = "INSTALL_AND_WORK_DERBY_DB";
    private static final String ACTION_RE_INSTALL_AND_WORK_DERBY_DB  = "RE_INSTALL_AND_WORK_DERBY_DB";
    private static final String ACTION_REMOVE_ALL_DERBY_DB           = "REMOVE_ALL_DERBY_DB";
    
    public static String getSMART_QUEUE_SYSYTEM_STATUS() {
        return SMART_QUEUE_SYSYTEM_STATUS;
    }
    
    public LocalDBLauncherConfiguration() {
        runMode=ACTION_INSTALL_AND_WORK_DERBY_DB;
    }
    
    @PostConstruct
    public void postConstruct(){
        logger.info("-> postConstruct: getRunMode()="+getRunMode());
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }

    public String getRunMode() {
        return runMode;
    }
    private static Properties getConfigProperties() {
        logger.debug("--->>> getConfigProperties:");
        if(configProperties == null){
            configProperties = new Properties ();
            InputStream resourceAsStream = null;
            try{
                resourceAsStream = LocalDBLauncherConfiguration.class.getResourceAsStream("/application.properties");
                configProperties.load(resourceAsStream);
            }catch(Exception ioe){
                logger.error("--->>> getConfigProperties:",ioe);
            }
        }
        logger.error("--->>> getConfigProperties: configProperties="+configProperties);
        return configProperties;
    }
    
    private void printBanner(){
        InputStream bis=null;
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/banner.txt")));
            String line=null;
            for(int numLinea=1;(line=br.readLine())!=null;numLinea++){
                logger.info(line);
            }
        }catch(Exception ioe){
            logger.warn("... y el /banner.txt ?");
        }
    }
    
    void executeInitDB(){    
        HOME_DERBY_DB   = "./DB_DERBY";
        ACTION_DERBY_DB = runMode;
        File serverWorkHome=null;
        File derbyHome=null;
        getConfigProperties();        

        serverWorkHome=new File(".");
        logger.debug("--->>> contextInitialized: serverWorkHome="+serverWorkHome.getAbsolutePath());
        
        derbyHome=new File(HOME_DERBY_DB);
        
        logger.debug("--->>> contextInitialized:  #########################################[  ACTION_DERBY_DB="+ACTION_DERBY_DB+" ]#########################################");
        if(ACTION_DERBY_DB.equals(ACTION_INSTALL_AND_WORK_DERBY_DB)){
            logger.debug("--->>> contextInitialized: derbyHome="+derbyHome.getAbsolutePath()+" exist?"+derbyHome.exists());
            if(!derbyHome.exists()){
                boolean createdDerbyHome=derbyHome.mkdirs();
                logger.debug("--->>> contextInitialized: created?"+createdDerbyHome+", now exist?"+derbyHome.exists());
            }
            
            if(derbyHome.exists()){
                logger.info("--->>> OK apache derby home at :"+derbyHome.getAbsolutePath());
                System.setProperty("derby.system.home", derbyHome.getAbsolutePath());
                ececuteTextConnection();
            } else {
                logger.warn("--->>> Can't create derby home at :"+derbyHome.getAbsolutePath());
                SMART_QUEUE_SYSYTEM_STATUS = SYSTEM_ERROR;
            }
            
        } else if(ACTION_DERBY_DB.equals(ACTION_RE_INSTALL_AND_WORK_DERBY_DB)){
            logger.debug("--->>> contextInitialized: derbyHome="+derbyHome.getAbsolutePath()+" exist?"+derbyHome.exists());
            if(!derbyHome.exists()){
                boolean createdDerbyHome=derbyHome.mkdirs();
                logger.debug("--->>> contextInitialized: created?"+createdDerbyHome+", now exist?"+derbyHome.exists());
            } else {
                logger.debug("--->>> contextInitialized: remove home");
                removeDirRecursevly(derbyHome,0);
                boolean createdDerbyHome=derbyHome.mkdirs();
                logger.debug("--->>> contextInitialized: re-created?"+createdDerbyHome+", now exist?"+derbyHome.exists());
            }
            
            if(derbyHome.exists()){
                logger.info("--->>> OK apache derby home at :"+derbyHome.getAbsolutePath());
                System.setProperty("derby.system.home", derbyHome.getAbsolutePath());
                ececuteTextConnection();
            } else {
                logger.warn("--->>> Can't create derby home at :"+derbyHome.getAbsolutePath());
                SMART_QUEUE_SYSYTEM_STATUS = SYSTEM_ERROR;
            }
            
        } else if(ACTION_DERBY_DB.equals(ACTION_REMOVE_ALL_DERBY_DB)){
            removeDirRecursevly(derbyHome,0);
            boolean createdDerbyHome=derbyHome.mkdirs();
            logger.debug("--->>> contextInitialized: re-created?"+createdDerbyHome+", now exist?"+derbyHome.exists());
            SMART_QUEUE_SYSYTEM_STATUS = SYSTEM_NOT_READY;
        } else{
            SMART_QUEUE_SYSYTEM_STATUS = SYSTEM_NOT_READY;
        }
        
        logger.debug("<<<===========================[ contextInitialized : "+SMART_QUEUE_SYSYTEM_STATUS+" ]================================================");
    }
    
    
    private boolean removeDirRecursevly(File directoryToBeDeleted,int level) {
        StringBuilder sbSpaced=new StringBuilder();
        for(int i=0;i<level;i++){
            sbSpaced.append("\t");
        }
        logger.debug("--->>> contextInitialized:"+sbSpaced+"rm -rf "+directoryToBeDeleted.getAbsolutePath());        
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                removeDirRecursevly(file,level++);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private void ececuteTextConnection() {
        try{
            conn = getConnection();
            SMART_QUEUE_SYSYTEM_STATUS = SYSTEM_READY;
        }catch(SQLException se){
            logger.error("Cant init DB:",se);
            SMART_QUEUE_SYSYTEM_STATUS = SYSTEM_ERROR;
        }finally{
            try{
                if(conn != null){
                    if(!conn.isClosed()){
                        logger.debug("--->>> contextInitialized: ...clissing DB");
                        conn.close();
                    }
                    conn=null;
                }
                
            }catch(SQLException se2){
                logger.error("Can't close DB:",se2);
            }
        }
    }
	
	public static Connection getConnection() throws SQLException{
		logger.info("->getConnection():");
		if(conn == null) {
			DBInstaller dbi = null;
			try {
				dbi = new DerbyDBInstaller("classpath:/application.properties", null);
			} catch (IOException ex) {
				logger.error("Error:",ex);
				ex.printStackTrace(System.err);
				return null;
			}
			Connection connectionForInit = dbi.getExistDB();

			if (connectionForInit == null) {
				logger.info("The DB does'nt exist -> installDBfromScratch !");
				connectionForInit = dbi.getInstallDBfromScratch();
			}

			logger.info("<-getConnection():OK connectionForInit=" + connectionForInit);
			conn = connectionForInit;
		}
		return conn;
	}
}
