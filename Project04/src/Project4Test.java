import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

public class Project4Test {
	
	@Test
	public void strategy1A() throws FileNotFoundException {
		File[] fileList=new File[5];
		fileList[0]=(new File("TestCaseA.java"));
		fileList[1]=(new File("TestCaseB.java"));
		fileList[2]=(new File("TestCaseC.java"));
		fileList[3]=(new File("TestCaseD.java"));
		fileList[4]=(new File("TestCaseE.java"));
		
		CloneDetector c1 = new CloneDetector(new Type1Check());
		assertEquals("{Group1=[TestCaseB.java, TestCaseE.java], Group2=[TestCaseD.java], Group0=[TestCaseA.java, TestCaseC.java]}"
				,c1.preformCheck(fileList).toString());
	}
	
	@Test
	public void strategy1B() throws FileNotFoundException{
		File[] fileList=new File[3];
		fileList[0]=(new File("Test1.java"));
		fileList[1]=(new File("Test2.java"));
		fileList[2]=(new File("Test3.java"));
		CloneDetector c1 = new CloneDetector(new Type1Check());
		assertEquals("{Group1=[Test3.java], Group0=[Test1.java, Test2.java]}"
				,c1.preformCheck(fileList).toString());
	}
	
	@Test
	public void strategy2A() throws FileNotFoundException{
		File[] fileList=new File[5];
		fileList[0]=(new File("TestCaseA.java"));
		fileList[1]=(new File("TestCaseB.java"));
		fileList[2]=(new File("TestCaseC.java"));
		fileList[3]=(new File("TestCaseD.java"));
		fileList[4]=(new File("TestCaseE.java"));
		ASTHelper ast = new ASTHelper();
		CloneDetector c1 = new CloneDetector(new Type2Check(ast));
		assertEquals("{Group1=[TestCaseD.java], Group0=[TestCaseA.java, TestCaseB.java, TestCaseC.java, TestCaseE.java]}"
				,c1.preformCheck(fileList).toString());
	}
	@Test
	public void strategy2B() throws FileNotFoundException{
		File[] fileList=new File[3];
		fileList[0]=(new File("Test1.java"));
		fileList[1]=(new File("Test2.java"));
		fileList[2]=(new File("Test3.java"));
		ASTHelper ast = new ASTHelper();
		CloneDetector c1 = new CloneDetector(new Type2Check(ast));
		assertEquals("{Group1=[Test3.java], Group0=[Test1.java, Test2.java]}"
				,c1.preformCheck(fileList).toString());
	}
	@Test
	public void strategy3A() throws FileNotFoundException{
		File[] fileList=new File[5];
		fileList[0]=(new File("TestCaseA.java"));
		fileList[1]=(new File("TestCaseB.java"));
		fileList[2]=(new File("TestCaseC.java"));
		fileList[3]=(new File("TestCaseD.java"));
		fileList[4]=(new File("TestCaseE.java"));
		CloneDetector c1 = new CloneDetector(new Type3Check(0.5,new ASTType3Helper()));
		System.out.println(c1.preformCheck(fileList));
		assertEquals("{Group1=[TestCaseD.java], Group0=[TestCaseA.java, TestCaseB.java, TestCaseC.java, TestCaseE.java]}"
				,c1.preformCheck(fileList).toString());
	}
	@Test
	public void strategy3B() throws FileNotFoundException{
		File[] fileList=new File[3];
		fileList[0]=(new File("Test1.java"));
		fileList[1]=(new File("Test2.java"));
		fileList[2]=(new File("Test3.java"));
		CloneDetector c1 = new CloneDetector(new Type3Check(0.5,new ASTType3Helper()));
		System.out.println(c1.preformCheck(fileList));
		assertEquals("{Group1=[Test3.java], Group0=[Test1.java, Test2.java]}"
				,c1.preformCheck(fileList).toString());
	}

}
