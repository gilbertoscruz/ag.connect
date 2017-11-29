package agrisolus.com.br.agconnect.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.bean.JSonMedidorAguaWeb;
import agrisolus.com.br.agconnect.bean.JSonParametros;
import agrisolus.com.br.agconnect.bean.RetornoWebWs;
import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua_Table;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua;
import agrisolus.com.br.agconnect.bean.agua.JSonParametroMedidorAgua_Table;
import agrisolus.com.br.agconnect.consts.Constantes;
import agrisolus.com.br.agconnect.http.JSonHttpServicoMedidor;
import agrisolus.com.br.agconnect.mop.MOP_Sensor_Values;
import agrisolus.com.br.agconnect.utils.LogUtils;
import agrisolus.com.br.agconnect.utils.UtilsData;
import agrisolus.com.br.agconnect.utils.UtilsRede;
import agrisolus.com.br.agconnect.utils.UtilsRelatorio;

/**
 * Created by gilbe on 24/11/2017.
 */

public class Controller_Envio_Sensor_Agua extends AsyncTask<Void, Void, Controller_Envio_Sensor_Agua.Controller_Envio_Sensor_Retorno> {

    public interface IControllerEnvioSensorAgua {
        void OnControllerEnvioSensorAgua(Controller_Envio_Sensor_Retorno controller_envio_sensor_retorno);
    }

    private Context context;
    private IControllerEnvioSensorAgua response;

    public Controller_Envio_Sensor_Agua(@NonNull Context context, @NonNull IControllerEnvioSensorAgua response) {
        /******************************************************************************************/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        /******************************************************************************************/

        /******************************************************************************************/
        this.context = context;
        this.response = response;
        /******************************************************************************************/
    }

    @Override
    protected Controller_Envio_Sensor_Retorno doInBackground(Void... voids) {

        try {
            JSonParametroMedidorAgua parametroMedidorAgua = SQLite.select()
                    .from(JSonParametroMedidorAgua.class)
                    .where(JSonParametroMedidorAgua_Table.id.eq(1L))
                    .limit(1).querySingle();

            /******************************************************************************************/

            /*JSonParametros parametros = Aplicacao.getInstance().getParametros();
            if (parametros == null) {
                return new Controller_Envio_Sensor_Retorno(0, true, new Exception("Parametros de envio inválidos."));
            }

            if (parametros.getServidorEndereco().equals("")) {
                return new Controller_Envio_Sensor_Retorno(0, true, new Exception("Parametros de envio inválidos. Endereco nao identificado."));
            }

            if (parametros.getServidorUsuario().equals("")) {
                return new Controller_Envio_Sensor_Retorno(0, true, new Exception("Parametros de envio inválidos. Usuario nao identificado."));
            }

            if (parametros.getServidorSenha().equals("")) {
                return new Controller_Envio_Sensor_Retorno(0, true, new Exception("Parametros de envio inválidos. Senha nao identificada."));
            }*/

            /******************************************************************************************/
            /******************************************************************************************/
            /******************************************************************************************/

            LogUtils.d(Constantes.TAG, "Controller_Envio_Sensor_Agua() Selecionando registros para serem enviados");
            long lRecords = SQLite.update(JSonMedidorAgua.class).set(JSonMedidorAgua_Table.status.eq((long) 1))
                    .where().executeUpdateDelete();

            LogUtils.i(Constantes.TAG, "Controller_Envio_Sensor_Agua() Registro selecionados: " + lRecords);

            List<JSonMedidorAgua> listaMedidorAgua = SQLite.select().from(JSonMedidorAgua.class)
                    .where(JSonMedidorAgua_Table.status.eq((long) 1)).queryList();

            if (listaMedidorAgua.isEmpty()) {
                return new Controller_Envio_Sensor_Retorno(0, false, null);
            }

            /******************************************************************************************/
            /******************************************************************************************/

            Date dtData = new Date();
            List<JSonMedidorAguaWeb> listaObjetosEnviar = new ArrayList<>();

            for (JSonMedidorAgua medidorAgua : listaMedidorAgua) {
                long litros = 0;

                switch (parametroMedidorAgua.getPolegadasCanoMedidor()) {
                    case 0: {
                        litros = medidorAgua.getCount() / Constantes.SENSOR_AGUA_PULSOS_MEIA_POLEGADA;
                        break;
                    }

                    case 1: {
                        litros = medidorAgua.getCount() / Constantes.SENSOR_AGUA_PULSOS_UMA_POLEGADA;
                        break;
                    }

                    /*case 2: {
                        litros = medidorAgua.getCount() / Constantes.SENSOR_AGUA_PULSOS_UMA_MEIA_POLEGADA;
                        break;
                    }*/
                }

                JSonMedidorAguaWeb jmaw = new JSonMedidorAguaWeb();
                jmaw.setEmpresa(0);
                jmaw.setAviario(0);
                jmaw.setData(dtData);
                jmaw.setNumeroMedidor(UtilsRede.getMacAddress(context));
                jmaw.setLeitura(litros);

                listaObjetosEnviar.add(jmaw);
            }

            /******************************************************************************************/
            /******************************************************************************************/

            if (listaObjetosEnviar.isEmpty()) {
                return new Controller_Envio_Sensor_Retorno(0, false, null);
            }

            try {

                RetornoWebWs retornoWebWs = new JSonHttpServicoMedidor().salvarMedidorAgua(listaObjetosEnviar);

                if (retornoWebWs == null) {
                    return new Controller_Envio_Sensor_Retorno(0, true, null);
                } else {
                    if (retornoWebWs.getCode() != 2) {
                        return new Controller_Envio_Sensor_Retorno(0, true, null);
                    } else {
                        return new Controller_Envio_Sensor_Retorno(lRecords, false, null);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                UtilsRelatorio.enviarRelatorio(e);
                LogUtils.e(Constantes.TAG, "Controller_Envio_Sensor_Agua() Erro ao enviar as informacoes. Erro: " + e.getMessage());
                return null;
            }

            /******************************************************************************************/
            /******************************************************************************************/
        } catch (Exception e) {
            e.printStackTrace();
            UtilsRelatorio.enviarRelatorio(e);
            LogUtils.e(Constantes.TAG, "Controller_Envio_Sensor_Agua() Erro ao enviar as informacoes. Erro: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Controller_Envio_Sensor_Retorno retorno) {
        super.onPostExecute(retorno);

        if (retorno == null || retorno.erro) {
            long lRecords = SQLite.update(JSonMedidorAgua.class).set(JSonMedidorAgua_Table.status.eq((long) 0))
                    .where(JSonMedidorAgua_Table.status.eq((long) 1))
                    .executeUpdateDelete();

            LogUtils.e(Constantes.TAG, "Controller_Envio_Sensor_Agua() Atualizando " + lRecords + " nao enviados.");
        } else if (!retorno.erro) {
            long lRecords = SQLite.delete().from(JSonMedidorAgua.class)
                    .where(JSonMedidorAgua_Table.status.eq((long) 1))
                    .executeUpdateDelete();

            LogUtils.e(Constantes.TAG, "Controller_Envio_Sensor_Agua() Removendo " + lRecords + " enviados.");
        }

        if (response != null) {
            response.OnControllerEnvioSensorAgua(retorno);
        }
    }

    public class Controller_Envio_Sensor_Retorno {
        public long registros;
        public boolean erro;
        public Exception exception;

        public Controller_Envio_Sensor_Retorno(long registros, boolean erro, Exception exception) {
            this.registros = registros;
            this.erro = erro;
            this.exception = exception;
        }
    }

}
