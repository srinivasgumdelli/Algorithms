/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mergesort;

import java.util.Random;

/**
 *
 * @author gumdelli
 */
// This program is to check the time taken for serial Merge Sort 
class Mergesort {
	private int[] numbers;

	private int number;

	public void sort(int[] values) {
		this.numbers = values;
		number = values.length;

		mergesort(0, number - 1);
	}

	private void mergesort(int low, int high) {
		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = (low + high) / 2;
			// Sort the left side of the array
			mergesort(low, middle);
			// Sort the right side of the array
			mergesort(middle + 1, high);
			// Combine them both
			merge(low, middle, high);
		}
	}

	private void merge(int low, int middle, int high) {

		// Helperarray
		int[] helper = new int[number];

		// Copy both parts into the helper array
		for (int i = low; i <= high; i++) {
			helper[i] = numbers[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;
		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) {
			if (helper[i] <= helper[j]) {
				numbers[k] = helper[i];
			} else {
				numbers[k] = helper[j];
			}
			k++;
			i++;
		}
		// Copy the rest of the left side of the array into the target array
		while (i <= middle) {
			numbers[k] = helper[i];
			k++;
			i++;
		}
		helper = null;

	}
}

public class Mergesortalg {
     public static int maxsize = 100000000;
    public static int arr[]=new int[maxsize];
    public static Random rand;
    public static final int seed = 100000000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
//    for(int j=0;j<arr.length;j++)
//        System.out.print(arr[j]+" ");
        Mergesort q= new Mergesort();
        long st = System.nanoTime();
        q.sort(arr);
        long et = System.nanoTime();
        System.out.println();
//        for(int j=0;j<arr.length;j++)
//        System.out.print(arr[j]+" ");
        long at = et-st;
        System.out.println();
        System.out.println("Time : "+at);


    }

}
