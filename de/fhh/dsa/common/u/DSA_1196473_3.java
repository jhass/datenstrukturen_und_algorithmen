package de.fhh.dsa.common.u;

import java.util.Arrays;

public class DSA_1196473_3 {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(listOfPrimeNumbers(80)));
	}
	
	public static long binomialStupid(long n, long k) {
		return factorial(n)/(factorial(n-k)*factorial(k));
	}
	
	public static long factorial(long n) {
		long product = 1;
		for (int i=2; i<=n; i++) {
			product = product*i;
		}
		return product;
	}
	
	public static long[] listOfPrimeNumbers(long m) {
		long[] numbers = getRangeOfOddNumbers(2, (int) m);
		int[] primeMarker = new int[numbers.length];
		Arrays.fill(primeMarker, 2);
		long square;
		int index;
		for (int i=0; i<numbers.length; i++) {
			if (primeMarker[i] == 2) {
				primeMarker[i] = 1;
				square = (long) Math.pow(numbers[i], 2);
				index = Arrays.binarySearch(numbers, square);
				if (index > 0) {
					primeMarker[index] = 0;
				} else {
					index = i;
				}
				for (int y=index+1; y<numbers.length; y++) {
					if (numbers[y] % numbers[i] == 0) {
						primeMarker[y] = 0;
					}
				}
			}
		}
		
		return filterOutPrimes(numbers, primeMarker, m);
	}
	
	private static long[] getRangeOfOddNumbers(int start, int end) {
		long[] numbers = new long[(int) Math.ceil((end/2.0))-1];
		int number;
		int y = 0;
		for (int i=0; i < end-1; i++) {
			number = (start+i);
			if ( number % 2 != 0) {
				numbers[y] = number;
				y++;
			}
		}
		return numbers;
	}
	
	private static long[] filterOutPrimes(long[] numbers, int[] primeMarker, long m) {
		int primeArrayLength = getPrimeArrayLength(m);
		long[] primes = new long[primeArrayLength];
		primes[0] = 2;
		int y=1;
		for (int i=0; i<numbers.length; i++) {
			if (primeMarker[i] == 1) {
				primes[y] = numbers[i];
				y++;
			}
		}
		
		return primes;
	}
	
	private static int getPrimeArrayLength(long m) {
		return (int) (m/(Math.log(m)-1.08366)-1);
	}
	
	
	
	public static long BinomialClever(long n, long k) {
		long[] primes = listOfPrimeNumbers(n);
		
		return 0;
	}
	
	private static int[] primeExponentsList(long[] primes, long n) {
		int[] exps = new int[primes.length];
		int i = 0;
		for (long prime : primes) {
			exps[i] = primeExponent(n, prime);
		}
		return exps;
	}
	
	
	private static int primeExponent(long n, long p) {
		double fract;
		int exp = 0;
		
		for (int i=1; i<=n; i++) {
			fract = Math.floor(n/Math.pow(p, i));
			if (fract < 1) {
				break;
			}
			exp += fract;
		}
		return exp;
	}
}
