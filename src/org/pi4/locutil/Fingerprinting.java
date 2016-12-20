package org.pi4.locutil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pi4.locutil.io.TraceGenerator;
import org.pi4.locutil.trace.Parser;
import org.pi4.locutil.trace.SignalStrengthSamples;
import org.pi4.locutil.trace.TraceEntry;

public class Fingerprinting {

	private List<TraceEntry> offlineTrace;
	private List<TraceEntry> onlineTrace;
	private static final int offlineSize = 25;
	private static final int onlineSize = 5;
	private static final MACAddress[] aPoints = {MACAddress.parse("00:14:BF:B1:7C:54")
													,MACAddress.parse("00:16:B6:B7:5D:8F")
													,MACAddress.parse("00:14:BF:B1:7C:57")
													,MACAddress.parse("00:14:BF:B1:97:8D")
													,MACAddress.parse("00:16:B6:B7:5D:9B")
													,MACAddress.parse("00:14:6C:62:CA:A4")
													,MACAddress.parse("00:14:BF:3B:C7:C6")
													,MACAddress.parse("00:14:BF:B1:97:8A")
													,MACAddress.parse("00:14:BF:B1:97:81")
													,MACAddress.parse("00:16:B6:B7:5D:8C")
													,MACAddress.parse("00:11:88:28:5E:E0")};
	
	public void getTraces(){
		
		String offlinePath = "data/MU.1.5meters.offline.trace", onlinePath = "data/MU.1.5meters.online.trace";
		
		//Construct parsers
		File offlineFile = new File(offlinePath);
		Parser offlineParser = new Parser(offlineFile);
		System.out.println("Offline File: " +  offlineFile.getAbsoluteFile());
		
		File onlineFile = new File(onlinePath);
		Parser onlineParser = new Parser(onlineFile);
		System.out.println("Online File: " + onlineFile.getAbsoluteFile());
		
		//Construct trace generator
		TraceGenerator tg;
		
		try {
			tg = new TraceGenerator(offlineParser, onlineParser,offlineSize,onlineSize);
			
			//Generate traces from parsed files
			tg.generate();
			
			//Iterate the trace generated from the offline file
			offlineTrace = tg.getOffline();			
			onlineTrace = tg.getOnline();			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<PositionTrace> getOfflineRadioMapEmpirical(){
		
		List<PositionTrace> data = new ArrayList<PositionTrace>();
		for(TraceEntry entry: offlineTrace) {
			Boolean entrySet = false;
			for(int i=0;i<data.size();i++){
				if(data.get(i).getPosition().equals(entry.getGeoPosition())){
					data.set(i, addSignals(data.get(i), entry.getSignalStrengthSamples()));
					entrySet=true;
				}
			}
			if(!entrySet){
				data.add(addSignals(new PositionTrace(entry.getGeoPosition(),new double[11]), entry.getSignalStrengthSamples()));
			}
		}
		return data;
		
	}
	
	public List<PositionTrace> getOnlineRadioMapEmpirical(){
		
		List<PositionTrace> data = new ArrayList<PositionTrace>();
		for(TraceEntry entry: onlineTrace) {
			Boolean entrySet = false;
			for(int i=0;i<data.size();i++){
				if(data.get(i).getPosition().equals(entry.getGeoPosition())){
					data.set(i, addSignals(data.get(i), entry.getSignalStrengthSamples()));
				}
			}
			if(!entrySet){
				data.add(addSignals(new PositionTrace(entry.getGeoPosition(),new double[11]), entry.getSignalStrengthSamples()));
			}
		}
		return data;
		
	}
	
	public PositionTrace addSignals(PositionTrace trace, SignalStrengthSamples signals){
		for (int i=0;i<aPoints.length;i++){
			if(signals.containsKey(aPoints[i])){
				if(trace.getSignals()[i]==0.0)
					trace.setSignal(signals.getAverageSignalStrength(aPoints[i]),i);
				else
					trace.setSignal((trace.getSignals()[i]+signals.getAverageSignalStrength(aPoints[i]))/2, i);
			}
		}
		return trace;
	}
	
	
}
