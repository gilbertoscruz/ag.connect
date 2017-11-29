package agrisolus.com.br.agconnect.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import agrisolus.com.br.agconnect.bean.JSonParametros;
import agrisolus.com.br.agconnect.bean.JSonUsuario;

/**
 * Created by Janaina on 23/10/2016.
 */

public class Aplicacao extends MultiDexApplication {

    private static Aplicacao instance = null;
    private static JSonParametros parametros = null;

    /* (non-Javadoc)
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        MultiDex.install(this);

        /******************************************************************************************/
        /******************************************************************************************/

        FlowManager.init(
                new FlowConfig.Builder(this).build()
        );

        FlowLog.setMinimumLoggingLevel(FlowLog.Level.E);

        /******************************************************************************************/
        /******************************************************************************************/
    }

    public static Aplicacao getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * Retorna o usuario da aplicacao no momento
     *
     * @return JSonUsuario
     */
    public JSonUsuario getUsuario() {
        return new Select().from(JSonUsuario.class).querySingle();
    }

    public JSonParametros getParametros() {
        if (parametros == null) {
            parametros = SQLite.select().from(JSonParametros.class).limit(1).querySingle();

            if (parametros == null) {
                parametros = new JSonParametros();

                parametros.save();
            }
        }

        return parametros;
    }

    /**
     * Obter a versao do aplicacao
     *
     * @return String
     */
    public String obterVersao() {
        try {
            PackageInfo _info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return _info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
