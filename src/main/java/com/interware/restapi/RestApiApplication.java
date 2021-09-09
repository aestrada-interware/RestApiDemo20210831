package com.interware.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		boolean appStart=true;
		if(args.length>=1) {
			if(args[0].equalsIgnoreCase("--RemoveDB")) {
				appStart=false;
				LocalDBLauncherConfiguration localDBLauncherConfiguration = new LocalDBLauncherConfiguration();
				localDBLauncherConfiguration.setRunMode(LocalDBLauncherConfiguration.ACTION_REMOVE_ALL_DERBY_DB);
				localDBLauncherConfiguration.executeInitDB();
			} else if(args[0].equalsIgnoreCase("--InstallDB")) {
				LocalDBLauncherConfiguration localDBLauncherConfiguration = new LocalDBLauncherConfiguration();
				localDBLauncherConfiguration.setRunMode(LocalDBLauncherConfiguration.ACTION_INSTALL_AND_WORK_DERBY_DB);
				localDBLauncherConfiguration.executeInitDB();
			} else if(args[0].equalsIgnoreCase("--ReinstallDB")) {
				LocalDBLauncherConfiguration localDBLauncherConfiguration = new LocalDBLauncherConfiguration();
				localDBLauncherConfiguration.setRunMode(LocalDBLauncherConfiguration.ACTION_RE_INSTALL_AND_WORK_DERBY_DB);
				localDBLauncherConfiguration.executeInitDB();
			}
		}

		if(appStart) {
			SpringApplication.run(RestApiApplication.class, args);
		}
	}

}
