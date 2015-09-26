package me.dwtj.jsrcdiff;

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
        public final F fst;
        public final S snd;

        public Pair(F fst, S snd)
        {
            this.fst = fst;
            this.snd = snd;
        }

        public static <F,S> Pair<F,S> make(F fst, S snd)
        {
            return new Pair<F, S>(fst, snd);
        }
    }
    
}
