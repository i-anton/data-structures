import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import seminar1.Solver;
import seminar1.SolverExt;

@RunWith(Enclosed.class)
public class TestSolver {
    public static class Simple {
        @Test
        public void allInBraces() {
            Assert.assertEquals((int)Solver.evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"),101);
            Assert.assertEquals((int)Solver.evaluate("( 1 + ( 5 * ( 4 * 5 ) ) ) ( 1 + ( 5 * 20 ) )"),101);
            Assert.assertEquals((int)Solver.evaluate("( 1 + 100 )"),101);
        }
    }
    public static class Ext {
        @Test
        public void allInBraces() {
            Assert.assertEquals((int)Solver.evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"),101);
            Assert.assertEquals((int)Solver.evaluate("( 1 + ( 5 * ( 4 * 5 ) ) ) ( 1 + ( 5 * 20 ) )"),101);
            Assert.assertEquals((int)Solver.evaluate("( 1 + 100 )"),101);
        }
        @Test
        public void ignoreSomeBraces() {
            Assert.assertEquals((int)SolverExt.evaluate("1 + ( 2 + 3 ) * 4 * 5"),101);
            Assert.assertEquals((int)SolverExt.evaluate("1 + 5 * 4 * 5"),101);
            Assert.assertEquals((int)SolverExt.evaluate("1 + 5 * 20"),101);
            Assert.assertEquals((int)SolverExt.evaluate("1 + 100"),101);
            Assert.assertEquals((int)SolverExt.evaluate("20 / 4"),5);
            Assert.assertEquals((int)SolverExt.evaluate("(101 - 1) / 5"),20);
        }
    }
}
