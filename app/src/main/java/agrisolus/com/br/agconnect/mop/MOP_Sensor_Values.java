package agrisolus.com.br.agconnect.mop;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import agrisolus.com.br.agconnect.app.Aplicacao;
import agrisolus.com.br.agconnect.bean.agua.JSonMedidorAgua;
import agrisolus.com.br.agconnect.consts.Constantes;

/**
 * Created by gilbe on 24/11/2017.
 */

public class MOP_Sensor_Values {

    public static List<JSonMedidorAgua> criarListaMedidorAgua() {
        List<JSonMedidorAgua> lista = new ArrayList<>();

        for (int i=0; i<= 20; i++) {
            lista.add(
                    new JSonMedidorAgua(
                            0,
                            new Random().nextInt(10000),
                            0,
                            0,
                            0,
                            new Date(),
                            0
                    )
            );
        }

        return lista;
    }

    public static JSonMedidorAgua getPojoData() {

        JSonMedidorAgua data = null;
        JSONObject object = null, jsonIp = null;

        try {
            String sJSon = readFromFile().replaceAll("\n", "").replaceAll("\r", "");

            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(sJSon));
            reader.setLenient(true);
            data = gson.fromJson(reader, JSonMedidorAgua.class);

            if (!sJSon.isEmpty()) {
                object = new JSONObject(sJSon);
            }

            String sIP = "{" + '"' + "endereco" + '"' + ":" + '"' + sJSon.substring(sJSon.indexOf("}")+1, sJSon.length() -1) + '"' + "}";
            sIP = sIP.replaceAll("\n", "").replaceAll("\r", "");

            jsonIp = new JSONObject(sIP);



            Log.d(Constantes.TAG, "IP >>> " + jsonIp.toString());

            return data;
        } catch (Exception e) {
            return null;
        }
    }

    private static String readFromFile() {
        try {
            InputStream is = Aplicacao.getInstance().getAssets().open("json/reques.json");
            int size = is.available();
            byte buffer[] = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return "" ;
        }
    }
}
