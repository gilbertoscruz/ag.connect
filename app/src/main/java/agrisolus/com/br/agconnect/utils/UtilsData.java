package agrisolus.com.br.agconnect.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by gilbe on 31/08/2017.
 */

public class UtilsData {

    /**
     * Converter Data String para Date
     * @param data_atual
     * @return Data convertida de string. Formato: dd/MM/yyyy hh:mm
     */
    public static Date converterStringData(String data_atual) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
            Date data = formatter.parse(data_atual);
            return data;
        } catch (Exception e) {
            return new Date();
        }
    }

    public static Date obtemDataAtual() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * Converter long para data
     *
     * @param date
     * @return String
     */
    public static String converterLongToDate(long date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(date);

        int mm_dia = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int mm_mes = cal.get(GregorianCalendar.MONTH) + 1;
        int mm_ano = cal.get(GregorianCalendar.YEAR);

        return String.format("%02d", mm_dia) + "/"
                + String.format("%02d", mm_mes) + "/"
                + mm_ano;
    }

    public static String convertLongToDate(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH)
                + "/" + cal.get(Calendar.YEAR);
    }

    /**
     * Obter a data corrente do sistema
     *
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDataAtual() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataHora(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataAtualInternacional(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataAtualSemSeparador() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * Obter a hora corrente do sistema
     *
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    public static String getHoraAtual() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * Retorna a data e hora atuais do sistema
     *
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDataHoraAtual() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataHoraAtualMySQLFormat() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataHoraAtualMySQLFormatNormal() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataHoraAtualSemSeparador() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDataHoraAtualSemSegundoSemSeparador() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmm");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static long hoursDiff(String datetime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date oldDate = dateFormat.parse(datetime);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            if (oldDate.before(currentDate)) {
                LogUtils.e("oldDate", "is previous date");
                LogUtils.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days);
            }

            return hours;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long computeDateTimeDiff(final String initialDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dateStop = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime()); //"14/02/2012 20:31:48";

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(initialDate);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays > 0) {
                return diffDays;
            } else {
                return 0;
            /* else if (diffHours > 0) {
                return diffHours;
            }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        //return 0;
    }


    /**
     * Calcular idadde
     * @param dataNasc
     * @return int
     */
    public static int calculaIdade(java.util.Date dataNasc) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(dataNasc);
        Calendar hoje = Calendar.getInstance();

        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
            idade--;
        }
        else
        {
            if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                idade--;
            }
        }

        return idade;
    }

    /**
     *
     * @param datePicker
     * @return
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    /**
     * getTimeFromTimerPicker
     * @param timePicker
     * @returnString
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static String getTimeFromTimerPicker(TimePicker timePicker) {
        return String.format("%02d", timePicker.getHour())
                + ":"
                + String.format("%02d", timePicker.getMinute())
                + ":"
                + "00";

    }

    /**
     * compareDates
     * @param psDate1
     * @param psDate2
     * @return boolean
     * @throws ParseException
     */
    public static boolean compareDates(String psDate1, String psDate2) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
        Date date1 = dateFormat.parse(psDate1);
        Date date2 = dateFormat.parse(psDate2);
        return date2.after(date1);
    }

    /**
     * compareDates
     * @param psDate1
     * @param psDate2
     * @return boolean
     * @throws ParseException
     */
    public static boolean compareDates(Date psDate1, Date psDate2) {
        return psDate2.after(psDate1);
    }
}
