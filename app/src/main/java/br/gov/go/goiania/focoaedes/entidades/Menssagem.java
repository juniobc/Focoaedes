package br.gov.go.goiania.focoaedes.entidades;


public class Menssagem {

    private int cdMsg;
    private String dsMsg;

    public Menssagem(int cdMsg, String dsMsg){

        this.cdMsg = cdMsg;
        this.dsMsg = dsMsg;

    }


    public int getCdMsg() {
        return cdMsg;
    }

    public void setCdMsg(int cdMsg) {
        this.cdMsg = cdMsg;
    }

    public String getDsMsg() {
        return dsMsg;
    }

    public void setDsMsg(String dsMsg) {
        this.dsMsg = dsMsg;
    }
}
