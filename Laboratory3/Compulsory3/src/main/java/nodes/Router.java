package nodes;

import java.util.Objects;

// A routed is a node that is identifiable
public class Router extends Node implements Identifiable{
    private String ipAddress;

    public Router(String name, String macAddress, Location mapLocation, String ipAddress) {
        super(name, macAddress, mapLocation);
        this.ipAddress = ipAddress;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "Router{" +
                "name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", mapLocation=" + mapLocation +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Router router = (Router) o;
        return Objects.equals(ipAddress, router.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ipAddress);
    }
}
