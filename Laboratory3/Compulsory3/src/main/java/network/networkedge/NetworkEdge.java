package network.networkedge;

import java.util.Objects;

public class NetworkEdge {
    private String srcNode, destNode;
    private long transferTime;

    public NetworkEdge(String srcNode, String destNode, long transferTime) {
        this.srcNode = srcNode;
        this.destNode = destNode;
        this.transferTime = transferTime;
    }

    public String getSrcNode() {
        return srcNode;
    }

    public void setSrcNode(String srcNode) {
        this.srcNode = srcNode;
    }

    public String getDestNode() {
        return destNode;
    }

    public void setDestNode(String destNode) {
        this.destNode = destNode;
    }

    public long getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(long transferTime) {
        this.transferTime = transferTime;
    }

    @Override
    public String toString() {
        return "NetworkEdge{" +
                "srcNode='" + srcNode + '\'' +
                ", destNode='" + destNode + '\'' +
                ", transferTime=" + transferTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkEdge that = (NetworkEdge) o;
        return transferTime == that.transferTime && Objects.equals(srcNode, that.srcNode) && Objects.equals(destNode, that.destNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(srcNode, destNode, transferTime);
    }
}
