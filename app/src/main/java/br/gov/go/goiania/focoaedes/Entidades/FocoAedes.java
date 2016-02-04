package br.gov.go.goiania.focoaedes.entidades;


public class FocoAedes {

    private String dsFocoAedes;
    private String nrLat;
    private String nrLong;
    private byte[] imgLocal;

    public FocoAedes(String dsFocoAedes, String nrLat, String nrLong, byte[] imgLocal) {

        this.dsFocoAedes = dsFocoAedes;
        this.nrLat = nrLat;
        this.nrLong = nrLong;
        this.imgLocal = imgLocal;
    }


    public String getDsFocoAedes() {
        return dsFocoAedes;
    }

    public void setDsFocoAedes(String dsFocoAedes) {
        this.dsFocoAedes = dsFocoAedes;
    }


    public String getNrLat() {
        return nrLat;
    }

    public void setNrLat(String nrLat) {
        this.nrLat = nrLat;
    }

    public String getNrLong() {
        return nrLong;
    }

    public void setNrLong(String nrLong) {
        this.nrLong = nrLong;
    }

    public byte[] getImgLocal() {
        return imgLocal;
    }

    public void setImgLocal(byte[] imgLocal) {
        this.imgLocal = imgLocal;
    }
}
