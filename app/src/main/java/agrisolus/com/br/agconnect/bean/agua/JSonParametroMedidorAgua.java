package agrisolus.com.br.agconnect.bean.agua;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import agrisolus.com.br.agconnect.data.AppDatabase;

/**
 * Created by gilbe on 22/11/2017.
 */

@Table(database = AppDatabase.class)
public class JSonParametroMedidorAgua extends BaseModel {

    @PrimaryKey
    @Column (defaultValue = "1")
    private long id;

    @Column
    private String endereco;

    @Column
    private String SSID;

    @Column
    private String senhaRede;

    @Column
    private boolean conectarAutomatico;

    @Column
    private int polegadasCanoMedidor;

    public JSonParametroMedidorAgua() {
    }

    public JSonParametroMedidorAgua(long id, String endereco, String SSID, String senhaRede, boolean conectarAutomatico, int polegadasCanoMedidor) {
        this.id = id;
        this.endereco = endereco;
        this.SSID = SSID;
        this.senhaRede = senhaRede;
        this.conectarAutomatico = conectarAutomatico;
        this.polegadasCanoMedidor = polegadasCanoMedidor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getPolegadasCanoMedidor() {
        return polegadasCanoMedidor;
    }

    public void setPolegadasCanoMedidor(int polegadasCanoMedidor) {
        this.polegadasCanoMedidor = polegadasCanoMedidor;
    }
}
