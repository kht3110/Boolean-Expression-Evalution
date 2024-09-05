import java.util.HashMap;

/**
 * This is the parser to parser our tokens generated from the tokenizer based on the following grammar rules:
 * <S>  :== <A> | <A> → <S>
 * <A>  :== <B> | <B> ∨ <A>
 * <B>  :== <C> | <C> ∧ <B>
 * <C>  :== <D> | ¬<D>
 * <D>  :== <variables> | ( <S> )
 * where <variables> are the boolean variables from the expression input in the tokenizer.
 *
 * If the tokens that we get from the tokenizer does not comply to our grammar rules, an exception will be thrown.
 *
 * The end result of the parser is the evaluation of the whole expression that we input in the tokenizer.
 */

public class Parser {

    private Tokenizer tokenizer;                    // The tokenizer that this parser will use
    private HashMap<String, Boolean> values;        // A hashmap that links the variables with their boolean values, which is provided by the Execution class

    /**
     * The constructor for the parser given a tokenizer that is set up for the tokens to be parsed and the map for the boolean values for the variables
     * @param tokenizer     a tokenizer that is set up for tokenizing the expression input
     * @param values        a hashmap to tell the boolean value of the variables
     */
    public Parser(Tokenizer tokenizer, HashMap<String, Boolean> values) {
        this.tokenizer = tokenizer;
        this.values = values;
    }

    /**
     * The start of the parser and to check if we have finished parsing the whole expression.
     * If after parsing the expression, there are still tokens awaiting to be parsed, this is not complying to our grammar rule and an exception will be thrown.
     * @return      the boolean value of the whole expression input
     */
    public boolean evaluate() {
        boolean parseS = parseS();
        if (this.tokenizer.hasNext() || this.tokenizer.getCurrentToken() != null) throw new IllegalArgumentException("Illegal grammar rule");
        return parseS;
    }

    /**
     * The non-terminal symbol to handle the implication operator.
     * This refers to the grammar rule <S>  :== <A> | <A> → <S>.
     * @return      the boolean value of the expression we put to <S>
     */
    private boolean parseS() {

        boolean parseA = parseA();

        if (this.tokenizer.hasNext() && this.tokenizer.getCurrentToken().getType().equals(Token.Type.IMP)) {
            this.tokenizer.next();
            boolean parseS = parseS();
            return new EvaluationImplication(parseA, parseS).evaluate();
        }

        return parseA;
    }

    /**
     * The non-terminal symbol to handle the disjunction operator.
     * This refers to the grammar rule <A>  :== <B> | <B> ∨ <A>.
     * @return      the boolean value of the expression we put to <A>
     */
    private boolean parseA() {

        boolean parseB = parseB();

        if (this.tokenizer.hasNext() && this.tokenizer.getCurrentToken().getType().equals(Token.Type.OR)) {
            this.tokenizer.next();
            boolean parseA = parseA();
            return new EvaluationOr(parseB, parseA).evaluate();
        }

        return parseB;
    }

    /**
     * The non-terminal symbol to handle the conjunction operator.
     * This refers to the grammar rule <B>  :== <C> | <C> ∧ <B>.
     * @return      the boolean value of the expression we put to <B>
     */
    private boolean parseB() {

        boolean parseC = parseC();

        if (this.tokenizer.hasNext() && this.tokenizer.getCurrentToken().getType().equals(Token.Type.AND)) {
            this.tokenizer.next();
            boolean parseB = parseB();
            return new EvaluationAnd(parseC, parseB).evaluate();
        }

        return parseC;
    }

    /**
     * The non-terminal symbol to handle the negation operator.
     * This refers to the grammar rule <C>  :== <D> | ¬<D>.
     * @return      the boolean value of the expression we put to <C>
     */
    private boolean parseC() {

        if (this.tokenizer.hasNext() && this.tokenizer.getCurrentToken().getType().equals(Token.Type.NOT)) {
            this.tokenizer.next();
            boolean parseD = parseD();
            return new EvaluationNegation(parseD).evaluate();
        } else {
            return parseD();
        }

    }

    /**
     * The terminal symbol to handle the variables and brackets.
     * This refers to the grammar rule <D>  :== <variables> | ( <S> ).
     * If the brackets are not in pairs or the tokens that we get are not Type.VAR or Type.LBRA, exception will be thrown.
     * @return      the boolean value of the expression we put to <D>
     */
    private boolean parseD() {

        // the case <variables>
        if (this.tokenizer.getCurrentToken().getType().equals(Token.Type.VAR)) {
            boolean temp = values.get(this.tokenizer.getCurrentToken().getVariable());
            this.tokenizer.next();
            return temp;
        }

        // the case ( <S> )
        else if (this.tokenizer.hasNext() && this.tokenizer.getCurrentToken().getType().equals(Token.Type.LBRA)) {
            this.tokenizer.next();

            // throw exception is a left bracket only
            if (this.tokenizer.getCurrentToken() == null) throw new IllegalArgumentException("Illegal grammar rule");
            boolean parseS = parseS();

            // check if we have a right bracket follows, otherwise throw exception
            if (this.tokenizer.getCurrentToken() != null && this.tokenizer.getCurrentToken().getType().equals(Token.Type.RBRA)) {
                this.tokenizer.next();
            } else throw new IllegalArgumentException("Illegal grammar rule");
            return parseS;
        }

        // throw exception if not Type.VAR or Type.LBRA
        else throw new IllegalArgumentException("Illegal grammar rule");
    }

}
