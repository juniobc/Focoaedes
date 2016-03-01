package br.gov.go.goiania.focoaedes.entidades;


public class FocoAedes {

    private int cdFocoAedes;
    private String dsFocoAedes;
    private String status;
    private byte[] imgLocal;
    private String urlImg;
    private String endereco;
    private String dtAbertura;
    private String hrAbertura;
    private String dtStatus;
    private String hrStatus;
    private Menssagem msgErro;

    public FocoAedes(){}

    public FocoAedes(int cdFocoAedes, String dsFocoAedes, String status) {

        this.cdFocoAedes = cdFocoAedes;
        this.dsFocoAedes = dsFocoAedes;
        this.status = status;
    }

    public FocoAedes(int cdFocoAedes, String dsFocoAedes, String status, byte[] imgLocal) {

        this.cdFocoAedes = cdFocoAedes;
        this.dsFocoAedes = dsFocoAedes;
        this.status = status;
        this.imgLocal = imgLocal;
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


    public byte[] getImgLocal() {
        return imgLocal;
    }

    public void setImgLocal(byte[] imgLocal) {
        this.imgLocal = imgLocal;
    }


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public String getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(String dtAbertura) {
        this.dtAbertura = dtAbertura;
    }

    public String getHrAbertura() {
        return hrAbertura;
    }

    public void setHrAbertura(String hrAbertura) {
        this.hrAbertura = hrAbertura;
    }

    public String getDtStatus() {
        return dtStatus;
    }

    public void setDtStatus(String dtStatus) {
        this.dtStatus = dtStatus;
    }

    public String getHrStatus() {
        return hrStatus;
    }

    public void setHrStatus(String hrStatus) {
        this.hrStatus = hrStatus;
    }


    public Menssagem getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(Menssagem msgErro) {
        this.msgErro = msgErro;
    }
}
