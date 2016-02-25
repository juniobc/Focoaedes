package br.gov.go.goiania.focoaedes.rede;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

/**
 * Created by m1031007 on 25/02/2016.
 */
public class ConsultaFocoAedes extends AsyncTask<Void, Void, List<FocoAedes>> {

    private static final String TAG = "ConsultaFocoAedes";

    private List<FocoAedes> focoAedes = null;
    private static final String ns = null;
    private ProgressDialog pd;
    private Context contexto;

    public ConsultaFocoAedes(Context contexto){

        this.contexto = contexto;

    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(contexto);
        pd.setMessage("Enviando dados...");
        pd.show();
    }

    @Override
    protected List<FocoAedes> doInBackground(Void... params) {
        return executa();
    }

    @Override
    protected void onPostExecute(List<FocoAedes> result) {

        pd.dismiss();

        Log.d(TAG, "onPostExecute - result.size(): " + result.size());

        for (FocoAedes temp : result) {
            //Toast.makeText(contexto, temp.getCdFocoAedes(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPostExecute - temp.getCdFocoAedes(): "+temp.getCdFocoAedes());
        }

    }

    public List<FocoAedes> executa(){

        try {

            focoAedes = parse(downloadUrl("http://webdesv.goiania.go.gov.br/sistemas/sa156/asp/sa15600004f8.asp?nr_cpf_contri=03120401137&cd_servico=190"));

        }catch(IOException e){

            e.printStackTrace();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return focoAedes;

    }

    private InputStream downloadUrl(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

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

            return true;

        }catch(XmlPullParserException e){

            return false;

        }
    }

    public List leXml(XmlPullParser parser) throws XmlPullParserException, IOException{

        List foco = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "dt");

        while (parser.next() != XmlPullParser.END_TAG) {
            Log.d(TAG,"CONTADOS: "+parser.getName());
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("solicitacao")) {
                //foco.add(leFoco(parser));
            } else {
                skip(parser);
            }
        }

        return foco;
    }

    private FocoAedes leFoco(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "solicitacao");
        int cdFocoAedes = 0;
        String dsFocoAedes = null;
        String status = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch(name){

                case "cd_sol":
                    cdFocoAedes = Integer.parseInt(le(parser, "cd_sol"));
                    break;

                case "status_atual":
                    status = le(parser, "status_atual");
                    break;

                case "in_descricao_st":
                    dsFocoAedes = le(parser, "in_descricao_st");
                    break;

            }
        }
        return new FocoAedes(cdFocoAedes, status,dsFocoAedes);
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

}
