import java.util.*;

public class Main2{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Define input symbols (lower-case alphabet and digits)
        String[] symbols = "abcdefghijklmnopqrstuvwxyz0123456789".split("");
        int numSymbols = symbols.length;

        // Define number of states
        int numStates = 3; // 1: Start, 2: Valid, 3: Invalid

        // Define initial state
        int initialState = 1;

        // Define final states
        Set<Integer> finalStates = new HashSet<>();
        finalStates.add(2); // State 2 is the only final state

        // Transition table
        // State 1: Start state, only letters are valid
        // State 2: Valid state, accepts letters and digits
        // State 3: Invalid state
        int[][] transitionTable = {
            // Transitions from State 1
            {2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            // Transitions from State 2
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            // Transitions from State 3
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}
        };

        // Input: string to validate
        System.out.println("Enter the input string:");
        String inputString = sc.next();

        // Process input string
        int currentState = initialState;
        boolean isValid = true;

        for (char ch : inputString.toCharArray()) {
            String currentSymbol = String.valueOf(ch);
            int symbolIndex = Arrays.asList(symbols).indexOf(currentSymbol);

            if (symbolIndex == -1) {
                System.out.println("Invalid input symbol encountered: " + currentSymbol);
                isValid = false;
                break;
            }

            currentState = transitionTable[currentState - 1][symbolIndex];

            if (currentState == 3) {
                isValid = false;
                break;
            }
        }

        // Check if the string halts in a final state
        if (isValid && finalStates.contains(currentState)) {
            System.out.println("Valid String");
        } else {
            System.out.println("Invalid String");
        }

        sc.close();
    }
}
