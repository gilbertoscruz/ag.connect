package agrisolus.com.br.agconnect.timers;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import agrisolus.com.br.agconnect.async.Task_Sensor_Medidor_Racao_Receber;
import agrisolus.com.br.agconnect.bean.racao.JSonMedidorRacao;

/**
 * Created by gilbe on 22/11/2017.
 */

public class Timer_Receber_Medidor_Racao extends CountDownTimer {

    private Context context;
    private IMedidorRacao response;
    public Timer_Receber_Medidor_Racao(@NonNull Context context, @NonNull IMedidorRacao response) {
        super(60000, 1000);
        this.response = response;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
    }

    @Override
    public void onFinish() {

        Task_Sensor_Medidor_Racao_Receber task = new Task_Sensor_Medidor_Racao_Receber(context, 0, new Task_Sensor_Medidor_Racao_Receber.ISensorMedidorRacaoReceber() {
            @Override
            public void OnSensorMedidorRacaoReceber(JSonMedidorRacao medidorRacao) {
                if (response != null) {
                    response.OnMedidorRacaoResponse(medidorRacao);
                }
            }
        });


        cancel();
        start();
    }

    public interface IMedidorRacao {
        void OnMedidorRacaoResponse(JSonMedidorRacao resposta);
    }
}
