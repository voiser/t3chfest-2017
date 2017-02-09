// Generated from /Users/david/Desktop/tf/CallCost.g4 by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CallCostParser}.
 */
public interface CallCostListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CallCostParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(CallCostParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallCostParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(CallCostParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link CallCostParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDef(CallCostParser.DefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallCostParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDef(CallCostParser.DefContext ctx);
}