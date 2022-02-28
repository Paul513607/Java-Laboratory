import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class Inventory {
    private ArrayList<HashSet<String>> intersections = new ArrayList<>();
    private ArrayList<Map.Entry<Integer, Integer>> indexInter = new ArrayList<>();

    public Inventory() {
    }

    public Inventory(ArrayList<HashSet<String>> intersections, ArrayList<Map.Entry<Integer, Integer>> indexInter) {
        this.intersections = intersections;
        this.indexInter = indexInter;
    }

    public ArrayList<HashSet<String>> getIntersections() {
        return intersections;
    }

    public ArrayList<Map.Entry<Integer, Integer>> getIndexInter() {
        return indexInter;
    }

    public void setIntersections(ArrayList<HashSet<String>> intersections) {
        this.intersections = intersections;
    }

    public void setIndexInter(ArrayList<Map.Entry<Integer, Integer>> indexInter) {
        this.indexInter = indexInter;
    }

    public HashSet<String> getInterAtIndex(int index) {
        return intersections.get(index);
    }

    public Map.Entry<Integer, Integer> getIndexInterAtIndex(int index) {
        return indexInter.get(index);
    }
}