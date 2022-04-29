package restService.model;

public class Payload {
    private String bn;
    private String bt;
    private String n;
    private String vs;
    private String v;

    public Payload(String bn, String bt, String n, String vs, String v) {
        this.bn = bn;
        this.bt = bt;
        this.vs = vs;
        this.n = n;
    }

    public String getBn() {
        return bn;
    }
    public String getBt() {
        return bt;
    }
    public String getN() {
        return n;
    }
    public String getVs() {
        return vs;
    }
    public String getV() {
        return v;
    }
}