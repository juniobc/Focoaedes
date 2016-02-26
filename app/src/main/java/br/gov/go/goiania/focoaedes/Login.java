package br.gov.go.goiania.focoaedes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.gov.go.goiania.focoaedes.banco.BancoHelper;
import br.gov.go.goiania.focoaedes.entidades.Usuario;
import br.gov.go.goiania.focoaedes.auxiliar.GerenciaSessao;
import br.gov.go.goiania.focoaedes.banco.UsuarioDB;
import br.gov.go.goiania.focoaedes.rede.CadastraUsuario;


public class Login extends AppCompatActivity {

    private EditText txtNmUsuario, txtDsEmail, txtNrCpf;
    public static GerenciaSessao sessao;
    private UsuarioDB usrDB;

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BancoHelper banco = new BancoHelper(this);
        SQLiteDatabase database = banco.getWritableDatabase();
        Log.d(TAG, "onCreate - bd"+database.getVersion());

        sessao = new GerenciaSessao(getApplicationContext());
        usrDB = new UsuarioDB(this);

        verificaUsuario();

    }

    @Override
    public void onResume(){

        super.onResume();

        verificaUsuario();

    }



    public void verificaUsuario(){

        if(sessao.isLoggedIn()){

            acessaHome();

        }else{

            Usuario usuario = this.usrDB.getTodosUsuario();

            if(this.usrDB.getTodosUsuario() == null){

                txtNmUsuario = (EditText) findViewById(R.id.nm_usuario);
                txtDsEmail = (EditText) findViewById(R.id.nm_email);
                txtNrCpf = (EditText) findViewById(R.id.nr_cpf);

            }else{

                sessao.criaSessaoLogin(String.valueOf(usuario.getCdUsr()), usuario.getNrCpf(), usuario.getNmUsr(), usuario.getDsEmail());

                acessaHome();

            }

        }

    }

    public void entrar(View v){

        if(consiste()){

            Usuario usr = new Usuario(txtNmUsuario.getText().toString(),
                    txtDsEmail.getText().toString(),
                    txtNrCpf.getText().toString());

            new CadastraUsuario(this, this, usr).execute();

        }

    }

    public boolean consiste(){

        if(txtNmUsuario.getText().toString().equals("")){
            Toast.makeText(this, "Informe o nome completo!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(txtDsEmail.getText().toString().equals("")){
            Toast.makeText(this, "Informe o email!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(txtNrCpf.getText().toString().equals("")){
            Toast.makeText(this, "Informe o CPF!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    public void acessaHome(){

        Intent i = new Intent(this, Home.class);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

}
