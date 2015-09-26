package me.dwtj.jsrcdiff;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.antlr.grammarsv4.JavaParser.FormalParameterContext;
import org.antlr.grammarsv4.JavaParser.FormalParameterListContext;
import org.antlr.grammarsv4.JavaParser.MethodDeclarationContext;
import org.antlr.grammarsv4.JavaParser.TypeContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import me.dwtj.jsrcdiff.Util.Pair;
import me.dwtj.jsrcdiff.Util.StringKey;


@SuppressWarnings("serial")
public class MethodMap extends TreeMap<MethodMap.Key, MethodMap.Value>
{
    public void add(MethodDeclarationContext methodDecl)
    {
        Pair<Key,Value> entry = makeEntry(methodDecl);
        put(entry.fst, entry.snd);
    }
    
    protected Pair<Key,Value> makeEntry(MethodDeclarationContext methodDecl)
    {
        Key k = Key.make(methodDecl);
        Value v = Value.make(methodDecl);
        return Pair.make(k, v);
    }
    

    public static class Key extends StringKey implements Comparable<Key>
    {
        public static Key make(MethodDeclarationContext methodDecl)
        {
            FormalParameterListContext params = methodDecl.formalParameters().formalParameterList();
            List<FormalParameterContext> list = null;
            if (params != null) {
                list = params.formalParameter();
            }
            return new Key(methodDecl.type(), methodDecl.Identifier(), list);
        }
        
        /**
         * @param retType  The return type of the method. `null` means that the method has no return
         *                 type (i.e. it is declared `void`).
         * @param id       The identifier (i.e. the unqualified name) of the method.
         * @param paramList   A list of the method's formal parameters. Both `null` and an empty list are
         *                 mean that the method has no formal parameters.
         */
        public Key(TypeContext retType, TerminalNode id, List<FormalParameterContext> paramList)
        {
            assert id != null;

            StringBuilder key = new StringBuilder();
            key.append(retType == null ? "void" : retType.getText()).append(" ");
            key.append(id.getText() + '(');
            key.append(makeParamListString(paramList));
            key.append(')');
            this.key = key.toString();
        }
        
        private static String makeParamListString(List<FormalParameterContext> paramList)
        {
            if (paramList == null) {
                return "";
            } else {
                List<String> paramTypes = new ArrayList<String>();
                for (FormalParameterContext param : paramList) {
                    paramTypes.add(param.type().getText());
                }
                return String.join(", ", paramTypes);
            }
        }

        @Override
        public int compareTo(Key other) {
            return key.compareTo(other.key);
        }
    }

    
    public static class Value
    {
        public static Value make(MethodDeclarationContext methodDecl) {
            return new Value(methodDecl);
        }

        private final MethodDeclarationContext value;
        
        public Value(MethodDeclarationContext methodDecl)
        {
            if (methodDecl == null) {
                throw new IllegalArgumentException("`methodDecl` can't be `null`.");
            }
            value = methodDecl;
        }
        
        // TODO: Add getters for other properties of a method declaration (e.g. qualifiers).

        public String getId() {
            return value.Identifier().getText();
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
