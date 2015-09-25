package me.dwtj.jsrcdiff;

import java.io.IOException;

import org.antlr.grammarsv4.JavaLexer;
import org.antlr.grammarsv4.JavaParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


public class Main
{
    public static void main(String args[]) throws IOException
    {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        System.out.println(tree.toStringTree(parser));  // Print LISP-style tree.
    }
}
