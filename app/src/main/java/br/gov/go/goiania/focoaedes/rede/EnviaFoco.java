package br.gov.go.goiania.focoaedes.rede;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by m1031007 on 22/02/2016.
 */
public class EnviaFoco {

    private static final String TAG = "EnviaFoco";

    private ProgressDialog pd;

    private static final String REGISTER_URL = "http://webdesv.goiania.go.gov.br/sistemas/sa156/asp/sa15600004a0.asp";
    private Context contexto;

    public EnviaFoco(Context contexto){

        this.contexto = contexto;

    }

    public void executa(){

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        new AlertDialog.Builder(contexto)
                                .setTitle("Resposta")
                                .setMessage(response)
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        new AlertDialog.Builder(contexto)
                                .setTitle("Resposta")
                                .setMessage(error.toString())
                                .show();
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

        pd = new ProgressDialog(contexto);

        pd.setMessage("Enviando dados...");
        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(contexto);
        /*Map<String, String> params;
        try{
            params = stringRequest.getHeaders();
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                Toast.makeText(contexto, entry.getKey()+" : "+entry.getValue(), Toast.LENGTH_SHORT).show();
            }
        } catch (AuthFailureError error){
            Toast.makeText(contexto, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();
        }*/
        requestQueue.add(stringRequest);

    }

}
