import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import solutions.Q8_02_RobotGridSolution;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MemoryExtension.class)
@ExtendWith(TimingExtension.class)
public class Q8_02_RobotGridTest {

    private static Stream<Arguments> data() {
        int[][] input1 = {{0, 0, 0}, {0, 0, 1}, {0, 0, 0}};
        int[][] output1 = {{0,0},{1,0},{1,1},{2,1},{2,2}};
        int[][] input2 = {{0, 0, 0}};
        int[][] output2 = {{0,0},{0,1},{0,2}};
        int[][] input3 = {{0, 0, 0}, {1, 1, 1}, {0, 0, 0}};
        int[][] output3 = {};
        int[][] input4 = {{0, 0, 0}, {1, 0, 0}, {0, 0, 0}};
        int[][] output4 = {{0,0},{0,1},{0,2},{1,2},{2,2}};
        int[][] input5 = {{0, 0, 0}, {0, 1, 0}, {1, 1, 0}};
        int[][] output5 = {{0,0},{0,1},{0,2},{1,2},{2,2}};
        return Stream.of(
                Arguments.of(null, new int[0][0]),
                Arguments.of(new int[1][1], new int[][]{{0,0}}),
                Arguments.of(input1, output1),
                Arguments.of(input2, output2),
                Arguments.of(input3, output3),
                Arguments.of(input4, output4),
                Arguments.of(input5, output5));
    }

    @ParameterizedTest
    @MethodSource("data")
    public void test(int[][] input, int[][] output)  {
        assertTimeout( Duration.ofMillis(100), () -> {
            int[][] result = Q8_02_RobotGridSolution.findPath(input);
            assertNotNull(result);
            assertEquals(result.length, output.length);
            for (int i = 0; i < output.length; i++) {
                assertArrayEquals(output[i], result[i], "Row: "+i);
            }
        });
    }
}
