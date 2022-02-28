package Utilities;

import java.time.LocalTime;

public class Event {
    private EventType name;
    private int capacity;
    LocalTime startTime, endTime;

    public Event() {
    }

    public Event(EventType name, int capacity, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.capacity = capacity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventType getName() {
        return name;
    }

    public void setName(EventType name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
                "name=" + eventName +
                ", capacity=" + capacity +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
