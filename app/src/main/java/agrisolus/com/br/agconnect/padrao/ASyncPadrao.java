package agrisolus.com.br.agconnect.padrao;

import android.content.Context;
import android.os.AsyncTask;

import agrisolus.com.br.agconnect.componente.dialogos.DialogoProgresso;


/**
 * Created by gilbe on 30/08/2017.
 */

public abstract class ASyncPadrao<T> extends AsyncTask<Void, Void, T> {

    private Context context;
    private int mensagemAguarde;
    private DialogoProgresso progressDialog;

    public ASyncPadrao(Context context, int mensagemAguarde) {
        this.context = context;
        this.mensagemAguarde = mensagemAguarde;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (context != null) {
            if (mensagemAguarde > 0) {
                progressDialog = new DialogoProgresso(context, context.getString(mensagemAguarde));
                progressDialog.setCancelable(false);

                try {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(T params) {
        if (context != null) {
            try {
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected Context getContext() {
        return this.context;
    }
}
