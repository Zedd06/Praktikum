package org.pi4.locutil;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class ErrorRate {	
	//Werte aus Datei lesen
	LinkedList<String> result = new LinkedList<String>();
	ArrayList <Double> list = new ArrayList<Double>();
	
	public void readFile() throws IOException{
		int start=0, end=0;
		String s = null, tmp = null;
		
		BufferedReader br = new BufferedReader(new FileReader("output.txt"));
		String[] real = new String[3];
		String[] estimated = new String[3];
	

		while((s = br.readLine()) != null){
			start = s.indexOf("(");
			end = s.indexOf(")");
			tmp = s.substring(start+1, end-5);
			real  = format(tmp);
			
			start = end;
			end = s.length();
			tmp = s.substring(start+3, end-5);
			estimated = format(tmp);
						
			list.add(calcErrorRate(real, estimated));	
		}
		double[] arr = new double[list.size()];
		for(int i=0;i<list.size();i++){arr[i]=list.get(i);}
		arr=insertionSort(arr);
		for(int i=0;i<list.size();i++){result.add(arr[i]+"\t");}
		br.close();
	}
	
	//Fehlerquoute berechnen
	public double calcErrorRate(String[] a, String[] b) {
		double x = 0;
		for(int i=0; i<a.length; i++) {
			x += Math.pow(Double.parseDouble(a[i])-Double.parseDouble(b[i]),2);
		}
		x = Math.sqrt(x);
		return Math.round(x*100.0)/100.0;
	} 
	
	
	//Formatierung
	public String[] format(String a){
		String[] help = new String[3];
		int i = 0;
		for(String value : a.split(",")){
			value = value.trim();
			help[i] = value;
			i++;
		}
		return help;
	}
	
	
	//Insertionsort Algorithmus zum sortieren der Werte in einer Datei
	public static double[] insertionSort(double[] sorted){
		double temp;
		for(int i = 1; i < sorted.length; i++){
			temp = sorted[i];
			int j = i;
			while(j > 0 && sorted[j-1] >temp){
				sorted[j] = sorted[j-1];
				j--;
			}
			sorted[j] = temp;
		}
		return sorted;
	}
	
	
	//Verarbeitete Werte in neue Datei schreiben
	public void writeFile(String datei) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(datei));
		for(int i=0; i<result.size();i++) {
			bw.write(result.get(i));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		System.out.println("Datei " + "\"" + datei + "\" erfolgreich erstellt!");
	}
}