/*""" Title: Remove an Element at specific index from an Array in Java source code
Author: Prabhu, R
Date: N.D.
Code version: N/A
Availability: https://www.geeksforgeeks.org/remove-an-element-at-specific-index-from-an-array-in-java/"""
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Type1Check implements Strategy {

	private HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();

	/**
	 * Takes in folders with multiple files and comparing each
	 * Prints to the screen the similar files by grouping them
	 */
	@Override
	public HashMap<String, ArrayList<String>> cloneCheck(File[] fileList) throws FileNotFoundException {
		for (int i = 0; i < fileList.length; i++) {

			ArrayList<String> eachGroup = new ArrayList<String>();
			eachGroup.add(fileList[i].getName());

			for (int j = i + 1; j < fileList.length; j++) {
				Scanner firstFile = new Scanner(fileList[i]);
				Scanner compareTarget = new Scanner(fileList[j]);
				if (compareTwoFiles(firstFile, compareTarget)) {
					eachGroup.add(fileList[j].getName());
					System.out.println(fileList[i].getName().toString()+ " and "+ fileList[j].getName().toString()+ " are clones.");
					fileList = removeTheElement(fileList, j);
					//j--;
				}
				else{
					System.out.println(fileList[i].getName().toString()+ " and "+ fileList[j].getName().toString()+ " are NOT clones.");
				}
			}
			result.put("Group" + i, eachGroup);
		}

		return this.result;
	}
	
	/**
	 * Making each file into a string and comparing the strings
	 * @param f1 first file
	 * @param f2 second file
	 * @return if the two files are the same
	 */
	public boolean compareTwoFiles(Scanner f1,Scanner f2){
		String file1="";
		String temp1="";
		String file2="";
		String temp2="";
		while(f1.hasNextLine()){
			temp1=f1.nextLine();
			if(!temp1.contains("class")){
				file1=file1+temp1;
			}
		}
		while(f2.hasNextLine()){
			temp2=f2.nextLine();
			if(!temp2.contains("class")){
				file2=file2+temp2;
			}
		}
		file1=file1.replaceAll("\\s+","");
		file2=file2.replaceAll("\\s+","");
		if(file1.equals(file2)){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * one file in in group remove from array
	 * @param arr array of files
	 * @param index
	 * @return a file array
	 */
	public static File[] removeTheElement(File[] arr, int index) {

		// If the array is empty
		// or the index is not in array range
		// return the original array
		if (arr == null || index < 0 || index >= arr.length) {

			return arr;
		}

		// Create another array of size one less
		File[] anotherArray = new File[arr.length - 1];

		// Copy the elements except the index
		// from original array to the other array
		for (int i = 0, k = 0; i < arr.length; i++) {

			// if the index is
			// the removal element index
			if (i == index) {
				continue;
			}
			// if the index is not
			// the removal element index
			anotherArray[k++] = arr[i];
		}
		// return the resultant array
		return anotherArray;
	}

}
