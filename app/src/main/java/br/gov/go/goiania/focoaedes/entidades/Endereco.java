package br.gov.go.goiania.focoaedes.entidades;

public class Endereco {

    private int cdBairro;
    private String nmBairro;
    private int cdLogr;
    private String nmLogr;

    public Endereco(int cdBairro, String nmBairro, int cdLogr, String nmLogr){

        this.cdBairro = cdBairro;
        this.nmBairro = nmBairro;
        this.setCdLogr(cdLogr);
        this.setNmLogr(nmLogr);

    }


    public int getCdBairro() {
        return cdBairro;
    }

    public void setCdBairro(int cdBairro) {
        this.cdBairro = cdBairro;
    }

    public String getNmBairro() {
        return nmBairro;
    }

    public void setNmBairro(String nmBairro) {
        this.nmBairro = nmBairro;
    }


    public int getCdLogr() {
        return cdLogr;
    }

    public void setCdLogr(int cdLogr) {
        this.cdLogr = cdLogr;
    }

    public String getNmLogr() {
        return nmLogr;
    }

    public void setNmLogr(String nmLogr) {
        this.nmLogr = nmLogr;
    }
}
