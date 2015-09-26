package me.dwtj.jsrcdiff;

import java.util.List;

import org.antlr.grammarsv4.JavaBaseVisitor;
import org.antlr.grammarsv4.JavaParser;
import org.antlr.grammarsv4.JavaParser.AnnotationTypeDeclarationContext;
import org.antlr.grammarsv4.JavaParser.ClassBodyDeclarationContext;
import org.antlr.grammarsv4.JavaParser.ConstructorDeclarationContext;
import org.antlr.grammarsv4.JavaParser.EnumDeclarationContext;
import org.antlr.grammarsv4.JavaParser.FieldDeclarationContext;
import org.antlr.grammarsv4.JavaParser.GenericConstructorDeclarationContext;
import org.antlr.grammarsv4.JavaParser.GenericMethodDeclarationContext;
import org.antlr.grammarsv4.JavaParser.InterfaceDeclarationContext;
import org.antlr.grammarsv4.JavaParser.MemberDeclarationContext;
import org.antlr.grammarsv4.JavaParser.MethodDeclarationContext;
import org.antlr.grammarsv4.JavaParser.ModifierContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;


public class MapsBuilder
{
    protected final FieldMap fieldMap = new FieldMap();
    protected final MethodMap methodMap = new MethodMap();

    protected boolean mapsAreBuilt = false;

    
    public FieldMap getFieldMap() {
        assert mapsAreBuilt: "Must call `build()` before a `FieldMap` can be retrieved.";
        return fieldMap;
    }

    public MethodMap getMethodMap() {
        assert mapsAreBuilt: "Must call `build()` before a `MethodMap` can be retrieved.";
        return methodMap;
    }
    
    public FieldMap build(JavaParser parser)
    {
        Visitor visitor = new Visitor();
        visitor.visit(parser.compilationUnit());

        mapsAreBuilt = true;
        return fieldMap;
    }
    

    protected class Visitor extends JavaBaseVisitor<Void>
    {
        /**
         * A property used to propagate a class body declaration's modifiers down to member
         * declarations (field declarations and method declarations in particular).
         */
        protected final ParseTreeProperty<List<ModifierContext>> modifiersProp;
        
        public Visitor()
        {
            modifiersProp = new ParseTreeProperty<List<ModifierContext>>();
        }        

        /**
         * Sets the modifiers of the given node to be the given list.
         */
        protected void setModifiers(ParseTree node, List<ModifierContext> modifiers) {
            modifiersProp.put(node, modifiers);
        }
        
        /**
         * Gets the modifiers which have been previously set for the given node.
         */
        protected List<ModifierContext> getModifiers(ParseTree node) {
            return modifiersProp.get(node);
        }
        
        /**
         * Propagates the modifiers from the parent of `node` to `node` itself.
         */
        protected void propagateModifiersFromParent(ParseTree node)
        {
            ParseTree parent = node.getParent();
            if (parent == null) {
                String msg = "Node has no parent from which to propagate modifiers.";
                throw new IllegalArgumentException(msg);
            }

            List<ModifierContext> modifiers = modifiersProp.get(parent);
            if (modifiers == null) {
                String msg = "Parent has no modifiers to propagate down.";
                throw new IllegalArgumentException(msg);
            }
            
            modifiersProp.put(node, modifiers);
        }

        @Override
        public Void visitClassBodyDeclaration(ClassBodyDeclarationContext ctx)
        {
            MemberDeclarationContext memberDecl = ctx.memberDeclaration();
            if (memberDecl != null) {
                setModifiers(ctx, ctx.modifier());
                visit(memberDecl);
            }
            return null;
        }
        
        @Override
        public Void visitMemberDeclaration(MemberDeclarationContext ctx)
        {
            propagateModifiersFromParent(ctx);
            return visitChildren(ctx);
        }
        
        @Override
        public Void visitMethodDeclaration(MethodDeclarationContext ctx)
        {
            propagateModifiersFromParent(ctx);
            methodMap.add(getModifiers(ctx), ctx);
            return null;
        }
        
        @Override
        public Void visitFieldDeclaration(FieldDeclarationContext ctx)
        {
            propagateModifiersFromParent(ctx);
            fieldMap.add(getModifiers(ctx), ctx);
            return null;
        }
        
        @Override
        public Void visitGenericMethodDeclaration(GenericMethodDeclarationContext ctx)
        {
            String msg = "This proof of concept does not yet support generic methods.";
            throw new UnsupportedOperationException(msg);
        }
        
        @Override
        public Void visitConstructorDeclaration(ConstructorDeclarationContext ctx)
        {
            String msg = "This proof of concept does not yet support constructors.";
            throw new UnsupportedOperationException(msg);
        }
        
        @Override
        public Void visitGenericConstructorDeclaration(GenericConstructorDeclarationContext ctx)
        {
            String msg = "This proof of concept does not yet support generic constructors.";
            throw new UnsupportedOperationException(msg);
        }
        
        @Override
        public Void visitInterfaceDeclaration(InterfaceDeclarationContext ctx)
        {
            String msg = "This proof of concept does not yet support interface declarations.";
            throw new UnsupportedOperationException(msg);
        }
        
        @Override
        public Void visitAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx)
        {
            String msg = "This proof of concept does not yet support annotation type declarations.";
            throw new UnsupportedOperationException(msg);
        }
        
        @Override
        public Void visitEnumDeclaration(EnumDeclarationContext ctx)
        {
            String msg = "This proof of concept does not yet support enum declarations.";
            throw new UnsupportedOperationException(msg);
        }
    }
}
