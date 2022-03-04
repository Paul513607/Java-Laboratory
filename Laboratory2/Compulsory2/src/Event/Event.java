package Event;

import java.time.LocalTime;

public class Event {
    private EventType name;
    private int size;
    LocalTime startTime, endTime;

    public Event() {
    }

    public Event(EventType name, int size, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.size = size;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventType getName() {
        return name;
    }

    public void setName(EventType name) {
        this.name = name;
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
        String eventName;
        switch (name) {
            case LAB:
                eventName = "Lab";
                break;
            case COURSE:
                eventName = "Course";
                break;
            case SEMINAR:
                eventName = "Seminar";
                break;
            default:
                eventName = "Unknown";
        }
        return "Event{" +
                "name='" + eventName + '\'' +
                ", capacity=" + size +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
