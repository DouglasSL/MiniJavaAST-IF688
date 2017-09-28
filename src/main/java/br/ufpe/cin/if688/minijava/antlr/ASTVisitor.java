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
		VarDeclList vdl = new VarDeclList();
		StatementList sl = new StatementList();
		Exp e = (Exp) ctx.expression().accept(this);
		
		for (int i = 1; i < ctx.identifier().size(); i++) {
			fl.addElement(new Formal((Type) ctx.type(i).accept(this), (Identifier) ctx.identifier(i).accept(this)));
		}
		
		for (int i = 0; i < ctx.varDeclaration().size(); i++) {
			VarDeclarationContext vdc = ctx.varDeclaration().get(i);
			vdl.addElement((VarDecl) vdc.accept(this));;
		}
		
		for (int i = 0; i < ctx.statement().size(); i++) {
			StatementContext sc = ctx.statement(i);
			sl.addElement((Statement) sc.accept(this));
		}
		
		return new MethodDecl(type, aid0, fl, vdl, sl, e);
	}

	@Override
	public Object visitType(TypeContext ctx) {
		String type = ctx.getText();
		
		if (type.equals("int[]")) {
			return new IntArrayType();			
		} else if (type.equals("boolean")) {
			return new BooleanType();
		} else if (type.equals("int")) {
			return new IntegerType();
		} else {
			return new IdentifierType(type);
		}
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		String token = ctx.getStart().getText();

		if (token.equals("{")) {
			StatementList sl = new StatementList();

			for (int i = 0 ; i < ctx.statement().size(); i++) {
				StatementContext sc = ctx.statement(i);
				sl.addElement((Statement) sc.accept(this));
			}
			
			return new Block(sl);
			
		} else if (token.equals("if")) {
			Exp exp = (Exp) ctx.expression(0).accept(this);
			
			Statement s1 = (Statement) ctx.statement(0).accept(this);
			Statement s2 = (Statement) ctx.statement(1).accept(this);

			return new If(exp, s1, s2);
			
		} else if (token.equals("while")) {
			Exp e = (Exp) ctx.expression(0).accept(this);
			Statement s = (Statement) ctx.statement(0).accept(this);

			return new While(e, s);
			
		} else if (token.equals("System.out.println")) {
			Exp e = (Exp) ctx.expression(0).accept(this);

			return new Print(e);
			
		} else if (ctx.expression().size() == 1) {
			Identifier aid = (Identifier) ctx.identifier().accept(this);
			Exp e = (Exp) ctx.expression(0).accept(this);

			return new Assign(aid, e);
			
		} else {
			
			Identifier aid = (Identifier) ctx.identifier().accept(this);
			Exp e1 = (Exp) ctx.expression(0).accept(this);
			Exp e2 = (Exp) ctx.expression(1).accept(this);

			return new ArrayAssign(aid, e1, e2);
		}
	}

	@Override
	public Object visitExpression(ExpressionContext ctx) {
		return null;
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		return new Identifier(ctx.getText());
	}

}
