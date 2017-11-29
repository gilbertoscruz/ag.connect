package agrisolus.com.br.agconnect.bean.racao;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import agrisolus.com.br.agconnect.data.AppDatabase;

/**
 * Created by gilbe on 22/11/2017.
 */

@Table(database = AppDatabase.class)
public class JSonParametroMedidorRacao extends BaseModel {

    @PrimaryKey
    @Column
    private String endereco;

    @Column
    private String SSID;

    @Column
    private String senhaRede;

    @Column
    private boolean conectarAutomatico;

    public JSonParametroMedidorRacao() {
    }

    public JSonParametroMedidorRacao(String endereco, String SSID, String senhaRede, boolean conectarAutomatico) {
        this.endereco = endereco;
        this.SSID = SSID;
        this.senhaRede = senhaRede;
        this.conectarAutomatico = conectarAutomatico;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getSenhaRede() {
        return senhaRede;
    }

    public void setSenhaRede(String senhaRede) {
        this.senhaRede = senhaRede;
    }

    public boolean isConectarAutomatico() {
        return conectarAutomatico;
    }

    public void setConectarAutomatico(boolean conectarAutomatico) {
        this.conectarAutomatico = conectarAutomatico;
    }
}
