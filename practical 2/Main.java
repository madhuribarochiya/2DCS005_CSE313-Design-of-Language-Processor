import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of input symbols:");
        int numSymbols = sc.nextInt();

        
        String[] symbols = new String[numSymbols];
        for (int i = 0; i < numSymbols; i++) {
            System.out.println("Enter symbol " + (i + 1) + ":");
            symbols[i] = sc.next();
        }

        System.out.println("Enter number of states:");
        int numStates = sc.nextInt();

        
        System.out.println("Enter initial state (1 to " + numStates + "):");
        int initialState = sc.nextInt();

        System.out.println("Enter number of final states:");
        int numFinalStates = sc.nextInt();


        Set<Integer> finalStates = new HashSet<>();
        for (int i = 0; i < numFinalStates; i++) {
            System.out.println("Enter final state " + (i + 1) + " (1 to " + numStates + "):");
            finalStates.add(sc.nextInt());
        }

        int[][] transitionTable = new int[numStates][numSymbols];
        System.out.println("Define the transition table:");
        for (int i = 0; i < numStates; i++) {
            for (int j = 0; j < numSymbols; j++) {
                System.out.println("From state " + (i + 1) + " with input '" + symbols[j] + "', go to state (1 to " + numStates + "):");
                transitionTable[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter the input string:");
        String inputString = sc.next();

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
        }

        if (isValid && finalStates.contains(currentState)) {
            System.out.println("Valid String");
        } else {
            System.out.println("Invalid String");
        }

        sc.close();
    }
}