package event;

import java.time.LocalTime;
import java.util.Objects;

/** The Event class. An event object has a name, a type (which is defined in the enum EventType), a size, a startTime and an endTime. */
public class Event {
    private String name;
    private EventType type;
    private int size;
    LocalTime startTime, endTime;

    public Event() {
    }

    /** In the Event class constructor we will validate if the startTime is smaller than the endTime. */
    public Event(String name, EventType type, int size, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException{
        if (startTime.compareTo(endTime) > 0)
            throw new IllegalArgumentException("The startTime of the event has to be smaller than the endTime.");
        this.name = name;
        this.type = type;
        this.size = size;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType name) {
        this.type = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String eventType;
        switch (type) {
            case LAB:
                eventType = "Lab";
                break;
            case COURSE:
                eventType = "Course";
                break;
            case SEMINAR:
                eventType = "Seminar";
                break;
            default:
                eventType = "Unknown";
        }
        return "Event{" +
                "name='" + name + '\'' +
                ", type='" + eventType + '\'' +
                ", size=" + size +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return size == event.size && Objects.equals(name, event.name) && type == event.type && Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, size, startTime, endTime);
    }
}
