
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.print.attribute.standard.Compression;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jdt.core.JavaModelException;

public class Type3Check implements Strategy {
	/*
	 * as we read in the nodes to a toString of node and add it to a hashmap when
	 * comparing it to another file, check to see if there is somewhere already full
	 * or not arraylist
	 */
	private ASTType3Helper ast;
	private HashMap<Integer, String> hashMap;
	private HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
	private boolean isClone;
	private double threshold;

	/**
	 * constructor
	 * @param threshold the cut off level of the cloning
	 * @param tree ast tree
	 */
	public Type3Check(double threshold, ASTType3Helper tree) {
		ast = tree;
		hashMap = new HashMap<>();
		isClone = true;
		this.threshold = threshold;
	}

	@Override
	public HashMap<String, ArrayList<String>> cloneCheck(File[] fileList) throws FileNotFoundException {
		// do a nested loop from j = 0, this will result from n^2 but it's necessary

		for (int i = 0; i < fileList.length; i++) {

			ArrayList<String> eachGroup = new ArrayList<String>();
			eachGroup.add(fileList[i].getName());
			try {
				if (!ast.hasFile(fileList[i].getName())) {
					ast.createAST(fileList[i]);
				}
				//				System.out.println(fileList[i].getName());
			} catch (JavaModelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < fileList.length; j++) {
				if (!fileList[j].getName().equals(fileList[i].getName())) {

					try {
						if (!ast.hasFile(fileList[j].getName())) {
							ast.createAST(fileList[j]);
						}
					} catch (JavaModelException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (CompareFunction(ast.getList().get(fileList[i].getName()), ast.getList().get(fileList[j].getName()))) {
						System.out.println(fileList[i].getName().toString()+ " and "+ fileList[j].getName().toString()+ " are clones.");
						eachGroup.add(fileList[j].getName());
						fileList=(File[]) ArrayUtils.removeElement(fileList, fileList[j]);
						j--;
					}
					else{
						System.out.println(fileList[i].getName().toString()+ " and "+ fileList[j].getName().toString()+ " are NOT clones.");
						
					}
					//result.put("Group" + (i+1), eachGroup);
					result.put("Group" + i, eachGroup);
				}
			}
		}
		return this.result;
	}

	/**
	 * comparing each thing in the body of methods
	 * @param list1 list of methods for file one
	 * @param list2 list of methods for file two
	 * @return true if they are the same
	 */
	public boolean CompareFunction(HashMap<String, ArrayList<String>> list1,
			HashMap<String, ArrayList<String>> list2) {
		double totalStatement = 0, totalCloneLine = 0;

		for (String eachLine : list1.get("expression")) {
			if (list2.get("expression").contains(eachLine)) {
				totalCloneLine++;
			}
			totalStatement++;
		}
		for (String eachLine : list1.get("variable")) {
			if (list2.get("variable").contains(eachLine)) {
				totalCloneLine++;
			}
			totalStatement++;
		}
		for (String eachLine : list1.get("return")) {
			if (list2.get("return").contains(eachLine)) {
				totalCloneLine++;
			}
			totalStatement++;
		}
		for (String eachLine : list1.get("while")) {
			if (list2.get("while").contains(eachLine)) {
				totalCloneLine++;
			}
			totalStatement++;
		}
		for (String eachLine : list1.get("foreach")) {
			if (list2.get("foreach").contains(eachLine)) {
				totalCloneLine++;
			}
			totalStatement++;
		}
		return totalCloneLine / totalStatement >= this.threshold;
	}
}
