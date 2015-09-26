package me.dwtj.jsrcdiff;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.antlr.grammarsv4.JavaParser.FieldDeclarationContext;
import org.antlr.grammarsv4.JavaParser.TypeContext;
import org.antlr.grammarsv4.JavaParser.VariableDeclaratorContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import me.dwtj.jsrcdiff.Util.Pair;
import me.dwtj.jsrcdiff.Util.StringKey;


@SuppressWarnings("serial")
public class FieldMap extends TreeMap<FieldMap.Key, FieldMap.Value>
{
    /**
     * Add all field declarations associated with the given `fieldDecl` to this map. Note that this
     * might add multiple key-value pairs to the map in cases where the field declaration includes
     * multiple declarators, for example, `int i, j`.
     */
    public void add(FieldDeclarationContext fieldDecl)
    {
        for (Pair<Key,Value> entry: makeAllEntriesFrom(fieldDecl)) {
            put(entry.fst, entry.snd);
        }
    }
    
    protected static List<Pair<Key,Value>> makeAllEntriesFrom(FieldDeclarationContext fieldDecl)
    {
        // Individual variable declarator nodes do not have type information, but each of our keys
        // needs to have a type associated with it. Thus, no individual parse tree node has all of
        // the information needed to make a single entry. This is why we have this helper method
        // make a list of entries from a parse tree node instead of having some helper which makes
        // one entry per node.
        
        List<Pair<Key,Value>> entries = new ArrayList<Pair<Key,Value>>();
        TypeContext type = fieldDecl.type();

        for (VariableDeclaratorContext varDecl: fieldDecl.variableDeclarators().variableDeclarator())
        {
            Key k = new Key(type, varDecl.variableDeclaratorId().Identifier());
            Value v = new Value(varDecl);
            entries.add(Pair.make(k, v));
        }
        return entries;
    }


    public static class Key extends StringKey implements Comparable<Key>
    {
        public Key(TypeContext type, TerminalNode id)
        {
            assert type != null;
            assert id != null;
            
            Pair<String,String> normalized = Util.normalizedArrayDecl(type.getText(), id.getText());
            key = normalized.fst + " " + normalized.snd;
        }
        
        @Override
        public int compareTo(Key other) {
            return key.compareTo(other.key);
        }
    }
    

    public static class Value
    {
        private final VariableDeclaratorContext value;
        
        public Value(VariableDeclaratorContext varDecl)
        {
            if (varDecl == null) {
                throw new IllegalArgumentException("`varDecl` can't be `null`.");
            }
            value = varDecl;
        }
        
        // TODO: Add getter for other properties of a field declaration (e.g. qualifiers).
        
        public String getId() {
            return value.variableDeclaratorId().Identifier().getText();
        }
        
        public boolean equals(Object other)
        {
            if (!(other instanceof Value)) {
                return false;
            }
            
            Value otherVal = (Value) other;
            return getId().equals(otherVal.getId());  // TODO: Add criteria for equality.
        }
    }
}
