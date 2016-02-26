package br.gov.go.goiania.focoaedes.auxiliar;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import br.gov.go.goiania.focoaedes.Login;

public class GerenciaSessao {

    SharedPreferences pref;
    Editor editor;
    Context contexto;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "UsuarioFocoAedes";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_CODIGO = "codigo";
    public static final String KEY_NOME = "nome";
    public static final String KEY_CPF = "cpf";
    public static final String KEY_EMAIL = "email";

    public GerenciaSessao(Context contexto){
        this.contexto = contexto;
        pref = contexto.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void criaSessaoLogin(String codigo, String Cpf , String nome, String email){

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_CODIGO, codigo);
        editor.putString(KEY_CPF, Cpf);
        editor.putString(KEY_NOME, nome);
        editor.putString(KEY_EMAIL, email);
        editor.commit();

    }

    public void checaLogin(){

        if(!this.isLoggedIn()){
            Intent i = new Intent(contexto, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(i);
        }

    }

    public HashMap<String, String> getDadosUsr(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_CODIGO, pref.getString(KEY_CODIGO, null));
        user.put(KEY_CPF, pref.getString(KEY_CPF, null));
        user.put(KEY_NOME, pref.getString(KEY_NOME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;
    }

    public void logoutUsr(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(contexto, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contexto.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}
