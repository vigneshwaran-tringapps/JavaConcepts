package com.java.sampleprogram;

public class Manacher {

    private Manacher() {}

    /**
     * This function implements Manacher's algorithm that finds
     * the longest palindromic substring in a linear time
     * If there is no unique longest palindromic substring it returns the first one to occur
     * 
     * @param input
     * @return the longest palindromic substring in input
     */
    public static String getLongestPalindromicSubstring(String input) {
        if (input == null)
            return null;

        final int length = input.length();
        if (length == 0)
            return "";

        // arr represents input string in a way that will act the same for strings of even and uneven length
        // i.e. '#' is placed between each letter from input
        final char[] arr = new char[2 * length + 1];
        for (int i = length - 1; i >= 0; i--) {
            arr[2 * i + 1] = input.charAt(i);
            arr[2 * i] = '#';
        }
        arr[2 * length] = '#';

        final int arrLength = length * 2;

        // LPS[i] - palindrome span(radius) with center at arr[i]
        final int[] LPS = new int[arrLength + 1];
        int p = 0;
        for (int i = 1; i <= arrLength; i++) {
            LPS[i] = 0;
            if (LPS[p] + p >= i)            	
                LPS[i] = Math.min(LPS[2 * p - i], p + LPS[p] - i);
            while (i + LPS[i] + 1 <= arrLength && i - LPS[i] - 1 >= 0 && arr[i + LPS[i] + 1] == arr[i - LPS[i] - 1])
                LPS[i]++;
            if (p + LPS[p] < i + LPS[i])
                p = i;
        }

        // find the palindrome with the biggest span
        int valueMax = 0;
        int indexMax = 0;
        for (int i = 0; i < arrLength; i++) {
            if (valueMax < LPS[i]) {
                valueMax = LPS[i];
                indexMax = i;
            }
        }

        // reconstruct the palindrome given its index in LPS and span
        final int palindromeSpan = valueMax / 2;
        if (indexMax % 2 == 0) {
            return input.substring(indexMax/2  - palindromeSpan, indexMax/2 + palindromeSpan);
        } else {
            return input.substring(indexMax/2  - palindromeSpan, indexMax/2 + palindromeSpan + 1);
        }
    }
   
    public static void main(String args[]){
    	System.out.println(getLongestPalindromicSubstring("ammadamlam"));
    }
}