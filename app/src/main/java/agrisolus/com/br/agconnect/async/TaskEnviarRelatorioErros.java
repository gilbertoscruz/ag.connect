package agrisolus.com.br.agconnect.async;

import android.app.Application;
import android.os.AsyncTask;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.http.JSonHttpServicoRelatorioErros;
import agrisolus.com.br.agconnect.utils.LogUtils;


/**
 * Created by Janaina on 02/12/2016.
 */

public class TaskEnviarRelatorioErros extends AsyncTask<Exception, Void, Void> {

    public TaskEnviarRelatorioErros() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Exception... params) {
        LogUtils.d(Constantes.TAG, "TaskEnviarRelatorioErros.doInBackground()...");

        try {
            new JSonHttpServicoRelatorioErros().enviarRelatorio(
                    params[0],
                    Aplicacao.getInstance().obterVersao()
            );

        } catch (Exception e) {
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }

    public Application getApplicationUsingReflection() throws Exception {
        return (Application) Class.forName("android.app.AppGlobals")
                .getMethod("getInitialApplication").invoke(null, (Object[]) null);
    }
}
