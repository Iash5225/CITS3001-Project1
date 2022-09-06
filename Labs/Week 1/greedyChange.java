import java.util.*;

public class greedyChange {
    /**
     * Apply the greedy algorithm to calculate coin change.
     *
     * @param amount a non-negative integer which is required to be made up.
     * @param denominations the available coin types (unique positive integers)
     * @return a map of each denomination to the number of times it is used in the solution.
     **/

    // A Stack to hold the Array elements which sum to the desired Target Sum.
    private static final Stack<Integer> stack = new Stack<>();

    // Store the summation of current elements held in stack.
    private static int sumInStack = 0;

    /* A List Interface of Integer[] array to hold all the
       combinations of array elements which sum to target. */
    private static final List<Integer[]> combinationsList = new ArrayList<>();

    public static Map<Integer, Integer> greedyChange(int amount, int[] denominations) {
        //fill in code here
        int minnumber = GreedyAlgo(amount, denominations);
        int[] subset = ExpandedSubset(denominations, minnumber);
        getSummations(subset, 0, subset.length, amount, minnumber);
        Arrays.sort(denominations);
        Map<Integer, Integer> Res = new HashMap<>();
        Integer[] result = countItems(combinationsList, denominations);
        for (int i = 0; i < denominations.length; i++) {
            Res.put(denominations[i], result[i]);
        }
        return Res;
    }


    public static int GreedyAlgo(int amount, int[] denominations) {
        //fill in code here
        int[] pointer = new int[amount + 1];
        int[] change = new int[denominations.length];
        pointer[0] = 0;
        for (int i = 0; i < amount + 1; i++) {
            pointer[i] = i;
        }
        for (int i = 2; i < amount + 1; i++) {
            for (int j = 0; j < denominations.length; j++) {
                int coin = denominations[j];
                if ( (i - coin) >= 0 ){
                    pointer[i] = Math.min(pointer[i], 1 + pointer[i - coin]);
                    if ( i == amount ){

                        change[j] = pointer[i - coin];
                    }
                }
            }
        }
        return pointer[amount];
    }

    public static void getSummations(int[] data, int startIndex, int endIndex, int targetSum, int numbersLimit) {
        /* Check to see if the sum of array elements stored in the
           Stack is equal to the desired Target Sum. If it is then
           convert the array elements in the Stack to an Integer[]
           Array and add it to the combinationsList List.       */
        if ( sumInStack == targetSum ){
            if ( stack.size() <= numbersLimit ){
                combinationsList.add(stack.toArray(new Integer[0]));
            }
        }
        for (int currIndex = startIndex; currIndex < endIndex; currIndex++) {
            if ( sumInStack + data[currIndex] <= targetSum ){
                stack.push(data[currIndex]);
                sumInStack += data[currIndex];

                // Make the currentIndex +1, and then use recursion to carry on.
                getSummations(data, currIndex + 1, endIndex, targetSum, numbersLimit);
                sumInStack -= stack.pop();
            }
        }
    }

    public static int[] ExpandedSubset(int[] arr, int t) {
        ArrayList<Integer> clist = new ArrayList<>();
        if ( t > arr.length ){
            for (int coin : arr) {
                for (int j = 0; j <= t; j++) {
                    clist.add(coin);
                }
            }
        }
        int[] test = clist.stream().mapToInt(i -> i).toArray();
        if ( t <= arr.length ){
            return arr;
        }
        return test;
    }

    private static Integer[] countItems(List<Integer[]> arr, int[] denominations) {
        List<Integer> asList = Arrays.asList(arr.get(0));
        Integer[] test = new Integer[denominations.length];
        for (int i = 0; i < test.length; i++) {
            int coin = denominations[i];
            test[i] = Collections.frequency(asList, coin);
        }
        return test;
    }

    public static void main(String[] args) {
        new greedyChange().startDemo(args);
    }

    private void startDemo(String[] args) {

        int[] denominations = {1,2,5,10,25};
        Map<Integer,Integer> change = greedyChange(188, denominations);
        Integer[] keys = change.keySet().toArray(new Integer[0]);
        Arrays.sort(keys);
        for(Integer i: keys)
            System.out.println(i+":"+change.get(i));
    }

}
