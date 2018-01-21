package seminar1;

import seminar1.collections.ArrayStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) = 101
 * ( 1 + ( 5 * ( 4 * 5 ) ) )
 * ( 1 + ( 5 * 20 ) ) = 101
 * ( 1 + 100 ) = 101
 *
 * 1 + ( 2 + 3 ) * 4 * 5 = 101
 * 1 + 5 * 4 * 5 = 101
 * 1 + 5 * 20 = 101
 * 1 + 100 = 101
 * 20 / 4 = 5
 * (101 - 1) / 5 = 20
 *
 * Считаем, что операции деления на ноль отсутствуют
 */
public class SolverExt {

    private static final String QUIT = "q";

    private static final char LEFT_PAREN   = '(';
    private static final char RIGHT_PAREN  = ')';
    private static final char PLUS         = '+';
    private static final char MINUS        = '-';
    private static final char TIMES        = '*';
    private static final char DIVISION     = '/';

    private static boolean isOperation(char a){
        return a == PLUS || a == MINUS || a == DIVISION || a == TIMES;
    }

    private static boolean hasPrecedence(char op1, char op2) {
        return op2 != LEFT_PAREN && op2 != RIGHT_PAREN
                && ((op1 != TIMES && op1 != DIVISION) || (op2 != PLUS && op2 != MINUS));
    }

    private static double applyOp(char op, double b, double a)
    {
        switch (op)
        {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case TIMES:
                return a * b;
            case DIVISION:
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    public static double evaluate(String values) {
        char[] chars = values.toCharArray();
        ArrayStack<Character> operator = new ArrayStack<>();
        ArrayStack<Double> operand = new ArrayStack<>();
        int i = 0;
        while (i < chars.length) {
            if (isOperation(chars[i])){
                while (!operator.isEmpty() && hasPrecedence(chars[i], operator.top())){
                    operand.push(applyOp(operator.pop(),operand.pop(),operand.pop()));
                }
                operator.push(chars[i]);
            } else if (chars[i]==LEFT_PAREN){
                operator.push(chars[i]);
            } else if (chars[i]==RIGHT_PAREN) {
                while (operator.top() != LEFT_PAREN){
                    operand.push(applyOp(operator.pop(),operand.pop(),operand.pop()));
                }
                operator.pop();
            } else if (Character.isDigit(chars[i])){
                StringBuilder sb = new StringBuilder().append(chars[i]);
                while (i < chars.length-1){
                    if (!Character.isDigit(chars[i+1])) break;
                    sb.append(chars[i+1]);
                    i++;
                }
                operand.push(Double.parseDouble(sb.toString()));
            }
            i++;
        }
        assert (operator.size() >= 0 && operand.size() >= 1);
        while (!operator.isEmpty())
            operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));

        return operand.pop();
    }
    public static void main(String[] args) {
        try (BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in))) {
            String sequence;
            while (!QUIT.equals(sequence = lineReader.readLine())) {
                System.out.println(evaluate(sequence.replaceAll("\\s","")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
