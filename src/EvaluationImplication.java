/**
The evaluation for the expression with an IMP operator, for example, a → b.
 **/

public class EvaluationImplication implements Evaluation {

    boolean value1;     // the first boolean input, i.e. a in our example
    boolean value2;     // the second boolean input, i.e. b in our example

    /**
     * The constructor for an to-be-evaluated expression with an IMP operator
     * @param value1    the first boolean input
     * @param value2    the second boolean input
     */
    public EvaluationImplication(boolean value1, boolean value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    /**
     * The evalution for an expression with an IMP operator according to the following truth table
     *  a | b | a → b
     *  T | T | T
     *  T | F | F
     *  F | T | T
     *  F | F | T
     * @return      the evaluation of a → b
     */
    public boolean evaluate() {
        if (this.value1 && !this.value2) return false;
        else return true;
    }

}
