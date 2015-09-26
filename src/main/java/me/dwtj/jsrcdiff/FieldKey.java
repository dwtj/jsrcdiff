package me.dwtj.jsrcdiff;

import java.util.ArrayList;
import java.util.List;

import org.antlr.grammarsv4.JavaParser.FieldDeclarationContext;
import org.antlr.grammarsv4.JavaParser.TypeContext;
import org.antlr.grammarsv4.JavaParser.VariableDeclaratorContext;
import org.antlr.grammarsv4.JavaParser.VariableDeclaratorIdContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import me.dwtj.jsrcdiff.Util.Pair;

public class FieldKey implements Comparable<FieldKey>
{
    private final String key;
    
    public FieldKey(TypeContext type, TerminalNode id)
    {
        assert type != null;
        assert id != null;
        
        Pair<String,String> normalized = Util.normalizedArrayDecl(type.getText(), id.getText());
        key = normalized.fst + " " + normalized.snd;
    }
    
    @Override
    public int compareTo(FieldKey other)
    {
        return key.compareTo(other.key);
    }
    
    @Override
    public String toString()
    {
        return key;
    }
    
    public static List<FieldKey> make(FieldDeclarationContext fieldDecl)
    {
        List<FieldKey> keys = new ArrayList<FieldKey>();
        TypeContext type = fieldDecl.type();

        for (VariableDeclaratorContext decl: fieldDecl.variableDeclarators().variableDeclarator()) {
            keys.add(new FieldKey(type, decl.variableDeclaratorId().Identifier()));
        }
        return keys;
    }
}
