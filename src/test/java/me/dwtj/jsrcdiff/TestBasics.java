package me.dwtj.jsrcdiff;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import me.dwtj.jsrcdiff.Util.Pair;

public class TestBasics
{
    public static String equalA = "src/test/jsrcdiff/Basics.java";
    public static String equalB = "src/test/jsrcdiff/Basics.java";
    
    @Test
    public void testEqual() throws IOException {
        Pair<FieldMap,MethodMap> mapsA = Main.getMapsFromFile(equalA);
        Pair<FieldMap,MethodMap> mapsB = Main.getMapsFromFile(equalB);
        
        assertEquals(Main.pairDiff(mapsA, mapsB), "");
    }
}
