package agrisolus.com.br.agconnect.bean.agua;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

import agrisolus.com.br.agconnect.data.AppDatabase;

/**
 * Created by gilbe on 22/11/2017.
 */

@Table(database = AppDatabase.class)
public class JSonMedidorAgua extends BaseModel {

    @PrimaryKey (autoincrement = true)
    @Column
    private long id;

    @SerializedName("count")
    @Expose
    @Column
    private long count; // Onde x é o número de pulsos entre a última leitura e a atual

    @SerializedName("time")
    @Expose
    @Column
    private long timer; // Onde t é o tempo decorrido da última leitura até a atual (em segundos)

    @SerializedName("total")
    @Expose
    @Column
    private long total; // Onde T é o total de pulsos desde o ligamento do sensor até a leitura atual

    @SerializedName("elapsed")
    @Expose
    @Column
    private long elapsed; //  Onde e é o tempo total desde o ligamento do sensor até a leitura atual (em segundos)

    @Column (name = "received_date")
    private Date date;

    @Column
    private long status;

    public JSonMedidorAgua() {
    }

    public JSonMedidorAgua(long id, long count, long timer, long total, long elapsed, Date date, long status) {
        this.id = id;
        this.count = count;
        this.timer = timer;
        this.total = total;
        this.elapsed = elapsed;
        this.date = date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
