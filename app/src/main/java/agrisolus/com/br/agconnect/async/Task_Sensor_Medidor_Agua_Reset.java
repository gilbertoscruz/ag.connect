package agrisolus.com.br.agconnect.async;

import android.content.Context;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua_Table;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.http.JSonHttpSensorAgua;
import agrisolus.com.br.agconnect.padrao.ASyncPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilbe on 22/11/2017.
 */

public class Task_Sensor_Medidor_Agua_Reset extends ASyncPadrao<Boolean> {

    public Task_Sensor_Medidor_Agua_Reset(Context context, int mensagemAguarde) {
        super(context, mensagemAguarde);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            JSonParametroMedidorAgua param = SQLite.select().from(JSonParametroMedidorAgua.class)
                    .where(JSonParametroMedidorAgua_Table.id.eq(1L))
                    .limit(1).querySingle();

            if (param != null) {
                return new JSonHttpSensorAgua().resetMedidor(
                        param.getEndereco()
                );
            } else {
                LogUtils.d(Constantes.TAG, "Task_Sensor_Medidor_Agua_Reset - Configuracao invalida");
                return false;
            }
        } catch (Exception e) {
            LogUtils.e(Constantes.TAG, "Task_Sensor_Medidor_Agua_Reset - Erro: " + e.getMessage());
            e.printStackTrace();
            UtilsRelatorio.enviarRelatorio(e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean params) {
        super.onPostExecute(params);
    }
}
