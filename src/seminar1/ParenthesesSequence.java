package seminar1;

import seminar1.collections.ArrayStack;

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
public class ParenthesesSequence {

    private static final String QUIT = "q";

    private static final char LEFT_PAREN     = '(';
    private static final char RIGHT_PAREN    = ')';

    // sequence = "()()" | "((((" | ")()(" | ...
    public static boolean isBalanced(String sequence) {
        char[] chars = sequence.toCharArray();
        ArrayStack<Character> ms = new ArrayStack<>();
        for (char aChar : chars) {
            switch (aChar) {
                case LEFT_PAREN:
                    ms.push(LEFT_PAREN);
                    break;
                case RIGHT_PAREN:
                    if (ms.size() == 0) return false;
                    ms.pop();
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
