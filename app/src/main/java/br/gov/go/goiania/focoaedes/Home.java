package br.gov.go.goiania.focoaedes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.gov.go.goiania.focoaedes.auxiliar.ListaFocoAedes;
import br.gov.go.goiania.focoaedes.banco.FocoAedesDB;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;
import br.gov.go.goiania.focoaedes.rede.ConsultaFocoAedes;

public class Home extends AppCompatActivity {

    private static final String TAG = "Home";

    private static ListaFocoAedes adapter;
    private static FocoAedesDB focoAedesDB;
    private static ListView listFcAedesView;
    private static List<FocoAedes> listaFA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, CadastraFoco.class);
                startActivity(i);
            }
        });

        this.focoAedesDB = new FocoAedesDB(this);

        this.listaFA = buscaFocoAedes();

        adapter = new ListaFocoAedes(this,R.layout.lista_foco_aedes, listaFA);

        listFcAedesView = (ListView) findViewById(R.id.list_foco_aedes);
        if(buscaFocoAedes() != null)
            listFcAedesView.setAdapter(adapter);

    }

    @Override
    public void onStart(){

        super.onStart();
        Log.d(TAG, "onStart");

        if(this.listaFA != null){
            this.listaFA.clear();
            this.listaFA.addAll(buscaFocoAedes());
            adapter.notifyDataSetChanged();
        }

    }

    public static void atualisaLista(){

        if(listaFA != null){
            listaFA.clear();
            listaFA.addAll(focoAedesDB.getTodosFocosAedes());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume(){

        super.onResume();

        Log.d(TAG, "onResume");

        if(this.listaFA != null){
            this.listaFA.clear();
            this.listaFA.addAll(buscaFocoAedes());
            adapter.notifyDataSetChanged();
        }

    }

    public List<FocoAedes> buscaFocoAedes(){

        return this.focoAedesDB.getTodosFocosAedes();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_load) {
            new ConsultaFocoAedes(this).execute();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        moveTaskToBack(true);
        finish();

    }

    public void criaSolicitacao(View v){

        Toast.makeText(this, "testegdfgdf!", Toast.LENGTH_LONG).show();

    }
}
