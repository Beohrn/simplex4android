package com.googlecode.simplex4android;

import java.util.ArrayList;

public class SimplexProblemDual extends SimplexProblem {

	private ArrayList<Double> deltaByF; //Zeile unter dem Tableau f�r den dualen Simplex
	
	/**
	 * Standardkonstruktor f�r ein leeres SimplexProblem zum anschlie�enden Hinzuf�gen der Zielfunktion und Nebenbedingungen.
	 * Die Zeile der delta-Werte ist bereits enthalten.
	 */
	public SimplexProblemDual(){
		super();
		this.deltaByF = new ArrayList<Double>();
	}
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verf�gung.
	 * @param tableau
	 * @param target
	 */
	public SimplexProblemDual(double[][] tableau, int[] target){ 
		super(tableau, target);
		SimplexLogic.findPivots(this);
	    SimplexLogic.calcDeltas(this);
	    SimplexLogic.calcDeltaByF(this);
	}
	
	/**
	 * Gibt ein Array mit den x/f-Werten f�r jede Zeile zur�ck.
	 * @return Array mit den x/f-Werten f�r jede Zeile
	 */
	public double[] getDeltaByF() {
		return this.convertToDblArray(this.deltaByF);
	}
	
	/**
	 * �berschreibt die delta/f-Werte.
	 * @param deltaByF neue delta/f-Werte
	 */
	public void setDeltaByF(double[] deltaByF) {
		this.deltaByF = this.convertToDblArrayList(deltaByF);
	}
	
	/**Methode die das komplette Simplex-Tableau f�r den dualen Simplex als HTML-String zur�ckgibt.
	 * 
	 * @return komplettes Tableau als Duales Problem als String in Html 
	 */
	public String tableauToHtml(){
		String html = "\n<html>\n<body>\n<table border=1 CELLSPACING=0>\n";
		//1. Zeile: Zielfunktion
		html = html + "<tr>\n<td></td><td></td>";		// direkt inkl. zwei leeren Eintr�gen 
		for(int i=0;i<target.size()-1;i++){
			html = html + "<td>" + (Math.round(target.get(i)*100.)/100.) + "</td>";
		}
		html = html + "<td></td></tr>\n";
		//2. Zeile: zwei Zeilen frei Durchnummerierung der Spalten + x +x/f
		html = html + "<tr><td></td><td></td>";		// direkt inkl. zwei leeren Eintr�gen
		for(int i=0;i<target.size()-1;i++){
			html = html +"<td>"+ (i+1) +"</td>";
		}
		html = html + "<td>x</td>";
		//ab der 3. Zeile: das eigentliche Tableau, die ersten beiden Spalten auch wie im Tableau + x/f
		for(int i=0;i<tableau.size()-1;i++){			//so oft ausf�hren wie es Zeilen-1 im Tableau gibt
			html = html + "<tr><td>"+ target.get(pivots.get(i))+"</td><td>" +(pivots.get(i)+1) +"</td>";
			for(int j=0;j<tableau.get(0).size();j++){
				html = html + "<td>" + (Math.round((tableau.get(i).get(j))*100.)/100.)+"</td>";
			}
			html = html + "</tr>\n";
		}
		// Letzte Zeile: extra behandlung f�r delta-Wert
		html = html + "<tr><td></td><td></td>"; //inkl. zwei leerfelder
		for(int i=0;i<tableau.get(0).size();i++){
			html = html + "<td>" + (Math.round((tableau.get(tableau.size()-1).get(i))*100.)/100.) +"</td>";
		}
		html = html + "</tr>\n";
		// allerletzte Zeile mit den delta/f-Werten
		html = html + "<tr><td></td><td></td>"; //inkl. zwei leerfelder
		for(int i=0;i<deltaByF.size();i++){
			if(deltaByF.get(i)!=-1)html = html + "<td>" + (Math.round(deltaByF.get(i)*100.)/100.) +"</td>";
			else html = html + "<td>&#8211;</td>";
		}
		html = html + "</tr>\n";
		html = html + "</table>\n</body>\n</html>";
		return html;
	}
}