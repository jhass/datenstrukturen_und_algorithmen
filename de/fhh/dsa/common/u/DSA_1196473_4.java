 package de.fhh.dsa.common.u;

import java.util.Arrays;

public class DSA_1196473_4 {
	private static long start;
	
	public static void main(String[] args) {
		
		/********** comment out one or more **********************************/
		//testAlgorithmsA();
		System.out.println();
		//testStraightInsertion();
		System.out.println();
		//testFindMinMax();
		System.out.println();
		//testMergeSortVSStraightInsertion();
	}
	
	/***************** Aufgabe 8) d) ******************************************/
	private static void testAlgorithmsA() {
		int[] lengths = {10, 100, 200, 3000, 10000, 50000};
		int[] a, b;
		for (int n : lengths) {
			a = getRandomArray(n);
			b = Arrays.copyOf(a, a.length);
			
			startBenchmark();
			bubbleSort(a);
			stopBenchmark("bubbleSort n="+n);
			
			startBenchmark();
			straightSelection(b);
			stopBenchmark("straightSelection n="+n);
		}
	}

	/****************** Aufgabe 8) a) und b) ********************************/ 
	public static int[] bubbleSort(int[] a) {
		int tmp;
		boolean switched;
		for (int j=1; j <= a.length; j++) {
			switched = false;
			for (int i=0; i<a.length-j; i++) {
				if (a[i] > a[i+1]) {
					tmp = a[i];
					a[i] = a[i+1];
					a[i+1] = tmp;
					switched = true;
				}
			}
			if (!switched) {
				break;
			}
		}
		return a;
	}
	
	/******************* Aufgabe 8) c) ****************************************/
	public static int[] straightSelection(int[] a) {
		int pos_min,help;
		for(int i = 0; i < a.length; i++) {
			pos_min = i;
			for(int j = i+1; j < a.length; j++) {
				if(a[j] < a[pos_min]) {
					pos_min = j;
				}
			}
			help = a[pos_min];
			a[pos_min] = a[i];
			a[i] = help;
		}
		return a;
	}
	
	/********************* Aufgabe 9) b) und d) ******************************/
	public static void testStraightInsertion() {
		int[] a = {17,35,62,43,11,9,22,57};
		System.out.println("a = "+Arrays.toString(a));
		straightInsertion(a);
		System.out.println("straightInsertion(a):"+Arrays.toString(a));
	}
	
	public static int[] straightInsertion(int[] a) {
		int j, tmp;
		for (int i=0; i<a.length; i++) {
			tmp = a[i];
			j = searchInsertPoint(a, tmp, 0, i-1);
			for (int k=i;  k > j; k--) {
				a[k] = a[k-1];
			}
			a[j] = tmp;
		}
		return a;
	}
	
	public static int searchInsertPoint(int[] a, int k, int l, int h) {
		if (l > h) {
			return l;
		}
		
		int m = (l+h)/2;
		
		if (k < a[m]) {
			return searchInsertPoint(a, k, l, m-1);
		} else {
			return searchInsertPoint(a, k, m+1, h);
		}
	}
	
	
	/****************** Aufgabe 9) c) **************************************/
	public static int search(int[] a, int k) {
		return search(a, k, a.length/2);
	}
	
	public static int search(int[] a, int k, int m) {
		if (k == a[m]) {
			return m;
		} else if (m == a.length-1 || m == 0) {
			return -1;
		}
		
		if (k < a[m]) {
			return search(a, k, m/2);
		} else {
			return search(a, k, m+((a.length-m)/2));
		}
	}
	
	
	/**************** Aufgabe 12) d) *****************************/
	public static void testFindMinMax() {
		int[] a = {17,35,62,43,11,9,22,57};
		System.out.println("a = "+Arrays.toString(a));
		System.out.println("[min, max]:"+Arrays.toString(findMinMaxRecursive(a)));
	}
	
	public static int[] findMinMaxRecursive(int[] a) {
		return findMinMaxRecursive(a, 0, a.length-1);
	}
	
	public static int[] findMinMaxRecursive(int[] a, int l, int h) {
		int min, max;
		if (h-l == 0) {
			min = max = a[l];
		} else if (h-l <= 1) {
			if (a[l] > a[h]) {
				min = a[h];
				max = a[l];
			} else {
				min = a[l];
				max = a[h];
			}
		} else {
			int mid = (l+h)/2;
			int[] left = findMinMaxRecursive(a, l, mid);
			int[] right = findMinMaxRecursive(a, mid+1, h);
			min = left[0] < right[0] ? left[0] : right[0];
			max = left[1] > right[1] ? left[1] : right[1];
		}
		int[] ret = {min, max};
		return ret;
	}
	
	
	/****************** Aufgabe 14 a) ***************************************/
	
	public static int[] mergeSort(int[] a) {
		return mergeSort(a, 0, a.length-1);
	}
	
	public static int[] mergeSort(int[] a, int l, int h) {
		if (l >= h) {
			return a;
		}
		
		int m = (l+h)/2;
		a = mergeSort(a, l, m);
		a = mergeSort(a, m+1, h);
		
		return merge(a, l,m,h);
	}
	
	private static int[] merge(int[] a, int l, int m, int h) {
		int i = 0;
		int j = l;
		int[] b = new int[m+1];
		while (j <= m) {
			b[i] = a[j];
			i++;
			j++;
		}
		
		i=0;
		int k = l;
		while (k<j && j<=h) {
			if (b[i]<a[j]) {
				a[k] = b[i];
				i++;
			} else {
				a[k] = a[j];
				j++;
			}
			k++;
		}
		
		while (k<j) {
			a[k] = b[i];
			i++;
			k++;
		}
		
		return a;
	}
	
	/************************** Aufgabe 14) b) *******************************/
	private static void testMergeSortVSStraightInsertion() {
		int[] lengths = {50000, 100000, 200000};
		int[] a, b;
		for (int n : lengths) {
			a = getRandomArray(n);
			b = Arrays.copyOf(a, a.length);
			
			startBenchmark();
			straightInsertion(a);
			stopBenchmark("straightInsertion n="+n);
			
			startBenchmark();
			mergeSort(b);
			stopBenchmark("mergeSort n="+n);
		}
	}
	
	
	/*************************** Auxiliary ***********************************/
	
	private static int[] getRandomArray(int n) {
		int[] a = new int[n];
		for (int i=0; i<n; i++) {
			a[i] = (int) (Math.random()*1000);
		}
		return a;
	}
	
	private static void startBenchmark() {
		start = System.currentTimeMillis();
	}
	
	private static void stopBenchmark(String prefix) {
		long stop = System.currentTimeMillis();
		System.out.format("%s: %dms\n", prefix, stop-start);
	}
}