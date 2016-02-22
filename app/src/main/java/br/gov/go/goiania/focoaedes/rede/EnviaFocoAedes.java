package br.gov.go.goiania.focoaedes.rede;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

public class EnviaFocoAedes extends AsyncTask<String, Void, String> {

    private static final String REGISTER_URL = "http://webdesv.goiania.go.gov.br/sistemas/sa156/asp/sa15600004a0.asp";
    private static String resposta;

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

        return executa();

    }

    @Override
    protected void onPreExecute() {
        /*pd = new ProgressDialog(contexto);
        pd.setMessage("Enviando dados...");
        pd.show();*/
    }

    @Override
    protected void onPostExecute(String result) {

        new AlertDialog.Builder(contexto)
                .setTitle("Resposta")
                .setMessage(result)
                .show();

    }

    public String executa(){

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, REGISTER_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse: " + response);
                    resposta = response;
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error);
                    //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    resposta = "Erro: "+error.toString();
                }
            }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("opr","Inclui_solicitacao");
                params.put("txt_cd_contri","532");
                params.put("txt_nm_contri","SEBASTIAO JUNIO MENEZES CAMPOS");
                params.put("txt_cd_munic","25300");
                params.put("txt_nr_cpf_contri","03120401137");
                params.put("txt_in_email_contri","juniobc@gmail.com");
                params.put("txt_cd_servico","190");

                params.put("txt_cd_munic_solic","25300");
                params.put("txt_cd_bairro_solic","16");
                params.put("txt_cd_logr_solic","20143");
                params.put("txt_en_lt_logr_solic","s2");
                params.put("txt_en_qd_logr_solic","q10");
                params.put("txt_en_nr_logr_solic","3659");

                params.put("sel_tp_logr_solicitacao","PCA");
                params.put("txt_nm_cd_munic_solicitacao","GOIANIA");
                params.put("txt_nm_cd_bairro_solicitacao","16");
                params.put("txt_nm_cd_logr_solicitacao","20143");
                params.put("txt_ds_solicitacao","teste de inclusao");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(contexto);
        requestQueue.add(stringRequest);

        return resposta;

    }

    /*public String executa(String urls) throws IOException{

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
    }*/

}
