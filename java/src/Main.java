import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public Main() 
	{
		int size 	= 100000;
		int max 	= 6;
		long startTime = System.nanoTime(); 
		Integer[] itms = generateRandomNumbers(size, max);
	
		for (int i = 0; i < itms.length; i ++)
		{
			System.out.print(itms[i] + ", ");
		}
		
		Integer[] unsortedRadix = itms.clone();
		Integer[] unsortedQuick = itms.clone();
		Integer[] unsortedMerge = itms.clone();

		long endTime = System.nanoTime();
		double duration = Math.floor(((endTime - startTime)/1000000000.0) * 1000 + 0.5 ) / 1000;

		System.out.println("Generating " + size + " Random numbers with max place value of " + max + ".");
		
		startTime = System.nanoTime(); 
		Integer[] sortedRadix = radixSort(unsortedRadix);
		endTime = System.nanoTime();
		duration = Math.floor(((endTime - startTime)/1000000000.0) * 1000 + 0.5 ) / 1000;

		System.out.println("Radix Sort took... 	" + duration + " seconds.");
		
		startTime = System.nanoTime(); 
		Integer[] sortedQuick = quicksort(unsortedQuick);
		endTime = System.nanoTime();
		duration = Math.floor(((endTime - startTime)/1000000000.0) * 1000 + 0.5 ) / 1000;

		System.out.println("Quicksort took... 	" + duration + " seconds.");
		
		startTime = System.nanoTime(); 
		Integer[] sortedMerge = mergesort(unsortedMerge);
		endTime = System.nanoTime();
		duration = Math.floor(((endTime - startTime)/1000000000.0) * 1000 + 0.5 ) / 1000;

		System.out.println("Mergesort took... 	" + duration + " seconds.");

		for (int i = 0; i < itms.length; i++) 
		{
			System.out.print(sortedRadix[i] + ", ");
		}
		System.out.println("Done");
	}

	public Integer[] generateRandomNumbers(int size, int places) {
		Integer[] rNums = new Integer[size];

		Random r = new Random();

		for (int i = 0; i < size; i++) 
		{
			rNums[i] = r.nextInt((int) Math.pow(10, places));
		}

		return rNums;
	}

	public Integer[] quicksort(Integer[] items)
	{
		return quicksort(0, items.length-1, items);
	}
	
	public Integer[] quicksort(int low, int high, Integer[] items) 
	{
		int i = low;
		int j = high;
		int pivot = items[low + (high - low) / 2];

		while (i <= j) 
		{
			while (items[i] < pivot) 
				i++; 
			
			while (items[j] > pivot) 
				j--;
			
			if (i <= j) 
			{
				int temp = items[i];
				items[i] = items[j];
				items[j] = temp;
				i++;
				j--;
			}
		}
		if (low < j)
			quicksort(low, j, items);
		if (i < high)
			quicksort(i, high, items);
		return items;
	}

	public Integer[] mergesort(Integer[] items)
	{
		return mergesort(0, items.length-1, items);
	}
	public Integer[] mergesort(int low, int high, Integer[] items) 
	{
	    if (low < high) 
	    {
	    	int middle = low + (high - low) / 2;
	    	mergesort(low, middle, items);
	    	mergesort(middle + 1, high, items);
	    	merge(low, middle, high, items);
	    }
	    return items;
	}
	
	public void merge(int low, int middle, int high, Integer[] items) 
	{
		Integer[] helper = new Integer[items.length];
	    for (int i = low; i <= high; i++) 
	    {
	    	helper[i] = items[i];
	    }

	    int i = low;
	    int j = middle + 1;
	    int k = low;
	    while (i <= middle && j <= high) 
	    {
	    	if (helper[i] <= helper[j]) 
	    	{
	    		items[k] = helper[i];
	    		i++;
	    	} 
	    	else 
	    	{
	    		items[k] = helper[j];
	    		j++;
	    	}
	    	k++;
	    }
	    while (i <= middle) 
	    {
	    	items[k] = helper[i];
	      	k++;
	      	i++;
	    }
	}

	public Integer[] radixSort(Integer[] items) 
	{
		int shift = 1; 
		int toPlace = (int) Math.floor(Math.log10(Collections.max(Arrays.asList(items)))); 

		Vector<ArrayList<Integer>> vBuckets = new Vector<ArrayList<Integer>>(); 																	
		for (int b = 0; b < 10; b++)
			vBuckets.add(new ArrayList<Integer>()); // Initialize ArrayLists

		for (int i = 0; i <= toPlace; i++) 
		{
			for (int j = 0; j < items.length; j++) 
			{
				int bucketNumber = (items[j] / shift) % 10;
				vBuckets.elementAt(bucketNumber).add(items[j]);
			}
			Vector<Integer> combineBuckets = new Vector<Integer>();
			for (int k = 0; k < vBuckets.size(); k++) 
			{
				combineBuckets.addAll(vBuckets.elementAt(k));
				vBuckets.removeElementAt(k);
				vBuckets.insertElementAt(new ArrayList<Integer>(), k);
			}
			items = combineBuckets.toArray(new Integer[combineBuckets.size()]);
			shift *= 10;
		}
		return items;
	}
}
