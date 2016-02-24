package br.gov.go.goiania.focoaedes.rede;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import br.gov.go.goiania.focoaedes.R;

public class EnviaFoco {

    private static final String TAG = "EnviaFoco";

    private ProgressDialog pd;

    private static final String REGISTER_URL = "http://webdesv.goiania.go.gov.br/sistemas/sa156/asp/sa15600004a0.asp";
    private Context contexto;

    public EnviaFoco(Context contexto){

        this.contexto = contexto;

    }

    public void executa(){

        Map<String,String> params = new HashMap<String, String>();
        params.put("opr","abre_solicitacao");
        params.put("txt_cd_contri","532");
        params.put("txt_nm_contri","SEBASTIAO JUNIO MENEZES CAMPOS");
        params.put("txt_cd_munic","25300");
        params.put("txt_nr_cpf_contri","03120401137");
        params.put("txt_in_email_contri","juniobc@gmail.com");
        params.put("txt_cd_servico","190");

        params.put("txt_cd_munic_solicitacao","25300");
        params.put("txt_cd_bairro_solicitacao","105");
        params.put("txt_cd_logr_solicitacao","20143");
        params.put("txt_en_lt_logr_solicitacao","456");
        params.put("txt_en_qd_logr_solicitacao","65");
        params.put("txt_en_nr_logr_solicitacao","8595");

        params.put("txt_tp_logr_solicitacao","RUA");
        params.put("txt_nm__munic_solicitacao","GOIANIA");
        params.put("txt_nm_bairro_solicitacao","SETOR CRIMEIA OESTE");
        params.put("txt_nm_logr_solicitacao","DES AIROSA ALVES DE CASTRO");
        params.put("txt_ds_solicitacao", "teste de inclusao");

        ImageView image = (ImageView) ((Activity) contexto).findViewById(R.id.img_foto);

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();

        //create a file to write bitmap data
        File file = new File(contexto.getCacheDir(), "teste");

        try {
            file.createNewFile();


            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        MultipartRequest stringRequest = new MultipartRequest(REGISTER_URL,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                new AlertDialog.Builder(contexto)
                        .setTitle("Resposta")
                        .setMessage(error.toString())
                        .show();
            }
        },new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d(TAG, "onResponse: " + response);
                new AlertDialog.Builder(contexto)
                        .setTitle("Resposta")
                        .setMessage(response)
                        .show();
            }
        },file, params){

            @Override
            public Map<String,String> getParams(){

                Map<String,String> params = new HashMap<String,String>();

                params.put("opr","teste");

                return params;
            }

        };

        pd = new ProgressDialog(contexto);

        pd.setMessage("Enviando dados...");
        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(contexto);

        requestQueue.add(stringRequest);

    }

}
