package agrisolus.com.br.agconnect.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import agrisolus.com.br.agconnect.consts.Constantes;

/**
 * Created by Gilberto on 25/10/2016.
 */

public abstract class ActivityPadrao extends AppCompatActivity {

    private int REQ_PERM_SYSTEM_ALERT_WINDOW = 1003;

    @Override
    protected void onStart() {
        super.onStart();
        //UtilsFonte.setFonteObjetos(this, findViewById(android.R.id.content), Constantes.FONTE_PADRAO_SISTEMA);
    }

    /**
     * Requisita a permissao para a leitura do disco externo
     */
    public void requestPermission_WINDOW_ALERT() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permissao = checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW);

            if (permissao != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                        REQ_PERM_SYSTEM_ALERT_WINDOW
                );
            }
        }
    }

    /**
     * obterPermissoesAplicativo
     *
     * @return boolean
     */
    public boolean obterPermissoesAplicativo() {

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listPermissionNeeded = new ArrayList<>();

            int hasReadPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int hasAccountManagerPermission = checkSelfPermission(Manifest.permission.ACCOUNT_MANAGER);
            int hasGetAccountPermission = checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
            int hasGetReadExternalAccessPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasGetWriteExternalAccessPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasGetMapAccessFinePermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasGetMapAccessCoarsePermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

            if (hasReadPhoneState != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(android.Manifest.permission.READ_PHONE_STATE);
            }

            if (hasAccountManagerPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(android.Manifest.permission.ACCOUNT_MANAGER);
            }

            if (hasGetAccountPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(android.Manifest.permission.GET_ACCOUNTS);
            }

            if (hasGetReadExternalAccessPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (hasGetWriteExternalAccessPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (hasGetMapAccessFinePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (hasGetMapAccessCoarsePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (!listPermissionNeeded.isEmpty()) {
                requestPermissions(listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
                        Constantes.REQUEST_ID_MULTIPLE_PERMISSIONS);

                return true;
            }
        }

        return false;
    }

    public boolean isScreenLarge() {
        final int screenSize = getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;

        return screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE
                || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public boolean verificarPermissaoEscreverSeguranca(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int hasWriteSecurePermission = checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS);
        } else {

        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_PERM_SYSTEM_ALERT_WINDOW) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, REQ_PERM_SYSTEM_ALERT_WINDOW);
                }
            }
        }
    }

    public abstract void initMetodos();
}
