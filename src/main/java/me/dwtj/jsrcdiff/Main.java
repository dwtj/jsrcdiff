package me.dwtj.jsrcdiff;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.antlr.grammarsv4.JavaLexer;
import org.antlr.grammarsv4.JavaParser;
import org.antlr.v4.runtime.*;

import me.dwtj.jsrcdiff.FieldMap;
import me.dwtj.jsrcdiff.MethodMap.Key;
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
        
        pairDiff(mapsA, mapsB);
        
        /*
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
        */
    }
    
    public static void pairDiff(Pair<FieldMap,MethodMap> a, Pair<FieldMap,MethodMap> b) {
    	// diff the methods first
    	
    	// if keys match and body is different, CM
    	Set<MethodMap.Key> removeSet = new HashSet<MethodMap.Key>();
    	for (MethodMap.Key k: a.snd.keySet()) {
    		if (b.snd.keySet().contains(k)) {
    			System.out.println("contains");
    			if (a.snd.get(k).compareTo(b.snd.get(k)) != 0) {
    				System.out.println("CM: " + k);
    			}
    		}
    		removeSet.add(k);
    	}
    	// get rid of keys we've seen already
    	a.snd.keySet().removeAll(removeSet);
    	b.snd.keySet().removeAll(removeSet);
    	// anything leftover in a is DM
    	for (MethodMap.Key k: a.snd.keySet()) {
    		System.out.println("DM: " + k);
    	}
    	// anything leftover in b is AM
    	for (MethodMap.Key k: b.snd.keySet()) {
    		System.out.println("AM: " + k);
    	}
    	
        //for (MethodMap.Key k: a.snd.keySet()) {
        //	System.out.println(k);
        //	System.out.println(mapsB.snd.keySet().contains(k));
        //	System.out.println(mapsA.snd.get(k).compareTo(mapsB.snd.get(k)));
        //}
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
