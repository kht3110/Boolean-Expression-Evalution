import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Arrays.compareUnsigned;
import static org.junit.Assert.assertEquals;

public class ExecutionTest {

    @Test
    public void testFindAllVariablesSingleVar() {
        String input = "a";

        Set<String> variables = new HashSet<>(Execution.findAllVariables(input));

        Set<String> expected = new HashSet<>();
        expected.add("a");

        assertEquals("Wrong token set returned", expected, variables);
    }

    @Test
    public void testFindAllVariablesDoubleVar() {
        String input = "~(x^y)^((~x∨y)^(~y∨y))";

        Set<String> variables = new HashSet<>(Execution.findAllVariables(input));

        Set<String> expected = new HashSet<>();
        expected.add("x");
        expected.add("y");

        assertEquals("Wrong token set returned", expected, variables);
    }

    @Test
    public void testFindAllVariablesMultipleVar1() {
        String input = "¬(¬x∨¬y)∨(x∨¬z)";

        Set<String> variables = new HashSet<>(Execution.findAllVariables(input));

        Set<String> expected = new HashSet<>();
        expected.add("x");
        expected.add("y");
        expected.add("z");

        assertEquals("Wrong token set returned", expected, variables);
    }

    @Test
    public void testFindAllVariablesMultipleVar2() {
        String input = "~(e^f)∨(a→b)";

        Set<String> variables = new HashSet<>(Execution.findAllVariables(input));

        Set<String> expected = new HashSet<>();
        expected.add("e");
        expected.add("f");
        expected.add("a");
        expected.add("b");

        assertEquals("Wrong token set returned", expected, variables);
    }

    @Test
    public void testGetAllCombinationSingleVar() {
        String input = "a";

        Set<String> variablesSet = new HashSet<>(Execution.findAllVariables(input));
        ArrayList<String> variables = new ArrayList<>(variablesSet);
        HashMap<String, Boolean>[] combinations = Execution.getAllCombination(variables);

        HashMap<String, Boolean>[] expected = new HashMap[]{
                new HashMap<>() {{put("a", true);}},
                new HashMap<>() {{put("a", false);}}
        };

        assertEquals("Wrong combination set returned", expected, combinations);
    }

    @Test
    public void testGetAllCombinationDoubleVar() {
        String input = "~(x^y)^((~x∨y)^(~y∨y))";

        Set<String> variablesSet = new HashSet<>(Execution.findAllVariables(input));
        ArrayList<String> variables = new ArrayList<>(variablesSet);
        HashMap<String, Boolean>[] combinations = Execution.getAllCombination(variables);

        HashMap<String, Boolean>[] expected = new HashMap[]{
                new HashMap<>() {{put("x", true); put("y", true);}},
                new HashMap<>() {{put("x", true); put("y", false);}},
                new HashMap<>() {{put("x", false); put("y", true);}},
                new HashMap<>() {{put("x", false); put("y", false);}}
        };

        assertEquals("Wrong combination set returned", expected, combinations);
    }

    @Test
    public void testGetAllCombinationMultipleVar1() {
        String input = "¬(¬x∨¬y)∨(x∨¬z)";

        Set<String> variablesSet = new HashSet<>(Execution.findAllVariables(input));
        ArrayList<String> variables = new ArrayList<>(variablesSet);
        HashMap<String, Boolean>[] combinations = Execution.getAllCombination(variables);

        HashMap<String, Boolean>[] expected = new HashMap[]{
                new HashMap<>() {{put("x", true); put("y", true); put("z", true);}},
                new HashMap<>() {{put("x", true); put("y", true); put("z", false);}},
                new HashMap<>() {{put("x", true); put("y", false); put("z", true);}},
                new HashMap<>() {{put("x", true); put("y", false); put("z", false);}},
                new HashMap<>() {{put("x", false); put("y", true); put("z", true);}},
                new HashMap<>() {{put("x", false); put("y", true); put("z", false);}},
                new HashMap<>() {{put("x", false); put("y", false); put("z", true);}},
                new HashMap<>() {{put("x", false); put("y", false); put("z", false);}}
        };

        assertEquals("Wrong combination set returned", expected, combinations);
    }

    @Test
    public void testGetAllCombinationMultipleVar2() {
        String input = "~(e^f)∨(a→b)";

        Set<String> variablesSet = new HashSet<>(Execution.findAllVariables(input));
        ArrayList<String> variables = new ArrayList<>(variablesSet);
        HashMap<String, Boolean>[] combinations = Execution.getAllCombination(variables);

        HashMap<String, Boolean>[] expected = new HashMap[]{
                new HashMap<>() {{put("a", true); put("b", true); put("e", true); put("f", true);}},
                new HashMap<>() {{put("a", true); put("b", true); put("e", true); put("f", false);}},
                new HashMap<>() {{put("a", true); put("b", true); put("e", false); put("f", true);}},
                new HashMap<>() {{put("a", true); put("b", true); put("e", false); put("f", false);}},
                new HashMap<>() {{put("a", true); put("b", false); put("e", true); put("f", true);}},
                new HashMap<>() {{put("a", true); put("b", false); put("e", true); put("f", false);}},
                new HashMap<>() {{put("a", true); put("b", false); put("e", false); put("f", true);}},
                new HashMap<>() {{put("a", true); put("b", false); put("e", false); put("f", false);}},
                new HashMap<>() {{put("a", false); put("b", true); put("e", true); put("f", true);}},
                new HashMap<>() {{put("a", false); put("b", true); put("e", true); put("f", false);}},
                new HashMap<>() {{put("a", false); put("b", true); put("e", false); put("f", true);}},
                new HashMap<>() {{put("a", false); put("b", true); put("e", false); put("f", false);}},
                new HashMap<>() {{put("a", false); put("b", false); put("e", true); put("f", true);}},
                new HashMap<>() {{put("a", false); put("b", false); put("e", true); put("f", false);}},
                new HashMap<>() {{put("a", false); put("b", false); put("e", false); put("f", true);}},
                new HashMap<>() {{put("a", false); put("b", false); put("e", false); put("f", false);}}
        };

        assertEquals("Wrong combination set returned", expected, combinations);
    }

    @Test
    public void testFormatPrintOutSingleVar() {
        String input = "a";

        ArrayList<String> values = new ArrayList<>();
        values.add("a");

        String result = Execution.formatPrintOut(values, input);
        String expected = " a | a";

        assertEquals("Wrong string of heading returned", expected, result);

        ArrayList<String> values1 = new ArrayList<>();
        values1.add("T");

        ArrayList<String> values2 = new ArrayList<>();
        values2.add("F");

        result = Execution.formatPrintOut(values1, "T") + "\n" + Execution.formatPrintOut(values2, "F");
        expected = " T | T\n" + " F | F";

        assertEquals("Wrong string of results returned", expected, result);
    }

    @Test
    public void testFormatPrintOutMultipleVar() {
        String input = "¬(¬x∨¬y)∨(x∨¬z)";

        ArrayList<String> values = new ArrayList<>();
        values.add("x");
        values.add("y");
        values.add("z");

        String result = Execution.formatPrintOut(values, input);
        String expected = " x | y | z | ¬(¬x∨¬y)∨(x∨¬z)";

        assertEquals("Wrong string of heading returned", expected, result);

        ArrayList<String> values1 = new ArrayList<>();
        values1.add("T");
        values1.add("T");
        values1.add("T");

        ArrayList<String> values2 = new ArrayList<>();
        values2.add("T");
        values2.add("T");
        values2.add("F");

        result = Execution.formatPrintOut(values1, "T") + "\n" + Execution.formatPrintOut(values2, "T");
        expected = " T | T | T | T\n" + " T | T | F | T";

        assertEquals("Wrong string of results returned", expected, result);
    }
}
