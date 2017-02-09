// Generated from /Users/david/Desktop/tf/CallCost.g4 by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CallCostParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CallCostVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CallCostParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(CallCostParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link CallCostParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef(CallCostParser.DefContext ctx);
}