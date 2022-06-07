package com.LagBug.ThePit.Others;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.LagBug.ThePit.Main;

public class UpdateChecker {

	private Main main;
	private int projectID;
	private String newVersion;
	private String currentVersion;
	private URL url;
	
	public UpdateChecker(Main main, int projectID) {
		this.main = main;
		this.projectID = projectID;
		this.currentVersion = this.main.getDescription().getVersion();
		try {
			url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
		} catch (MalformedURLException ex) { }
	}	

	
    public UpdateResult getResult() {
    	try {
         	URLConnection con = url.openConnection();
            this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            
        	int currentV = Integer.parseInt(currentVersion.replace(".", ""));
        	int newV = Integer.parseInt(newVersion.replace(".", ""));
            
            if (newV > currentV) {
            	return UpdateResult.FOUND;
            } else if (newV < currentV) {
            	return UpdateResult.DEVELOPMENT;
            }
            	return UpdateResult.NOT_FOUND;
            
    	} catch (Exception ex) {
    		return UpdateResult.ERROR;
    	}
    }
    
    public int getProjectID() {
    	return projectID;
    }
    
    public String getCurrentVersion() {
    	return currentVersion;
    }
    
    public String getNewVersion() {
    	return newVersion;
    }
}