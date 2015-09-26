package me.dwtj.jsrcdiff;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.antlr.grammarsv4.JavaLexer;
import org.antlr.grammarsv4.JavaParser;
import org.antlr.grammarsv4.JavaParser.ClassBodyDeclarationContext;
import org.antlr.grammarsv4.JavaParser.FieldDeclarationContext;
import org.antlr.grammarsv4.JavaParser.MethodDeclarationContext;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;


public class Main
{
    public static void main(String args[]) throws IOException
    {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        String fileA = fileToString(args[0]);
        String fileB = fileToString(args[1]);

        ANTLRInputStream input = new ANTLRInputStream(fileA);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);

        //diveToMakeAllFieldKeys(parser);
        //diveToMakeAllMethodKeys(parser);
    }
    
    public static String getSourceInterval(String src, TokenStream tokens, Interval interval)
    {
        Token start = tokens.get(interval.a);
        Token end = tokens.get(interval.b);
        return src.substring(start.getStartIndex(), end.getStopIndex() + 1);
    }
    
    public static String fileToString(String file) throws IOException
    {
        Path path = Paths.get(file);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, Charset.defaultCharset());
    }
    
    /*
    public static Interval diveForTestInterval(JavaParser parser)
    {
        return parser.compilationUnit()
                     .typeDeclaration(0)
                     .classDeclaration()
                     .classBody()
                     .classBodyDeclaration(0)
                     .getSourceInterval();
    }
    
    public static FieldDeclarationContext diveForTestFieldDecl(JavaParser parser)
    {
        return parser.compilationUnit()
                     .typeDeclaration(0)
                     .classDeclaration()
                     .classBody()
                     .classBodyDeclaration(0)
                     .memberDeclaration()
                     .fieldDeclaration();
    }
    
    public static List<FieldKey> diveToMakeAllFieldKeys(JavaParser parser)
    {
        List<ClassBodyDeclarationContext> bodyDecls = parser.compilationUnit()
                                                            .typeDeclaration(0)
                                                            .classDeclaration()
                                                            .classBody()
                                                            .classBodyDeclaration();

        List<FieldKey> keys = new ArrayList<FieldKey>();
        for (ClassBodyDeclarationContext decl : bodyDecls)
        {
            FieldDeclarationContext fieldDecl = decl.memberDeclaration().fieldDeclaration();
            if (fieldDecl != null) {
                keys.addAll(FieldKey.make(fieldDecl));
            }
        }
        return keys;
    }
    
    public static List<MethodKey> diveToMakeAllMethodKeys(JavaParser parser)
    {
        List<ClassBodyDeclarationContext> bodyDecls = parser.compilationUnit()
                                                            .typeDeclaration(0)
                                                            .classDeclaration()
                                                            .classBody()
                                                            .classBodyDeclaration();

        List<MethodKey> keys = new ArrayList<MethodKey>();
        for (ClassBodyDeclarationContext decl : bodyDecls)
        {
            MethodDeclarationContext methodDecl = decl.memberDeclaration()
                                                      .methodDeclaration();
            if (methodDecl != null) {
                keys.add(MethodKey.make(methodDecl));
            }
        }
        return keys;
    }
    */
}
