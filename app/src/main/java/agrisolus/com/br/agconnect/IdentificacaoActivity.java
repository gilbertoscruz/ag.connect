package agrisolus.com.br.agconnect;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Delete;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.async.TaskAutenticar;
import agrisolus.com.br.agconnect.base.ActivityPadrao;
import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua;
import agrisolus.com.br.agconnect.bean.JSonUsuario;
import agrisolus.com.br.agconnect.componente.dialogos.DialogoMensagemErro;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.mop.MOP_Sensor_Values;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsMensagens;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

public class IdentificacaoActivity extends ActivityPadrao {

    private EditText edtNomeAcesso, edtSenhaAcesso;
    private Button btnLoginContinuar, btnLoginSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacao);

        if (isScreenLarge()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        edtNomeAcesso = (EditText) findViewById(R.id.edtNomeAcesso);
        edtSenhaAcesso = (EditText) findViewById(R.id.edtSenhaAcesso);
        btnLoginContinuar = (Button) findViewById(R.id.btnLoginContinuar);
        btnLoginSair = (Button) findViewById(R.id.btnLoginSair);

        requestPermission_WINDOW_ALERT();
        obterPermissoesAplicativo();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Aplicativo sem permissão de acesso ao sistema.", Toast.LENGTH_LONG).show();
                btnLoginContinuar.setEnabled(false);
            }
        }

        try {
            JSonUsuario usuarioBean = Aplicacao.getInstance().getUsuario();

            if (usuarioBean != null) {
                edtNomeAcesso.setText(usuarioBean.getLogin().toLowerCase());
                edtSenhaAcesso.setText(usuarioBean.getToken());
            }
        } catch (Exception e) {
            UtilsRelatorio.enviarRelatorio(e);
        }

        //edtNomeAcesso.setText("prosolus");
        //edtSenhaAcesso.setText("prosolus$12345");

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        initMetodos();
    }

    /**
     * Verificar os campos da tela
     *
     * @return boolean
     */
    private boolean verificarCampos() {

        if (TextUtils.isEmpty(edtNomeAcesso.getText().toString())) {
            edtNomeAcesso.setError(getString(R.string.app_identificacao_msg_erro_email));
            edtNomeAcesso.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtSenhaAcesso.getText().toString())) {
            edtSenhaAcesso.setError(getString(R.string.app_identificacao_msg_erro_senha));
            edtSenhaAcesso.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initMetodos() {
        btnLoginContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //JSonMedidorAgua medidor = MOP_Sensor_Values.getPojoData();

                if (verificarCampos()) {
                    final String strUsuario = edtNomeAcesso.getText().toString();
                    final String strSenha = edtSenhaAcesso.getText().toString();

                    TaskAutenticar taskAutenticar = new TaskAutenticar(
                            IdentificacaoActivity.this,
                            strUsuario,
                            strSenha,
                            new TaskAutenticar.OnAutenticacao() {
                                @Override
                                public void onAutenticacaoSucesso(Intent intent) {
                                    JSonUsuario jSonUsuario;

                                    try {
                                        jSonUsuario = intent.getParcelableExtra(Constantes.KEY_CONTA_USUARIO);

                                        if (Aplicacao.getInstance().getUsuario() != null) {
                                            if (!Aplicacao.getInstance().getUsuario().getLogin().equalsIgnoreCase(jSonUsuario.getLogin())) {
                                                Delete.table(JSonMedidorAgua.class);
                                                Delete.table(JSonParametroMedidorAgua.class);
                                            }
                                        }

                                        Delete.table(JSonUsuario.class);
                                        jSonUsuario.setToken(edtSenhaAcesso.getText().toString());
                                        jSonUsuario.save();
                                    } catch (Exception e) {
                                        UtilsRelatorio.enviarRelatorio(e);

                                        UtilsMensagens.onMensagemErro(IdentificacaoActivity.this, getString(R.string.app_autenticacao_erro_autenticar), new DialogoMensagemErro.OnDialogoMensagem() {
                                            @Override
                                            public void onOKClick() {
                                                edtNomeAcesso.requestFocus();
                                            }
                                        });

                                        edtNomeAcesso.requestFocus();

                                        return;
                                    }

                                    IdentificacaoActivity.this.finish();

                                    final Intent intentAuth = new Intent(IdentificacaoActivity.this, PrincipalActivity.class);
                                    intentAuth.putExtras(intent.getExtras());
                                    startActivity(intentAuth);
                                }

                                @Override
                                public void onAutenticacaoErro(Intent intent) {
                                    LogUtils.e(Constantes.TAG, "Não foi possivel efetuar o login do assinante.");

                                    if (intent != null) {

                                        if (intent.hasExtra(Constantes.KEY_CONTA_ERRO)) {
                                            UtilsMensagens.onMensagemErro(IdentificacaoActivity.this, getString(R.string.app_autenticacao_erro_autenticar), new DialogoMensagemErro.OnDialogoMensagem() {
                                                @Override
                                                public void onOKClick() {
                                                    edtNomeAcesso.requestFocus();
                                                }
                                            });
                                        } else {
                                            UtilsMensagens.onMensagemErro(IdentificacaoActivity.this, getString(R.string.app_autenticacao_erro_autenticar), new DialogoMensagemErro.OnDialogoMensagem() {
                                                @Override
                                                public void onOKClick() {
                                                    edtNomeAcesso.requestFocus();
                                                }
                                            });
                                        }

                                    } else {
                                        UtilsMensagens.onMensagemErro(IdentificacaoActivity.this, getString(R.string.app_autenticacao_erro_autenticar), new DialogoMensagemErro.OnDialogoMensagem() {
                                            @Override
                                            public void onOKClick() {
                                                edtNomeAcesso.requestFocus();
                                            }
                                        });
                                    }

                                    edtNomeAcesso.requestFocus();
                                }
                            }
                    );

                    taskAutenticar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });

        btnLoginSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentificacaoActivity.this.finish();
            }
        });
    }
}
