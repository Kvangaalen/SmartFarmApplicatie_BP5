package restService.Model;

public class apparaatlocatie {
    private String productid, kasnaam, vaknummer, gebruikersnaam;

    public apparaatlocatie(String productid, String kasnaam, String vaknummer, String gebruikersnaam){
        this.productid = productid;
        this.kasnaam = kasnaam;
        this.vaknummer = vaknummer;
        this.gebruikersnaam = gebruikersnaam;

    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getKasnaam() {
        return kasnaam;
    }

    public void setKasnaam(String kasnaam) {
        this.kasnaam = kasnaam;
    }

    public String getVaknummer() {
        return vaknummer;
    }

    public void setVaknummer(String vaknummer) {
        this.vaknummer = vaknummer;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }
}
