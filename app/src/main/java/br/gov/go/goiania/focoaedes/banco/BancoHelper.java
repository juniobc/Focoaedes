package br.gov.go.goiania.focoaedes.banco;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String TAG = "BancoHelper";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "focoaedes";
    private static final String TABLE_USUARIO = "t0001";
    private static final String TABLE_FOCO_AEDES = "t0002";

    private static final String KEY_DT_CAD = "dt_cad";

    private static final String KEY_CD_USR = "cd_usr";


    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
