import java.io.*;
import java.util.*;

public class LexicalAnalyzer {

    // Predefined sets for classification
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
        "int", "float", "char", "if", "else", "while", "for", "return", "const", "void", "main", "switch", "case", "break", "continue", "printf", "scanf"
    ));
    private static final Set<String> OPERATORS = new HashSet<>(Arrays.asList(
        "+", "-", "*", "/", "=", "==", "!=", "<", ">", "<=", ">=", "&&", "||", "!", "++", "--", "&", "|", "^", "~"
    ));
    private static final Set<String> PUNCTUATION = new HashSet<>(Arrays.asList(
        ";", ",", "(", ")", "{", "}", "[", "]"
    ));

    // Symbol Table for identifiers
    private static final Map<String, String> symbolTable = new HashMap<>();

    public static void analyze(String input) {
        int index = 0;
        int length = input.length();
        List<String> errors = new ArrayList<>();

        while (index < length) {
            char currentChar = input.charAt(index);

            // Skip whitespaces
            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            // Remove comments
            if (currentChar == '/' && index + 1 < length) {
                if (input.charAt(index + 1) == '/') {
                    // Single-line comment
                    index = length; // Skip the rest of the line
                    continue;
                } else if (input.charAt(index + 1) == '*') {
                    // Multi-line comment
                    index += 2;
                    while (index + 1 < length && !(input.charAt(index) == '*' && input.charAt(index + 1) == '/')) {
                        index++;
                    }
                    if (index + 1 < length) {
                        index += 2; // Skip closing */
                    } else {
                        errors.add("Error: Unclosed comment");
                    }
                    continue;
                }
            }

            // Detect strings
            if (currentChar == '"') {
                StringBuilder stringToken = new StringBuilder("\"");
                index++;
                while (index < length && input.charAt(index) != '"') {
                    if (input.charAt(index) == '\\') { // Handle escape sequences
                        stringToken.append(input.charAt(index));
                        index++;
                    }
                    stringToken.append(input.charAt(index));
                    index++;
                }
                if (index < length && input.charAt(index) == '"') {
                    stringToken.append('"');
                    System.out.println("String: " + stringToken);
                    index++;
                } else {
                    errors.add("Error: Unclosed string literal");
                }
                continue;
            }

            // Detect constants
            if (Character.isDigit(currentChar)) {
                StringBuilder constant = new StringBuilder();
                boolean hasDot = false;
                while (index < length && (Character.isDigit(input.charAt(index)) || input.charAt(index) == '.')) {
                    if (input.charAt(index) == '.') {
                        if (hasDot) {
                            errors.add("Error: Malformed constant with multiple dots");
                            break;
                        }
                        hasDot = true;
                    }
                    constant.append(input.charAt(index));
                    index++;
                }
                if (constant.toString().endsWith(".")) {
                    errors.add("Error: Malformed constant ending with a dot: " + constant);
                } else {
                    System.out.println("Constant: " + constant);
                }
                continue;
            }

            // Detect identifiers and keywords
            if (Character.isLetter(currentChar) || currentChar == '_') {
                StringBuilder identifier = new StringBuilder();
                while (index < length && (Character.isLetterOrDigit(input.charAt(index)) || input.charAt(index) == '_')) {
                    identifier.append(input.charAt(index));
                    index++;
                }
                String token = identifier.toString();
                if (KEYWORDS.contains(token)) {
                    System.out.println("Keyword: " + token);
                } else {
                    System.out.println("Identifier: " + token);
                    symbolTable.put(token, "Identifier");
                }
                continue;
            }

            // Detect operators
            boolean matchedOperator = false;
            for (String op : OPERATORS) {
                if (input.startsWith(op, index)) {
                    System.out.println("Operator: " + op);
                    index += op.length();
                    matchedOperator = true;
                    break;
                }
            }
            if (matchedOperator) continue;

            // Detect punctuation
            if (PUNCTUATION.contains(String.valueOf(currentChar))) {
                System.out.println("Punctuation: " + currentChar);
                index++;
                continue;
            }

            // Handle unknown characters
            errors.add("Error: Unknown token " + currentChar);
            index++;
        }

        // Display errors
        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "practical 3/file3.c";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Lexical Analysis Output:");
            while ((line = reader.readLine()) != null) {
                analyze(line);
            }

            // Display Symbol Table
            System.out.println("\nSymbol Table:");
            for (Map.Entry<String, String> entry : symbolTable.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
