package agrisolus.com.br.agconnect.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import agrisolus.com.br.agconnect.R;
import agrisolus.com.br.agconnect.bean.racao.JSonParametroMedidorRacao;
import agrisolus.com.br.agconnect.utils.UtilsMensagens;

/**
 * Created by gilbe on 22/11/2017.
 */

public class Dialogo_Configurar_Medidor_Racao extends Dialog {

    public Dialogo_Configurar_Medidor_Racao(@NonNull Context context, @NonNull final IDialogo_Configurar_Medidor_Racao response) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        try {
            getWindow().setBackgroundDrawableResource(R.color.corPretoTransparente);
        } catch (Exception e) {

        }

        setContentView(R.layout.dialogo_configurar_medidor);

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

                JSonParametroMedidorRacao param = new JSonParametroMedidorRacao(
                        sEndereco, sRedeNome, sRedeSenha, true
                );

                if (response != null) {
                    response.OnConfigurarMedidorRacaoSucesso(param);
                }

                dismiss();
            }
        });
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

    public interface IDialogo_Configurar_Medidor_Racao {
        void OnConfigurarMedidorRacaoSucesso(JSonParametroMedidorRacao parametroMedidorRacao);
    }
}
