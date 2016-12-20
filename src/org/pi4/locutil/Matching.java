package org.pi4.locutil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class Matching {
	
	List<PositionTrace> offlineTraces;
	List<PositionTrace> onlineTraces;
	
	
	
	
	public Matching(List<PositionTrace> offlineTraces,List<PositionTrace> onlineTraces){
		this.onlineTraces = onlineTraces;
		this.offlineTraces = offlineTraces;
	}
	
	public GeoPosition[] getNearest(){
		GeoPosition position[] = new GeoPosition[onlineTraces.size()];
		double distance = 1000000.0;
		
		for(PositionTrace on: onlineTraces){
			for(PositionTrace off: offlineTraces){
				double d =getDistance(off.getSignals(),on.getSignals());
				if(distance > d){
					distance = d;
					position[onlineTraces.indexOf(on)] = off.getPosition();
				}
			}
			distance = 1000000.0;
		}
		return position;
	}
	
	
	
	public void schreibeDatei(String dateiname, GeoPosition[] berechnet) throws IOException{
		//GeoPosition[] berechnet = getNearest();
		FileWriter fw = new FileWriter(dateiname);
	    BufferedWriter bw = new BufferedWriter(fw);
	    for(PositionTrace on: onlineTraces){
			bw.write(on.getPosition().toString() +" "+berechnet[onlineTraces.indexOf(on)].toString()+"\n");
		}
		bw.close();
		System.out.println("Datei " + "\"" + dateiname + "\" erfolgreich erstellt!");
	}
	
	
	
	public double getDistance(double[] signalOf, double[] signalOn){
		double a=0;
		
		for(int i=0;i<11;i++){
			if(signalOf[i] !=0 && signalOn[i]!=0)
				a +=Math.pow(signalOn[i]-signalOf[i], 2);
				
		}
		return Math.sqrt(a);
	}
	
	

	
	
	@SuppressWarnings("null")
	public GeoPosition getKNearest(int k){
		//GeoPosition position = null;
		double distance[] = null;
		HashMap<Double,GeoPosition>  map= new HashMap<Double,GeoPosition>();
		List<GeoPosition> list= null;
		
		
		for(PositionTrace on: onlineTraces){
			for(PositionTrace off: offlineTraces){
				distance[offlineTraces.indexOf(off)] = getDistance(off.getSignals(),on.getSignals());
				map.put(distance[offlineTraces.indexOf(off)], off.getPosition());
			}
			distance = Statistics.sort(distance);
		}
		for(int i=0;i<k;i++){
			list.add(map.get(distance[i]));
		}
		//position = Statistics.avg(list,1);
		return Statistics.avg(list,1);
	}
	

}
