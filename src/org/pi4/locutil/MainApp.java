package org.pi4.locutil;

import java.io.IOException;
import java.util.List;

public class MainApp {

	public static void main(String[] args) throws IOException {
		//Fingerprinting
		List<PositionTrace> offline;
		List<PositionTrace> online;
		Fingerprinting fprint = new Fingerprinting();
		fprint.getTraces();
		
		//empirical fingerprinting
		offline = fprint.getOfflineRadioMapEmpirical();
		online = fprint.getOnlineRadioMapEmpirical();
		
		//model-based fingerprinting
		
		//Matching
		Matching match = new Matching(offline, online);
		int k = 3; //define influencing nearest neighbor
		String output = "output.txt"; //define name for output-file
		
		try {
			//nearest neighbor matching
			match.schreibeDatei(output,match.getNearest());
		
			//k-nearest neighbor matching
			//match.schreibeDatei(output,match.getKNearest(k));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ErrorRate
		ErrorRate err = new ErrorRate();
		String datei = "error.txt";
		try{
			err.readFile();
			err.writeFile(datei);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 
		
	}

}