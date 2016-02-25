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
    private static final String KEY_NR_LAT = "nr_lat";
    private static final String KEY_NR_LONG = "nr_long";
    private static final String KEY_IMG_LOCAL = "img_local";
    private static final String KEY_DT_CAD = "dt_cad";

    protected static final String CREATE_FOCOAEDES_TABLE = "CREATE TABLE " + TABLE_FOCO_AEDES + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DS_FOCO_AEDES + " TEXT," + KEY_NR_LAT + " TEXT,"
            + KEY_NR_LONG + " TEXT," + KEY_IMG_LOCAL + " BLOB, "+KEY_DT_CAD+" INTEGER)";

    public FocoAedesDB(Context contexto){

        bh = new BancoHelper(contexto);

    }

    /*public void addFocoAedes(FocoAedes fcAedes) {
        SQLiteDatabase db = bh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DS_FOCO_AEDES, fcAedes.getDsFocoAedes().toUpperCase());
        values.put(KEY_NR_LAT, fcAedes.getNrLat());
        values.put(KEY_NR_LONG, fcAedes.getNrLong());
        values.put(KEY_IMG_LOCAL, fcAedes.getImgLocal());
        values.put(KEY_DT_CAD, System.currentTimeMillis());

        db.insert(TABLE_FOCO_AEDES, null, values);
        db.close();
    }

    public List<FocoAedes> getTodosFocosAedes() {

        List<FocoAedes> fcAedesArray = new ArrayList<FocoAedes>();
        FocoAedes fcAedes;

        String selectQuery = "SELECT "+KEY_ID+", "+KEY_DS_FOCO_AEDES+", "+KEY_NR_LAT+", "+KEY_NR_LONG+", "+KEY_DT_CAD+", "+KEY_IMG_LOCAL
                +" FROM " + TABLE_FOCO_AEDES;

        SQLiteDatabase db = bh.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                fcAedes = new FocoAedes(cursor.getString(cursor.getColumnIndex(KEY_DS_FOCO_AEDES)),
                        cursor.getString(cursor.getColumnIndex(KEY_NR_LAT)),
                        cursor.getString(cursor.getColumnIndex(KEY_NR_LONG)),
                        cursor.getBlob(cursor.getColumnIndex(KEY_IMG_LOCAL)));

                fcAedesArray.add(fcAedes);
            } while (cursor.moveToNext());
        }else{
            fcAedesArray = null;
        }

        return fcAedesArray;
    }*/

}
