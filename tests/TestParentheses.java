import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import seminar1.ParenthesesSequence;
import seminar1.ParenthesesSequenceExt;

@RunWith(Enclosed.class)
public class TestParentheses {
    public static class Simple{
        @Test
        public void singleLevelBalanced(){
            Assert.assertTrue(ParenthesesSequence.isBalanced(""));
            Assert.assertTrue(ParenthesesSequence.isBalanced("()"));
            Assert.assertTrue(ParenthesesSequence.isBalanced("()()"));
        }
        @Test
        public void multiLevelBalanced(){
            Assert.assertTrue(ParenthesesSequence.isBalanced("(())"));
            Assert.assertTrue(ParenthesesSequence.isBalanced("(())()(())"));
        }
        @Test
        public void singleLevelUnbalanced(){
            Assert.assertFalse(ParenthesesSequence.isBalanced(")("));
            Assert.assertFalse(ParenthesesSequence.isBalanced("(()"));
            Assert.assertFalse(ParenthesesSequence.isBalanced("()("));
        }
        @Test
        public void multiLevelUnbalanced(){
            Assert.assertFalse(ParenthesesSequence.isBalanced("(()))"));
            Assert.assertFalse(ParenthesesSequence.isBalanced("(())()((())"));
        }
    }
    public static class Ext{
        @Test
        public void singleLevelBalanced(){
            Assert.assertTrue(ParenthesesSequenceExt.isBalanced(""));
            Assert.assertTrue(ParenthesesSequenceExt.isBalanced("()"));
            Assert.assertTrue(ParenthesesSequenceExt.isBalanced("()[]{}"));
        }
        @Test
        public void multiLevelBalanced(){
            Assert.assertTrue(ParenthesesSequenceExt.isBalanced("(())"));
            Assert.assertTrue(ParenthesesSequenceExt.isBalanced("{([])}"));
            Assert.assertTrue(ParenthesesSequenceExt.isBalanced("(())()(())"));
            Assert.assertTrue(ParenthesesSequenceExt.isBalanced("([[]]){}(())"));
        }
        @Test
        public void singleLevelUnbalanced(){
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced(")("));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("]["));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("(()"));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("}}{"));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("()("));
        }
        @Test
        public void multiLevelUnbalanced(){
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("({[{"));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("([]})"));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("({)}[](())"));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("(()))"));
            Assert.assertFalse(ParenthesesSequenceExt.isBalanced("(())()((())"));
        }
    }
}
