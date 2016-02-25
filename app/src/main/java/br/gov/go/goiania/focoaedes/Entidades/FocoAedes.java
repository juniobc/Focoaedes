package br.gov.go.goiania.focoaedes.entidades;


public class FocoAedes {

    private int cdFocoAedes;
    private String dsFocoAedes;
    private String status;
    private String urlImg;

    public FocoAedes(int cdFocoAedes, String dsFocoAedes, String status) {

        this.cdFocoAedes = cdFocoAedes;
        this.dsFocoAedes = dsFocoAedes;
        this.status = status;
    }


    public String getDsFocoAedes() {
        return dsFocoAedes;
    }

    public void setDsFocoAedes(String dsFocoAedes) {
        this.dsFocoAedes = dsFocoAedes;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }


    public int getCdFocoAedes() {
        return cdFocoAedes;
    }

    public void setCdFocoAedes(int cdFocoAedes) {
        this.cdFocoAedes = cdFocoAedes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
