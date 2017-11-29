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

public class DialogoMensagemErro extends Dialog {

    /**
     * OnDialogoMensagem
     */
    public interface OnDialogoMensagem {
        void onOKClick();
    }

    /**
     * DialogoSelecionarBalanca
     *
     * @param context
     */
    public DialogoMensagemErro(Context context, String mensagem, final OnDialogoMensagem response) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogo_janela_erro);
        getWindow().setBackgroundDrawableResource(R.color.corFundoDialogoMensagem);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        ((TextView) findViewById(R.id.tv_janela_texto)).setText(mensagem);

        findViewById(R.id.bt_sim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if (response != null) {
                    response.onOKClick();
                }
            }
        });
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }
}
