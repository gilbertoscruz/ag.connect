package agrisolus.com.br.agconnect.componente.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import agrisolus.com.br.agconnect.R;

/**
 * Created by Janaina on 24/01/2017.
 */

public class DialogoMensagemSair extends Dialog {

    /**
     * OnDialogoMensagem
     */
    public interface OnDialogoMensagem {
        void onSimClick();

        void onNaoClick();
    }

    /**
     * DialogoSelecionarBalanca
     *
     * @param context
     */
    public DialogoMensagemSair(Context context, String mensagem, final OnDialogoMensagem response) {
        super(context);

        if (response == null) {
            return;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.corPretoTransparente);
        setContentView(R.layout.dialogo_janela_sair);

        /*UtilsFonte.setFonteObjetos(getContext(), getWindow().getDecorView(), Constantes.FONTE_PADRAO_SISTEMA);
        UtilsFonte.setFonteObjeto(this.getContext(), findViewById(R.id.tv_janela_texto), Constantes.FONTE_PADRAO_SISTEMA);
        UtilsFonte.setFonteObjeto(this.getContext(), findViewById(R.id.bt_sim), Constantes.FONTE_PADRAO_SISTEMA_NEGRITO);*/

        ((TextView) findViewById(R.id.tv_janela_texto)).setText(mensagem);

        findViewById(R.id.bt_sim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.onSimClick();
            }
        });
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }
}
