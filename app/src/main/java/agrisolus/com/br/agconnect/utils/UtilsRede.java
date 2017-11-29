package agrisolus.com.br.agconnect.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by gilbe on 19/09/2017.
 */

public class UtilsRede {

    /**
     * Obtem o IP atual da rede
     *
     * @return String
     */
    public static String getEnderecoIPLocal() {
        String ip = "";

        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();

                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (networkInterface.getName().contains("wlan") || networkInterface.getName().contains("eth")) {
                        if (inetAddress.isSiteLocalAddress()) {
                            ip = inetAddress.getHostAddress();
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            ip = "0.0.0.0";
            e.printStackTrace();
            UtilsRelatorio.enviarRelatorio(e);
        }

        if (ip.trim().equals("")) {
            ip = "0.0.0.0";
        }

        return ip;
    }

    /**
     * Verificar se a rede esta disponivel
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();

                if (info != null) {
                    for (NetworkInfo in : info) {
                        if (in.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            UtilsRelatorio.enviarRelatorio(e);
        }

        return false;
    }

    /**
     * Validar se a rede (internet) esta disponvel
     *
     * @param context
     * @return boolean
     */
    public static boolean redeDisponivel(Context context) {
        boolean ret = true;

        if (!isNetworkAvailable(context)) {
            ret = false;
        }

        return ret;
    }

    public static String getMacAddress(Context context) {
        try {
            WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String macAddress = wimanager.getConnectionInfo().getMacAddress();
            if (macAddress == null) {
                macAddress = "00:00:00:00:00:00";
            }

            return macAddress;
        } catch (Exception e) {
            UtilsRelatorio.enviarRelatorio(e);
            return "00:00:00:00:00:00";
        }
    }
}
