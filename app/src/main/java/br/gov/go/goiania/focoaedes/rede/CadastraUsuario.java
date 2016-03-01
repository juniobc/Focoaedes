package br.gov.go.goiania.focoaedes.rede;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.gov.go.goiania.focoaedes.Login;
import br.gov.go.goiania.focoaedes.banco.UsuarioDB;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;
import br.gov.go.goiania.focoaedes.entidades.Usuario;

/**
 * Created by sebastiao on 25/02/2016.
 */
public class CadastraUsuario extends AsyncTask<Void, Void, String> {

    private static final String TAG = "CadastraUsuario";

    private Login login;

    private ProgressDialog pd;
    private Context contexto;
    private String cdUsuario = null;
    private Usuario usuario;
    private int cdErro = 0;
    private static final String ns = null;

    public CadastraUsuario(Login login, Context contexto, Usuario usuario){

        this.contexto = contexto;
        this.usuario = usuario;
        this.login = login;

    }


    @Override
    protected String doInBackground(Void... params) {
        try {

            cdUsuario = parse(downloadUrl("http://www.goiania.go.gov.br/sistemas/sa156/asp/sa15600004f6.asp?txt_nm_contri="+usuario.getNmUsr().replace(" ","%20")
            +"&txt_nr_cpf_contri="+usuario.getNrCpf()+"&txt_in_email_contri="+usuario.getDsEmail()));

        }catch(IOException e){

            e.printStackTrace();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return cdUsuario;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(contexto);
        pd.setMessage("Entrando no sistema...");
        pd.show();
}

    @Override
    protected void onPostExecute(String result) {

        pd.dismiss();

        Log.d(TAG, "onPostExecute - result: " + result);

        if(cdErro == 0){

            UsuarioDB usrDB;

            usrDB = new UsuarioDB(contexto);

            usuario.setCdUsr(Integer.parseInt(result));

            usrDB.addUsuario(usuario);

            login.sessao.criaSessaoLogin(String.valueOf(usuario.getCdUsr()), usuario.getNrCpf(), usuario.getNmUsr(), usuario.getDsEmail());

            login.acessaHome();

        }else{

            result = result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();

            new AlertDialog.Builder(contexto)
                    .setMessage(result)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();

        }

        /*for (FocoAedes temp : result) {
            //Toast.makeText(contexto, temp.getCdFocoAedes(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPostExecute - temp.getCdFocoAedes(): "+temp.getCdFocoAedes());
        }*/

    }

    private InputStream downloadUrl(String urlString) throws IOException {

        Log.d(TAG, "downloadUrl - urlString: "+urlString);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();

        return conn.getInputStream();
    }

    public String parse(InputStream in) throws XmlPullParserException, IOException {

        try {

            String retorno;

            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            retorno = verificaErro(parser);
            if (retorno != null)
                return retorno;
            else
                return leXml(parser);

        } finally {
            in.close();
        }
    }

    public String verificaErro(XmlPullParser parser) throws IOException{
        try{
            Log.d(TAG, "verificaErro");
            String retorno = "Erro ao cadastrar usuário! Tente novamente mais tarde.";

            parser.require(XmlPullParser.START_TAG, ns, "msg");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("ds")) {
                    retorno = le(parser,"ds");
                } else {
                    skip(parser);
                }
            }

            cdErro = 1;

            return retorno;

        }catch(XmlPullParserException e){

            Log.d(TAG, "verificaErro - XmlPullParserException: "+e.toString());

            e.printStackTrace();

            return null;

        }
    }

    public String leXml(XmlPullParser parser) throws XmlPullParserException, IOException{

        String cdUsuario = null;

        parser.require(XmlPullParser.START_TAG, ns, "dt");

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("cd_contri")) {
                cdUsuario = le(parser, "cd_contri");
            } else {
                skip(parser);
            }
        }

        return cdUsuario;
    }

    private String le(XmlPullParser parser, String campo) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, campo);
        String campoR = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, campo);
        return campoR;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
