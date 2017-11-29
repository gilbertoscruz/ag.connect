package agrisolus.com.br.agconnect.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import agrisolus.com.br.agconnect.data.AppDatabase;

/**
 * Created by gilbe on 24/11/2017.
 */

@Table(database = AppDatabase.class)
public class JSonParametros extends BaseModel implements Parcelable {

    @PrimaryKey
    @Column (defaultValue = "1")
    private long id;

    @Column
    private String servidorEndereco;

    @Column
    private long servidorPorta;

    @Column
    private String servidorUsuario;

    @Column
    private String servidorSenha;

    /* Padrao de envio: 30 minutos */
    @Column (defaultValue = "1800000")
    private long enviarEm;


    @Column (defaultValue = "60000")
    private long sensorAguaRequisitarEm;

    @Column (defaultValue = "60000")
    private long sensorRacaoRequisitarEm;

    public JSonParametros() {
    }

    public JSonParametros(long id, String servidorEndereco, long servidorPorta, String servidorUsuario, String servidorSenha, long enviarEm, long sensorAguaRequisitarEm, long sensorRacaoRequisitarEm) {
        this.id = id;
        this.servidorEndereco = servidorEndereco;
        this.servidorPorta = servidorPorta;
        this.servidorUsuario = servidorUsuario;
        this.servidorSenha = servidorSenha;
        this.enviarEm = enviarEm;
        this.sensorAguaRequisitarEm = sensorAguaRequisitarEm;
        this.sensorRacaoRequisitarEm = sensorRacaoRequisitarEm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServidorEndereco() {
        return servidorEndereco;
    }

    public void setServidorEndereco(String servidorEndereco) {
        this.servidorEndereco = servidorEndereco;
    }

    public long getServidorPorta() {
        return servidorPorta;
    }

    public void setServidorPorta(long servidorPorta) {
        this.servidorPorta = servidorPorta;
    }

    public String getServidorUsuario() {
        return servidorUsuario;
    }

    public void setServidorUsuario(String servidorUsuario) {
        this.servidorUsuario = servidorUsuario;
    }

    public String getServidorSenha() {
        return servidorSenha;
    }

    public void setServidorSenha(String servidorSenha) {
        this.servidorSenha = servidorSenha;
    }

    public long getEnviarEm() {
        return enviarEm;
    }

    public void setEnviarEm(long enviarEm) {
        this.enviarEm = enviarEm;
    }

    public long getSensorAguaRequisitarEm() {
        return sensorAguaRequisitarEm;
    }

    public void setSensorAguaRequisitarEm(long sensorAguaRequisitarEm) {
        this.sensorAguaRequisitarEm = sensorAguaRequisitarEm;
    }

    public long getSensorRacaoRequisitarEm() {
        return sensorRacaoRequisitarEm;
    }

    public void setSensorRacaoRequisitarEm(long sensorRacaoRequisitarEm) {
        this.sensorRacaoRequisitarEm = sensorRacaoRequisitarEm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.servidorEndereco);
        dest.writeLong(this.servidorPorta);
        dest.writeString(this.servidorUsuario);
        dest.writeString(this.servidorSenha);
        dest.writeLong(this.enviarEm);
        dest.writeLong(this.sensorAguaRequisitarEm);
        dest.writeLong(this.sensorRacaoRequisitarEm);
    }

    protected JSonParametros(Parcel in) {
        this.id = in.readLong();
        this.servidorEndereco = in.readString();
        this.servidorPorta = in.readLong();
        this.servidorUsuario = in.readString();
        this.servidorSenha = in.readString();
        this.enviarEm = in.readLong();
        this.sensorAguaRequisitarEm = in.readLong();
        this.sensorRacaoRequisitarEm = in.readLong();
    }

    public static final Creator<JSonParametros> CREATOR = new Creator<JSonParametros>() {
        @Override
        public JSonParametros createFromParcel(Parcel source) {
            return new JSonParametros(source);
        }

        @Override
        public JSonParametros[] newArray(int size) {
            return new JSonParametros[size];
        }
    };
}
