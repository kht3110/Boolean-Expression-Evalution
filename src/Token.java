/**
 * Token objects to store tokens that are extracted from the tokenizer. Token types are as follows:
 * VAR: variable
 * NOT: negation, i.e. ¬
 * OR: disjuntion, i.e. ∨
 * AND: conjuntion, i.e. ∧
 * IMP: implication, i.e. →
 * LBRA: left bracklet, i.e. (
 * RBRA: right bracklet, i.e. )
 */
public class Token {

    //    Enum Types defined according to the previous definition.
    public enum Type {
        VAR, NOT, OR, AND, IMP, LBRA, RBRA;
    }

    private String variable;        // The variable to be stored in the String form. The name of the variable if the token type is VAR, else the operator of the type
    private Type type;              // The type of the token according to the enum

    public Token(String variable, Type type) {
        this.variable = variable;
        this.type = type;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
