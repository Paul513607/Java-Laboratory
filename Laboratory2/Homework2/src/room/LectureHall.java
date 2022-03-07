package room;

import java.util.Objects;

/** The LectureHall class. It extends the Room abstract class, and it has a new attribute, namely a boolean that tell us if the LectureHall hasVideoProjector. */
public class LectureHall extends Room {
    private boolean hasVideoProjector;

    public LectureHall() {
    }

    public LectureHall(String name, int capacity, boolean hasVideoProjector) {
        super(name, capacity);
        this.hasVideoProjector = hasVideoProjector;
    }

    public boolean isHasVideoProjector() {
        return hasVideoProjector;
    }

    public void setHasVideoProjector(boolean hasVideoProjector) {
        this.hasVideoProjector = hasVideoProjector;
    }

    @Override
    public String toString() {
        return "LectureHall{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", hasVideoProjector=" +  hasVideoProjector +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LectureHall that = (LectureHall) o;
        return hasVideoProjector == that.hasVideoProjector;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hasVideoProjector);
    }
}
