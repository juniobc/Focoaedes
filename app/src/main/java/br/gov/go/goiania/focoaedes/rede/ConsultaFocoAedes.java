package br.gov.go.goiania.focoaedes.rede;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;
import br.gov.go.goiania.focoaedes.xml.ListaFocoAedes;


public class ConsultaFocoAedes extends AsyncTask<Void, Void, List<FocoAedes>> {

    private static final String TAG = "ConsultaFocoAedes";

    private List<FocoAedes> focoAedes = null;
    private ProgressDialog pd;
    private Context contexto;

    public ConsultaFocoAedes(Context contexto){

        this.contexto = contexto;

    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(contexto);
        pd.setMessage("Consultando solicitações...");
        pd.show();
    }

    @Override
    protected List<FocoAedes> doInBackground(Void... params) {

        try {

            ListaFocoAedes retorno = new ListaFocoAedes();

            focoAedes = retorno.executa(downloadUrl(
                    "http://webdesv.goiania.go.gov.br/sistemas/sa156/asp/sa15600004f8.asp?nr_cpf_contri=03120401137&cd_servico=190"));

        }catch(IOException e){

            e.printStackTrace();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return focoAedes;
    }

    @Override
    protected void onPostExecute(List<FocoAedes> result) {

        pd.dismiss();

        if(result != null){
            Log.d(TAG, "onPostExecute - result.size(): " + result.size());
        }else{
            Log.d(TAG, "onPostExecute - result: null");
        }

        //Log.d(TAG, "onPostExecute - result.size(): " + result.size());

        /*for (FocoAedes temp : result) {
            //Toast.makeText(contexto, temp.getCdFocoAedes(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPostExecute - temp.getCdFocoAedes(): "+temp.getCdFocoAedes());
        }*/

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

}
