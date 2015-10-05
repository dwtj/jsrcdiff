package me.dwtj.jsrcdiff;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import me.dwtj.jsrcdiff.Util.Pair;

public class TestBasics
{
    public static final String equalBasic = "src/test/jsrcdiff/Basics.java";
    public static final String equalBasicWithMethodBody = "src/test/jsrcdiff/BasicsWithMethodBody.java";
    public static final String diffBasicWithMethodBody = "src/test/jsrcdiff/BasicsWithMethodBody-diff.java/";
    
    @Test
    public void testBasicEqual() throws IOException {
        Pair<FieldMap,MethodMap> mapsA = Main.getMapsFromFile(equalBasic);
        Pair<FieldMap,MethodMap> mapsB = Main.getMapsFromFile(equalBasic);
        
        assertEquals(Main.pairDiff(mapsA, mapsB), "");
    }

    @Test
    public void testBasicEqualWithMethodBody() throws IOException {
        Pair<FieldMap,MethodMap> mapsA = Main.getMapsFromFile(equalBasicWithMethodBody);
        Pair<FieldMap,MethodMap> mapsB = Main.getMapsFromFile(equalBasicWithMethodBody);
        
        assertEquals(Main.pairDiff(mapsA, mapsB), "");
    }

    // XXX NOTE: if this test fails, first check if it's because of the order
    // 			 of the output is different than expected. On my machine, ordering
    //			 is always the same, but I'm not sure if this is guaranteed.
    @Test
    public void testBasicDiff() throws IOException {
        Pair<FieldMap,MethodMap> mapsA = Main.getMapsFromFile(equalBasicWithMethodBody);
        Pair<FieldMap,MethodMap> mapsB = Main.getMapsFromFile(diffBasicWithMethodBody);
        
        String expected = "CM: void method0()\n"
        		    	+ "CM: void method1()\n"
        		    	+ "DM: String method4(int, double)\n"
        		    	+ "DM: double method5(String)\n"
        		    	+ "DM: double method6(Map<String,String>)\n"
        		    	+ "DM: int method2(int)\n"
        		    	+ "DM: int method3(int)\n"
        		    	+ "AM: String method4(int, double, String)\n"
        		    	+ "AM: double method2(int)\n"
        		    	+ "AM: double method5(Map<String,String>)\n"
        		    	+ "AM: double method7(String)\n"
        		    	+ "AM: int method3(double)\n"
        		    	+ "CFI: int i\n"
        		    	+ "CFI: int[] intArr1\n"
        		    	+ "CFI: String stringDoubleArr\n"
        		    	+ "CFI: String s\n"
        		    	+ "CFI: int x\n"
        		    	+ "DF: HashMap<String,String> map0\n"
        		    	+ "DF: int intArr2\n"
        		    	+ "DF: int intDeepArr2\n"
        		    	+ "DF: int[][][][][][] intDeepArr1\n"
        		    	+ "AF: HashMap<String,String> mapXXX\n"
        		    	+ "AF: double intDeepArr2\n"
        		    	+ "AF: int intArr3\n"
        		    	+ "AF: int[][][][][][][] intDeepArr1\n";
        
        assertEquals(Main.pairDiff(mapsA, mapsB), expected);
    }
}