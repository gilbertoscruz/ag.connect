package agrisolus.com.br.agconnect.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import agrisolus.com.br.agconnect.R;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua_Table;
import agrisolus.com.br.agconnect.utils.UtilsMensagens;

/**
 * Created by gilbe on 22/11/2017.
 */

public class Dialogo_Configurar_Medidor_Agua extends Dialog {

    public interface IDialogo_Configurar_Medidor_Agua {
        void OnConfigurarMedidorAguaSucesso(JSonParametroMedidorAgua parametroMedidorAgua);
    }

    public Dialogo_Configurar_Medidor_Agua(@NonNull Context context, @NonNull final IDialogo_Configurar_Medidor_Agua response) {
        super(context);

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawableResource(R.color.corPretoTransparente);

            setContentView(R.layout.dialogo_configurar_medidor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        preencherCampos();

        findViewById(R.id.bt_confirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarDados()) {
                    return;
                }

                String sEndereco = ((TextView) findViewById(R.id.et_endereco_ip)).getText().toString().trim();
                String sRedeNome = ((TextView) findViewById(R.id.et_rede)).getText().toString().trim();
                String sRedeSenha = ((TextView) findViewById(R.id.et_rede_senha)).getText().toString().trim();

                int tipoMedidor = 0;

                if (((RadioButton) findViewById(R.id.rb_sensor_meia_polegada)).isChecked()) {
                    tipoMedidor = 0;
                } else if (((RadioButton) findViewById(R.id.rb_sensor_uma_polegada)).isChecked()) {
                    tipoMedidor = 1;
                }/* else if (((RadioButton) findViewById(R.id.rb_sensor_umameia_polegada)).isChecked()) {
                    tipoMedidor = 2;
                }*/

                JSonParametroMedidorAgua param = new JSonParametroMedidorAgua(
                        1, sEndereco, sRedeNome, sRedeSenha, true, tipoMedidor
                );

                if (response != null) {
                    response.OnConfigurarMedidorAguaSucesso(param);
                }

                dismiss();
            }
        });
    }

    private void preencherCampos() {
        JSonParametroMedidorAgua param = SQLite.select().from(JSonParametroMedidorAgua.class)
                .where(JSonParametroMedidorAgua_Table.id.eq(1L))
                .limit(1).querySingle();

        if (param != null) {
            ((TextView) findViewById(R.id.et_endereco_ip)).setText(param.getEndereco().trim());
            ((TextView) findViewById(R.id.et_rede)).setText(param.getSSID().trim());
            ((TextView) findViewById(R.id.et_rede_senha)).setText(param.getSenhaRede().trim());
        }
    }

    private boolean validarDados() {
        String sEndereco = ((TextView) findViewById(R.id.et_endereco_ip)).getText().toString().trim();
        String sRedeNome = ((TextView) findViewById(R.id.et_rede)).getText().toString().trim();
        String sRedeSenha = ((TextView) findViewById(R.id.et_rede_senha)).getText().toString().trim();

        if (sEndereco.isEmpty()) {
            UtilsMensagens.onMensagemErro(getContext(), getContext().getString(R.string.app_msg_erro_endereco_ip), null);
            findViewById(R.id.et_endereco_ip).requestFocus();
            return false;
        }

        if (sRedeNome.isEmpty()) {
            UtilsMensagens.onMensagemErro(getContext(), getContext().getString(R.string.app_msg_erro_rede_nome), null);
            findViewById(R.id.et_rede).requestFocus();
            return false;
        }

        if (sRedeSenha.isEmpty()) {
            UtilsMensagens.onMensagemErro(getContext(), getContext().getString(R.string.app_msg_erro_rede_senha), null);
            findViewById(R.id.et_rede_senha).requestFocus();
            return false;
        }

        return true;
    }
}
