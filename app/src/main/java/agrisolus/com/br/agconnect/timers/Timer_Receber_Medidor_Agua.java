package agrisolus.com.br.agconnect.timers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;

import agrisolus.com.br.agconnect.async.Task_Sensor_Medidor_Agua_Receber;
import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilbe on 22/11/2017.
 */

public class Timer_Receber_Medidor_Agua extends CountDownTimer {

    public interface IMedidorAgua {
        void OnMedidorAguaResponse(JSonMedidorAgua resposta);
    }

    private Context context;
    private IMedidorAgua response;

    public Timer_Receber_Medidor_Agua(@NonNull Context context, @NonNull IMedidorAgua response) {
        super(60000, 1000);
        this.response = response;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.d(Constantes.TAG, "Timer_Receber_Medidor_Agua >> Timer: " + millisUntilFinished);
    }

    @Override
    public void onFinish() {
        try {
            cancel();
            start();

            Task_Sensor_Medidor_Agua_Receber task = new Task_Sensor_Medidor_Agua_Receber(context, 0, new Task_Sensor_Medidor_Agua_Receber.ISensorMedidorAguaReceber() {
                @Override
                public void OnSensorMedidorAguaReceber(JSonMedidorAgua medidorAgua) {
                    if (response != null) {
                        response.OnMedidorAguaResponse(medidorAgua);
                    }
                }
            });

            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            e.printStackTrace();
            UtilsRelatorio.enviarRelatorio(e);
        }
    }
}
