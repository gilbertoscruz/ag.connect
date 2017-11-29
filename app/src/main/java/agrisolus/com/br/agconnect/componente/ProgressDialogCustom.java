package agrisolus.com.br.agconnect.componente;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;

/**
 * Created by gilberto on 09/03/2017.
 */

public class ProgressDialogCustom extends CountDownTimer {

    private Context context;
    private ProgressDialog progressDialog;
    private int progress = 1;
    private OnProgressDialogCustom response;

    public ProgressDialogCustom(long millisInFuture, long countDownInterval, Context context, int mensagem, OnProgressDialogCustom response) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.response = response;

        progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(10);
        progressDialog.setProgress(progress);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(context.getString(mensagem)); //R.string.app_aviario_balanca_informacao_msg_aguarde));
        progressDialog.show();

        /*UtilsFonte.setFonteObjetos(context, progressDialog.findViewById(android.R.id.content), Constantes.FONTE_PADRAO_SISTEMA);*/
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (progressDialog != null) {
            progressDialog.setProgress(progress);
            progress++;
        }
    }

    @Override
    public void onFinish() {
        this.cancel();

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        response.OnTerminar();
    }

    public interface OnProgressDialogCustom {
        void OnTerminar();
    }
}
