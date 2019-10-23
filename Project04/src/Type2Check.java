
 import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Scanner;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jdt.core.JavaModelException;

public class Type2Check implements Strategy{
	
	/*
	 * as we read in the nodes to a toString of node and add it to a hashmap
	 * when comparing it to another file, check to see if there is somewhere already full or not
	 * arraylist
	 */
	private ASTHelper ast;
	private HashMap<Integer, String> hashMap;
	private HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
	private boolean isClone;

	/**
	 * constructor for strategy 2
	 * @param tree an AST tree
	 */
	public Type2Check(ASTHelper tree){
		ast=tree;
		hashMap= new HashMap<>();
		isClone=true;
	}
	
	/**
	 * checking to see if the files are clones
	 */
	@Override
	public HashMap<String, ArrayList<String>> cloneCheck(File[] fileList) throws FileNotFoundException {
		for (int i = 0; i < fileList.length; i++) {

			ArrayList<String> eachGroup = new ArrayList<String>();
			eachGroup.add(fileList[i].getName());
			try {
				ast.createAST(fileList[i]);
				} catch (JavaModelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			hash();
			for (int j = i + 1; j < fileList.length; j++) {
				try {
					ast.createAST(fileList[j]);
					} catch (JavaModelException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(compareHash()){
					eachGroup.add(fileList[j].getName());
					System.out.println(fileList[i].getName().toString()+ " and "+ fileList[j].getName().toString()+ " are clones.");
					//fileList = removeTheElement(fileList, j);
					fileList=(File[]) ArrayUtils.removeElement(fileList, fileList[j]);
					j--;
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
	 * putting values of interest to a hashmap
	 */
	public void hash(){
		for(int i =0; i<ast.getList().size();i++){
			String methodDec=ast.getList().get(i).getName().toString()+ast.getList().get(i).getReturnType2().toString()+
					ast.getList().get(i).parameters().toString();
			hashMap.put(methodDec.hashCode(), methodDec);
		}
		ast.clearList();
	}
	
	/**
	 * comparing the hashcodes of two files
	 * @return
	 */
	public boolean compareHash(){
		for(int i =0;i<ast.getList().size();i++){
			String methodDec=ast.getList().get(i).getName().toString()+ast.getList().get(i).getReturnType2().toString()+
					ast.getList().get(i).parameters().toString();
			if(hashMap.containsKey(methodDec.hashCode())) {
				isClone=true;
			}
			else {
				isClone=false;
			}
		}
		ast.clearList();
		return isClone;
	}
}

