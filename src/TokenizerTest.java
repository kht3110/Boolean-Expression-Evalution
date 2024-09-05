import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {

    @Test
    public void testSignleVar() {
        String input = "a";
        Tokenizer tokenizer = new Tokenizer(input);

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "a", tokenizer.getCurrentToken().getVariable());
    }

    @Test
    public void testDoubleVar() {
        String input = "¬a∧b";
        Tokenizer tokenizer = new Tokenizer(input);

        assertEquals("Wrong Token Type", Token.Type.NOT, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "¬", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "a", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.AND, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "∧", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "b", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Current Token should be null", null, tokenizer.getCurrentToken());
    }

    @Test
    public void testAND() {
        String input = "a∧b";
        Tokenizer tokenizer = new Tokenizer(input);

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "a", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.AND, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "∧", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "b", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Current Token should be null", null, tokenizer.getCurrentToken());
    }

    @Test
    public void testOR() {
        String input = "c∨d";
        Tokenizer tokenizer = new Tokenizer(input);

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "c", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.OR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "∨", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "d", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Current Token should be null", null, tokenizer.getCurrentToken());
    }

    @Test
    public void testIMP() {
        String input = "e→f";
        Tokenizer tokenizer = new Tokenizer(input);

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "e", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.IMP, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "→", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "f", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Current Token should be null", null, tokenizer.getCurrentToken());
    }

    @Test
    public void testMID() {
        String input = "(a∧¬(b→c))";
        Tokenizer tokenizer = new Tokenizer(input);

        assertEquals("Wrong Token Type", Token.Type.LBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "(", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "a", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.AND, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "∧", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.NOT, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "¬", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.LBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "(", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "b", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.IMP, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "→", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "c", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.RBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", ")", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.RBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", ")", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Current Token should be null", null, tokenizer.getCurrentToken());
    }

    @Test
    public void testADV() {
        String input = "~(e^f)∨(a→b)";
        Tokenizer tokenizer = new Tokenizer(input);

        assertEquals("Wrong Token Type", Token.Type.NOT, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "¬", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.LBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "(", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "e", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.AND, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "∧", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "f", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.RBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", ")", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.OR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "∨", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.LBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "(", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "a", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.IMP, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "→", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.VAR, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", "b", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Wrong Token Type", Token.Type.RBRA, tokenizer.getCurrentToken().getType());
        assertEquals("Wrong Token Value", ")", tokenizer.getCurrentToken().getVariable());

        tokenizer.next();

        assertEquals("Current Token should be null", null, tokenizer.getCurrentToken());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException1() throws IllegalArgumentException {
        String input = "=";
        Tokenizer tokenizer = new Tokenizer(input);
        while (tokenizer.hasNext()) {
            tokenizer.next();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException {
        String input = "5";
        Tokenizer tokenizer = new Tokenizer(input);
        while (tokenizer.hasNext()) {
            tokenizer.next();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException {
        String input = ".";
        Tokenizer tokenizer = new Tokenizer(input);
        while (tokenizer.hasNext()) {
            tokenizer.next();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException {
        String input = "a+";
        Tokenizer tokenizer = new Tokenizer(input);
        while (tokenizer.hasNext()) {
            tokenizer.next();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException5() throws IllegalArgumentException {
        String input = "ab6";
        Tokenizer tokenizer = new Tokenizer(input);
        while (tokenizer.hasNext()) {
            tokenizer.next();
        }
    }
}
