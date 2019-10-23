/*""" Title: Eclipse JDT-Abstract Syntax Tree(AST) and the Java Model source code
Author: Vogel, L., Scholz, S., and Pfaff, F
Date: 2018
Code version: 23.06.2018
Availability: https://www.vogella.com/tutorials/eclipseJDT/article-html"""
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;

public class ASTType3Helper {

	private static HashMap<String, HashMap<String, ArrayList<String>>> listOfFile = new HashMap<String, HashMap<String, ArrayList<String>>>();
	private static String currentFileName = "";

	/**
	 * create AST
	 * @param file a file for AST
	 * @throws JavaModelException
	 * @throws IOException
	 */
	public void createAST(File file) throws JavaModelException, IOException {
		parse(readFileToString(file));

	}

	/**
	 * hashmap and gets the list
	 * @return
	 */
	public HashMap<String, HashMap<String, ArrayList<String>>> getList() {
		return this.listOfFile;
	}
	
	/**
	 * parse through the tree
	 * @param str string to parse
	 */
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		HashMap<String, ArrayList<String>> tempMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> variableDeclarList = new ArrayList<String>();
		ArrayList<String> expressionList = new ArrayList<String>();
		ArrayList<String> returnList = new ArrayList<String>();
		ArrayList<String> whileStatementList = new ArrayList<String>();
		ArrayList<String> foreachStatement = new ArrayList<String>();
		
		cu.accept(new ASTVisitor() {

			public boolean visit(VariableDeclarationStatement node) {
				variableDeclarList.add(node.toString());
				return true;
			}

			public boolean visit(ExpressionStatement node) {
				expressionList.add(node.toString());
				return true;
			}

			public boolean visit(ReturnStatement node) {
				returnList.add(node.toString());
				return true;
			}

			public boolean visit(WhileStatement node) {
				whileStatementList.add(node.toString());
				return true;
			}

			public boolean visit(ForeachStatement node) {
				foreachStatement.add(node.toString());
				return true;
			}

		});
		tempMap.put("variable", variableDeclarList);
		tempMap.put("expression", expressionList);
		tempMap.put("return", returnList);
		tempMap.put("while", whileStatementList);
		tempMap.put("foreach", foreachStatement);
		listOfFile.put(currentFileName, tempMap);
	}

	/**
	 * seeing if there is a file
	 * @param fileName name of file in string
	 * @return true if there is a file
	 */
	public boolean hasFile(String fileName) {
		if (this.listOfFile.containsKey(fileName)) {
			return true;
		}
		return false;
	}

	/**
	 * takes in a file and turns it into a long string
	 * @param file to put into string
	 * @return the file in string format
	 * @throws IOException
	 */
	public static String readFileToString(File file) throws IOException {
		currentFileName = file.getName();
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(file));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}

}