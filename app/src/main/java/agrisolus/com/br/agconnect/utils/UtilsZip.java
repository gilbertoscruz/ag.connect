package agrisolus.com.br.agconnect.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import agrisolus.com.br.agconnect.consts.Constantes;

/**
 * Created by gilbe on 19/09/2017.
 */

public class UtilsZip {

    private static final int ZIP_BUFFER = 80000;

    /**
     * Zip arquivo
     * @param arquivos
     * @param nomeZip
     * @return String
     * @throws Exception
     */
    public static boolean zipArquivos(String[] arquivos, String nomeZip) throws Exception {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(nomeZip);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[ZIP_BUFFER];

            for (int i = 0; i < arquivos.length; i++) {
                Log.d(Constantes.TAG, arquivos[i]);
                Log.d(Constantes.TAG, "Adicionando arquivo no ZIP: " + arquivos[i]);

                FileInputStream fi = new FileInputStream(arquivos[i]);
                origin = new BufferedInputStream(fi, ZIP_BUFFER);
                ZipEntry entry = new ZipEntry(arquivos[i].substring(arquivos[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);

                int count;
                while ((count = origin.read(data, 0, ZIP_BUFFER)) != -1) {
                    out.write(data, 0, count);
                }

                origin.close();
            }

            out.close();
        } catch (Exception E) {
            UtilsRelatorio.enviarRelatorio(E);
            return false;
        }

        return true;
    }

    /**
     * Zip arquivo
     * @param arquivos
     * @param nomeZip
     * @return String
     * @throws Exception
     */
    public static boolean zipArquivo(String arquivos, String nomeZip) throws Exception {
        BufferedInputStream origin = null;

        try {
            FileOutputStream dest = new FileOutputStream(nomeZip);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[ZIP_BUFFER];

            Log.d(Constantes.TAG, arquivos);
            Log.d(Constantes.TAG, "Adicionando arquivo no ZIP: " + arquivos);

            FileInputStream fi = new FileInputStream(arquivos);
            origin = new BufferedInputStream(fi, ZIP_BUFFER);

            ZipEntry entry = new ZipEntry(arquivos.substring(arquivos.lastIndexOf("/") + 1));
            out.putNextEntry(entry);

            int count;
            while ((count = origin.read(data, 0, ZIP_BUFFER)) != -1) {
                out.write(data, 0, count);
            }

            fi.close();
            origin.close();
            out.close();
        } catch (Exception E) {
            UtilsRelatorio.enviarRelatorio(E);
            return false;
        } finally {
            if (origin != null) {
                origin.close();
            }
        }

        return true;
    }
}
