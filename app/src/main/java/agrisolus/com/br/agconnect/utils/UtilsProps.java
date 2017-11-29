package agrisolus.com.br.agconnect.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gilbe on 10/08/2017.
 */

public class UtilsProps {

    private static final String PROP_NAME = "agrisolus.com.br.agconnect.AGAVES_PROPRIEDADES_GERAIS";

    public static void salvarPropriedade(Context context, String propriedade, Object valor) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (valor instanceof Integer) {
            editor.putInt(propriedade, (int) valor);
        } else if (valor instanceof  Long) {
            editor.putLong(propriedade, (long) valor);
        } else if (valor instanceof String) {
            editor.putString(propriedade, (String) valor);
        } else if (valor instanceof Float || valor instanceof Double) {
            editor.putFloat(propriedade, (float) valor);
        }

        editor.apply();
        editor.commit();
    }

    public static <T> Object obterPropriedade(Context context, String propriedade) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPref.getAll().get(propriedade);
    }

    public static boolean propExiste(Context context, String propriedade) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPref.contains(propriedade);
    }

    /*public static <T extends String> String obterPropriedade(Context context, String propriedade) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPref.getString(propriedade, "");
    }

    public static <T extends Integer> int obterPropriedade(Context context, String propriedade) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPref.getInt(propriedade, 0);
    }

    public static <T extends Long> long obterPropriedade(Context context, String propriedade) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPref.getLong(propriedade, 0);
    }

    public static <T extends Float> float obterPropriedade(Context context, String propriedade) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPref.getFloat(propriedade, 0);
    }*/

    /*public static String obterPropriedade(Context context, String propriedade) {
        SharedPreferences sharedPref = context.getSharedPreferences(PROP_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPref.getString(propriedade, "");
    }*/

}
