package seminar1;

import seminar1.collections.ArrayStack;
import seminar1.collections.LinkedStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 1. пустая строка — правильная скобочная последовательность;
 * 2. правильная скобочная последовательность,
 *      взятая в скобки одного типа — правильная скобочная последовательность;
 * 3. правильная скобочная последовательность,
 *      к которой приписана слева или справа правильная скобочная последовательность
 *      — тоже правильная скобочная последовательность.
 */
public class ParenthesesSequenceExt {

    private static final String QUIT = "q";

    private static final char LEFT_PAREN     = '(';
    private static final char RIGHT_PAREN    = ')';
    private static final char LEFT_BRACE     = '{';
    private static final char RIGHT_BRACE    = '}';
    private static final char LEFT_BRACKET   = '[';
    private static final char RIGHT_BRACKET  = ']';

    // sequence = "()()" | "(({}[]))[[[" | "{}" | ...
    public static boolean isBalanced(String sequence) {
        char[] chars = sequence.toCharArray();
        LinkedStack<Character> ms = new LinkedStack<>();
        for (char aChar : chars) {
            Character temp;
            switch (aChar) {
                case LEFT_PAREN:
                    ms.push(aChar);
                    break;
                case LEFT_BRACE:
                    ms.push(aChar);
                    break;
                case LEFT_BRACKET:
                    ms.push(aChar);
                    break;
                case RIGHT_PAREN:
                    if (ms.size() == 0) return false;
                    temp = ms.pop();
                    if (temp != LEFT_PAREN) return false;
                    break;
                case RIGHT_BRACE:
                    if (ms.size() == 0) return false;
                    temp = ms.pop();
                    if (temp != LEFT_BRACE) return false;
                    break;
                case RIGHT_BRACKET:
                    if (ms.size() == 0) return false;
                    temp = ms.pop();
                    if (temp != LEFT_BRACKET) return false;
                    break;
            }
        }
        return ms.size() == 0;
    }


    public static void main(String[] args) {
        try (BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in))) {
            String sequence;
            while (!QUIT.equals(sequence = lineReader.readLine())) {
                System.out.println(isBalanced(sequence) ? "YES" : "NO");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
