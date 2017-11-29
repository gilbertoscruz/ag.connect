package agrisolus.com.br.agconnect.bean;

import java.util.Date;

/**
 * Created by gilbe on 24/11/2017.
 */

public class JSonMedidorAguaWeb {

    private long empresa;
    private long aviario;
    private Date data;
    private String numeroMedidor;
    private long leitura;

    public JSonMedidorAguaWeb() {
    }

    public JSonMedidorAguaWeb(long empresa, long aviario, Date data, String numeroMedidor, long leitura) {
        this.empresa = empresa;
        this.aviario = aviario;
        this.data = data;
        this.numeroMedidor = numeroMedidor;
        this.leitura = leitura;
    }

    public long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(long empresa) {
        this.empresa = empresa;
    }

    public long getAviario() {
        return aviario;
    }

    public void setAviario(long aviario) {
        this.aviario = aviario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNumeroMedidor() {
        return numeroMedidor;
    }

    public void setNumeroMedidor(String numeroMedidor) {
        this.numeroMedidor = numeroMedidor;
    }

    public long getLeitura() {
        return leitura;
    }

    public void setLeitura(long leitura) {
        this.leitura = leitura;
    }
}
