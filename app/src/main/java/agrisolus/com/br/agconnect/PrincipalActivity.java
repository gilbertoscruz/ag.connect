package agrisolus.com.br.agconnect;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Date;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.async.Task_Sensor_Medidor_Agua_Conectar;
import agrisolus.com.br.agconnect.base.ActivityPadrao;
import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua_Table;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua_Table;
import agrisolus.com.br.agconnect.bean.racao.JSonMedidorRacao;
import agrisolus.com.br.agconnect.bean.racao.JSonParametroMedidorRacao;
import agrisolus.com.br.agconnect.componente.dialogos.DialogoMensagemConfirmacao;
import agrisolus.com.br.agconnect.dialogo.Dialogo_Configurar_Medidor_Agua;
import agrisolus.com.br.agconnect.dialogo.Dialogo_Configurar_Medidor_Racao;
import agrisolus.com.br.agconnect.timers.Timer_Enviar_Informacao;
import agrisolus.com.br.agconnect.timers.Timer_Receber_Medidor_Agua;
import agrisolus.com.br.agconnect.timers.Timer_Receber_Medidor_Racao;
import agrisolus.com.br.agconnect.utils.UtilsData;
import agrisolus.com.br.agconnect.utils.UtilsMensagens;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;


public class PrincipalActivity extends ActivityPadrao {

    private Timer_Receber_Medidor_Agua timer_receber_medidor_agua;
    private Timer_Receber_Medidor_Racao timer_receber_medidor_racao;
    private Timer_Enviar_Informacao timer_enviar_informacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isScreenLarge()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (findViewById(R.id.textAppCopyright) != null) {
            String sAppCopyright = getString(R.string.app_powered_by);
            SpannableString spannedCla = new SpannableString(sAppCopyright);
            spannedCla.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), sAppCopyright.length() - 9, sAppCopyright.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannedCla.setSpan(new ForegroundColorSpan(ContextCompat.getColor(PrincipalActivity.this, R.color.corAmarelo)), sAppCopyright.length() - 9, sAppCopyright.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) findViewById(R.id.textAppCopyright)).setText(spannedCla);
        }

        ((TextView) findViewById(R.id.textAppVersion)).setText(
                String.format(getString(R.string.app_versao), Aplicacao.getInstance().obterVersao())
        );

        initMetodos();
        iniciarConfMedidorAgua();
        iniciarConfMedidorRacao();
    }

    @Override
    public void initMetodos() {
        timer_enviar_informacao = new Timer_Enviar_Informacao(this);
        timer_enviar_informacao.start();

        timer_receber_medidor_agua = new Timer_Receber_Medidor_Agua(this, new Timer_Receber_Medidor_Agua.IMedidorAgua() {
            @Override
            public void OnMedidorAguaResponse(JSonMedidorAgua resposta) {
                if (resposta != null) {
                    resposta.setDate(new Date());
                    resposta.save();
                } else {
                    UtilsRelatorio.enviarRelatorio(new Exception("Timer_Receber_Medidor_Agua->OnMedidorAguaResponse() " + resposta));
                }

                iniciarConfMedidorAgua();
            }
        });
        timer_receber_medidor_agua.start();

        timer_receber_medidor_racao = new Timer_Receber_Medidor_Racao(this, new Timer_Receber_Medidor_Racao.IMedidorRacao() {
            @Override
            public void OnMedidorRacaoResponse(JSonMedidorRacao resposta) {
                if (resposta != null) {
                    resposta.setDate(new Date());
                    resposta.save();

                    iniciarConfMedidorRacao();
                }
            }
        });
        timer_receber_medidor_racao.start();

        findViewById(R.id.bt_configurar_medidor_racao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogo_Configurar_Medidor_Racao configurarMedidorRacao = new Dialogo_Configurar_Medidor_Racao(PrincipalActivity.this, new Dialogo_Configurar_Medidor_Racao.IDialogo_Configurar_Medidor_Racao() {
                    @Override
                    public void OnConfigurarMedidorRacaoSucesso(JSonParametroMedidorRacao parametroMedidorRacao) {

                        if (parametroMedidorRacao != null) {
                            Delete.table(JSonParametroMedidorRacao.class);
                            parametroMedidorRacao.save();
                        }

                        UtilsMensagens.onMensagemInformacao(PrincipalActivity.this, getString(R.string.app_medidor_racao_msg_configurado_sucesso), null);
                        iniciarConfMedidorRacao();
                    }
                });

                configurarMedidorRacao.show();
            }
        });

        findViewById(R.id.bt_configurar_medidor_agua).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogo_Configurar_Medidor_Agua configurarMedidorAgua = new Dialogo_Configurar_Medidor_Agua(PrincipalActivity.this, new Dialogo_Configurar_Medidor_Agua.IDialogo_Configurar_Medidor_Agua() {
                    @Override
                    public void OnConfigurarMedidorAguaSucesso(JSonParametroMedidorAgua parametroMedidorAgua) {

                        if (parametroMedidorAgua != null) {
                            parametroMedidorAgua.setId(1);
                            parametroMedidorAgua.save();
                        }

                        UtilsMensagens.onMensagemInformacao(PrincipalActivity.this, getString(R.string.app_medidor_agua_msg_configurado_sucesso), null);
                        iniciarConfMedidorAgua();
                    }
                });

                configurarMedidorAgua.show();
            }
        });

        if (findViewById(R.id.bt_conectar_medidor_agua) != null) {
            findViewById(R.id.bt_conectar_medidor_agua).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    conectarSensorAgua();
                }
            });
        }
    }

    private void iniciarConfMedidorAgua() {
        JSonParametroMedidorAgua jsonParamAgua = SQLite.select()
                .from(JSonParametroMedidorAgua.class)
                .where(JSonParametroMedidorAgua_Table.id.eq(1L))
                .limit(1)
                .querySingle();

        if (jsonParamAgua == null) {
            ((TextView) findViewById(R.id.tv_medidor_agua_endereco_ip)).setText(
                    String.format(getString(R.string.app_medidor_endereco_medidor), getString(R.string.app_endereco_nao_configurado))
            );

            ((TextView) findViewById(R.id.tv_medidor_agua_rede)).setText(
                    String.format(getString(R.string.app_medidor_rede_medidor), getString(R.string.app_rede_nao_configurada))
            );

            ((TextView) findViewById(R.id.tv_medidor_agua_status)).setText(
                    String.format(getString(R.string.app_medidor_status_medidor), getString(R.string.app_medidor_status_desconectado))
            );

            ((TextView) findViewById(R.id.tv_medidor_agua_ultimo_recebimento)).setText(
                    String.format(getString(R.string.app_medidor_ultimo_recebimento), getString(R.string.app_medidor_ultimo_recebimento_invalido))
            );
        } else {
            ((TextView) findViewById(R.id.tv_medidor_agua_endereco_ip)).setText(
                    String.format(getString(R.string.app_medidor_endereco_medidor), jsonParamAgua.getEndereco())
            );

            ((TextView) findViewById(R.id.tv_medidor_agua_rede)).setText(
                    String.format(getString(R.string.app_medidor_rede_medidor), jsonParamAgua.getSSID())
            );

            ((TextView) findViewById(R.id.tv_medidor_agua_status)).setText(
                    String.format(getString(R.string.app_medidor_status_medidor), getString(R.string.app_medidor_status_conectado))
            );

            JSonMedidorAgua jsonMedidorAgua = SQLite.select().from(JSonMedidorAgua.class)
                    .orderBy(JSonMedidorAgua_Table.id.desc())
                    .querySingle();

            if (jsonMedidorAgua != null) {
                ((TextView) findViewById(R.id.tv_medidor_agua_ultimo_recebimento)).setText(
                        String.format(getString(R.string.app_medidor_ultimo_recebimento), UtilsData.getDataHora(jsonMedidorAgua.getDate()))
                );
            } else {
                ((TextView) findViewById(R.id.tv_medidor_agua_ultimo_recebimento)).setText(
                        String.format(getString(R.string.app_medidor_ultimo_recebimento), getString(R.string.app_medidor_ultimo_recebimento_invalido))
                );
            }
        }
    }

    private void iniciarConfMedidorRacao() {
        JSonParametroMedidorRacao jsonParamRacao = SQLite.select().from(JSonParametroMedidorRacao.class).limit(1).querySingle();

        if (jsonParamRacao == null) {
            ((TextView) findViewById(R.id.tv_medidor_racao_endereco_ip)).setText(
                    String.format(getString(R.string.app_medidor_endereco_medidor), getString(R.string.app_endereco_nao_configurado))
            );

            ((TextView) findViewById(R.id.tv_medidor_racao_rede)).setText(
                    String.format(getString(R.string.app_medidor_rede_medidor), getString(R.string.app_rede_nao_configurada))
            );

            ((TextView) findViewById(R.id.tv_medidor_racao_status)).setText(
                    String.format(getString(R.string.app_medidor_status_medidor), getString(R.string.app_medidor_status_desconectado))
            );

            ((TextView) findViewById(R.id.tv_medidor_racao_ultimo_recebimento)).setText(
                    String.format(getString(R.string.app_medidor_ultimo_recebimento), getString(R.string.app_medidor_ultimo_recebimento_invalido))
            );
        } else {
            ((TextView) findViewById(R.id.tv_medidor_racao_endereco_ip)).setText(
                    String.format(getString(R.string.app_medidor_endereco_medidor), jsonParamRacao.getEndereco())
            );

            ((TextView) findViewById(R.id.tv_medidor_racao_rede)).setText(
                    String.format(getString(R.string.app_medidor_rede_medidor), jsonParamRacao.getSSID())
            );

            ((TextView) findViewById(R.id.tv_medidor_racao_status)).setText(
                    String.format(getString(R.string.app_medidor_status_medidor), getString(R.string.app_medidor_status_desconectado))
            );

            JSonMedidorAgua jsonMedidorAgua = SQLite.select().from(JSonMedidorAgua.class).orderBy(JSonMedidorAgua_Table.id.desc()).querySingle();

            if (jsonMedidorAgua != null) {
                ((TextView) findViewById(R.id.tv_medidor_racao_ultimo_recebimento)).setText(
                        String.format(getString(R.string.app_medidor_ultimo_recebimento), UtilsData.getDataHora(jsonMedidorAgua.getDate()))
                );
            } else {
                ((TextView) findViewById(R.id.tv_medidor_racao_ultimo_recebimento)).setText(
                        String.format(getString(R.string.app_medidor_ultimo_recebimento), getString(R.string.app_medidor_ultimo_recebimento_invalido))
                );
            }
        }
    }

    private void conectarSensorAgua() {

        Task_Sensor_Medidor_Agua_Conectar task = new Task_Sensor_Medidor_Agua_Conectar(this, R.string.app_medidor_agua_msg_conectando_sensor, new Task_Sensor_Medidor_Agua_Conectar.ISensorMedidorAguaConectar() {
            @Override
            public void OnSensorMedidorAguaConectar(Boolean conectado) {
                if (conectado) {
                    ((TextView) findViewById(R.id.tv_medidor_agua_status)).setText(
                            String.format(getString(R.string.app_medidor_status_medidor), getString(R.string.app_medidor_status_conectado))
                    );
                } else {
                    ((TextView) findViewById(R.id.tv_medidor_agua_status)).setText(
                            String.format(getString(R.string.app_medidor_status_medidor), getString(R.string.app_medidor_status_desconectado))
                    );
                }

                timer_receber_medidor_agua.cancel();
                timer_receber_medidor_agua.start();
            }
        });

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer_enviar_informacao != null) {
            timer_enviar_informacao.cancel();
            timer_enviar_informacao = null;
        }

        if (timer_receber_medidor_agua != null) {
            timer_receber_medidor_agua.cancel();
            timer_receber_medidor_agua = null;
        }

        if (timer_receber_medidor_racao != null) {
            timer_receber_medidor_racao.cancel();
            timer_receber_medidor_racao = null;
        }
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        UtilsMensagens.onMensagemConfirmacao(this, getString(R.string.app_mensagem_sair_aplicativo), new DialogoMensagemConfirmacao.OnDialogoMensagem() {
            @Override
            public void onSimClick() {
                PrincipalActivity.this.finish();
                //android.os.Process.killProcess(android.os.Process.myPid());
            }

            @Override
            public void onNaoClick() {
            }
        });
    }

}
