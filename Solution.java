import java.util.Scanner;


/**
 * 
 */
public class Solution {

	// **** ****
	final static int MAX_N		= 1000001;

	// **** ****
	static int[] cache 			= null;
	static int[] next			= null;
	
	static	int minFactor		= 3;
	
	/**
	 * initialize the cache for bottom up approach
	 */
	static void cacheInitBottomUp(int n) {
		
		// **** perform sanity check(s) ****
		if ((n < 0) || (n >= MAX_N)) {
			System.err.println("cacheInitBottomUp <<< unexpected n: " + n);
			return;
		}
		
		// **** allocate arrays (if needed) ****
		if (cache == null) {
			
			// **** allocate arrays ****
			cache 	= new int[MAX_N];
			next	= new int[MAX_N];
			
			// **** fill cache [0 : 2] ****
			cache[2] = 2;
			cache[1] = 1;
			cache[0] = 0;
			
			// **** fill next [0 : 2] ****
			next[2] = 1;
			next[1] = 0;
			next[0] = -1;
		}
		
		// **** ****
		int a		= 0;
		int b		= 0;
		int	max		= Integer.MIN_VALUE;
		int	min		= Integer.MAX_VALUE;
		int minVal	= Integer.MAX_VALUE;
		
		// **** fill cache and next arrays ****
		for (int i = minFactor; i <= n; i++) {

			// **** process each factor [3 : i] ****
			minVal 	= Integer.MAX_VALUE;
			for (int factor = 1; (factor * factor) <= i; factor++) {

				// **** if not a factor then skip ****
				if ((i % factor) != 0) {
					continue;
				}

				// **** ****
				a	= i / factor;
				b	= i / a;
				
				// **** ****
				if ((a != 1) && (b != 1)) {
					max		= Math.max(a, b);
					min		= 1 + cache[max];
				} else {
					min 	= 1 + cache[i - 1];
				}
								
				// **** update the minimum value (if needed) ****
				if (min < minVal) {
					minVal 	= min;
					if ((a != 1) && (b != 1)) {
						next[i] = max;
					} else {
						next[i] = i - 1;
					}
				}
			}

			// **** update the cache entry ****
			cache[i] = minVal;
		}
		
		// **** update minFactor (if needed) ****
		if (n > minFactor) {
			minFactor = n;
		}
	}


	/**
	 * look up n in the cache (if in range)
	 */
	static int	bottomUp(int n) {
		if ((n >= 0) && (n < MAX_N)) {
			return cache[n];
		} else {
			return -1;
		}
	}
	

	/**
	 * show the path from n to 0
	 */
	static void showPath(int n) {
		
		// **** ****
		if (n == 0) {
			System.out.print(n);
		} else {
			System.out.print(n + "->");
		}
		
		// **** ****
		while (n > 0) {
			System.out.print(next[n]);
			n = next[n];
			if (n > 0) {
				System.out.print("->");
			}
		}
		
		// **** ****
		System.out.print(" : ");
	}


	/**
	 * Function to complete.
	 */
	static int downToZero(int n) {
		cacheInitBottomUp(n);
		return bottomUp(n);
	}
	

	/**
	 * Test scaffolding.
	 */
	public static void main(String[] args) {

		// ????? ????
		long totalDuration	= 0;

		// **** open scanner ****
		Scanner sc			= new Scanner(System.in);

		// **** read number of queries ****
		int	Q 				= sc.nextInt();
		
		// **** loop once per query ****
		for (int q = 0; q < Q; q++) {

			// **** read the value ****
			int n			= sc.nextInt();
					
			// ???? start time????
			long start 		= System.currentTimeMillis();

			// **** function to complete ****
			int answer = downToZero(n);

			// ???? end time ????
			long end 		= System.currentTimeMillis();

			// ???? compute duration ????
			long duration	= end - start;
			System.out.println("main <<< duration: " + duration + " ms");
			totalDuration 	+= duration;
			
			// ???? display selected path ????
			showPath(n);
			
			// **** display answer ****
			System.out.println(answer);
		}
		
		// ???? display duration ????
		System.out.println("main <<< totalDuration: " + totalDuration + " ms");

		// **** close scanner ****
		sc.close();
	}

}
