/**
 *
 */
package agrisolus.com.br.agconnect.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import agrisolus.com.br.agconnect.data.AppDatabase;

/**
 * @author EGILCRU
 */
@Table(database = AppDatabase.class)
public class JSonUsuario extends BaseModel implements Parcelable {

    @SerializedName("Login")
    @Expose
    @PrimaryKey
    @Column
    private String login;

    @SerializedName("Nome")
    @Expose
    @Column
    private String nome;

    @SerializedName("IdBase")
    @Expose
    @Column
    private String idBase;

    @SerializedName("Base")
    @Expose
    @Column
    private String nomeBase;

    @SerializedName("Token")
    @Expose
    @Column
    private String token;

    public JSonUsuario() {
    }

    public JSonUsuario(String login, String nome, String idBase, String nomeBase, String token) {
        this.login = login;
        this.nome = nome;
        this.idBase = idBase;
        this.nomeBase = nomeBase;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdBase() {
        return idBase;
    }

    public void setIdBase(String idBase) {
        this.idBase = idBase;
    }

    public String getNomeBase() {
        return nomeBase;
    }

    public void setNomeBase(String nomeBase) {
        this.nomeBase = nomeBase;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.nome);
        dest.writeString(this.idBase);
        dest.writeString(this.nomeBase);
        dest.writeString(this.token);
    }

    protected JSonUsuario(Parcel in) {
        this.login = in.readString();
        this.nome = in.readString();
        this.idBase = in.readString();
        this.nomeBase = in.readString();
        this.token = in.readString();
    }

    public static final Creator<JSonUsuario> CREATOR = new Creator<JSonUsuario>() {
        @Override
        public JSonUsuario createFromParcel(Parcel source) {
            return new JSonUsuario(source);
        }

        @Override
        public JSonUsuario[] newArray(int size) {
            return new JSonUsuario[size];
        }
    };
}
