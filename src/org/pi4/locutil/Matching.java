package org.pi4.locutil;

import java.util.HashMap;
import java.util.Map;

public class Matching {
	
	Map<int[], double[]> offlineTraces = new HashMap<int[], double[]>();
	Map<int[], double[]> onlineTraces = new HashMap<int[], double[]>();
	
	public Matching(Map<int[], double[]> offlineTraces,Map<int[], double[]> onlineTraces){
		this.onlineTraces = onlineTraces;
		this.offlineTraces = offlineTraces;
	}
	
	public GeoPosition getNearest(){
		for(Map.Entry<int[], double[]> e : offlineTraces.entrySet()){
			  int[] position = e.getKey();
			  double[] signal = e.getValue();
			  
			  for()
			  
			}
		return position
	}
	

	public GeoPosition getKNearest(int k){
		
	}
	

}
