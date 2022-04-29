package restService.model;

public class Gebruikerinformatie {
    private String gebruikersNaam, naam, email, telefoonnummer, wachtwoord, bedrijfsNaam;

    public Gebruikerinformatie(String gebruikersNaam, String naam, String email, String telefoonnummer,String wachtwoord, String bedrijfsNaam){
        this.gebruikersNaam = gebruikersNaam;
        this.naam = naam;
        this.email = email;
        this.telefoonnummer = telefoonnummer;
        this.wachtwoord = wachtwoord;
        this.bedrijfsNaam = bedrijfsNaam;
    }



    public String getGebruikersNaam() {
        return gebruikersNaam;
    }

    public void setGebruikersNaam(String gebruikersNaam) {
        this.gebruikersNaam = gebruikersNaam;
    }

    public String getNaamEigenaar() {
        return naam;
    }

    public void setNaamEigenaar(String naam) {
        this.naam = naam;
    }

    public String getEmailAdres() {
        return email;
    }

    public void setEmailAdres(String email) {
        this.email = email;
    }

    public String getTelefoonEigenaar() {
        return telefoonnummer;
    }

    public void setTelefoonEigenaar(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getBedrijfsNaam() {
        return bedrijfsNaam;
    }

    public void setBedrijfsNaam(String bedrijfsNaam) {
        this.bedrijfsNaam = bedrijfsNaam;
    }
}
