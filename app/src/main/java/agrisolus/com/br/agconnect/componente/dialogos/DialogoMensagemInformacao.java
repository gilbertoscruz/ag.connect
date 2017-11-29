package agrisolus.com.br.agconnect.componente.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import agrisolus.com.br.agconnect.R;

/**
 * Created by Janaina on 24/01/2017.
 */

public class DialogoMensagemInformacao extends Dialog {

    /**
     * OnDialogoMensagem
     */
    public interface OnDialogoMensagem {
        void onOKClick();
    }

    private OnDialogoMensagem response;

    /**
     * DialogoSelecionarBalanca
     *
     * @param context
     */
    public DialogoMensagemInformacao(Context context, String mensagem, final OnDialogoMensagem response) {
        super(context);//, R.style.DialogoFullScreen);
        this.response = response;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogo_janela_informacao);
        getWindow().setBackgroundDrawableResource(R.color.corFundoDialogoMensagem);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        /*UtilsFonte.setFonteObjetos(getContext(), getWindow().getDecorView(), Constantes.FONTE_PADRAO_SISTEMA);
        UtilsFonte.setFonteObjeto(this.getContext(), findViewById(R.id.tv_janela_texto), Constantes.FONTE_PADRAO_SISTEMA);
        UtilsFonte.setFonteObjeto(this.getContext(), findViewById(R.id.bt_sim), Constantes.FONTE_PADRAO_SISTEMA_NEGRITO);*/

        ((TextView) findViewById(R.id.tv_janela_texto)).setText(mensagem);

        findViewById(R.id.bt_sim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response != null) {
                    response.onOKClick();
                }

                dismiss();
            }
        });
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

}
