import java.util.*;

public class Main {
    /**
     * Finds the shortest sequence of words in the dictionary such that the first word is the startWord,
     * the last word is the endWord, and each word is equal to the previous word with one letter changed.
     * All words in the sequence are the same length. If no sequence is possible, an empty array is returned.
     * It is assumed that both startWord and endWord are elements of the dictionary.
     *
     * @param dictionary The set of words that can be used in the sequence; all words in the dictionary are capitalised.
     * @param startWord  the first word on the sequence.
     * @param endWord    the last word in the sequence.
     * @return an array containing the shortest sequence from startWord to endWord, in order,
     * using only words from the dictionary that differ by a single character.
     */
    public static String[] findPath(String[] dictionary, String startWord, String endWord) {

        // convert dictionary to a set
        Set<String> dictSet = new HashSet<>(Arrays.asList(dictionary));

        // Keep track of neighbours found for each node we visit
        HashMap<String, ArrayList<String>> nodeNeighbours = new HashMap<>();

        // Keep track of how far away a node is from the start word
        HashMap<String, Integer> distances = new HashMap<>();

        // List to store our solution in
        ArrayList<String> solution = new ArrayList<>();

        List<List<String>> res = new ArrayList<>();
        // Perform a BFS to get nodes and their distances from START.
        // Do this until we find the end node.
        // add each word in dict to our node neighbours map
        for (String node : dictSet) {
            nodeNeighbours.put(node, new ArrayList<>());
        }

        Queue<String> q = new LinkedList<>();
        q.add(startWord);
        distances.put(startWord, 0);
        while (!q.isEmpty()) {
            boolean foundEnd = false;
            for (int i = 0; i < q.size(); i++) {
                String current = q.remove();
                int currentDistance = distances.get(current);
                //Find all combinations of letters
                ArrayList<String> currNodeNeighbours = Nextneighbour(current, dictSet);
                for (String k : currNodeNeighbours) {
                    nodeNeighbours.get(current).add(k);
                    if ( !distances.containsKey(k) ){
                        distances.put(k, currentDistance + 1);
                        if ( !Objects.equals(endWord, k) ){
                            q.offer(k);
                        } else {
                            foundEnd = true;
                            break;
                        }
                    }
                }
                if ( foundEnd ){
                    break;
                }
            }
            if ( foundEnd ){
                break;
            }
        }

        getPath(startWord, endWord, nodeNeighbours, distances, solution, res);

        String[] finalPath = new String[res.get(0).size()];
        finalPath = res.get(0).toArray(finalPath);
        return finalPath;
    }

    private static void getPath(String currNode, String endNode, HashMap<String, ArrayList<String>> nodeNeighbours,
                                HashMap<String, Integer> distances, ArrayList<String> solution, List<List<String>> res) {
        solution.add(currNode);

        if ( Objects.equals(currNode, endNode) ){
            res.add(new ArrayList<>(solution));
            return;
        }

        for (String neighbour : nodeNeighbours.get(currNode)) {
            if ( distances.get(currNode) + 1 == distances.get(neighbour) ){
                getPath(neighbour, endNode, nodeNeighbours, distances, solution, res);
                solution.remove(solution.size() - 1);
            }
        }
    }
    public static ArrayList<String> Nextneighbour(String s, Set<String> dictionary) {
        ArrayList<String> neighbours = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            String startString = s.substring(0, i);
            String EndString = s.substring(i + 1);
            for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
                String WordCombination = startString + alphabet + EndString;
                if ( dictionary.contains(WordCombination) && !WordCombination.equals(s) ){
                    neighbours.add(WordCombination);
                }
            }
        }
        return neighbours;
    }
    public static void main(String[] args) {
        Main.test();
    }
    public static void test() {
        String[] dictionary = new String[]{"AIM", "ARM", "ART", "RIM", "RAM", "RAT", "ROT", "RUM", "RUN", "BOT", "JAM", "JOB", "JAB", "LAB", "LOB", "LOG", "SUN"};
        String[] solution = Main.findPath(dictionary, "AIM", "BOT");
        System.out.println(Arrays.toString(solution));
    }
}



