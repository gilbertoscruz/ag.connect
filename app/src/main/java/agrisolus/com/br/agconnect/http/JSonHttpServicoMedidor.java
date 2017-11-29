package agrisolus.com.br.agconnect.http;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.bean.JSonMedidorAguaWeb;
import agrisolus.com.br.agconnect.bean.RetornoWebWs;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.padrao.HttpJSONPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsData;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilberto on 07/03/2017.
 */

public class JSonHttpServicoMedidor extends HttpJSONPadrao {

    public RetornoWebWs salvarMedidorAgua(int empresa, int aviario, String data, String numeroRegistro, long leitura) {
        try {
            JSONObject objModel = new JSONObject();
            objModel.accumulate("IdEstabelecimento", empresa);
            objModel.accumulate("IdAviario", aviario <= 0 ? null : aviario);
            objModel.accumulate("Data", data);
            objModel.accumulate("NumeroMedidor", numeroRegistro);
            objModel.accumulate("VlMedidor", leitura);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("token", obtemToken());
            jsonObject.accumulate("IdBase", Aplicacao.getInstance().getUsuario().getIdBase());
            jsonObject.accumulate("model", objModel);

            try {
                String sbRetorno = httpRequest(
                        Constantes.CONST_MEDIDOR_AGUA_SALVAR,
                        jsonObject
                );

                RetornoWebWs retorno = new Gson().fromJson(sbRetorno, RetornoWebWs.class);
                return retorno;
            } catch (Exception e) {
                LogUtils.e(Constantes.TAG, "Erro: Nao foi possivel salvar a medicao de agua. " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Salva uma lista com as medicoes de agua
     * @param lista Lista contendo as medicoes
     * @return RetornoWeb com os parametros de retorno
     */
    public RetornoWebWs salvarMedidorAgua(List<JSonMedidorAguaWeb> lista) {
        try {
            JSONArray arrLista = new JSONArray();

            for (JSonMedidorAguaWeb medidor : lista) {
                JSONObject objModel = new JSONObject();
                objModel.accumulate("IdEstabelecimento", medidor.getEmpresa());
                objModel.accumulate("IdAviario", medidor.getAviario() <= 0 ? null : medidor.getAviario());
                objModel.accumulate("Data", UtilsData.getDataHora(medidor.getData()));
                objModel.accumulate("NumeroMedidor", medidor.getNumeroMedidor());
                objModel.accumulate("VlMedidor", 0);
                objModel.accumulate("Quantidade", medidor.getLeitura());

                arrLista.put(objModel);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("token", obtemToken());
            jsonObject.accumulate("IdBase", Aplicacao.getInstance().getUsuario().getIdBase());
            jsonObject.accumulate("model", arrLista);

            try {
                String sbRetorno = httpRequest(
                        Constantes.CONST_MEDIDOR_AGUA_LISTA_SALVAR,
                        jsonObject
                );

                RetornoWebWs retorno = new Gson().fromJson(sbRetorno, RetornoWebWs.class);
                return retorno;
            } catch (Exception e) {
                LogUtils.e(Constantes.TAG, "Erro: Nao foi possivel salvar a lista de medicao de agua. " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            LogUtils.e(Constantes.TAG, "JSonHttpServicoMedidor.salvarMedidorAgua() - Erro: " + e.getMessage());
            UtilsRelatorio.enviarRelatorio(e);
            return null;
        }
    }

    public List<String> medidorAguaObterListaMedidores() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("token", obtemToken());
            jsonObject.accumulate("IdBase", Aplicacao.getInstance().getUsuario().getIdBase());

            try {
                String sbRetorno = httpRequest(
                        Constantes.CONST_MEDIDOR_AGUA_LISTA_MEDIDORES,
                        jsonObject
                );

                JSONArray objArr = new JSONObject(sbRetorno).getJSONArray("retorno");
                List<String> lista = new Gson().fromJson(objArr.toString(), List.class);
                return lista;
            } catch (Exception e) {
                LogUtils.e(Constantes.TAG, "Erro: Nao foi possivel obter a lista de medidores de agua. " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
