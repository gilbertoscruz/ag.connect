package agrisolus.com.br.agconnect.http;

import com.google.gson.Gson;

import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
import agrisolus.com.br.agconnect.bean.racao.JSonMedidorRacao;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.padrao.HttpJSONPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilbe on 22/11/2017.
 */

public class JSonHttpSensorRacao extends HttpJSONPadrao {

    /**
     * Retorna as informações do sensor
     * @return JSonMedidorAgua
     */
    public JSonMedidorRacao obterInformacaoMedidorRacao(String url) {
        try {
            String retorno = MEDIDOR_Request(url);
            return new Gson().fromJson(retorno, JSonMedidorRacao.class);
        } catch (Exception e) {
            LogUtils.d(Constantes.TAG, "JSonHttpSensorRacao.obterInformacaoMedidorRacao() - Erro: " + e.getMessage());
            UtilsRelatorio.enviarRelatorio(e);
            return null;
        }
    }

}
