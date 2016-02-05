package br.gov.go.goiania.focoaedes.banco;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.gov.go.goiania.focoaedes.entidades.Usuario;

public class UsuarioDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "focoaedes";
    private static final String TABLE_USUARIO = "usuario";

    private static final String KEY_ID = "cd_usr";
    private static final String KEY_NM_USR = "cd_nm_usr";
    private static final String KEY_NR_TEL = "nr_tel";
    private static final String KEY_DS_EMAIL = "ds_email";
    private static final String KEY_CD_SENHA = "cd_senha";
    private static final String KEY_DT_CAD = "cd_dt_cad";

    private static final String TABLE_FOCO_AEDES = "foco_aedes";

    private static final String KEY_ID2 = "cd_foco_aedes";
    private static final String KEY_DS_FOCO_AEDES = "ds_foco_aedes";
    private static final String KEY_NR_LAT = "nr_lat";
    private static final String KEY_NR_LONG = "nr_long";
    private static final String KEY_IMG_LOCAL = "img_local";
    private static final String KEY_DT_CAD2 = "dt_cad";

    private static final String TAG = "UsuarioDB";

    public UsuarioDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USUARIO_TABLE = "CREATE TABLE " + TABLE_USUARIO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NM_USR + " TEXT," + KEY_NR_TEL + " INTEGER,"
                + KEY_DS_EMAIL + " TEXT," + KEY_CD_SENHA + " TEXT," + KEY_DT_CAD + " INTEGER )";

        db.execSQL(CREATE_USUARIO_TABLE);

        String CREATE_FOCOAEDES_TABLE = "CREATE TABLE " + TABLE_FOCO_AEDES + "("
                + KEY_ID2 + " INTEGER PRIMARY KEY," + KEY_DS_FOCO_AEDES + " TEXT," + KEY_NR_LAT + " TEXT,"
                + KEY_NR_LONG + " TEXT," + KEY_IMG_LOCAL + " BLOB, "+KEY_DT_CAD2+" INTEGER)";

        Log.d(TAG, "onCreate CREATE_FOCOAEDES_TABLE" + CREATE_FOCOAEDES_TABLE);

        db.execSQL(CREATE_FOCOAEDES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUsuario(Usuario usr) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NM_USR, usr.getNmUsr().toUpperCase());
        values.put(KEY_NR_TEL, usr.getNrTel());
        values.put(KEY_DS_EMAIL, usr.getDsEmail().toUpperCase());
        values.put(KEY_DT_CAD, System.currentTimeMillis());

        db.insert(TABLE_USUARIO, null, values);
        db.close();
    }

    public Usuario getTodosUsuario() {

        Usuario usr;

        String selectQuery = "SELECT "+KEY_ID+", "+KEY_NM_USR+", "+KEY_NR_TEL+", "+KEY_DS_EMAIL+", "+KEY_DT_CAD
                +" FROM " + TABLE_USUARIO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                usr = new Usuario(cursor.getString(cursor.getColumnIndex(KEY_NM_USR)),
                        cursor.getString(cursor.getColumnIndex(KEY_DS_EMAIL)),
                        Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_NR_TEL))));
            } while (cursor.moveToNext());
        }else{
            usr = null;
        }

        return usr;
    }
}
