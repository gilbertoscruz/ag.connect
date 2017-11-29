package agrisolus.com.br.agconnect.async;

import android.content.Context;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
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

public class Task_Sensor_Medidor_Agua_Receber extends ASyncPadrao<JSonMedidorAgua> {

    private ISensorMedidorAguaReceber response;

    public Task_Sensor_Medidor_Agua_Receber(Context context, int mensagemAguarde, @NonNull ISensorMedidorAguaReceber response) {
        super(context, mensagemAguarde);
        this.response = response;
    }

    @Override
    protected JSonMedidorAgua doInBackground(Void... voids) {
        try {
            JSonMedidorAgua medidorAgua = new JSonHttpSensorAgua().obterInformacaoMedidorAgua();

            return medidorAgua;
            /*} else {
                LogUtils.d(Constantes.TAG, "Task_Sensor_Medidor_Agua_Receber.obterInformacaoMedidorAgua() - Configuracao invalida");
                UtilsRelatorio.enviarRelatorio(new Exception("Task_Sensor_Medidor_Agua_Receber->param is null"));
                return null;
            }*/
        } catch (Exception e) {
            LogUtils.e(Constantes.TAG, "Task_Sensor_Medidor_Agua_Receber - Erro: " + e.getMessage());
            e.printStackTrace();
            UtilsRelatorio.enviarRelatorio(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSonMedidorAgua params) {
        super.onPostExecute(params);

        if (response != null) {
            response.OnSensorMedidorAguaReceber(params);
        }
    }

    public interface ISensorMedidorAguaReceber {
        void OnSensorMedidorAguaReceber(JSonMedidorAgua medidorAgua);
    }
}
