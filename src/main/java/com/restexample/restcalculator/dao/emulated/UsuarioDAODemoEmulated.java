package com.restexample.restcalculator.dao.emulated;

import com.interware.restapi.dto.Usuario;
import com.restexample.restcalculator.dao.UsuarioDAO;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;
/**
 *
 * @author Alfredo Estrada
 */
@Scope("singleton")
@Repository
public class UsuarioDAODemoEmulated implements UsuarioDAO {
    private static final Logger logger = Logger.getLogger(UsuarioDAODemoEmulated.class);
    
    private LinkedHashMap<String , Usuario> db;

    public UsuarioDAODemoEmulated() {
        db = new LinkedHashMap<String , Usuario>();
    }
    
    @PostConstruct
    public void postConstruct(){
        logger.info("-> postConstruct: ok");
    }
    
    @Override
    public List<Usuario> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario insertOrUpdate(Usuario u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Usuario u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
