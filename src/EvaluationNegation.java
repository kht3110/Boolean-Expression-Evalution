/**
The evaluation for the expression with an NOT operator, for example, ¬a.
 **/

public class EvaluationNegation implements Evaluation {

    boolean value;      // the boolean input, i.e. a in our example

    /**
     * The constructor for an to-be-evaluated expression with an NOT operator
     * @param value    the boolean input
     */
    public EvaluationNegation(boolean value) {
        this.value = value;
    }

    /**
     * The evalution for an expression with an NOT operator according to the following truth table
     *  a | ¬a
     *  T | F
     *  F | T
     * @return      the evaluation of ¬a
     */
    public boolean evaluate() {
        return !this.value;
    }

}
