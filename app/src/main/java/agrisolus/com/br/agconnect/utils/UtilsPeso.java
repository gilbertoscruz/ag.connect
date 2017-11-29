package agrisolus.com.br.agconnect.utils;

/**
 * Created by Janaina on 01/12/2016.
 */

public class UtilsPeso {

    /**
     * Calcular o peso real
     * @param peso
     * @param ca
     * @param cf
     * @return double
     */
    public static double calculaPeso(double peso, double ca, double cf) {
        return (peso * ca) + cf;
    }

}
