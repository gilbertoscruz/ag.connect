package agrisolus.com.br.agconnect.utils;

import android.content.Context;

import agrisolus.com.br.agconnect.componente.dialogos.DialogoMensagemConfirmacao;
import agrisolus.com.br.agconnect.componente.dialogos.DialogoMensagemErro;
import agrisolus.com.br.agconnect.componente.dialogos.DialogoMensagemInformacao;

/**
 * Created by gilberto on 15/03/2017.
 */

public class UtilsMensagens {

    /**
     * onMensagemErro Apresenta uma mensagem personalizada ao usuario
     *
     * @param mensagem Texto da mensagem a ser apresentada
     * @param response Interface para a resposta
     */
    public static void onMensagemErro(Context context, String mensagem, DialogoMensagemErro.OnDialogoMensagem response) {
        DialogoMensagemErro dme = new DialogoMensagemErro(
                context,
                mensagem,
                response
        );

        dme.show();
    }

    /**
     * onMensagemConfirmacao Apresenta uma mensagem personalizada ao usuario
     *
     * @param mensagem Texto da mensagem a ser apresentada
     * @param response Interface para a resposta
     */
    public static void onMensagemConfirmacao(Context context, String mensagem, DialogoMensagemConfirmacao.OnDialogoMensagem response) {
        DialogoMensagemConfirmacao dme = new DialogoMensagemConfirmacao(
                context,
                mensagem,
                response
        );

        dme.show();
    }

    /**
     * onMensagemInformacao Apresenta uma mensagem personalizada ao usuario
     *
     * @param mensagem Texto da mensagem a ser apresentada
     * @param response Interface para a resposta
     */
    public static void onMensagemInformacao(Context context, String mensagem, DialogoMensagemInformacao.OnDialogoMensagem response) {
        DialogoMensagemInformacao dme = new DialogoMensagemInformacao(
                context,
                mensagem,
                response
        );

        dme.show();
    }


}
