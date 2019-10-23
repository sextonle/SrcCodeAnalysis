/*""" Title: Eclipse JDT-Abstract Syntax Tree(AST) and the Java Model source code
Author: Vogel, L., Scholz, S., and Pfaff, F
Date: 2018
Code version: 23.06.2018
Availability: https://www.vogella.com/tutorials/eclipseJDT/article-html"""
*/
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {
    List<MethodDeclaration> methods = new ArrayList<>();

    /**
     * method to visit method declaration
     */
    @Override
    public boolean visit(MethodDeclaration node) {
        methods.add(node);
        return super.visit(node);
    }

    /**
     * returns the methods
     * @return
     */
    public List<MethodDeclaration> getMethods() {
        return methods;
    }
}