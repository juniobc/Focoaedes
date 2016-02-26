package br.gov.go.goiania.focoaedes.banco;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String TAG = "BancoHelper";

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "focoaedes";

    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UsuarioDB.CREATE_USUARIO_TABLE);
        db.execSQL(FocoAedesDB.CREATE_FOCOAEDES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,"onUpgrade - oldVersion: "+oldVersion);
        //if (oldVersion < 3) {
            db.execSQL(FocoAedesDB.DROP_FOCO_AEDES);
            db.execSQL(FocoAedesDB.CREATE_FOCOAEDES_TABLE);
        //}

    }
}
