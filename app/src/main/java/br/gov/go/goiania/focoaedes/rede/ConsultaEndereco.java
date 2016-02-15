package br.gov.go.goiania.focoaedes.rede;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.Endereco;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConsultaEndereco{

    private static final String TAG = "ConsultaEndereco";
    private List<Endereco> endereco = null;
    private int tpConsulta;
    private CharSequence caracteres;

    public ConsultaEndereco(CharSequence caracteres, int tpConsulta){

        this.tpConsulta = tpConsulta;
        this.caracteres = caracteres;

    }

    public List<Endereco> executa(){

        try {

            if(tpConsulta == 0)
                endereco = parse(downloadUrl("http://webdesv.goiania.go.gov.br/sistemas/sa156/asp/sa15600004f3.asp?nm_bairro="+caracteres));
            else if(tpConsulta == 1)
                endereco = parse(downloadUrl("http://webdesv.goiania.go.gov.br/sistemas/sa156/asp/sa15600004f3.asp?nm_bairro=bue"));

        }catch(IOException e){

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onPostExecute - getCdBairro:"+endereco.get(0).getCdBairro());
        Log.d(TAG, "onPostExecute - getNmBairro:"+endereco.get(0).getNmBairro());
        Log.d(TAG, "onPostExecute - getCdLogr:"+endereco.get(0).getCdLogr());
        Log.d(TAG, "onPostExecute - getNmLogr:"+endereco.get(0).getNmLogr());

        return endereco;

    }

    /*public String executa(String url){

        Log.d(TAG, "executa");

        Log.d(TAG, "executa url: "+url);

        RequestQueue queue = Volley.newRequestQueue(this.contexto);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d(TAG, "executa Response.Listener - response: "+response);
                        body = "Response is: "+ response;

                        endereco = parse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Erro ao buscar endereco!");
                body = "That didn't work!";
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return "";

    }*/

    private InputStream downloadUrl(String urlString) throws IOException {
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

    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            if (verificaErro(parser))
                return null;
            else
                return leXml(parser);
        } finally {
            in.close();
        }
    }

    public boolean verificaErro(XmlPullParser parser) throws IOException{

        try{

            parser.require(XmlPullParser.START_TAG, ns, "msg");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("msg")) {
                    Log.d(TAG, "verificaErro");
                    return true;
                } else {
                    skip(parser);
                }
            }

            return false;

        }catch(XmlPullParserException e){

            return false;

        }

    }

    public List leXml(XmlPullParser parser) throws XmlPullParserException, IOException{

        List enderecos = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "dt");

        switch(tpConsulta){

            case 0:
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    if (name.equals("bairro")) {
                        enderecos.add(leBairro(parser));
                    } else {
                        skip(parser);
                    }
                }

                return enderecos;

            case 1:

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    if (name.equals("logr")) {
                        enderecos.add(leLogradouro(parser));
                    } else {
                        skip(parser);
                    }
                }

                return enderecos;

            default:
                return null;

        }

    }

    private Endereco leBairro(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "bairro");
        int cdBairro = 0;
        String nmBairro = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("cd")) {
                cdBairro = Integer.parseInt(leCodigo(parser));
            } else if (name.equals("nm")) {
                nmBairro = leNome(parser);
            }else {
                skip(parser);
            }
        }
        return new Endereco(cdBairro, nmBairro, 0, "");
    }

    private Endereco leLogradouro(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "bairro");
        int cdLogr = 0;
        String nmLogr = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("cd")) {
                cdLogr = Integer.parseInt(leCodigo(parser));
            } else if (name.equals("nm")) {
                nmLogr = leNome(parser);
            }else {
                skip(parser);
            }
        }
        return new Endereco(0, "", cdLogr, nmLogr);
    }

    private String leCodigo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "cd");
        String cdBairro = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "cd");
        return cdBairro;
    }

    private String leNome(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "nm");
        String nmBairro = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "nm");
        return nmBairro;
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

    /*public String executa(String urls) throws IOException{
        Volley = v;
        InputStream is = null;

        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            body = writeXml(ListfcAedes);
            OutputStream output = new BufferedOutputStream(conn.getOutputStream());
            output.write(body.getBytes());
            output.flush();

            int response = conn.getResponseCode();
            Log.d(TAG, "executa - O codigo da resposta é: " + response);

            is = conn.getInputStream();

            body = readIt(is, 500);

        }finally {
            conn .disconnect();
        }

        return "teste";

    }*/

}
