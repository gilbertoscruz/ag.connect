package agrisolus.com.br.agconnect.utils;

import android.os.AsyncTask;

import agrisolus.com.br.agconnect.async.TaskEnviarRelatorioErros;

/**
 * Created by Janaina on 23/01/2017.
 */

public class UtilsRelatorio {

    /**
     * Envia o relatorio de erros para o site
     * @param exception
     */
    public static void enviarRelatorio(Exception exception) {
        TaskEnviarRelatorioErros tere = new TaskEnviarRelatorioErros();
        tere.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, exception);
    }

}
