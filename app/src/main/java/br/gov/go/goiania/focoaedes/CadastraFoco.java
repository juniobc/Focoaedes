package br.gov.go.goiania.focoaedes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import br.gov.go.goiania.focoaedes.localizacao.BuscaEndereco;
import br.gov.go.goiania.focoaedes.rede.EnviaFocoAedes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Handler;

import br.gov.go.goiania.focoaedes.auxiliar.ConexaoGoogle;
import br.gov.go.goiania.focoaedes.auxiliar.DbBitmapUtility;
import br.gov.go.goiania.focoaedes.banco.FocoAedesDB;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

public class CadastraFoco extends AppCompatActivity implements LocationListener {

    private static final String TAG = "CadastraFoco";

    private ImageView imgFoto;
    private ConexaoGoogle conectaLocalizacao;
    private EditText nrLat, nrLong, dsFocoAedes;
    private FocoAedesDB focoAedesDB;
    private Location mLastLocation;
    //AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_foco);

        /*conectaLocalizacao = new ConexaoGoogle(this, this);
        conectaLocalizacao.conecta();*/

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Inicio"));
        tabLayout.addTab(tabLayout.newTab().setText("Perguntas"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new FocoAedesPager (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        /*nrLat = (EditText) findViewById(R.id.txt_nr_lat);
        nrLong = (EditText) findViewById(R.id.txt_nr_long);
        dsFocoAedes = (EditText) findViewById(R.id.ds_foco_aedes);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        imgFoto = (ImageView) findViewById(R.id.img_foto);

        this.focoAedesDB = new FocoAedesDB(this);

        //mResultReceiver = new AddressResultReceiver(null);


    }

    /*protected void startIntentService() {
        Intent intent = new Intent(this, BuscaEndereco.class);
        intent.putExtra(BuscaEndereco.RECEIVER, mResultReceiver);
        intent.putExtra(BuscaEndereco.LOCATION_DATA_EXTRA, this.mLastLocation);
        startService(intent);
    }

    @SuppressLint("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {

            final Address address = resultData.getParcelable(BuscaEndereco.LOCATION_DATA_EXTRA);
            final String message = resultData.getString(BuscaEndereco.RESULT_DATA_KEY);

            if (resultCode == BuscaEndereco.SUCCESS_RESULT) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //progressBar.setVisibility(View.GONE);
                        //infoText.setVisibility(View.VISIBLE);
                        Log.d(TAG, " onReceiveResult Sucess - " +
                                "CEP: " + address.getAddressLine(1));
                    }
                });
            }else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //progressBar.setVisibility(View.GONE);
                        //infoText.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onReceiveResult Error"+message);
                    }
                });
            }
        }
    }*/

    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG, "onLocationChanged getLatitude" + String.valueOf(location.getLatitude()));
        Log.d(TAG, "onLocationChanged getLongitude" + location.getLongitude());

        /*nrLat.setText(String.valueOf(location.getLatitude()));
        nrLong.setText(String.valueOf(location.getLongitude()));*/

        mLastLocation = location;

        //startIntentService();

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
            List<FocoAedes> listfcAedes = new ArrayList<FocoAedes>();

            Bitmap bm = ((BitmapDrawable)imgFoto.getDrawable()).getBitmap();

            fcAedes = new FocoAedes(dsFocoAedes.getText().toString(),
                    nrLat.getText().toString(),
                    nrLong.getText().toString(),
                    DbBitmapUtility.getBytes(bm));

            focoAedesDB.addFocoAedes(fcAedes);

            /*ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                listfcAedes.add(fcAedes);
                new EnviaFocoAedes(this, listfcAedes).execute("http://intradesv.goiania.go.gov.br/sistemas/sismp/asp/sismp22222s1.asp");
            } else {
                Toast.makeText(this, "Não foi possivel enviar a denuncia, favor verifique sua conexão!", Toast.LENGTH_LONG).show();
            }*/

            return true;
        }

        return false;

    }

    public boolean consiste(){

        if(dsFocoAedes.getText().toString().equals("")){
            Toast.makeText(this, "Informe a descrição do ambiente!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(nrLat.toString().equals("")){
            Toast.makeText(this, "Aguarde o local!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(nrLong.toString().equals("")){
            Toast.makeText(this, "Aguarde o local!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

}
