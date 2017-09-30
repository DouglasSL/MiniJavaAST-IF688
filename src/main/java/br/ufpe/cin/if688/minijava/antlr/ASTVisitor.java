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
		
		for (int i = 0; i < ctx.classDeclaration().size(); i++) {
			ClassDeclarationContext cdc =  ctx.classDeclaration().get(i);
			classList.addElement((ClassDecl) cdc.accept(this));
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
		
		for (int i = 0; i < ctx.varDeclaration().size(); i++) {
			VarDeclarationContext vdc = ctx.varDeclaration().get(i);
			vdl.addElement((VarDecl) vdc.accept(this));
		}
		
		for (int i = 0; i < ctx.methodDeclaration().size(); i++) {
			MethodDeclarationContext mdc = ctx.methodDeclaration().get(i);
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
		Exp e = null;
		
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
		
		Object auxExp = ctx.expression().accept(this);
		
		if (auxExp instanceof Exp){
			e = (Exp) auxExp;
		} else {
			e =  new IdentifierExp(ctx.expression().getText());
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
				StatementContext sc = ctx.statement().get(i);
				sl.addElement((Statement) sc.accept(this));
			}
			
			return new Block(sl);
			
		} else if (token.equals("if")) {
			Object auxExp = ctx.expression(0).accept(this);
			Exp e = null;
			if (auxExp instanceof Exp){
				e = (Exp) auxExp;
			} else {
				e =  new IdentifierExp(ctx.expression(0).getText());
			}
			Statement s1 = (Statement) ctx.statement().get(0).accept(this);
			Statement s2 = (Statement) ctx.statement().get(1).accept(this);

			return new If(e, s1, s2);
			
		} else if (token.equals("while")) {
			Object auxExp = ctx.expression(0).accept(this);
			Exp e = null;
			if (auxExp instanceof Exp){
				e = (Exp) auxExp;
			} else {
				e =  new IdentifierExp(ctx.expression(0).getText());
			}
			Statement s = (Statement) ctx.statement().get(0).accept(this);

			return new While(e, s);
			
		} else if (token.equals("System.out.println")) {
			Object auxExp = ctx.expression(0).accept(this);
			Exp e = null;
			if (auxExp instanceof Exp){
				e = (Exp) auxExp;
			} else {
				e =  new IdentifierExp(ctx.expression(0).getText());
			}

			return new Print(e);
			
		} else if (ctx.expression().size() == 1) {
			Identifier aid = (Identifier) ctx.identifier().accept(this);
			Object auxExp = ctx.expression(0).accept(this);
			Exp e = null;
			if (auxExp instanceof Exp){
				e = (Exp) auxExp;
			} else {
				e =  new IdentifierExp(ctx.expression(0).getText());
			}
			return new Assign(aid, e);
			
		} else {
			Identifier aid = (Identifier) ctx.identifier().accept(this);
			Object auxExp = ctx.expression(0).accept(this);
			Exp e1 = null;
			if (auxExp instanceof Exp){
				e1 = (Exp) auxExp;
			} else {
				e1 =  new IdentifierExp(ctx.expression(0).getText());
			}
			Object auxExp1 = ctx.expression(1).accept(this);
			Exp e2 = null;
			if (auxExp1 instanceof Exp){
				e2 = (Exp) auxExp1;
			} else {
				e2 =  new IdentifierExp(ctx.expression(1).getText());
			}

			return new ArrayAssign(aid, e1, e2);
		}
	}

	@Override
	public Object visitExpression(ExpressionContext ctx) {
		int eCount = ctx.expression().size();
		int cCount = ctx.getChildCount();
		String tokenS = ctx.getStart().getText();

		if (cCount > 4 && ctx.getChild(1).getText().equals(".")) {
			Object auxExp = ctx.expression(0).accept(this);
			Exp e = null;
			if (auxExp instanceof Exp){
				e = (Exp) auxExp;
			} else {
				e =  new IdentifierExp(ctx.expression(0).getText());
			}
			Identifier aid = (Identifier) ctx.identifier().accept(this);

			ExpList el = new ExpList();
			Exp e1 = null;
			Object auxExp1 = null;
			for (int i = 1; i < ctx.expression().size(); i++) {
				ExpressionContext ec = ctx.expression(i);
				
				auxExp1 = ec.accept(this);
				
				if (auxExp1 instanceof Exp){
					e1 = (Exp) auxExp1;
				} else {
					e1 =  new IdentifierExp(ctx.expression(i).getText());
				}
				
				el.addElement(e1);
			}

			return new Call(e, aid, el);
		}

		if (eCount == 2) {
			Object auxExp = ctx.expression(0).accept(this);
			Exp e1 = null;
			if (auxExp instanceof Exp){
				e1 = (Exp) auxExp;
			} else {
				e1 =  new IdentifierExp(ctx.expression(0).getText());
			}
			Object auxExp1 = ctx.expression(1).accept(this);
			Exp e2 = null;
			if (auxExp1 instanceof Exp){
				e2 = (Exp) auxExp1;
			} else {
				e2 =  new IdentifierExp(ctx.expression(1).getText());
			}
			String operator = ctx.getChild(1).getText();

			if (cCount == 3) {
				switch (operator) {
				case "&&": return new And(e1, e2);
				case "<": return new LessThan(e1, e2);
				case "+": return new Plus(e1, e2);
				case "-": return new Minus(e1, e2);
				default: return new Times(e1, e2); 
				}
				
			} else {
				return new ArrayLookup(e1, e2);
			}
		} else if (eCount == 1) {
			Object auxExp = ctx.expression(0).accept(this);
			Exp e = null;
			if (auxExp instanceof Exp){
				e = (Exp) auxExp;
			} else {
				e =  new IdentifierExp(ctx.expression(0).getText());
			}

			switch (tokenS) {
			case "!": return new Not(e);
			case "(": return e;
			case "new": return new NewArray(e);
			default: return new ArrayLength(e);
			}
			
		} else if (tokenS.equals("new")) {
			return new NewObject((Identifier) ctx.identifier().accept(this));
			
		} else if (tokenS.equals("this")) {
			return new This();
			
		} else if (tokenS.endsWith("true")) {
			return new True();
			
		} else if (tokenS.endsWith("false")) {
			return new False();
			
		} else if (tokenS.matches("\\d+")) {
			return new IntegerLiteral(Integer.parseInt(ctx.INTEGER_LITERAL().getText()));
		} else {
			return (Identifier) ctx.identifier().accept(this);
		}
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		return new Identifier(ctx.getText());
	}

}
