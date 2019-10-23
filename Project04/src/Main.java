import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		String path="";
		String file="";
		ArrayList<File> list=new ArrayList<>();	
		//getting which method the user wants to use
		System.out.println("Please select the method (the number) for input data from following: ");
		System.out.println("1. File containing a list of files");
		System.out.println("2. From a file containing file paths");
		String methodChoice=in.next();
		
		//if the user enters in a file with file names
		if(methodChoice.equals("1")){
			System.out.println("Enter in file name: ");
			file= in.next();
			Scanner scan =new Scanner(new File(file));
			while(scan.hasNextLine()){
				list.add(new File(scan.nextLine()));
			}
			File[] fileList = new File[list.size()];
			for(int i =0; i<list.size();i++){
				fileList[i]=list.get(i);
			}
			getStrategyChoice(fileList);
		}
		
		//if the user enters a file with file paths
		else if(methodChoice.equals("2")){
			System.out.println("Enter in a file of file paths: ");
			file = in.next();
			Scanner scan = new Scanner(new File(file));
			while(scan.hasNextLine()){
				list.add(new File(scan.nextLine()));
			}
			File[] fileList = new File[list.size()];
			for(int i =0; i<list.size();i++){
				fileList[i]=list.get(i);
			}
			getStrategyChoice(fileList);
		}
		else{
			System.out.println("You have entered in an incorrect option. Try again later");
		}	
	}
	
	/**
	 * calling the various strategies depending on what the user wants to check
	 * @param fileList list of files
	 * @throws FileNotFoundException in case the file is not found
	 */
	public static void getStrategyChoice(File[] fileList) throws FileNotFoundException{
		CloneDetector c1;
		Scanner in =new Scanner(System.in);
		System.out.println("Please select which strategy you want to test: ");
		System.out.println("1. Strategy 1");
		System.out.println("2. Strategy 2");
		System.out.println("3. Strategy 3");
		String strategyChoice=in.next();
		if(strategyChoice.equals("1")){
			c1 = new CloneDetector(new Type1Check());
			System.out.println(c1.preformCheck(fileList));
		}
		else if(strategyChoice.equals("2")){
			ASTHelper ast = new ASTHelper();
			c1 = new CloneDetector(new Type2Check(ast));
			System.out.println(c1.preformCheck(fileList));
		}
		else if(strategyChoice.equals("3")){
			 c1 = new CloneDetector(new Type3Check(0.5,new ASTType3Helper()));
			 System.out.println(c1.preformCheck(fileList));
		}
		else{
			System.out.println("You have entered in an incorrect option. Try again later");
		}
	}
}