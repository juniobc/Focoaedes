package br.gov.go.goiania.focoaedes.entidades;



public class Usuario {

    private int cdUsr;
    private String nmUsr;
    private String dsEmail;
    private String nrCpf;

    public Usuario(){}

    public Usuario(int cdUsr, String nmUsr, String dsEmail, String nrCpf){

        this.cdUsr = cdUsr;
        this.nmUsr = nmUsr;
        this.dsEmail = dsEmail;
        this.nrCpf = nrCpf;

    }

    public Usuario(String nmUsr, String dsEmail, String nrCpf){

        this.nmUsr = nmUsr;
        this.dsEmail = dsEmail;
        this.nrCpf = nrCpf;

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

    public String getNrCpf() {
        return nrCpf;
    }

    public void setNrCpf(String nrCpf) {
        this.nrCpf = nrCpf;
    }


    public int getCdUsr() {
        return cdUsr;
    }

    public void setCdUsr(int cdUsr) {
        this.cdUsr = cdUsr;
    }
}
