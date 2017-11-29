package agrisolus.com.br.agconnect.http;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.padrao.HttpJSONPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsData;

/**
 * Handles the comminication with Parse.com
 * <p>
 * User: udinic Date: 3/27/13 Time: 3:30 AM
 */
public class JSonHttpServicoRelatorioErros extends HttpJSONPadrao {

    /**
     * Enviar um relatorio de erros para o servidor
     *
     * @param exception
     */
    public void enviarRelatorio(Exception exception, String versao) {
        LogUtils.d(Constantes.TAG, "Enviando relatorio de erros...");

        try {
            Aplicacao app = (Aplicacao) Aplicacao.getInstance();

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("data", UtilsData.getDataHoraAtualMySQLFormatNormal());
            jsonObject.accumulate("aplicacao", Constantes.TAG);
            jsonObject.accumulate("versao", versao);
            jsonObject.accumulate("usuario", app.getUsuario().getLogin());
            jsonObject.accumulate("descricao", getStackTrace(exception));

            String sbRetorno = httpRequest(
                    Constantes.CONST_URL_RELATORIO_ERROS,
                    jsonObject
            );

        } catch (Exception e) {
            LogUtils.e(Constantes.TAG, "Erro ao enviar o relatorio de erros: " + e.getMessage());
        }
    }

    /**
     * enviarRelatorio
     *
     * @param exception
     * @param versao
     */
    public void enviarRelatorio(Throwable exception, String versao, String usuario) {
        LogUtils.d(Constantes.TAG, "Enviando relatorio de erros...");

        try {
            String errorMsg = getStackTraceElement(exception) + " - " + getStackTrace(exception);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("data", UtilsData.getDataHoraAtualMySQLFormatNormal());
            jsonObject.accumulate("aplicacao", Constantes.TAG);
            jsonObject.accumulate("versao", versao);
            jsonObject.accumulate("usuario", usuario);
            jsonObject.accumulate("descricao", errorMsg);

            String sbRetorno = httpRequest(
                    Constantes.CONST_URL_RELATORIO_ERROS,
                    jsonObject
            );
        } catch (Exception e) {
            LogUtils.e(Constantes.TAG, "Erro ao enviar o relatorio de erros: " + e.getMessage());
        }
    }

    /**
     * getStackTrace
     *
     * @param throwable
     * @return
     */
    private String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * getStackTraceElement
     *
     * @param throwable
     * @return
     */
    private String getStackTraceElement(final Throwable throwable) {

        if (throwable.getStackTrace() == null) {
            return "";
        }

        if (throwable.getStackTrace().length < 0) {
            return "";
        }

        String s = "Method: " + throwable.getStackTrace()[0].getMethodName()
                + " on line " + throwable.getStackTrace()[0].getLineNumber();

        return s;
    }
}
