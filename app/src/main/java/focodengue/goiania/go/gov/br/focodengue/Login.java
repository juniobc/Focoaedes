package focodengue.goiania.go.gov.br.focodengue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import focodengue.goiania.go.gov.br.focodengue.Entidades.Usuario;
import focodengue.goiania.go.gov.br.focodengue.auxiliar.GerenciaSessao;
import focodengue.goiania.go.gov.br.focodengue.banco.UsuarioDB;


public class Login extends AppCompatActivity {

    private EditText txtNmUsuario, txtDsEmail, txtNrTelefone;
    private GerenciaSessao sessao;
    private UsuarioDB usrDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sessao = new GerenciaSessao(getApplicationContext());
        this.usrDB = new UsuarioDB(this);

        if(sessao.isLoggedIn()){

            acessaHome();

        }else{

            if(this.usrDB.getTodosUsuario() == null){

                txtNmUsuario = (EditText) findViewById(R.id.nm_usuario);
                txtDsEmail = (EditText) findViewById(R.id.nm_email);
                txtNrTelefone = (EditText) findViewById(R.id.nr_telefone);

            }else{

                acessaHome();

            }

        }

    }

    public void entrar(View v){

        if(consiste()){

            Usuario usr = new Usuario(txtNmUsuario.getText().toString(),
                    txtDsEmail.getText().toString(),
                    Long.parseLong(txtNrTelefone.getText().toString()));

            this.usrDB.addUsuario(usr);

            acessaHome();

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

        if(txtNrTelefone.getText().toString().equals("")){
            Toast.makeText(this, "Informe o telefone!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    public void acessaHome(){

        Intent i = new Intent(this, Home.class);
        startActivity(i);

    }

}
