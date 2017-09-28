package br.ufpe.cin.if688.minijava.antlr;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufpe.cin.if688.minijava.antlr.impParser.ClassDeclarationContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.ExpressionContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.GoalContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.IdentifierContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.MainClassContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.MethodDeclarationContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.StatementContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.TypeContext;
import br.ufpe.cin.if688.minijava.antlr.impParser.VarDeclarationContext;
import br.ufpe.cin.if688.minijava.ast.*;

public class ASTVisitor implements impVisitor<Object>{

	@Override
	public Object visit(ParseTree arg0) {
		return arg0.accept(this);
	}

	@Override
	public Object visitChildren(RuleNode arg0) {
		return null;
	}

	@Override
	public Object visitErrorNode(ErrorNode arg0) {
		return null;
	}

	@Override
	public Object visitTerminal(TerminalNode arg0) {
		return null;
	}

	@Override
	public Object visitGoal(GoalContext ctx) {
		MainClass mainClass = (MainClass) ctx.mainClass().accept(this);
		ClassDeclList classList = new ClassDeclList();
		
		for (ClassDeclarationContext classDecl : ctx.classDeclaration()) {
			classList.addElement((ClassDecl) classDecl.accept(this));
		}
		
		return new Program(mainClass, classList);
	}

	@Override
	public Object visitMainClass(MainClassContext ctx) {
		Identifier ai1 = (Identifier) ctx.identifier(0).accept(this);
		Identifier ai2 = (Identifier) ctx.identifier(0).accept(this);
		Statement as = (Statement) ctx.statement().accept(this);
		return new MainClass(ai1, ai2, as);
	}

	@Override
	public Object visitClassDeclaration(ClassDeclarationContext ctx) {
		VarDeclList vdl = new VarDeclList();
		MethodDeclList mdl = new MethodDeclList();
		
		for (VarDeclarationContext vdc : ctx.varDeclaration()) {
			vdl.addElement((VarDecl) vdc.accept(this));
		}
		
		for (MethodDeclarationContext mdc : ctx.methodDeclaration()) {
			mdl.addElement((MethodDecl) mdc.accept(this));
		}
		
		if (ctx.identifier().size() == 1) {
			Identifier aid = (Identifier) ctx.identifier(0).accept(this);
		
			return new ClassDeclSimple(aid, vdl, mdl);
			
		} else {
			Identifier aid1 = (Identifier) ctx.identifier(0).accept(this);
			Identifier aid2 = (Identifier) ctx.identifier(1).accept(this);
			
			return new ClassDeclExtends(aid1, aid2, vdl, mdl);
		}
		
	}

	@Override
	public Object visitVarDeclaration(VarDeclarationContext ctx) {
		Type type = (Type) ctx.type().accept(this);
		Identifier aid = (Identifier) ctx.identifier().accept(this);
		return new VarDecl(type, aid);
	}

	@Override
	public Object visitMethodDeclaration(MethodDeclarationContext ctx) {
		Type type = (Type) ctx.type(0).accept(this);
		Identifier aid0 = (Identifier) ctx.identifier(0).accept(this);
		FormalList fl = new FormalList();
		VarDeclList vl = new VarDeclList();
		StatementList sl = new StatementList();
		Exp e = (Exp) ctx.expression().accept(this);
		
		for (int i = 1; i < ctx.identifier().size(); i++) {
			
		}
		
		return new MethodDecl(null, null, null, null, null, null);
	}

	@Override
	public Object visitType(TypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitExpression(ExpressionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
