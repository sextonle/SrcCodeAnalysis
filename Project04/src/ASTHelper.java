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
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ASTHelper {
	
	private ArrayList<MethodDeclaration>list= new ArrayList<>();

	/**
	 * sets up AST by calling parse and accepts visitor
	 * @param file
	 * @throws JavaModelException
	 * @throws IOException
	 */
    public void createAST(File file) throws JavaModelException, IOException {
    	String fileString = fileToString(file);
            CompilationUnit parse = parse(fileString);
            MethodVisitor visitor = new MethodVisitor();
            parse.accept(visitor);
            
            for (MethodDeclaration method : visitor.getMethods()) {
            list.add(method);
            }
    }
    public ArrayList<MethodDeclaration> getList(){
    	return list;
    }

    /**
     * Reads a String and creates the AST DOM for manipulating the
     * Java source file
     * 
     * REALLY THE ONE CREATING THE AST
     * 
     * @param fileString
     * @return
     */
    public static CompilationUnit parse(String fileString) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(fileString.toCharArray());
        parser.setResolveBindings(true);
        return (CompilationUnit) parser.createAST(null); // parse
    }
    
    /**
     * puts the entire file in a long string to be parsed
     * @param file
     * @return
     * @throws IOException
     */
    public String fileToString(File file) throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String appendedString = "";
    	String bufferString=br.readLine();
    	while(bufferString != null) {
    		appendedString += bufferString;
    		bufferString=br.readLine();
    	}
    	br.close();
    	return appendedString;
    }
    public void clearList(){
    	list.clear();
    }
}