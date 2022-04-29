package restService.model;

public class idealeomstandigheden {
    String plantras, plantsoort;
    int idealetempratuur, idealeluchtvochtigheid, idealebodemvochtigheid, idealelux;

    public idealeomstandigheden(String plantras, String plantsoort, int idealetempratuur, int idealeluchtvochtigheid, int idealebodemvochtigheid, int idealelux){
        this.plantras = plantras;
        this.plantsoort = plantsoort;
        this.idealetempratuur = idealetempratuur;
        this.idealeluchtvochtigheid = idealeluchtvochtigheid;
        this.idealebodemvochtigheid = idealebodemvochtigheid;
        this.idealelux = idealelux;
    }

    public String getPlantras() {
        return plantras;
    }

    public void setPlantras(String plantras) {
        this.plantras = plantras;
    }

    public String getPlantsoort() {
        return plantsoort;
    }

    public void setPlantsoort(String plantsoort) {
        this.plantsoort = plantsoort;
    }

    public int getIdealetempratuur() {
        return idealetempratuur;
    }

    public void setIdealetempratuur(int idealetempratuur) {
        this.idealetempratuur = idealetempratuur;
    }

    public int getIdealeluchtvochtigheid() {
        return idealeluchtvochtigheid;
    }

    public void setIdealeluchtvochtigheid(int idealeluchtvochtigheid) {
        this.idealeluchtvochtigheid = idealeluchtvochtigheid;
    }

    public int getIdealebodemvochtigheid() {
        return idealebodemvochtigheid;
    }

    public void setIdealebodemvochtigheid(int idealebodemvochtigheid) {
        this.idealebodemvochtigheid = idealebodemvochtigheid;
    }

    public int getIdealelux() {
        return idealelux;
    }

    public void setIdealelux(int idealelux) {
        this.idealelux = idealelux;
    }
}
