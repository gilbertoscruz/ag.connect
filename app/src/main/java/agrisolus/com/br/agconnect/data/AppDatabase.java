package agrisolus.com.br.agconnect.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Janaina on 09/12/2016.
 */

@Database(
        name = AppDatabase.NAME,
        version = AppDatabase.VERSION
)
public class AppDatabase {

    public static final String NAME = "AppDBAgriSolusConnect"; // we will add the .db extension
    public static final int VERSION = 2;

}
