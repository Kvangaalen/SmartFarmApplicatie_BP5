package restService.Model;

public class Gemetenverschilwaarde {
    private String productid, datum, tijdstip;
    private int  gemetentempratuur, gemetenluchtvochtigheid, gemetenbodemvochtigheid, gemetenlux;

    public Gemetenverschilwaarde(String productid, String datum, String tijdstip, int gemetentempratuur, int gemetenluchtvochtigheid, int gemetenbodemvochtigheid, int gemetenlux){
        this.productid = productid;
        this.datum = datum;
        this.tijdstip = tijdstip;
        this.gemetentempratuur = gemetentempratuur;
        this.gemetenluchtvochtigheid = gemetenluchtvochtigheid;
        this.gemetenbodemvochtigheid = gemetenbodemvochtigheid;
        this. gemetenlux = gemetenlux;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public int getGemetentempratuur() {
        return gemetentempratuur;
    }

    public void setGemetentempratuur(int gemetentempratuur) {
        this.gemetentempratuur = gemetentempratuur;
    }

    public int getGemetenluchtvochtigheid() {
        return gemetenluchtvochtigheid;
    }

    public void setGemetenluchtvochtigheid(int gemetenluchtvochtigheid) {
        this.gemetenluchtvochtigheid = gemetenluchtvochtigheid;
    }

    public int getGemetenbodemvochtigheid() {
        return gemetenbodemvochtigheid;
    }

    public void setGemetenbodemvochtigheid(int gemetenbodemvochtigheid) {
        this.gemetenbodemvochtigheid = gemetenbodemvochtigheid;
    }

    public int getGemetenlux() {
        return gemetenlux;
    }

    public void setGemetenlux(int gemetenlux) {
        this.gemetenlux = gemetenlux;
    }

}
