import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    public HashMap<String, Boolean>[] varCombinations(String[] variables) {
        int spacesize = (int) Math.pow(2, variables.length);

        HashMap<String, Boolean>[] rtn = new HashMap[spacesize];

        for (int i = 0; i < spacesize; i++) {
            rtn[i] = new HashMap<>();

            if (i < spacesize / 2) rtn[i].put(variables[0], true);
            else rtn[i].put(variables[0], false);

            if (variables.length > 1) {
                for (int j = 1; j < variables.length; j++) {
                    if (i % Math.pow(2, variables.length - j) < Math.pow(2, variables.length - j - 1)) rtn[i].put(variables[j], true);
                    else rtn[i].put(variables[j], false);
                }
            }
        }

        return rtn;
    }

    public void test(String input, int numVar, HashMap<String, Boolean>[] pending, boolean[] expectedResults) {
        for (int i = 0; i < Math.pow(2, numVar); i++) {
            Tokenizer tokenizer = new Tokenizer(input);
            Parser parser = new Parser(tokenizer, pending[i]);
            assertEquals("Should be " + expectedResults[i], expectedResults[i], parser.evaluate());
        }
    }

    @Test
    public void testSignleVar() {
        String input = "a";
        String[] variables = new String[]{"a"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, false};

        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testNOT() {
        String input = "¬a";
        String[] variables = new String[]{"a"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{false, true};

        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testAND() {
        String input = "a∧b";
        String[] variables = new String[]{"a", "b"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, false, false, false};

        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testOR() {
        String input = "c∨d";
        String[] variables = new String[]{"c", "d"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, true, true, false};

        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testIMP() {
        String input = "e→f";
        String[] variables = new String[]{"e", "f"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, false, true, true};

        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testDoubleVar() {
        String input = "¬a∧b";
        String[] variables = new String[]{"a", "b"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{false, false, true, false};

        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testMID1() {
        String input = "a∧b∨c";
        String[] variables = new String[]{"a", "b", "c"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true,  true, true, false, true, false, true, false};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testMID2() {
        String input = "a∨b∧c";
        String[] variables = new String[]{"a", "b", "c"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true,  true, true, true, true, false, false, false};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testMID3() {
        String input = "(a∧¬(b→c))";
        String[] variables = new String[]{"a", "b", "c"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{false, true, false, false, false, false, false, false};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testMID4() {
        String input = "x∨¬(y∧x)";
        String[] variables = new String[]{"x", "y"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, true, true, true, true, true, true, true};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testADV1() {
        String input = "(¬x∨¬y)∨¬(z∧y)";
        String[] variables = new String[]{"x", "y", "z"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{false, true, true, true, true, true, true, true};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testADV2() {
        String input = "¬(¬x∨¬y)∨(x∨¬z)";
        String[] variables = new String[]{"x", "y", "z"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, true, true, true, false, true, false, true};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testADV3() {
        String input = "¬(x∨¬y)∧z";
        String[] variables = new String[]{"x", "y", "z"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{false, false, false, false, true, false, false, false};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testADV4() {
        String input = "¬(x∨¬y∧z)";
        String[] variables = new String[]{"x", "y", "z"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{false, false, false, false, true, true, false, true};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testADV5() {
        String input = "~(e^f)∨(a→b)";
        String[] variables = new String[]{"a", "b", "e", "f"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, true, true, true, false, true, true, true, true, true, true, true, true, true, true, true};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testADV6() {
        String input = "~(x^y)^((~x∨y)^(~y∨y))";
        String[] variables = new String[]{"x", "y"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{false, false, true, true};
        test(input, variables.length, pending, expectedResults);
    }

    @Test
    public void testADV7() {
        String input = "(x∨y)^((x^z)∨(x^~z))∨((x^y)∨y)";
        String[] variables = new String[]{"x", "y", "z"};

        HashMap<String, Boolean>[] pending = varCombinations(variables);

        boolean[] expectedResults = new boolean[]{true, true, true, true, true, true, false, false};
        test(input, variables.length, pending, expectedResults);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException1() throws IllegalArgumentException {
        String input = "(x∨y)^((x^z)∨(x^~z))∨((x^y)∨y";
        HashMap<String, Boolean> valueAssigned = new HashMap<>();

        valueAssigned.put("x", true);
        valueAssigned.put("y", true);
        valueAssigned.put("z", true);

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, valueAssigned);
        parser.evaluate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException {
        String input = "ab";
        HashMap<String, Boolean> valueAssigned = new HashMap<>();

        valueAssigned.put("a", true);
        valueAssigned.put("b", true);

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, valueAssigned);
        parser.evaluate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException {
        String input = "a∨^b";
        HashMap<String, Boolean> valueAssigned = new HashMap<>();

        valueAssigned.put("a", true);
        valueAssigned.put("b", true);

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, valueAssigned);
        parser.evaluate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException {
        String input = "a)";
        HashMap<String, Boolean> valueAssigned = new HashMap<>();

        valueAssigned.put("a", true);

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, valueAssigned);
        parser.evaluate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException5() throws IllegalArgumentException {
        String input = ")";
        HashMap<String, Boolean> valueAssigned = new HashMap<>();

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, valueAssigned);
        parser.evaluate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException6() throws IllegalArgumentException {
        String input = "¬";
        HashMap<String, Boolean> valueAssigned = new HashMap<>();

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, valueAssigned);
        parser.evaluate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testException7() throws IllegalArgumentException {
        String input = "→a∨b";
        HashMap<String, Boolean> valueAssigned = new HashMap<>();

        valueAssigned.put("a", true);
        valueAssigned.put("b", true);

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, valueAssigned);
        parser.evaluate();
    }
}
