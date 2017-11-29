package agrisolus.com.br.agconnect.componente.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import agrisolus.com.br.agconnect.R;

/**
 * Created by gilberto on 22/03/2017.
 */

public class DialogoProgresso extends Dialog {

    private TextView tx_texto_progresso;
    private ProgressBar pb_progresso;

    public DialogoProgresso(@NonNull Context context, CharSequence mensagem) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogo_janela_progresso);

        try {
            getWindow().setBackgroundDrawableResource(R.color.corFundoDialogoMensagem);
        } catch (Exception e) {
        }

        try {
            getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        } catch (Exception e) {
        }

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        //UtilsFonte.setFonteObjetos(getContext(), getWindow().getDecorView(), Constantes.FONTE_PADRAO_SISTEMA);

        tx_texto_progresso = (TextView) findViewById(R.id.tv_janela_texto);
        if (tx_texto_progresso != null) {
            tx_texto_progresso.setText(mensagem);
        }

        pb_progresso = (ProgressBar) findViewById(R.id.pb_progresso);
        if (pb_progresso != null) {
            pb_progresso.setIndeterminate(true);
        }
    }

}
