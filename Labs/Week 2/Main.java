import java.util.*;

public class Main {

    /**
     * Returns the shortest tour found by exercising the NN algorithm
     * from each possible starting city in table.
     * table[i][j] == table[j][i] gives the cost of travel between City i and City j.
     */
    public static int[] tspnn(double[][] table) {
        int[] shortestTour = new int[table.length];
        double TotalShortDistance = -1.0;
        for (int i = 0; i < table.length; i++) {
            int[] currentTour = new int[table.length];
            currentTour[0] = i;
            boolean[] visited = new boolean[table.length];
            visited[i] = true;
            int currentLocation = i;
            double TourDistance = 0;
            for (int k = 1; k < table.length; k++) {
                double shortest = -1.0;
                int shortestIndex = -1;
                for (int j = 0; j < table.length; j++) {
                    if ( !visited[j] && ((shortest < 0 && shortestIndex < 0) || (table[currentLocation][j] < shortest)) ){
                        shortest = table[currentLocation][j];
                        shortestIndex = j;
                    }
                }
                currentTour[k] = shortestIndex;
                currentLocation = shortestIndex;
                visited[shortestIndex] = true;
                TourDistance += shortest;
            }
            TourDistance += table[i][currentTour[table.length - 1]];
            if ( TotalShortDistance < 0 || TourDistance < TotalShortDistance ){
                shortestTour = currentTour;
                TotalShortDistance = TourDistance;
            }
        }
        return shortestTour;
    }

    /**
     * Uses 2-OPT repeatedly to improve cs, choosing the shortest option in each iteration.
     * You can assume that cs is a valid tour initially.
     * table[i][j] == table[j][i] gives the cost of travel between City i and City j.
     **/
    public static int[] tsp2opt(int[] cs, double[][] table) {
        // COMPLETE THIS METHOD
        int initialTourDistance = GetTourDistance(cs, table);
        boolean FoundImprovement = false;
        int[] res = cs;

        while (!FoundImprovement) {
            for (int i = 0; i < cs.length - 1; i++) {
                boolean updatedTour = false;
                for (int j = i + 1; j < cs.length; j++) {
                    int[] trialTour = optSwap(res, i, j);
                    int trialDistance = GetTourDistance(trialTour, table);
                    if ( trialDistance < initialTourDistance ){
                        res = trialTour;
                        initialTourDistance = trialDistance;
                        updatedTour = true;
                        break;
                    }
                }
                if ( updatedTour ){
                    break;
                }
                if ( i == cs.length - 2 ){
                    FoundImprovement = true;
                }
            }
        }
        return res;
    }

    public static int[] optSwap(int[] cs, int v1, int v2) {
        int[] res = new int[cs.length];
        if ( v1 >= 0 ) System.arraycopy(cs, 0, res, 0, v1);
        for (int x = v1, y = v2; y >= v1; y--, x++) {
            res[x] = cs[y];
        }
        if ( cs.length - (v2 + 1) >= 0 ) System.arraycopy(cs, v2 + 1, res, v2 + 1, cs.length - (v2 + 1));
        return res;
    }

    public static int GetTourDistance(int[] cs, double[][] table) {
        int totalDistance = 0;
        int prevCity = cs[0];
        for (int i = 1; i < cs.length; i++) {
            totalDistance += table[prevCity][cs[i]];
            prevCity = cs[i];
        }
        totalDistance += table[prevCity][cs[0]];
        return totalDistance;
    }

    public static void main(String[] args) {
        double[][] distances;
        distances = new double[][]{{0, 1, 1}, {1, 0, Math.sqrt(2)}, {1, Math.sqrt(2), 0}};
        int[] res = tspnn(distances);

        int[] test = tsp2opt(res, distances);
        System.out.println(Arrays.toString(res));
    }
}