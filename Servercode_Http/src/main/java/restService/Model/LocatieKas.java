package restService.Model;

public class LocatieKas {

    private String gebruikersnaam, kasnaam, straatnaam, huisnummer, plaats, postcode;


    public LocatieKas(String gebruikersnaam, String kasnaam, String straatnaam, String huisnummer, String plaats, String postcode) {
        this.gebruikersnaam =gebruikersnaam;
        this.kasnaam = kasnaam;
        this.straatnaam = straatnaam;
        this.huisnummer = huisnummer;
        this.plaats = plaats;
        this.postcode = postcode;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getKasnaam() {
        return kasnaam;
    }

    public void setKasnaam(String kasnaam) {
        this.kasnaam = kasnaam;
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
}
