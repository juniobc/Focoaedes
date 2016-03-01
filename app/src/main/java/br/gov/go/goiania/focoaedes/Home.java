package br.gov.go.goiania.focoaedes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.concurrent.ExecutionException;

import br.gov.go.goiania.focoaedes.auxiliar.ListaFocoAedes;
import br.gov.go.goiania.focoaedes.banco.FocoAedesDB;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;
import br.gov.go.goiania.focoaedes.rede.ConsultaFocoAedes;

public class Home extends AppCompatActivity {

    private static final String TAG = "Home";

    private static ListaFocoAedes adapter;
    private static FocoAedesDB focoAedesDB;
    private static ListView listFcAedesView;
    private static List<FocoAedes> listaFA = null;

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
                startActivityForResult(i, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        buscaFocoAedes();

    }

    @Override
    public void onStart(){

        super.onStart();
        Log.d(TAG, "onStart");

        buscaFocoAedes();

    }

    @Override
    public void onResume(){

        super.onResume();

        Log.d(TAG, "onResume");

        /*if(this.listaFA != null){
            this.listaFA.clear();
            this.listaFA.addAll(buscaFocoAedes());
            adapter.notifyDataSetChanged();
        }*/

    }

    public void buscaFocoAedes(){

        try {
            new ConsultaFocoAedes(this).execute().get();
            //return new ConsultaFocoAedes(this).execute().get();
        } catch (InterruptedException e) {
            Toast.makeText(this, "Não foi possivel consultar as solicitações!", Toast.LENGTH_SHORT).show();
            //return null;
        } catch (ExecutionException e) {
            Toast.makeText(this, "Não foi possivel consultar as solicitações!", Toast.LENGTH_SHORT).show();
            //return null;
        }

        //return this.focoAedesDB.getTodosFocosAedes();

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
            buscaFocoAedes();
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
