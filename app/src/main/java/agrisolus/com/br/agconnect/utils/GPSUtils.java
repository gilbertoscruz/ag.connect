/**
 * 
 */
package agrisolus.com.br.agconnect.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * @author egilcru
 *
 */
public class GPSUtils {

	/**
	 * Ativar o GPS 
	 * @param contexto
	 */
	public static void ligarGPS(final Context contexto) {
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		contexto.sendBroadcast(intent);
		
		String provider = Settings.Secure.getString(
				contexto.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		
		if (!provider.contains("gps")) {
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			contexto.sendBroadcast(poke);
		}
	}

	/**
	 * Desativar o GPS 
	 * @param contexto
	 */
	public static void desligarGPS(final Context contexto) {
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", false);
		contexto.sendBroadcast(intent);
		
		String provider = Settings.Secure.getString(
				contexto.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		if (provider.contains("gps")) {
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			contexto.sendBroadcast(poke);
		}
	}

	/**
	 * Verfifica se o GPS esta ativo
	 * @param context
	 * @return boolean
     */
	public static boolean gpsAtivo(final Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * Verifica se o aplicativo possui permissao de acesso a interface GPS
	 * @param TAG
	 * @param context
     * @return boolean
     */
	public static boolean gpsPermissaoAcesso(String TAG, Context context) {
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			Log.e(TAG, "Aplicativo sem permissao para acessar as opcoes de GPS (Rede)...");
			return false;
		} else {
			return true;
		}
	}
}
