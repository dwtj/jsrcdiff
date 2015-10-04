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
        
        // Comparing field maps
        for (FieldMap.Key k: mapsA.fst.keySet()) {
        	System.out.println(k);
        	System.out.println(mapsB.fst.keySet().contains(k));
        	System.out.println(mapsA.fst.get(k).compareTo(mapsB.fst.get(k)));
        }
        
        // Comparing method maps
        for (MethodMap.Key k: mapsA.snd.keySet()) {
        	System.out.println(k);
        	System.out.println(mapsB.snd.keySet().contains(k));
        	System.out.println(mapsA.snd.get(k).compareTo(mapsB.snd.get(k)));
        }
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
