package agrisolus.com.br.agconnect.utils;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import agrisolus.com.br.agconnect.app.Aplicacao;

/**
 * Created by gilberto on 17/02/2017.
 */

public class UtilsHttp {

    /**
     * Verifica se possui acesso a internet
     *
     * @return boolean
     */
    public static final boolean isAcessaInternet() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (UtilsRede.redeDisponivel(Aplicacao.getInstance())) {
            return isOnlineAcessaInternet();
        } else {
            return false;
        }
    }

    private static boolean isOnlineAcessaInternet() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static Long getTotalBytesManual(int localUid) {

        File dir = new File("/proc/uid_stat/");
        String[] children = dir.list();
        if (!Arrays.asList(children).contains(String.valueOf(localUid))) {
            return 0L;
        }
        File uidFileDir = new File("/proc/uid_stat/" + String.valueOf(localUid));
        File uidActualFileReceived = new File(uidFileDir, "tcp_rcv");
        File uidActualFileSent = new File(uidFileDir, "tcp_snd");

        String textReceived = "0";
        String textSent = "0";

        try {
            BufferedReader brReceived = new BufferedReader(new FileReader(uidActualFileReceived));
            BufferedReader brSent = new BufferedReader(new FileReader(uidActualFileSent));
            String receivedLine;
            String sentLine;

            if ((receivedLine = brReceived.readLine()) != null) {
                textReceived = receivedLine;
            }
            if ((sentLine = brSent.readLine()) != null) {
                textSent = sentLine;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Long.valueOf(textReceived).longValue() + Long.valueOf(textReceived).longValue();
    }

}
