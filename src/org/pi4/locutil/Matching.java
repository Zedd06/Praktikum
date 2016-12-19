package org.pi4.locutil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matching {
	
	List<PositionTrace> offlineTraces;
	List<PositionTrace> onlineTraces;
	
	
	
	
	public Matching(List<PositionTrace> offlineTraces,List<PositionTrace> onlineTraces){
		this.onlineTraces = onlineTraces;
		this.offlineTraces = offlineTraces;
	}
	
	public GeoPosition getNearest(){
		GeoPosition position = null;
		double distance = 1000000.0;
		
		for(PositionTrace on: onlineTraces){
			for(PositionTrace off: offlineTraces){
				double d =getDistance(off.getPosition(),on.getPosition());
				if(distance > d){
					distance = d;
					position = off.getPosition();
				}
			}
		}
		return position;
	}
	
	public double getDistance(GeoPosition off, GeoPosition on){
		return Math.sqrt(Math.pow(on.getX()-off.getX(), 2)+Math.pow(on.getY()-off.getY(), 2)+Math.pow(on.getZ()-off.getZ(), 2));
	}
	
	

	
	
	@SuppressWarnings("null")
	public GeoPosition getKNearest(int k){
		//GeoPosition position = null;
		double distance[] = null;
		HashMap<Double,GeoPosition>  map= new HashMap<Double,GeoPosition>();
		List<GeoPosition> list= null;
		
		
		for(PositionTrace on: onlineTraces){
			for(PositionTrace off: offlineTraces){
				distance[offlineTraces.indexOf(off)] = getDistance(off.getPosition(),on.getPosition());
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
