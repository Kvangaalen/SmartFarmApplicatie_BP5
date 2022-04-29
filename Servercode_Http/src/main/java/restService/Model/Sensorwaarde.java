package restService.model;

public class Sensorwaarde {
    private int tempratuur, luchtVochtigheid, bodemVochtigheid, lichtSterkte;
    private String tijd, datum, productId;

    public Sensorwaarde(int tempratuur, int luchtVochtigheid, int bodemVochtigheid, int lichtSterkte){
        this.tempratuur = tempratuur;
        this.luchtVochtigheid = luchtVochtigheid;
        this.bodemVochtigheid = bodemVochtigheid;
        this.lichtSterkte = lichtSterkte;
    }

    public Sensorwaarde(int tempratuur, int luchtVochtigheid, int bodemVochtigheid, int lichtSterkte, String tijd, String datum, String productId){
        this.tempratuur = tempratuur;
        this.luchtVochtigheid = luchtVochtigheid;
        this.bodemVochtigheid = bodemVochtigheid;
        this.lichtSterkte = lichtSterkte;
        this.tijd = tijd;
        this.datum = datum;
        this.productId = productId;
    }

    public String getTijd() { return tijd; }

    public void setTijd(String tijd) { this.tijd = tijd; }

    public String getDatum() { return datum; }

    public void setDatum(String datum) { this.datum = datum;}

    public String getProductid() { return productId; }

    public void setProductid(String productId) { this.productId = productId;}

    public int getTempratuur() {
        return tempratuur;
    }

    public void setTempratuur(int tempratuur) {
        this.tempratuur = tempratuur;
    }

    public int getLuchtVochtigheid() {
        return luchtVochtigheid;
    }

    public void setLuchtVochtigheid(int luchtVochtigheid) {
        this.luchtVochtigheid = luchtVochtigheid;
    }

    public int getBodemVochtigheid() {
        return bodemVochtigheid;
    }

    public void setBodemVochtigheid(int bodemVochtigheid) {
        this.bodemVochtigheid = bodemVochtigheid;
    }

    public int getLichtSterkte() {
        return lichtSterkte;
    }

    public void setLichtSterkte(int lichtSterkte) {
        this.lichtSterkte = lichtSterkte;
    }
}
