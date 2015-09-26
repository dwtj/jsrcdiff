package me.dwtj.jsrcdiff;

import java.util.Map;

import org.stringtemplate.v4.ST;


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
            return key.equals(other);
        }
        
        @Override
        public String toString() {
            return key;
        } 
    }


    private static final String STRING_TEMPLATE = String.join("\n",
        "{",
        "  <entries:{entry | <entry.key>: <entry.value>,\n}>",
        "}"
    );


    public static <K,V> String toString(Map<K,V> map)
    {
        if (map.size() == 0) {
            return "{}";
        } else {
            ST st = new ST(STRING_TEMPLATE);
            for (Map.Entry<K,V> entry: map.entrySet()) {
                st.addAggr("entries.{key,value}", entry.getKey(), entry.getValue());
            }
            return st.render();
        }
    }
}
