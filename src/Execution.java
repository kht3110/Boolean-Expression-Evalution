import java.util.*;

/**
 * The user interface for inputting the expression and particular boolean values for the variables.
 */

public class Execution {

    public static void main(String[] args) {

        // set up the scanner for input for later use
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the expression that you would like to evaluate.\n" +
                "Please use the following representations:\n" +
                "Negation: ¬;\n" +
                "Disjunction: ∨;\n" +
                "Conjunction: ∧;\n" +
                "Implication: →.\n");

        while (sc.hasNext()) {
            String input = sc.next();

            // get all the variables from the expression and sort them in order to be printed in the truth table later
            ArrayList<String> variables = new ArrayList<>(findAllVariables(input));
            variables.sort(null);

            System.out.println("Which one would you like to perform?\n" +
                    "1. Evaluate all the possible combinations of the variables\n" +
                    "2. Evaluate a particular total variable assignment");

            while (sc.hasNext()) {
                String choice = sc.next();

                // return the truth table for all the possible combinations of the boolean values of the variables
                if (choice.equals("1")) {
                    printResults(variables, input);
                    break;
                }

                // return the evaluation based on the particular input of boolean values of the variables
                else if (choice.equals("2")) {

                    HashMap<String, Boolean> assignedValues = new HashMap<>();

                    System.out.println("Please provide the boolean values for the variables, where T stands for true and F stands for false.");

                    for (String var: variables) {
                        System.out.println(var + "?");

                        while (sc.hasNext()) {

                            String bool = sc.next();

                            if (bool.equals("true") || bool.equals("T") || bool.equals("t")) {
                                assignedValues.put(var, true);
                                break;
                            } else if (bool.equals("false") || bool.equals("F") || bool.equals("f")) {
                                assignedValues.put(var, false);
                                break;
                            } else {
                                System.out.println("Invalid input. Please enter either T or F.");
                            }
                        }
                    }

                    printHeading(variables, input);
                    printOneResult(assignedValues, variables, input);
                    break;

                }

                // ask the users to re-enter the choice if the input is invalid
                else {
                    System.out.println("Invalid input. Please enter either 1 or 2.");
                }
            }

            System.out.println("Please enter the expression that you would like to evaluate.\n" +
                    "Please use the following representations:\n" +
                    "Negation: ¬;\n" +
                    "Disjunction: ∨;\n" +
                    "Conjunction: ∧;\n" +
                    "Implication: →.\n");
        }
    }

    /**
     * Get all the variables from the expression.
     * @param input     the expression to be parsed
     * @return          the set of the variables in the expression
     */
    public static Set<String> findAllVariables(String input) {

        Set<String> rtn = new HashSet<>();

        for (int i = 0; i < input.length(); i++) {
            if (Character.isAlphabetic(input.charAt(i))) rtn.add(input.substring(i, i + 1));
        }

        return rtn;
    }

    /**
     * Print out the header for the returned table.
     * @param variables     the variables that are in the expression input
     * @param input         the expression to be parsed
     */
    public static void printHeading(ArrayList<String> variables, String input) {
        System.out.println(formatPrintOut(variables, input));
    }

    /**
     * Print out the truth table for the expression.
     * @param variables     the variables that are in the expression input
     * @param input         the expression to be parsed
     */
    public static void printResults(ArrayList<String> variables, String input) {

        // get all the possible combination of the boolean values of the variables
        HashMap<String, Boolean>[] pending = getAllCombination(variables);

        // print the header
        printHeading(variables, input);

        // loop through the possible combinations of the boolean values of the variables and print out the results one by one
        for (HashMap<String, Boolean> assignedValues: pending) {
            printOneResult(assignedValues, variables, input);
        }

        System.out.println("");
    }

    /**
     * Parse the expression given the boolean values for each variable and print out the result according to the format we set in the formatPrintOut function.
     * @param assignedValues        the map linking the boolean values to the variables in the expression
     * @param variables             the variables in the expression input
     * @param input                 the expression to be parsed
     */
    public static void printOneResult(HashMap<String, Boolean> assignedValues, ArrayList<String> variables, String input) {
        Tokenizer tokenizer = new Tokenizer(input);

        Parser parser = new Parser(tokenizer, assignedValues);

        ArrayList<String> valuesInOrder = new ArrayList<>();

        // put the boolean values to valuesInOrder according to the variable order for showing that in the String representation later
        for (int i = 0; i < variables.size(); i++) {
            // since I would like the boolean values to be shown in T/F style, I take the first char of the boolean value and turn it to upper case
            valuesInOrder.add(String.valueOf(assignedValues.get(variables.get(i))).substring(0, 1).toUpperCase());
        }

        // since I would like the boolean values to be shown in T/F style, I take the first char of the boolean value and turn it to upper case
        System.out.println(formatPrintOut(valuesInOrder, String.valueOf(parser.evaluate()).substring(0, 1).toUpperCase()));

    }

    /**
     * Generate all the possible combination based on the variables that are in the expression to be parsed.
     * @param variables     the list of variables in the expression input
     * @return              the hashmap linking the variables to their boolean values
     */
    public static HashMap<String, Boolean>[] getAllCombination(ArrayList<String> variables) {
        int spaceSize = (int) Math.pow(2, variables.size());
        HashMap<String, Boolean>[] rtn = new HashMap[spaceSize];
        for (int i = 0; i < spaceSize; i++) {
            rtn[i] = new HashMap<>();
        }

        for (int i = 0; i < variables.size(); i++) {
            int count = 0;
            int factor = (int) Math.pow(2, (i + 1));

            while (count < spaceSize) {

                int interval = spaceSize / factor;

                for (int j = 0; j < interval; j++) {
                    rtn[j + count].put(variables.get(i), true);
                }

                for (int j = 0; j < interval; j++) {
                    rtn[j + count + interval].put(variables.get(i), false);
                }
                count += interval * 2;
            }
        }

        return rtn;
    }

    /**
     * Format the String representation for the ArrayList and the result.
     * @param values    the ArrayList of the boolean variables or their values
     * @param result    the boolean value of the expression
     * @return          the String representation of the ArrayList and result to be printed out
     */
    public static String formatPrintOut(ArrayList<String> values, String result) {

        String rtn = "";

        // appending the string with each value in the ArrayList
        for (int i = 0; i < values.size(); i++) {
            rtn += " " + values.get(i) + " |";
        }

        // appending the string with the result
        rtn += " " + result;

        return rtn;
    }

}
