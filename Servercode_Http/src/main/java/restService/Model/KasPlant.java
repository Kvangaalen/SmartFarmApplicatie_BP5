package restService.Model;

public class KasPlant {
    private Integer plantid, vaknummer;
    private String kasnaam,plantsoort, plantras, gebruikersnaam;

    public KasPlant (Integer plantid, String kasnaam, String plantsoort, String plantras, Integer vaknummer, String gebruikersnaam) {
        this.plantid = plantid;
        this.kasnaam = kasnaam;
        this.plantsoort = plantsoort;
        this.plantras = plantras;
        this.vaknummer = vaknummer;
        this.gebruikersnaam = gebruikersnaam;
    }

    public Integer getPlantid() {
        return plantid;
    }

    public void setPlantid(Integer plantid) {
        this.plantid = plantid;
    }

    public Integer getVaknummer() {
        return vaknummer;
    }

    public void setVaknummer(Integer vaknummer) {
        this.vaknummer = vaknummer;
    }

    public String getKasnaam() {
        return kasnaam;
    }

    public void setKasnaam(String kasnaam) {
        this.kasnaam = kasnaam;
    }

    public String getPlantsoort() {
        return plantsoort;
    }

    public void setPlantsoort(String plantsoort) {
        this.plantsoort = plantsoort;
    }

    public String getPlantras() {
        return plantras;
    }

    public void setPlantras(String plantras) {
        this.plantras = plantras;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }
}
