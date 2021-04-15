package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q8_02_RobotGridSolution {
    static int[][] down_right = {{1,0}, {0,1}};
    static int[][] down_right1 = {{0,1}, {1,0}};
    public static int[][] findPath(int[][] grid){
        if(grid == null || grid.length ==0 ) return new int[0][0];
        List<int[]> result = new ArrayList<>();
        findPath(grid, 0, 0, false, result);
        return result.toArray(new int[result.size()][2]);
    }
    private static boolean findPath(int[][] grid, int x, int y, boolean flip, List<int[]> result){
        if(x >= grid.length || y >= grid[0].length){
            return false;
        }else if(x == grid.length-1 && y == grid[0].length-1){
            result.add(new int[]{x, y});
            return true;
        }else if(grid[x][y] == 1){
            return false;
        }
        result.add(new int[]{x, y});
        int[][] down_right2 = flip?down_right1:down_right;
        for(int i=0;i<down_right2.length;i++){
            if(findPath(grid,x+down_right2[i][0], y+down_right2[i][1], !flip, result)){
                return true;
            }
        }
        result.remove(result.size()-1);
        return false;
    }
}
