package br.gov.go.goiania.focoaedes.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;


public class FocoAedesDB {

    private BancoHelper bh;

    private static final String TAG = "FocoAedesDB";

    private static final String TABLE_FOCO_AEDES = "t0002";


    private static final String KEY_ID = "cd_foco_aedes";
    private static final String KEY_DS_FOCO_AEDES = "ds_foco_aedes";
    private static final String KEY_STATUS = "status_foco_aedes";
    private static final String KEY_IMG_URL = "img_url";
    private static final String KEY_IMG_LOCAL = "img_local";
    private static final String KEY_END = "end_foco_aedes";
    private static final String KEY_DT_CAD = "dt_cad";

    protected static final String CREATE_FOCOAEDES_TABLE = "CREATE TABLE " + TABLE_FOCO_AEDES + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DS_FOCO_AEDES+ " TEXT," + KEY_STATUS +" TEXT," + KEY_IMG_URL + " TEXT,"
            +KEY_END +" TEXT,"+ KEY_IMG_LOCAL + " BLOB,"+KEY_DT_CAD+" INTEGER)";

    protected static final String DROP_FOCO_AEDES = "DROP TABLE IF EXISTS "+TABLE_FOCO_AEDES;

    public FocoAedesDB(Context contexto){

        bh = new BancoHelper(contexto);

    }

    public void addFocoAedes(FocoAedes fcAedes) {
        SQLiteDatabase db = bh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, fcAedes.getCdFocoAedes());
        if(fcAedes.getDsFocoAedes() != null)
            values.put(KEY_DS_FOCO_AEDES, fcAedes.getDsFocoAedes().toUpperCase());
        else
            values.putNull(KEY_DS_FOCO_AEDES);
        if(fcAedes.getStatus() != null)
            values.put(KEY_STATUS, fcAedes.getStatus().toUpperCase());
        else
            values.putNull(KEY_STATUS);
        if(fcAedes.getUrlImg() != null)
            values.put(KEY_IMG_URL, fcAedes.getUrlImg().toUpperCase());
        else
            values.putNull(KEY_IMG_URL);
        if(fcAedes.getEndereco() != null)
            values.put(KEY_END, fcAedes.getEndereco().toUpperCase());
        else
            values.putNull(KEY_END);
        values.put(KEY_IMG_LOCAL, fcAedes.getImgLocal());
        values.put(KEY_DT_CAD, System.currentTimeMillis());

        db.insert(TABLE_FOCO_AEDES, null, values);
        db.close();
    }

    public List<FocoAedes> getTodosFocosAedes() {

        List<FocoAedes> fcAedesArray = new ArrayList<FocoAedes>();
        FocoAedes fcAedes;

        String selectQuery = "SELECT "+KEY_ID+", "+KEY_DS_FOCO_AEDES+", "+KEY_STATUS+", "+KEY_IMG_URL+", "+KEY_END+", "+KEY_IMG_LOCAL
                +" FROM " + TABLE_FOCO_AEDES;

        SQLiteDatabase db = bh.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                fcAedes = new FocoAedes(cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_DS_FOCO_AEDES)),
                        cursor.getString(cursor.getColumnIndex(KEY_STATUS)),
                        cursor.getBlob(cursor.getColumnIndex(KEY_IMG_LOCAL)));

                fcAedesArray.add(fcAedes);
            } while (cursor.moveToNext());
        }else{
            fcAedesArray = null;
        }

        return fcAedesArray;
    }

}
