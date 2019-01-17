/**
 * Some ways to solve prime numbers
 *
 * @author Blake Mitrick
 * @version 1/16/19
 */
import java.util.Arrays;
import java.util.List;
import java.lang.Math;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Primes
{
    /** 
     * Prints  a 2D array with all non primes as a 0 and all primes as themselves, solving for these primes using the sieve of eratosthenes method. Very inefficient, just for the visual benefit
     * 
     * @param given the hight and width of the 2D array of numbers to be used as the sieve base
     */
    public static void twoDimensionalSieve(int given){
        double start = System.currentTimeMillis();
        ArrayList<Integer> listOfPrimes = new ArrayList<Integer>();
        int h = given;
        
        int[][] sieve = new int[h][h];
        
        for(int row = 0; row < sieve.length; row++){
            for(int column = 0; column < sieve[0].length; column++){
                sieve[row][column] = (row*h)+column + 1;
            }
        }
        
        for(int i =  0; i < h; i ++){
            for(int j = 0; j < h; j++){
                int worker = sieve[i][j];
                
                if (worker > 1 && worker <= Math.sqrt(h*h)){
                    for(int row = 0; row < sieve.length; row++){
                        for(int column = 0; column < sieve[0].length; column++){
                            if(sieve[row][column] % worker == 0 && sieve[row][column]!= worker){
                                sieve[row][column] = 0;
                            }
                        }
                    }
                }else if (worker > Math.sqrt(h*h)) break;
            }
        }
        
        for(int[] i: sieve){
            for(int j: i){
                if(j != 0) listOfPrimes.add(j);
            }
        }
        
        //System.out.println(listOfPrimes.size() -1);
        System.out.println("Calculating all the primes between 1 and " + (h*h) + " with an 2D Sieve of Eratosthenes took " + (System.currentTimeMillis() - start) + " ms");
        //System.out.println(listOfPrimes.toString());
    }
    
    /**
     * Prints a list of all the primes from 2 to the given variable. More efficient than the twoDimensionalSieve function but not completely optimized
     * 
     * @param given the last number to be checked for primality
     */
    public static void lineSieve(int given){
        double start = System.currentTimeMillis();
        ArrayList<Integer> listOfPrimes = new ArrayList<Integer>();
        int max = given;
        
        int[] allNums = IntStream.rangeClosed(2, max).toArray();
        
        for(int i = 0; i < allNums.length &&  (i+2) < Math.sqrt(max); i++){
            int worker = allNums[i];
            
            if (worker != 0){
                for(int j = i+1; j < allNums.length; j++){
                    if(allNums[j]% worker == 0) allNums[j] = 0;
                }
            }
        }
        
        for(int i: allNums){
            if(i != 0) listOfPrimes.add(i);
        }
        
        //System.out.println(listOfPrimes.size());
        System.out.println("Calculating all the primes between 1 and " + max + " with an Line Sieve of Eratosthenes took " + (System.currentTimeMillis() - start) + " ms");
        //System.out.println(listOfPrimes.toString());
    }
    
    /**
     * Prints a list of all the primes from 2 to the given variable. More efficient than the lineSieve, most optimized sieve I've coded so far. Based on directions I found online. Still not yet able to handle a one to a billion
     * 
     * @param given the last number to be checked for primality
     */
    public static void atkinSieve(int given){
        double start = System.currentTimeMillis();
        
        ArrayList<Integer> listOfPrimes = new ArrayList<Integer>();
        int max = given;
        
        boolean[] allNums = new boolean[max];
        
        for(int i = 1; i*i < max; i++){
            for(int j = 1; j*j < max; j++){
                int jSquare= j*j;
                int iSquare= i*i;
                
                int worker = (4*iSquare) + (jSquare);
                if((worker % 12 == 1 || worker % 12 == 5) && worker <= max) allNums[worker] ^= true;
                  
                worker = (3*iSquare) + (jSquare);
                if((worker % 12 == 7) && worker <= max) allNums[worker] ^= true;
                
                worker = (3*iSquare) - (jSquare);
                if(i > j && (worker % 12 == 11) && worker <= max) allNums[worker] ^= true;
            }
        }
        
        for (int i = 5; i * i < max; i++) { 
            if (allNums[i]) { 
                int timeSaver = i*i;
                for (int j = timeSaver; j < max; j += timeSaver) 
                    allNums[j] = false; 
            } 
        } 
        
        listOfPrimes.add(2);
        listOfPrimes.add(3);
        
        for(int i = 0; i < max; i++){
            if (allNums[i]) { 
                listOfPrimes.add(i);
            } 
        }

        System.out.println(listOfPrimes.size());
        System.out.println(listOfPrimes.toString());
        System.out.println("Calculating all the primes between 1 and " + max + " with an Atkin Sieve took " + (System.currentTimeMillis() - start) + " ms");
    }
    
    /**
     * Prints a list of all the primes from 2 to the given variable. More efficient than the lineSieve, 2nd most optimized sieve I've coded so far. Based on directions I found online. Still not yet able to handle a one to a billion
     * 
     * @param given the last number to be checked for primality
     */
    public static void atkinSieveSlow(int given){
        double start = System.currentTimeMillis();
        
        ArrayList<Integer> listOfPrimes = new ArrayList<Integer>();
        int max = given;
        
        boolean[] allNums = new boolean[max];
        
        for(int i = 1; i*i < max; i++){
            for(int j = 1; j*j < max; j++){
                int worker = (4*i*i) + (j*j);
                
                if((worker % 12 == 1 || worker % 12 == 5) && worker <= max) allNums[worker] ^= true;
                  
                worker = (3*i*i) + (j*j);
                if((worker % 12 == 7) && worker <= max) allNums[worker] ^= true;
                
                worker = (3*i*i) - (j*j);
                if(i > j && (worker % 12 == 11) && worker <= max) allNums[worker] ^= true;
            }
        }
        
        for (int i = 5; i * i < max; i++) { 
            if (allNums[i]) { 
                for (int j = i*i; j < max; j += i*i) 
                    allNums[j] = false; 
            } 
        } 
        
        listOfPrimes.add(2);
        listOfPrimes.add(3);
        
        for(int i = 0; i < max; i++){
            if (allNums[i]) { 
                listOfPrimes.add(i);
            } 
        }

        System.out.println(listOfPrimes.size());
        System.out.println(listOfPrimes.toString());
        System.out.println("Calculating all the primes between 1 and " + max + " with an Atkin Sieve slow took " + (System.currentTimeMillis() - start) + " ms");
    }
    
    public static void main(String[] args){
        //twoDimensionalSieve(1000);
        //lineSieve(1000000);
        //atkinSieveSlow(100000000);
        atkinSieve(10000000);
    }
}
