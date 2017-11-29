package agrisolus.com.br.agconnect.timers;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import agrisolus.com.br.agconnect.R;
import agrisolus.com.br.agconnect.controller.Controller_Envio_Sensor_Agua;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilbe on 22/11/2017.
 */

public class Timer_Enviar_Informacao extends CountDownTimer {

    private Context context;

    public Timer_Enviar_Informacao(@NonNull Context context) {
        //super(60000 * 30, 1000);
        super(60000 * 30, 1000);
        this.context = context;
    }

    @Override
    public void onTick(long time) {
        Activity activity = (Activity) context;

        try {
            if (activity != null) {

                if (activity.findViewById(R.id.layout_erro_enviar_arquivo) != null) {
                    if (activity.findViewById(R.id.tv_sem_internet) != null) {
                        try {
                            ((TextView) activity.findViewById(R.id.tv_sem_internet)).setText(
                                    activity.getString(R.string.app_sem_internet) + "\n" +
                                            activity.getString(R.string.app_sem_internet_verificar) + " " +
                                            String.format(
                                                    activity.getString(R.string.app_tempo_enviar),
                                                    TimeUnit.MILLISECONDS.toMinutes(time),
                                                    TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
                                            )
                            );
                        } catch (Exception e) {
                            UtilsRelatorio.enviarRelatorio(e);
                            e.printStackTrace();
                        }
                    }
                }

                if (activity.findViewById(R.id.tv_tempo_envio_arquivos) != null) {
                    ((TextView) (activity.findViewById(R.id.tv_tempo_envio_arquivos))).setText(
                            String.format(
                                    activity.getString(R.string.app_tempo_envio_arquivos_novo),
                                    TimeUnit.MILLISECONDS.toMinutes(time),
                                    TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
                            )
                    );
                }
            }
        } catch (Exception e) {
            UtilsRelatorio.enviarRelatorio(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish() {
        cancel();
        start();

        try {
            if (((Activity) context).findViewById(R.id.layout_erro_enviar_arquivo) != null) {
                ((Activity) context).findViewById(R.id.layout_erro_enviar_arquivo).setVisibility(View.GONE);
            }

            Controller_Envio_Sensor_Agua controller = new Controller_Envio_Sensor_Agua(context, new Controller_Envio_Sensor_Agua.IControllerEnvioSensorAgua() {
                @Override
                public void OnControllerEnvioSensorAgua(Controller_Envio_Sensor_Agua.Controller_Envio_Sensor_Retorno retorno) {
                    if (retorno == null) {
                        if (((Activity) context).findViewById(R.id.layout_erro_enviar_arquivo) != null) {
                            ((Activity) context).findViewById(R.id.layout_erro_enviar_arquivo).setVisibility(View.VISIBLE);
                        }
                    } else if (retorno.erro) {
                        if (((Activity) context).findViewById(R.id.layout_erro_enviar_arquivo) != null) {
                            ((Activity) context).findViewById(R.id.layout_erro_enviar_arquivo).setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            controller.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            UtilsRelatorio.enviarRelatorio(e);
            e.printStackTrace();
        }
    }
}
