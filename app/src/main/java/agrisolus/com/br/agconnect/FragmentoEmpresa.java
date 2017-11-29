package agrisolus.com.br.agconnect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Janaina on 22/11/2016.
 */

public class FragmentoEmpresa extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frame_empresa, container, false);

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String formattedDate = df.format(c.getTime());

        ((TextView) (rootView.findViewById(R.id.tvEmpresaNome))).setText(
                String.format(getString(R.string.app_powered_by_ano), formattedDate)
        );

        /*UtilsFonte.setFonteObjetos(inflater.getContext(), rootView, Constantes.FONTE_PADRAO_SISTEMA );*/

        return rootView;
    }
}
