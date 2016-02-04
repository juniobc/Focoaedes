package br.gov.go.goiania.focoaedes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import java.text.DateFormat;
import java.util.Date;

import br.gov.go.goiania.focoaedes.auxiliar.ConexaoGoogle;
import br.gov.go.goiania.focoaedes.auxiliar.DbBitmapUtility;
import br.gov.go.goiania.focoaedes.banco.FocoAedesDB;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

public class CadastraFoco extends AppCompatActivity implements LocationListener {

    private static final String TAG = "CadastraFoco";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private ImageView imgFoto;
    private ConexaoGoogle conectaLocalizacao;
    private EditText nrLat, nrLong, dsFocoAedes;
    private FocoAedesDB focoAedesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_foco);

        conectaLocalizacao = new ConexaoGoogle(this, this);
        conectaLocalizacao.conecta();

        nrLat = (EditText) findViewById(R.id.txt_nr_lat);
        nrLong = (EditText) findViewById(R.id.txt_nr_long);
        dsFocoAedes = (EditText) findViewById(R.id.ds_foco_aedes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        imgFoto = (ImageView) findViewById(R.id.img_foto);

        this.focoAedesDB = new FocoAedesDB(this);

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG, "onLocationChanged getLatitude" + String.valueOf(location.getLatitude()));
        Log.d(TAG, "onLocationChanged getLongitude" + location.getLongitude());

        nrLat.setText(String.valueOf(location.getLatitude()));
        nrLong.setText(String.valueOf(location.getLongitude()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro_foco, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }else if(id == R.id.menu_salvar){

            if(cadastraFoco())
                finish();
        }

        return true;//super.onOptionsItemSelected(item);
    }

    public boolean cadastraFoco(){

        if(consiste()){

            FocoAedes fcAedes;

            Bitmap bm = ((BitmapDrawable)imgFoto.getDrawable()).getBitmap();

            fcAedes = new FocoAedes(dsFocoAedes.getText().toString(),
                    nrLat.getText().toString(),
                    nrLong.getText().toString(),
                    DbBitmapUtility.getBytes(bm));

            focoAedesDB.addFocoAedes(fcAedes);

            return true;
        }

        return false;

    }

    public boolean consiste(){

        if(dsFocoAedes.getText().toString().equals("")){
            Toast.makeText(this, "Informe a descrição do ambiente!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    public void capturaImagem(View v){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imgFoto.setImageBitmap(photo);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                Toast.makeText(this, "Ocorreu um erro tente novamente!" +
                        data.getData(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
