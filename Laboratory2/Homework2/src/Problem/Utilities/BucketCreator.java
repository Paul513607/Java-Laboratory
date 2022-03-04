package Problem.Utilities;

import Event.Event;

import java.util.*;

public class BucketCreator {
    Map<Integer, ArrayList<Event>> sizeToEvents = new TreeMap<>(Collections.reverseOrder());

    public BucketCreator() {
    }

    public Map<Integer, ArrayList<Event>> getSizeToEvents() {
        return sizeToEvents;
    }

    public void setSizeToEvents(Map<Integer, ArrayList<Event>> sizeToEvents) {
        this.sizeToEvents = sizeToEvents;
    }

    @Override
    public String toString() {
        return "BucketsCreator{" +
                "sizeToEvents=" + sizeToEvents +
                '}';
    }

    public void createMap(ArrayList<Event> events) {
        for (Event event : events) {
            int size = event.getSize();
            if (!sizeToEvents.containsKey(size)) {
                sizeToEvents.put(size, new ArrayList<>());
            }
            sizeToEvents.get(size).add(event);
        }
    }

    public void sortMapValues() {
        for (Integer size : sizeToEvents.keySet()) {
            Collections.sort(sizeToEvents.get(size), (o1, o2) -> o1.getEndTime().compareTo(o2.getEndTime()));
        }
    }

    public void printBuckets() {
        sizeToEvents.entrySet().stream()
                .forEach(pair -> System.out.println(pair.getKey() + " -> " + pair.getValue()));
    }
}
