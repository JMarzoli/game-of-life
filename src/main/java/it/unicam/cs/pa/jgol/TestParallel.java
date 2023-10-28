package it.unicam.cs.pa.jgol;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TestParallel {

    public static boolean isPrime(int n) {
        if (n<=1) {
            throw new IllegalArgumentException();
        }
        int maxTest = (int) (Math.sqrt(n)+1);
        //\forall i\in [2,sqrt(n)]. n%i!=0.
        return IntStream.range(2,maxTest)
                .filter(i -> (n % i)==0).findAny().isEmpty();
    }


    public static void main(String[] argv) {
        int from = 9000000;
        int to = 10000000;
        long start = System.currentTimeMillis();
        int[] primes = getPrimesSequential(from,to);
        System.out.println("Time: "+(System.currentTimeMillis()-start));
        //System.out.println(Arrays.toString(primes));
    }

    private static int[] getPrimesSequential(int from, int to) {
        return IntStream.range(from, to).sequential().filter(TestParallel::isPrime).toArray();
    }

}
