package br.ufpe.cin.if688.minijava.antlr;
// Generated from imp.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link impParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface impVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link impParser#goal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoal(impParser.GoalContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#mainClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainClass(impParser.MainClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(impParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(impParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(impParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(impParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(impParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(impParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link impParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(impParser.IdentifierContext ctx);
}