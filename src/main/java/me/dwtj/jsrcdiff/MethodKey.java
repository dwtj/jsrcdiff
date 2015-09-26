package me.dwtj.jsrcdiff;

import java.util.ArrayList;
import java.util.List;

import org.antlr.grammarsv4.JavaParser.FormalParameterContext;
import org.antlr.grammarsv4.JavaParser.FormalParameterListContext;
import org.antlr.grammarsv4.JavaParser.MethodDeclarationContext;
import org.antlr.grammarsv4.JavaParser.TypeContext;
import org.antlr.v4.runtime.tree.TerminalNode;


public class MethodKey implements Comparable<MethodKey>
{
    private final String key;

    /**
     * @param retType  The return type of the method. `null` means that the method has no return
     *                 type (i.e. it is declared `void`).
     * @param id       The identifier (i.e. the unqualified name) of the method.
     * @param paramList   A list of the method's formal parameters. Both `null` and an empty list are
     *                 mean that the method has no formal parameters.
     */
    public MethodKey(TypeContext retType, TerminalNode id, List<FormalParameterContext> paramList)
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
    public int compareTo(MethodKey other)
    {
        return key.compareTo(other.key);
    }
    
    @Override
    public String toString()
    {
        return key;
    }
    
    public static MethodKey make(MethodDeclarationContext methodDecl)
    {
        FormalParameterListContext paramsCtx = methodDecl.formalParameters().formalParameterList();
        List<FormalParameterContext> list = null;
        if (paramsCtx != null) {
            list = paramsCtx.formalParameter();
        }
        return new MethodKey(methodDecl.type(), methodDecl.Identifier(), list);
    }
}
