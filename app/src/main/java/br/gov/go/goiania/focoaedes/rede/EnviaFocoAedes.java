package br.gov.go.goiania.focoaedes.rede;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

public class EnviaFocoAedes extends AsyncTask<String, Void, String> {

    Context contexto;
    ProgressDialog pd;
    List<FocoAedes> ListfcAedes;
    String body;
    private static final String TAG = "EnviaFocoAedes";

    public EnviaFocoAedes(Context context, List<FocoAedes> ListfcAedes){
        this.contexto = context;
        this.ListfcAedes = ListfcAedes;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
        return executa(urls[0]);
        } catch (IOException e) {
            return "Não foi possivel enviar a denuncia!.";
        }
    }

    @Override
    protected void onPreExecute() {
        /*pd = new ProgressDialog(contexto);
        pd.setMessage("Enviando dados...");
        pd.show();*/
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(contexto, body, Toast.LENGTH_LONG).show();

        new AlertDialog.Builder(contexto)
                .setTitle("XML")
                .setMessage(body)
                .show();
        //pd.dismiss();

    }

    public String executa(String urls) throws IOException{

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

    }

    private String writeXml(List<FocoAedes> focoAedes){
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "solicitacao_envio");
            for (FocoAedes fcAedes: focoAedes){
                serializer.startTag("", "dados_envio");
                serializer.startTag("", "dsFocoAedes");
                serializer.text(fcAedes.getDsFocoAedes());
                serializer.endTag("", "dsFocoAedes");
                serializer.endTag("", "dados_envio");
            }
            serializer.endTag("", "solicitacao_envio");
            serializer.endDocument();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}
