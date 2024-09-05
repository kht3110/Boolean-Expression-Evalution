/**
 * A class to tokenize the boolean expression to be evaluated, which is to transform a String of boolean expression into tokens.
 * If the character being checked can be recognized as one of the token types defined in Token.java, a new token is formed.
 * Otherwise, an IllegalTokenException is thrown.
 */

public class Tokenizer {

    private String buffer;              // String input to be transformed into tokens
    private Token currentToken;         // The current token to be parsed in the parser

    /**
     * The constructor for the tokenizer given an boolean expression to be parsed
     * @param buffer    a boolean expression to be evaluated
     */
    public Tokenizer(String buffer) {
        this.buffer = buffer;
        this.next();
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    /**
     * Get the next token based on the buffer and the tokens defined.
     * If the token cannot be recognized as any of the token types, an IllegalTokenException is thrown.
     * If the buffer is empty, return a null token.
     */
    public void next() {

        // trim the white spaces at the front of the buffer
        this.buffer = this.buffer.trim();

        // return a null token if the buffer is empty
        if (this.buffer == null | this.buffer == "" | this.buffer.length() == 0) {
            this.currentToken = null;
        } else {

            // define the first char in buffer for checking
            char firstChar = this.buffer.charAt(0);

            if (firstChar == '¬' || firstChar == '~') {
                this.currentToken = new Token("¬", Token.Type.NOT);
            } else if (firstChar == '∨') {
                this.currentToken = new Token("∨", Token.Type.OR);
            } else if (firstChar == '∧' || firstChar == '^') {
                this.currentToken = new Token("∧", Token.Type.AND);
            } else if (firstChar == '→') {
                this.currentToken = new Token("→", Token.Type.IMP);
            } else if (firstChar == '(') {
                this.currentToken = new Token("(", Token.Type.LBRA);
            } else if (firstChar == ')') {
                this.currentToken = new Token(")", Token.Type.RBRA);
            } else if (Character.isAlphabetic(firstChar)) {
                String varName = buffer.substring(0, 1);
                this.currentToken = new Token(varName, Token.Type.VAR);
            } else
                // thrown exception is not one of the token type cases
                throw new IllegalArgumentException("Illegal Token");

            // delete the first char from the buffer as that is turned into a token already
            this.buffer = this.buffer.substring(1);
        }

    }

    /**
     * Check if there is more char in the buffer string to be transformed in tokens
     * @return  True if the buffer is not empty, false otherwise
     */
    public boolean hasNext() {
        return this.buffer != null && this.buffer != "";
    }

}
