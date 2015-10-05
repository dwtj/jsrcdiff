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
        
        System.out.println(pairDiff(mapsA, mapsB));
    }
    
    public static String pairDiff(Pair<FieldMap,MethodMap> a, Pair<FieldMap,MethodMap> b) {
    	
    	String ret = new String();
    	
    	/*
    	 * Method Diff
    	 */

    	// find shared method keys between a and b
    	Set<MethodMap.Key> m_isec = new HashSet<MethodMap.Key>(a.snd.keySet());
    	m_isec.retainAll(b.snd.keySet());

    	// if keys match and body is different, CM
    	for (MethodMap.Key k: m_isec) {
			if (a.snd.get(k).compareTo(b.snd.get(k)) != 0) {
				ret += "CM: " + k + "\n";
			}
    	}
    	// remove shared keys
    	a.snd.keySet().removeAll(m_isec);
    	b.snd.keySet().removeAll(m_isec);

    	// anything leftover in a is DM
    	for (MethodMap.Key k: a.snd.keySet()) {
    		ret += "DM: " + k + "\n";
    	}

    	// anything leftover in b is AM
    	for (MethodMap.Key k: b.snd.keySet()) {
    		ret += "AM: " + k + "\n";
    	}
    	
    	/*
    	 * Field Diff
    	 */

    	// find shared field keys between a and b
    	Set<FieldMap.Key> f_isec = new HashSet<FieldMap.Key>(a.fst.keySet());
    	f_isec.retainAll(b.fst.keySet());

    	// if keys match and initializers or qualifiers are different, CFI
    	for (FieldMap.Key k: f_isec) {
			if (a.fst.get(k).compareTo(b.fst.get(k)) != 0) {
				ret += "CFI: " + k + "\n";
			}
    	}
    	// remove shared keys
    	a.fst.keySet().removeAll(f_isec);
    	b.fst.keySet().removeAll(f_isec);

    	// anything leftover in a is DF
    	for (FieldMap.Key k: a.fst.keySet()) {
    		ret += "DF: " + k + "\n";
    	}

    	// anything leftover in b is AF
    	for (FieldMap.Key k: b.fst.keySet()) {
    		ret += "AF: " + k + "\n";
    	}
    	
    	return ret;
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
