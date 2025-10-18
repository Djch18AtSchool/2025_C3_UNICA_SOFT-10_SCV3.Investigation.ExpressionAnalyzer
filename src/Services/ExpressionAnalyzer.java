package Services;

import Domain.Node;
import Domain.Stack;

import java.util.ArrayList;
import java.util.List;

public class ExpressionAnalyzer {

    private static class OperatorStack extends Stack<Character> {
        /**
         * Método toString para imprimir el estado de la pila en el log.
         * Muestra la pila desde la cima (izquierda) hasta el fondo (derecha).
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            Node<Character> current = this.top;
            while (current != null) {
                sb.append(current.getValue()).append(" ");
                current = current.getNext();
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static class ConversionResult {
        private boolean success;
        private String resultString;
        private List<String> stepLog;

        public ConversionResult(boolean success, String resultString, List<String> stepLog) {
            this.success = success;
            this.resultString = resultString;
            this.stepLog = stepLog;
        }

        public List<String> getStepLog() {
            return stepLog;
        }

        public void setStepLog(List<String> stepLog) {
            this.stepLog = stepLog;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getResultString() {
            return resultString;
        }

        public void setResultString(String resultString) {
            this.resultString = resultString;
        }
    }


    /**
     * Convierte una expresión infija a postfija usando nuestra Pila personalizada.
     * Esta es la "funcionalidad seleccionada" que demuestra el uso de la pila.
     * @param expression La cadena infija (ej. "5 + ( 6 * 2 )").
     * @return La cadena postfija (ej. "5 6 2 * +").
     */
    public static ConversionResult infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        OperatorStack operatorStack = new OperatorStack();
        List<String> log = new ArrayList<>();

        String[] tokens = expression.trim().split("\\s+");

        String logFormat = "%-10s | %-20s | %s";

        for (String token : tokens) {
            String logEntry;

            // 1. Si es un número (operando), va directo a la salida
            if (isNumber(token)) {
                output.append(token).append(" ");
                logEntry = String.format(logFormat, "'" + token + "'", operatorStack.toString(), output.toString());

                // 2. Si es '(', se apila
            } else if (token.equals("(")) {
                operatorStack.push('(');
                logEntry = String.format(logFormat, "'('", operatorStack.toString(), output.toString());

                // 3. Si es ')', desapilamos hasta encontrar el '('
            } else {
                String format = String.format(logFormat, "  (pop)", operatorStack.toString(), output.toString());
                if (token.equals(")")) {
                    logEntry = String.format(logFormat, "')'", operatorStack.toString(), output.toString());
                    log.add(logEntry);

                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        output.append(operatorStack.pop()).append(" ");
                        log.add(format);
                    }

                    if (operatorStack.isEmpty()) {
                        log.add("!! ERROR: Paréntesis no balanceados (falta '('). Se detiene el proceso.");
                        return new ConversionResult(false, "Paréntesis no balanceados (falta '(')", log);
                    }
                    operatorStack.pop();
                    log.add(String.format(logFormat, "  (pop '(')", operatorStack.toString(), output.toString()));

                    // 4. Si es un operador (+, -, *, /)
                } else if (isOperator(token)) {
                    char currentOp = token.charAt(0);
                    logEntry = String.format(logFormat, "'" + currentOp + "'", operatorStack.toString(), output.toString());
                    log.add(logEntry);

                    while (!operatorStack.isEmpty() && getPrecedence(operatorStack.peek()) >= getPrecedence(currentOp)) {
                        output.append(operatorStack.pop()).append(" ");
                        log.add(format);
                    }
                    operatorStack.push(currentOp);
                    log.add(String.format(logFormat, "  (push)", operatorStack.toString(), output.toString()));
                    continue;

                } else {
                    // Token desconocido
                    log.add("!! ERROR: Token desconocido: '" + token + "'");
                    return new ConversionResult(false, "Token desconocido: " + token, log);
                }

                log.add(logEntry);
            }
        }

        // 5. Al final, vaciamos los operadores restantes de la pila a la salida
        log.add(String.format(logFormat, "--- FIN ---", operatorStack.toString(), output.toString()));
        while (!operatorStack.isEmpty()) {
            char op = operatorStack.pop();
            if (op == '(') {
                log.add("!! ERROR: Paréntesis no balanceados (falta ')'). Se detiene el proceso.");
                return new ConversionResult(false, "Paréntesis no balanceados (falta ')')", log);
            }
            output.append(op).append(" ");
            log.add(String.format(logFormat, "  (pop)", operatorStack.toString(), output.toString()));
        }

        return new ConversionResult(true, output.toString().trim(), log);
    }

    /** Helper: Verifica si un token es un número. */
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Helper: Verifica si un token es un operador. */
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    /** Helper: Devuelve la precedencia de un operador (char). */
    private static int getPrecedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '(' -> 0;
            default -> -1;
        };
    }
}
