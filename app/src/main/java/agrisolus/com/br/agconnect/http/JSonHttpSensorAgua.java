package agrisolus.com.br.agconnect.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONObject;

import java.io.StringReader;

import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua_Table;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.padrao.HttpJSONPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilbe on 22/11/2017.
 */

public class JSonHttpSensorAgua extends HttpJSONPadrao {

    /**
     * Efetuar uma conexao com o medidor
     * @param ip ip do sensor
     * @param ssid ssid da rede
     * @param senha senha da rede
     * @return boolean
     */
    public boolean conectarMedidor(String ip, String ssid, String senha) {
        try {
            String url = "http://" + ip + "/?cmd=apcfg&ssid="
                    + setTranslate(ssid, ssid.length())
                    + "&password="
                    + setTranslate(senha, senha.length());

            String retorno = MEDIDOR_Request(url);
            return !retorno.isEmpty();
        } catch (Exception e) {
            LogUtils.d(Constantes.TAG, "JSonHttpSensorAgua.conectarMedidor() - Erro: " + e.getMessage());
            UtilsRelatorio.enviarRelatorio(e);
            return false;
        }
    }

    /**
     * Reset da conexao com o servidor
     * @param ip ip do sensor
     * @return boolean
     */
    public boolean resetMedidor(String ip) {
        try {
            String url = "http://" + ip + "/?cmd=rst";

            String retorno = MEDIDOR_Request(url);
            return !retorno.isEmpty();
        } catch (Exception e) {
            LogUtils.d(Constantes.TAG, "JSonHttpSensorAgua.resetMedidor() - Erro: " + e.getMessage());
            UtilsRelatorio.enviarRelatorio(e);
            return false;
        }
    }

    /**
     * Retorna as informações do sensor
     * @return JSonMedidorAgua medidor
     */
    public JSonMedidorAgua obterInformacaoMedidorAgua() {
        try {
            JSonParametroMedidorAgua param = SQLite.select().from(JSonParametroMedidorAgua.class)
                    .where(JSonParametroMedidorAgua_Table.id.eq(1L))
                    .limit(1).querySingle();


            if (param == null){
                    LogUtils.d(Constantes.TAG, "Task_Sensor_Medidor_Agua_Receber.obterInformacaoMedidorAgua() - Configuracao invalida");
                    UtilsRelatorio.enviarRelatorio(new Exception("Task_Sensor_Medidor_Agua_Receber->param is null"));
                    return null;
            }

            String url = "http://" + param.getEndereco() + "/";
            String retorno = MEDIDOR_Request(url);

            if (retorno == null) {
                UtilsRelatorio.enviarRelatorio(new Exception("Request invalido 1 - Retorno: " + retorno));
                return null;
            }

            if (retorno.isEmpty()) {
                UtilsRelatorio.enviarRelatorio(new Exception("Request invalido 2 - Retorno: " + retorno));
                return null;
            }

            if (retorno.trim().equals("")) {
                UtilsRelatorio.enviarRelatorio(new Exception("Request invalido 3 - Retorno: " + retorno));
                return null;
            }

            if (retorno.trim().contains("{")) {
                UtilsRelatorio.enviarRelatorio(new Exception("Request invalido 4 - Retorno: " + retorno));
                return null;
            }

            UtilsRelatorio.enviarRelatorio(new Exception("Request - Retorno: " + retorno));

            retorno = retorno.replaceAll("\n", "").replaceAll("\r", "");

            String sIPConectado = "{" + '"' + "endereco" + '"' + ":" + '"' + retorno.substring(retorno.indexOf("}")+1, retorno.length() -1) + '"' + "}";
            sIPConectado = sIPConectado.replaceAll("\n", "").replaceAll("\r", "");

            JSONObject objIp = new JSONObject(sIPConectado);

            if (objIp != null) {
                if (objIp.has("endereco")){
                    param.setId(1);
                    param.setEndereco(objIp.getString("endereco"));
                    param.save();
                }
            }

            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(retorno));
            reader.setLenient(true);
            return gson.fromJson(reader, JSonMedidorAgua.class);
        } catch (Exception e) {
            LogUtils.d(Constantes.TAG, "JSonHttpSensorAgua.obterInformacaoMedidorAgua() - Erro: " + e.getMessage());
            UtilsRelatorio.enviarRelatorio(e);
            return null;
        }
    }

}
