package agrisolus.com.br.agconnect.async;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import agrisolus.com.br.agconnect.R;
import agrisolus.com.br.agconnect.bean.JSonUsuario;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.http.JSonHttpAutenticar;
import agrisolus.com.br.agconnect.padrao.ASyncPadrao;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by Janaina on 02/12/2016.
 */

public class TaskAutenticar extends ASyncPadrao<Intent> {

    public interface OnAutenticacao {
        void onAutenticacaoSucesso(Intent intent);
        void onAutenticacaoErro(Intent intent);
    }

    private String strUsuario;
    private String strSenha;
    private OnAutenticacao response;

    public TaskAutenticar(Context context, String strUsuario, String strSenha, OnAutenticacao response) {
        super(context, R.string.app_autenticacao_msg_aguarde);

        this.strUsuario = strUsuario;
        this.strSenha = strSenha;
        this.response = response;
    }

    @Override
    protected Intent doInBackground(Void... voids) {
        LogUtils.d(Constantes.TAG, "Iniciando autenticacao...");
        JSonUsuario usuario = null;
        Bundle data = new Bundle();

        try {
            usuario = new JSonHttpAutenticar().autenticar(strUsuario, strSenha);

            if (usuario == null) {
                data.putString(Constantes.KEY_CONTA_ERRO, getContext().getString(R.string.app_autenticacao_erro_autenticar));
            } else {
                data.putString(Constantes.KEY_CONTA_USUARIO_NOME, strUsuario);
                data.putString(Constantes.KEY_CONTA_USUARIO_SENHA, strSenha);
                data.putParcelable(Constantes.KEY_CONTA_USUARIO, usuario);
            }

        } catch (Exception e) {
            UtilsRelatorio.enviarRelatorio(e);
            data.putString(Constantes.KEY_CONTA_ERRO, e.getMessage());
        }

        final Intent res = new Intent();
        res.putExtras(data);
        return res;
    }

    @Override
    protected void onPostExecute(Intent intent) {
        super.onPostExecute(intent);

        if (intent.hasExtra(Constantes.KEY_CONTA_ERRO)) {
            response.onAutenticacaoErro(intent);
        } else {
            response.onAutenticacaoSucesso(intent);
        }
    }

}
