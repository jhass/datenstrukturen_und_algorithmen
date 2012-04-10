package de.fhh.dsa.common.u;



/** Teilsummen berechnen
 * 	
 * @author Jonne Haß <jonne.hass@stud.fh-hannover.de> (1196473)
 * @author Richard Pump <richard.pump@stud.fh-hannover.de> (1195429)
 *
 */
public class DSA_1196473_1 {
	public static void main(String[] args) {
		int[] data = {31,-41,59,26,-53,58,97,-93,-23,84};
		
		long start = System.currentTimeMillis();
		System.out.println("Algorithmus 1 (vorgegebene Daten):");
		System.out.println("Ergebnis: "+findeMaxTeilsumme1(data));
		long stop = System.currentTimeMillis();
		System.out.println("Zeit: "+(stop-start)/1000.0+"s");
		System.out.println();
		
		
		data = getRandomArray(1000);
		System.out.println("Algorithmus 1 (n=1000):");
		start = System.currentTimeMillis();
		System.out.println("Ergebnis: "+findeMaxTeilsumme1(data));
		stop = System.currentTimeMillis();
		System.out.println("Zeit: "+(stop-start)/1000.0+"s");
		System.out.println();
		
		data = getRandomArray(10000);
		System.out.println("Algorithmus 2 (n=10000):");
		start = System.currentTimeMillis();
		System.out.println("Ergebnis: "+findeMaxTeilsumme2(data));
		stop = System.currentTimeMillis();
		System.out.println("Zeit: "+(stop-start)/1000.0+"s");
		System.out.println();
		
		data = getRandomArray(10000);
		System.out.println("Algorithmus 3 (n=10000):");
		start = System.currentTimeMillis();
		System.out.println("Ergebnis: "+findeMaxTeilsumme3(data));
		stop = System.currentTimeMillis();
		System.out.println("Zeit: "+(stop-start)/1000.0+"s");
		System.out.println();
	}
	
	public static int findeMaxTeilsumme1(int[] array) {
		int maxsum= Integer.MIN_VALUE;
		
		for (int i=0; i< array.length; i++) {
			for (int j=i; j< array.length; j++) {
				int tmpsum= 0;
				for (int k=i; k<=j; k++) {
					tmpsum += array[k];
				}
				if (tmpsum > maxsum) {
					maxsum = tmpsum;
				}
			}
		}
		return maxsum;
	}
	
	
	public static int findeMaxTeilsumme2(int[] array) {
		int maxsum = Integer.MIN_VALUE;
		
		for (int i=0; i< array.length; i++) {
			int tmpsum = 0;
			for (int j=i; j< array.length; j++) {
				tmpsum += array[j];
				if (tmpsum > maxsum) {
					maxsum = tmpsum;
				}
			}
		}
		return maxsum;
	}
	
	public static int findeMaxTeilsumme3(int[] array) {
		return findeMaxTeilsumme3(array, 0, array.length-1);
	}
	
	public static int findeMaxTeilsumme3(int[] array, int links, int rechts) {
		if (links == rechts) {
			return Math.max(0, array[links]);
		}
		
		int liRandMax = findeLiRandMax(array, links, rechts);
		int reRandMax = findeReRandMax(array, links, rechts);
		int mittelMax = findeMittelMax(array, links, rechts);
		
		int max =  Math.max(liRandMax, Math.max(reRandMax, mittelMax));
		int liMax = findeMaxTeilsumme3(array, links, links+(rechts-links)/2);
		int reMax = findeMaxTeilsumme3(array, links+(rechts-links)/2+1, rechts);
		
		return Math.max(max, Math.max(liMax, reMax));
	}
	
	public static int findeLiRandMax(int[] array, int links, int rechts){
		int max = array[links];
		int sum = max;
		
		for (int i = links+1; i <= rechts; i++) {
			sum += array[i];
			if (sum > max) max = sum; 
		}
		
		return max;
	}
	
	public static int findeReRandMax(int[] array, int links, int rechts) {
		int max = array[rechts];
		int sum = max;
		
		for (int i = rechts-1; i >= links; i--){
			sum += array[i];
			if (sum > max) max = sum; 
		}

		return max;
	}
	
	public static int findeMittelMax(int[] array, int links, int rechts){
		return findeReRandMax(array, links, links+(rechts-links)/2) + findeLiRandMax(array, links+(rechts-links)/2+1, rechts);
	}
	
	public static int[] getRandomArray(int n) {
		int[] a = new int[n];
		for (int i=0; i<n; i++) {
			a[i] = (int) (Math.random()*2000)-1000;
		}
		return a;
	}
}
