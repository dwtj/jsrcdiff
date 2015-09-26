package me.dwtj.jsrcdiff;

import java.io.IOException;

import org.junit.Test;

public class TestBasics
{
    public static String fileA = "src/test/jsrcdiff/Basics.java";
    public static String fileB = "src/test/jsrcdiff/Basics.java";
    
    @Test
    public void testBasics() throws IOException {
        Main.main(new String[] {fileA, fileB});
    }
}
