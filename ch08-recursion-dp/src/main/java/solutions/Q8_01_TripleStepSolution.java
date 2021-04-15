import java.util.Arrays;

public class Q8_01_TripleStepSolution {

    /*public static int countWays(int n) {
        if(n == 0) {
            return 1;
        }else if(n < 0){
            return 0;
        }

        return countWays(n-1)+countWays(n-2)+countWays(n-3);
    }*/

    public static long countWays(int n) {
        if( n < 0) return 0;
        long[] map = new long[n+1];
        Arrays.fill(map,-1);
        return countWays(n, map);
    }

    public static long countWays(int n, long[] map){
        if(n == 0){
            return 1;
        }else if(n < 0){
            return 0;
        }
        if(map[n] != -1){
            return map[n];
        }
        map[n] = countWays(n-1, map) + countWays(n-2, map) + countWays(n-3, map);
        return map[n];
    }
}
