package agrisolus.com.br.agconnect.async;

import android.content.Context;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import agrisolus.com.br.agconnect.bean.racao.JSonMedidorRacao;
import agrisolus.com.br.agconnect.bean.racao.JSonParametroMedidorRacao;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.http.JSonHttpSensorRacao;
import agrisolus.com.br.agconnect.padrao.ASyncPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilbe on 22/11/2017.
 */

public class Task_Sensor_Medidor_Racao_Receber extends ASyncPadrao<JSonMedidorRacao> {

    private ISensorMedidorRacaoReceber response;

    public Task_Sensor_Medidor_Racao_Receber(Context context, int mensagemAguarde, @NonNull ISensorMedidorRacaoReceber response) {
        super(context, mensagemAguarde);
        this.response = response;
    }

    @Override
    protected JSonMedidorRacao doInBackground(Void... voids) {
        try {
            JSonParametroMedidorRacao param = SQLite.select().from(JSonParametroMedidorRacao.class)
                    .limit(1).querySingle();


            if (param != null) {
                JSonMedidorRacao medidorRacao = new JSonHttpSensorRacao().obterInformacaoMedidorRacao(
                        param.getEndereco()
                );

                return medidorRacao;
            } else {
                LogUtils.d(Constantes.TAG, "Task_Sensor_Medidor_Racao_Receber.obterInformacaoMedidorAgua() - Configuracao invalida");
                return null;
            }
        } catch (Exception e) {
            LogUtils.e(Constantes.TAG, "Task_Sensor_Medidor_Racao_Receber - Erro: " + e.getMessage());
            e.printStackTrace();
            UtilsRelatorio.enviarRelatorio(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSonMedidorRacao params) {
        super.onPostExecute(params);

        if (response != null) {
            response.OnSensorMedidorRacaoReceber(params);
        }
    }

    public interface ISensorMedidorRacaoReceber {
        void OnSensorMedidorRacaoReceber(JSonMedidorRacao medidorRacao);
    }
}
