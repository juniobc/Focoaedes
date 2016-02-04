package br.gov.go.goiania.focoaedes.entidades;



public class Usuario {

    private String nmUsr;
    private String dsEmail;
    private long nrTel;

    public Usuario(){}

    public Usuario(String nmUsr, String dsEmail, long nrTel){

        this.nmUsr = nmUsr;
        this.dsEmail = dsEmail;
        this.nrTel = nrTel;

    }

    public String getNmUsr() {
        return nmUsr;
    }

    public void setNmUsr(String nmUsr) {
        this.nmUsr = nmUsr;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public long getNrTel() {
        return nrTel;
    }

    public void setNrTel(long nrTel) {
        this.nrTel = nrTel;
    }
}
