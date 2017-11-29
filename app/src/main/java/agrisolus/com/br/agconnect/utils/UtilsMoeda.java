/**
 *
 */
package agrisolus.com.br.agconnect.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author EGILCRU
 */
public class UtilsMoeda {

    /**
     * Formatar Moeda
     * @param number
     * @return String
     */
    public static String formatarMoeda(double number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(number);
    }

    /**
     * Formatar Moeda 2
     * @param number
     * @return String
     */
    public static String formatarMoeda2(double number) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        return formatter.format(number);
    }

    /**
     * Formatar Moeda 3
     * @param number
     * @return String
     */
    public static String formatarMoeda3(double number) {
        DecimalFormat formatter = new DecimalFormat("0");
        return formatter.format(number);
    }

    /**
     * Formatar Moeda 4 - Formato: ###,###.##
     * @param number numero a ser formatado
     * @return String
     */
    public static String formatarMoeda4(double number) {
        DecimalFormat formatter = new DecimalFormat("###,###.##");
        return formatter.format(number);
    }

    /**
     * @param value
     * @param decimalPlace
     * @return
     */
    public static double arredondarMoeda(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();
        return value;
    }


}
