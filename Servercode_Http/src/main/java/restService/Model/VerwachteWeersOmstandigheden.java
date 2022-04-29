package restService.model;

public class VerwachteWeersOmstandigheden {
    private String weertype, postcode;
    private int buitenTempratuur, buitenVochtigheid;
    public VerwachteWeersOmstandigheden(int buitenTempratuur, int buitenVochtigheid, String weertype) {
        this.buitenTempratuur = buitenTempratuur;
        this.buitenVochtigheid = buitenVochtigheid;
        this.weertype = weertype;
        this.postcode = postcode;
    }

    public int getBuitenTempratuur() {
        return buitenTempratuur;
    }

    public void setBuitenTempratuur(int buitenTempratuur) {
        this.buitenTempratuur = buitenTempratuur;
    }

    public int getBuitenVochtigheid() {
        return buitenVochtigheid;
    }

    public void setBuitenVochtigheid(int buitenVochtigheid) {
        this.buitenVochtigheid = buitenVochtigheid;
    }

    public String getWeertype() {
        return weertype;
    }

    public void setWeertype(String weertype) {
        this.weertype = weertype;
    }
}
