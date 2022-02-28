import java.util.*;
import java.util.regex.Pattern;

public class Homework1 {
    static final public int MAX_SIZE = 30000;
    static public long startTime, endTime, timeElapsed;

    static private void displaySyntaxMessage() {
        System.out.println("Syntax: java <program> <integer> <integer> <char> <char> ...");
    }

    static public boolean validateInput(String[] args) {
        System.out.println(args.length);
        if (args.length < 2) {
            return false;
        }

        try {
            Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        try {
            Integer.parseInt(args[1]);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        for (int i = 2; i < args.length; ++i) {
            if (args[i].length() > 1) {
                return false;
            }
            if (!(args[i].charAt(0) >= 'a' && args[i].charAt(0) <= 'z' || args[i].charAt(0) >= 'A' && args[i].charAt(0) <= 'Z')) {
                return false;
            }
        }

        return true;
    }

    static ArrayList<String> generateWords(int n, int p, String[] args) {
        ArrayList<String> words = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            String word = new String();
            for (int j = 0; j < p; ++j) {
                int index = (int) (Math.random() * (args.length - 2) + 2);
                char ch = args[index].charAt(0);
                word += ch;
            }
            words.add(word);
        }

        return words;
    }

    static boolean areWordsNeighbors(String s1, String s2) {
        Map<Character, Integer> wordLetters = new HashMap<>();
        for(int i = 0; i < s1.length(); ++i)
            wordLetters.put(s1.charAt(i), wordLetters.get(s1.charAt(i)) == null ? 1 : wordLetters.get(s1.charAt(i)) + 1);

        // if the letter doesn't exist in the first word, we ignore it and move onto the next one
        for(int i = 0; i < s2.length(); ++i)
            if (wordLetters.containsKey(s2.charAt(i)) && wordLetters.get(s2.charAt(i)) > 0)
                return true;
        return false;
    }

    static boolean[][] generateNeighborhoodMatrix(ArrayList<String> words, int size) {
        boolean[][] neighbors = new boolean[size][size];
        for(int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                neighbors[i][j] = areWordsNeighbors(words.get(i), words.get(j));
        return neighbors;
    }

    static ArrayList<ArrayList<String>> generateNeighbors(ArrayList<String> words) {
        ArrayList<ArrayList<String>> wordNeighbors = new ArrayList<>();
        for (int i = 0; i < words.size(); ++i) {
            ArrayList<String> tempArray = new ArrayList<>();
            for (int j = 0; j < words.size(); ++j)
                if (areWordsNeighbors(words.get(i), words.get(j))) tempArray.add(words.get(j));
            wordNeighbors.add(tempArray);
        }
        return wordNeighbors;
    }

    // could be generic but the matrix is of a primitive type
    static void printMatrixBool(boolean[][] matrix, int size) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j)
                System.out.print(matrix[i][j] + " ");
            System.out.print('\n');
        }
    }

    static<T> void printArrayListMatrix(ArrayList<ArrayList<T>> matrix, ArrayList<T> arr) {
        for (int i = 0; i < matrix.size(); ++i) {
            System.out.print(arr.get(i) + ": ");
            for (int j = 0; j < matrix.get(i).size(); ++j)
                System.out.print(matrix.get(i).get(j) + " ");
            System.out.print('\n');
        }
    }

    // Homework1 Bonus
    // O(n*p)
    static HashMap<Character, HashSet<String>> characterWordSet(ArrayList<String> words) {
        HashMap<Character, HashSet<String>> charWordSet = new HashMap<>();
        for (String word : words)
            for (int i = 0; i < word.length(); ++i) {
                if (!charWordSet.containsKey(word.charAt(i)))
                    charWordSet.put(word.charAt(i), new HashSet<>());
                charWordSet.get(word.charAt(i)).add(word);
            }
        return charWordSet;
    }

    // O(26*26)
    static Inventory intersectAll(HashMap<Character, HashSet<String>> charWordSet) {
        ArrayList<HashSet<String>> intersections = new ArrayList<>();
        ArrayList<Map.Entry<Integer, Integer>> indexInter = new ArrayList<>();

        int i = 0, j = 0;
        for (HashSet<String> s1 : charWordSet.values()) {
            for (HashSet<String> s2 : charWordSet.values()) {
                if (j <= i) {
                    j++;
                    continue;
                }

                HashSet<String> intersect = new HashSet<>(s1);
                Map.Entry<Integer, Integer> entry;
                entry = new AbstractMap.SimpleEntry<>(i, j);
                intersect.retainAll(s2);
                intersections.add(intersect);
                indexInter.add(entry);
            }
            i++;
            j = 0;
        }

        Inventory inventory = new Inventory(intersections, indexInter);

        return inventory;
    }

    static HashSet<String> findValueAtIndexMap(HashMap<Character, HashSet<String>> charWordSet, int index) {
        int i = 0;
        for (HashSet<String> wordSet : charWordSet.values()) {
            if (i == index)
                return wordSet;
            i++;
        }
        return null;
    }

    // O(26*26*n)
    static HashSet<String> naiveSolution(HashMap<Character, HashSet<String>> charWordSet, Inventory inventory) {
        int i = 0;
        ArrayList<HashSet<String>> intersects = inventory.getIntersections();
        for (HashSet<String> intersection : intersects) {
            if (intersection.size() >= 2) {
                Map.Entry<Integer, Integer> entry = inventory.getIndexInterAtIndex(i);
                HashSet<String> s1 = new HashSet<>(), s2 = new HashSet<>();
                s1 = findValueAtIndexMap(charWordSet, entry.getKey());
                s2 = findValueAtIndexMap(charWordSet, entry.getValue());
                HashSet<String> union = new HashSet<>(s1);
                if (s1 == null || s2 == null)
                    continue;
                union.addAll(s2);

                if (union.size() >= 3)
                    return union;
            }
        }
        return null;
    }

    static HashSet<String> goodSolution(HashMap<Character, HashSet<String>> charWordSet, Inventory inventory) {
        int i = 0;
        HashSet<String> union = naiveSolution(charWordSet, inventory);
        for (HashSet<String> wordSet : charWordSet.values()) {
            HashSet<String> inter = new HashSet<>(wordSet);
            inter.retainAll(union);
            if (inter.size() >= 2)
                union.addAll(wordSet);
        }
        return union;
    }

    public static void main(String[] args) {
        boolean valid = validateInput(args);
        if (!valid) {
            displaySyntaxMessage();
            return;
        }
        startTime = System.nanoTime();

        int n = Integer.parseInt(args[0]);
        int p = Integer.parseInt(args[1]);

        ArrayList<String> words = generateWords(n, p, args);
        if (n < MAX_SIZE) {
            for (String word : words) {
                System.out.print(word + " ");
            }
            System.out.println("");
        }

        boolean[][] neighbors = generateNeighborhoodMatrix(words, n);
        if (n < MAX_SIZE) {
            System.out.println("");
            System.out.println("The boolean neighborhood matrix:");
            printMatrixBool(neighbors, n);
        }

        ArrayList<ArrayList<String>> wordNeighbors = new ArrayList<>();
        wordNeighbors = generateNeighbors(words);
        if (n < MAX_SIZE) {
            System.out.println("");
            System.out.println("The neighborhood matrix:");
            printArrayListMatrix(wordNeighbors, words);
        }

        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("");
        System.out.println("Time elapsed: " + timeElapsed + " nanoseconds(ns).");
        System.out.print("\n\n\n");

        HashMap<Character, HashSet<String>> charWordSet = new HashMap<>();
        charWordSet = characterWordSet(words);
        System.out.println(charWordSet);

        System.out.print("\n\n\n");
        Inventory inventory = new Inventory();
        inventory = intersectAll(charWordSet);
        System.out.println(inventory.getIntersections());
        System.out.println(inventory.getIndexInter());

        System.out.print("\n\n\n");
        // HashSet<String> union = naiveSolution(charWordSet, inventory);
        HashSet<String> union = goodSolution(charWordSet, inventory);
        System.out.println("Size: " + union.size() + " Subset: " + union);
    }
}
