package me.dwtj.jsrcdiff;

import java.io.IOException;

import org.antlr.grammarsv4.JavaLexer;
import org.antlr.grammarsv4.JavaParser;
import org.antlr.v4.runtime.*;

import me.dwtj.jsrcdiff.FieldMap;
import me.dwtj.jsrcdiff.Util.Pair;


public class Main
{
    public static void main(String args[]) throws IOException
    {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        
        Pair<FieldMap,MethodMap> mapsA = getMapsFromFile(args[0]);
        Pair<FieldMap,MethodMap> mapsB = getMapsFromFile(args[1]);

        System.out.println("\nmapsA Method keys:");
        for (MethodMap.Key k: mapsA.snd.keySet()) {
        	System.out.println(k);
        }

        System.out.println("\nmapsA Method IDs:");
        for (MethodMap.Value v: mapsA.snd.values()) {
        	System.out.println(v.getId());
        }

        System.out.println("\nmapsA Method qualifiers:");
        for (MethodMap.Value v: mapsA.snd.values()) {
        	System.out.println(v.getQualifiers());
        }

        System.out.println("\nmapsA Method arg IDs:");
        for (MethodMap.Value v: mapsA.snd.values()) {
        	//System.out.println(v.getArgIds());
        	System.out.println(v.getArgIds());
        }
        
        System.out.println("\nmapsA Method body:");
        for (MethodMap.Value v: mapsA.snd.values()) {
        	System.out.println(v.getBody());
        }

        /*
        System.out.println("\nmapsA Field keys:\n");
        for (FieldMap.Key k: mapsA.fst.keySet()) {
            System.out.println(k);
        }

        System.out.println("\nmapsA Field values:\n");
        for (FieldMap.Value v: mapsA.fst.values()) {
            System.out.println(v);
        }
        
        System.out.println("\nfill mapsA:\n");
        System.out.println(mapsA);
        */
    }
    
    public static Pair<FieldMap,MethodMap> getMapsFromFile(String filePath) throws IOException
    {
        String sourceCode = Util.fileToString(filePath);

        ANTLRInputStream input = new ANTLRInputStream(sourceCode);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);

        MapsBuilder mapsBuilder = new MapsBuilder();
        mapsBuilder.build(parser);

        return Pair.make(mapsBuilder.getFieldMap(), mapsBuilder.getMethodMap());
    }
}
