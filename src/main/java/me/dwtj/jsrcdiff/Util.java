package me.dwtj.jsrcdiff;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.Interval;
import org.stringtemplate.v4.ST;

import me.dwtj.jsrcdiff.MethodMap.Key;


public class Util
{
    /**
     * Both `int[] j` and `int j[]` are syntactically valid array declarations in Java. If the
     * arguments are of the form `int j[]`, then this function converts these to the form `int[] j`.
     * If the arguments are of the form `int[] j`, then no changes happen. This function normalizes
     * array declarations of any dimension.
     * 
     * @return A `Pair` whose `fst` is the normalized array type and whose `snd` is the normalized
     *         identifier.
     */
    public static Pair<String,String> normalizedArrayDecl(String typeStr, String idStr)
    {
        if (idStr.endsWith("]"))
        {
            int left = idStr.indexOf('[');
            int right = idStr.length();
            String brackets = idStr.substring(left, right);
            idStr = idStr.substring(0, left);
            typeStr = typeStr + brackets;
        }
        return Pair.make(typeStr, idStr); 
    }


    public static class Pair<F,S>
    {
        public static <F,S> Pair<F,S> make(F fst, S snd)
        {
            return new Pair<F, S>(fst, snd);
        }

        public final F fst;
        public final S snd;

        public Pair(F fst, S snd)
        {
            this.fst = fst;
            this.snd = snd;
        }
        
        @Override
        public String toString() {
            return "(" + fst.toString() + ", " + snd.toString() + ")";
        }

        public boolean equals(Object other) {
            if (!(other instanceof Pair)) {
                return false;
            }
            Pair o = (Pair) other;
            return this.fst.equals(o.fst) && this.snd.equals(o.snd);
        }
    }


    /**
     * An abstract class meant to be extended by key types which can represent themselves using a
     * single `String`.
     */
    public static abstract class StringKey
    {
        protected String key;
        
        public StringKey() {
            key = "";
        }
        
        @Override
        public int hashCode() {
            return key.hashCode();
        }
        
        @Override
        public boolean equals(Object other) {
        	if (!(other instanceof StringKey)) {
        		return false;
        	}
        	StringKey o = (StringKey) other;
            return this.key.equals(o.key);
        }
        
        @Override
        public String toString() {
            return key;
        } 
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

    /* NOTE:
     * stringtemplate doesn't seem to be working, and I don't want to spend
     * the time right now fucking around and figuring out why. This is the
     * Error(s) I'm getting:
     *
     * [ERROR] /sanitized/path/jsrcdiff/Util.java:[128,19] method addAggr in class org.stringtemplate.v4.ST cannot be applied to given types;
     * [ERROR] required: java.lang.String,java.lang.Object[]
     * [ERROR] found: java.lang.String,K,V
     * [ERROR] reason: actual and formal argument lists differ in length
     *
     * So for now I'm just doing this manually.


    private static final String STRING_TEMPLATE = String.join("\n",
        "{",
        "  <entries:{entry | <entry.key>: <entry.value>,\n}>",
        "}"
    );
     */

    public static <K,V> String toString(Map<K,V> map)
    {
        if (map.size() == 0) {
            return "{}";
        } else {
            /* See: above NOTE

            ST st = new ST(STRING_TEMPLATE);
            for (Map.Entry<K,V> entry: map.entrySet()) {
                st.addAggr("entries.{key,value}", entry.getKey(), entry.getValue());
            }
            return st.render();
            */
            String s = new String("{ <entries:{\n");
            for (Map.Entry<K,V> entry: map.entrySet()) {
                s += "entry | " + entry.getKey() + ": " + entry.getValue() +
                        ",\n";
            }
            s += "}>}";
            return s;
        }
    }
}
