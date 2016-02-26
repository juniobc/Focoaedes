package br.gov.go.goiania.focoaedes.banco;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.gov.go.goiania.focoaedes.entidades.Usuario;

public class UsuarioDB {


    private BancoHelper bh;
    private static final String TABLE_USUARIO = "t0001";

    private static final String KEY_ID = "cd_usr";
    private static final String KEY_NM_USR = "cd_nm_usr";
    private static final String KEY_NR_CPF = "nr_cpf";
    private static final String KEY_DS_EMAIL = "ds_email";
    private static final String KEY_CD_SENHA = "cd_senha";
    private static final String KEY_DT_CAD = "cd_dt_cad";

    private static final String TAG = "UsuarioDB";

    protected static final String CREATE_USUARIO_TABLE = "CREATE TABLE " + TABLE_USUARIO + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NM_USR + " TEXT," + KEY_NR_CPF + " TEXT,"
            + KEY_DS_EMAIL + " TEXT," + KEY_CD_SENHA + " TEXT," + KEY_DT_CAD + " INTEGER )";

    public UsuarioDB(Context contexto){

        bh = new BancoHelper(contexto);

    }

    public void addUsuario(Usuario usr) {
        SQLiteDatabase db = bh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, usr.getCdUsr());
        values.put(KEY_NM_USR, usr.getNmUsr().toUpperCase());
        values.put(KEY_NR_CPF, usr.getNrCpf());
        values.put(KEY_DS_EMAIL, usr.getDsEmail().toUpperCase());
        values.put(KEY_DT_CAD, System.currentTimeMillis());

        db.insert(TABLE_USUARIO, null, values);
        db.close();
    }

    public Usuario getTodosUsuario() {

        Usuario usr;

        String selectQuery = "SELECT "+KEY_ID+", "+KEY_NM_USR+", "+KEY_NR_CPF+", "+KEY_DS_EMAIL+", "+KEY_DT_CAD
                +" FROM " + TABLE_USUARIO;

        SQLiteDatabase db = bh.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                usr = new Usuario(cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NM_USR)),
                        cursor.getString(cursor.getColumnIndex(KEY_DS_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(KEY_NR_CPF)));
            } while (cursor.moveToNext());
        }else{
            usr = null;
        }

        return usr;
    }
}
