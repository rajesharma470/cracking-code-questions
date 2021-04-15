
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@ExtendWith(MemoryExtension.class)
@ExtendWith(TimingExtension.class)
public class Q8_01_TripleStepTest {

    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(-2, 0),
                Arguments.of(-1, 0),
                Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(2, 2),
                Arguments.of(3, 4),
                Arguments.of(6, 24),
                Arguments.of(11, 504),
                Arguments.of(17, 19513),
                Arguments.of(30, 53798080),
                Arguments.of(60, 4680045560037375l),
                Arguments.of(70, 2073693258389777176l)
        );
    }


    @ParameterizedTest
    @MethodSource("data")
    public void test(int input, long output) {
        assertTimeout( Duration.ofMillis(100), () -> {
            assertEquals(output, Q8_01_TripleStep.countWays(input));
        });
    }
}
