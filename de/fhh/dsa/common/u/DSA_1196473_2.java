package de.fhh.dsa.common.u;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** Fibonacci, Fakultät, Logarithmus
*
* @author Jonne Haß <jonne.hass@stud.fh-hannover.de> (1196473)
* @author Richard Pump <richard.pump@stud.fh-hannover.de> (1195429)
*
*/
public class DSA_1196473_2 {
	private static long benchmarkStart;
	
	public static void main(String[] args) {
		
		// Aufgabe 3a)
		System.out.println("Fibonacci:");
		System.out.println();
		System.out.print("Preloading...");
		iterativeFibonacci(100000);
		recursiveFibonacci(30);
		System.out.println("done.\n");
		
		startBenchmark("Iterativ, n=4000");
		finishBenchmark(iterativeFibonacci(4000));
		
		startBenchmark("Iterativ, n=6000");
		finishBenchmark(iterativeFibonacci(6000));
		
		startBenchmark("Iterativ, n=10000");
		finishBenchmark(iterativeFibonacci(10000));
		
		
		startBenchmark("Rekursiv, n=20");
		finishBenchmark(recursiveFibonacci(20));
		
		startBenchmark("Rekursiv, n=25");
		finishBenchmark(recursiveFibonacci(25));
		
		startBenchmark("Rekursiv, n=30");
		finishBenchmark(recursiveFibonacci(30));
		
		
		// Aufgabe 3b)
		System.out.println();
		System.out.println();
		System.out.println("Fakultät:");
		System.out.println();
		
		System.out.print("Preloading...");
		iterativeFactorial(10000);
		recursiveFactorial(3000);
		System.out.println("done.\n");
		
		startBenchmark("Iterativ, n=1000");
		finishBenchmark(iterativeFactorial(1000));
		
		startBenchmark("Iterativ, n=2500");
		finishBenchmark(iterativeFactorial(2500));
		
		startBenchmark("Iterativ, n=5000");
		finishBenchmark(iterativeFactorial(5000));
		
		startBenchmark("Rekursiv, n=1000");
		finishBenchmark(recursiveFactorial(1000));
		
		startBenchmark("Rekursiv, n=2500");
		finishBenchmark(recursiveFactorial(2500));
		
		startBenchmark("Rekursiv, n=5000");
		finishBenchmark(recursiveFactorial(5000));
		
		
		// Aufgabe 3e)
		System.out.println();
		System.out.println();
		System.out.println("natürlicher Logarithmus:");
		int[] precisions =  {50, 75, 100};
		for (int precision : precisions) {
			System.out.println("  Genauigkeit: "+precision);
			for (int x=1; x<=10; x++) {
				System.out.println("    ln("+x+") = "+ln(x, precision));
			}
		}
	}
	
	public static BigInteger iterativeFibonacci(int n) {
		if (n < 2) {
			return BigInteger.valueOf(n);
		}
		BigInteger sum = BigInteger.valueOf(0);
		BigInteger n_1_sum = BigInteger.valueOf(1);
		BigInteger n_2_sum = BigInteger.valueOf(0);
		for (int i=2; i<=n; i++) {
			sum = (n_1_sum.add(n_2_sum));
			n_2_sum = n_1_sum;
			n_1_sum = sum;
		}
		
		return sum;
	}

	public static BigInteger recursiveFibonacci(int n) {
		if (n < 2) {
			return BigInteger.valueOf(n);
		}

		return recursiveFibonacci(n-1).add(recursiveFibonacci(n-2));
	}
	
	
	public static BigInteger iterativeFactorial(int n) {
		BigInteger product = BigInteger.valueOf(1);
		for (int i=2; i<=n; i++) {
			product = product.multiply(BigInteger.valueOf(i));
		}
		return product;
	}
	
	
	public static BigInteger recursiveFactorial(int n) {
		if (n < 2) {
			return BigInteger.valueOf(n);
		}
		return recursiveFactorial(n-1).multiply(BigInteger.valueOf(n));
	}
	
	public static BigDecimal ln(double x, int precision) throws IllegalArgumentException {
		if (!(x > 0)) {
			throw new IllegalArgumentException("x must be greater than 0");
		}
		
		BigDecimal result = BigDecimal.valueOf(0);
		int exponent;
		double numerator;
		double denominator;
		double numeratorBase = x-1;
		double denominatorBase = x+1;
		for (int k=0; k <= precision; k++) {
			exponent = 2*k+1;
			numerator = Math.pow(numeratorBase, exponent);
			denominator = Math.pow(denominatorBase, exponent);
			result = result.add(BigDecimal.valueOf(numerator/(exponent*denominator)));
		}
		return result.multiply(BigDecimal.valueOf(2));
	}
	
	private static void startBenchmark(String info) {
		System.out.println(info);
		benchmarkStart = System.currentTimeMillis();
		
	}
	
	private static void finishBenchmark(BigInteger result) {
		long stop = System.currentTimeMillis();
		System.out.println("Ergebnis: "+result);
		System.out.println("Zeit: "+(stop-benchmarkStart)+"ms");
		System.out.println();
	}
}
