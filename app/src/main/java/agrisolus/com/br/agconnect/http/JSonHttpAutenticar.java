package agrisolus.com.br.agconnect.http;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;

import org.json.JSONObject;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.bean.JSonUsuario;
import agrisolus.com.br.agconnect.bean.JSonUsuarioRetorno;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.padrao.HttpJSONPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;

/**
 * Created by gilberto on 07/03/2017.
 */

public class JSonHttpAutenticar extends HttpJSONPadrao {

    /**
     * Autenticar usuario
     *
     * @param usuario Nome do usuario no sistema
     * @param senha   Senha do usuario no sistema
     * @return JSonUsuario
     */
    public JSonUsuario autenticar(String usuario, String senha) {
        try {
            PackageManager manager = Aplicacao.getInstance().getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(Aplicacao.getInstance().getApplicationContext().getPackageName(), 0);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("token", criptografar(usuario, senha));
            jsonObject.accumulate("versao", info.versionName);
            jsonObject.accumulate("aplicacao", Constantes.TAG);

            String sbRetorno = httpRequest(
                    Constantes.CONST_URL_AUTENTICACAO,
                    jsonObject
            );

            try {
                JSonUsuarioRetorno jSonUsuarioRetorno = new Gson().fromJson(sbRetorno, JSonUsuarioRetorno.class);
                return jSonUsuarioRetorno.getRetorno();
            } catch (Exception e) {
                LogUtils.e(Constantes.TAG, "Erro: Falha na autenticacao do usuario. " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
