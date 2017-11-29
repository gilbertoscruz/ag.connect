package agrisolus.com.br.agconnect.padrao;

import android.net.SSLCertificateSocketFactory;
import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.bean.JSonUsuario;
import agrisolus.com.br.agconnect.utils.UtilsEncryption;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilberto on 07/03/2017.
 */

public class HttpJSONPadrao {

    private final ArrayList<String> translateTable = new ArrayList<>(); //"!\"#$%&' ()*+,-./:;<=>?@[\\]^_`{|}~";

    public HttpJSONPadrao() {

        if (translateTable.size() > 0) {
            translateTable.clear();
        }

        for (int i = 0; i < 16; i++) {
            String Aux = String.format("%%%x", i + 32);
            translateTable.add(Aux);
        }

        for (int i = 0; i < 7; i++) {
            String Aux = String.format("%%%x", i + 58);
            translateTable.add(Aux);
        }

        for (int i = 0; i < 6; i++) {
            String Aux = String.format("%%%x", i + 91);
            translateTable.add(Aux);
        }

        for (int i = 0; i < 4; i++) {
            String Aux = String.format("%%%x", i + 123);
            translateTable.add(Aux);
        }
    }

    /**
     * Criptografar senha do usuario
     *
     * @param usuario
     * @param senha
     * @return String
     */
    protected String criptografar(String usuario, String senha) {
        return UtilsEncryption.encryptBase64(usuario + "=" + senha);
    }

    /**
     * Retorna o usuario e senha do usuario registrado no sistema criptografado
     *
     * @return String
     */
    protected String obtemToken() {
        JSonUsuario usuario = Aplicacao.getInstance().getUsuario();
        return criptografar(usuario.getLogin(), usuario.getToken());
    }

    private HttpParams getHttpParams() {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
        HttpConnectionParams.setSoTimeout(httpParameters, 15000);
        return httpParameters;
    }

    private HttpPost getHttpConnection(final String URL, final StringEntity entity) {
        entity.setContentType("application/json; charset=utf-8");

        HttpPost httpPost = new HttpPost(URL);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        return httpPost;
    }

    /**
     * Requisicao HTTP JSON Padrao
     *
     * @param URL
     * @param object
     * @return String
     */
    protected String httpRequest(String URL, JSONObject object) throws Exception {
        HttpURLConnection httpURLConnection = null;

        try {
            java.net.URL url = new URL(URL);

            httpURLConnection = (HttpURLConnection) url.openConnection();

            if (httpURLConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) httpURLConnection;
                httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
            }

            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST httpRequest, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.setRequestProperty("charset", "utf-8");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(object.toString());
            wr.flush();
            wr.close();

            StringBuilder sbRetorno = new StringBuilder();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    sbRetorno.append(line);
                }
                reader.close();
            }

            httpURLConnection.disconnect();

            return sbRetorno.toString();
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("[httpRequest] Erro na conexao com o servidor.");
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    protected String MEDIDOR_Request(String URL) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().permitNetwork().build();
            StrictMode.setThreadPolicy(policy);

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
            HttpConnectionParams.setSoTimeout(httpParameters, 30000);

            HttpGet httpGet = new HttpGet(URL);
            httpGet.setHeader("USER-AGENT", "Mozilla/5.0");
            httpGet.setHeader("ACCEPT-LANGUAGE", "en-US,en;0.5");

            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            httpClient.getConnectionManager().shutdown();

            if (response.getStatusLine().getStatusCode() != 200) {
                return "";
            } else {
                return responseString;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            UtilsRelatorio.enviarRelatorio(new Exception("Request invalido - Retorno: " + ex.getMessage()));
            return null;
        }
    }

    /* Função usada para padronizar caracteres especiais no formato html
       Exemplo:
            IN = "123 456"
            SIZE = 7
            OUT = 123%20456  --> onde %20 corresponde ao caracter "espaço"
     */
    public String setTranslate(String IN, int size) {
        String OUT = "";

        for (int i = 0; i < size; i++) {
            char c = IN.charAt(i);

            if ((c >= 32 && c < 48)) {
                OUT += translateTable.get(c - 32);
            } else if (c >= 58 && c < 65) {
                OUT += translateTable.get(c - 58 + 16);
            } else if (c >= 91 && c < 97) {
                OUT += translateTable.get(c - 91 + 16 + 7);
            } else if (c >= 123 && c < 0x128) {
                OUT += translateTable.get(c - 123 + 16 + 6 + 7);
            } else
                OUT += String.valueOf(c);
        }
        return OUT;
    }
}
