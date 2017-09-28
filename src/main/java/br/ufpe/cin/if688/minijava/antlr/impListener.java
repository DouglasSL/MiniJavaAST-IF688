package br.ufpe.cin.if688.minijava.antlr;
// Generated from imp.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link impParser}.
 */
public interface impListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link impParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(impParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(impParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(impParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(impParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(impParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(impParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(impParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(impParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(impParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(impParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(impParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(impParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(impParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(impParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(impParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(impParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link impParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(impParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link impParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(impParser.IdentifierContext ctx);
}