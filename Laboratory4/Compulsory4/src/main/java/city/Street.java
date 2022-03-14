package city;

import java.util.Objects;

public class Street {
    String name;
    Double length;
    String interName1, interName2;

    public Street(String name, Double length, String interName1, String interName2) {
        this.name = name;
        this.length = length;
        this.interName1 = interName1;
        this.interName2 = interName2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getInterName1() {
        return interName1;
    }

    public void setInterName1(String interName1) {
        this.interName1 = interName1;
    }

    public String getInterName2() {
        return interName2;
    }

    public void setInterName2(String interName2) {
        this.interName2 = interName2;
    }

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", interName1='" + interName1 + '\'' +
                ", interName2='" + interName2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Objects.equals(name, street.name) && Objects.equals(length, street.length) && Objects.equals(interName1, street.interName1) && Objects.equals(interName2, street.interName2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length, interName1, interName2);
    }
}
