package br.gov.go.goiania.focoaedes.rede;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import br.gov.go.goiania.focoaedes.Home;
import br.gov.go.goiania.focoaedes.R;
import br.gov.go.goiania.focoaedes.auxiliar.DbBitmapUtility;
import br.gov.go.goiania.focoaedes.banco.FocoAedesDB;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;
import br.gov.go.goiania.focoaedes.xml.RetornoCadFoco;

public class EnviaFoco {

    private static final String TAG = "EnviaFoco";

    private ProgressDialog pd;

    private FocoAedesDB focoAedesDB;

    private static FocoAedes fcAedes;

    private static Bitmap bitmap;

    private static final String REGISTER_URL = "http://www.goiania.go.gov.br/sistemas/sa156/asp/sa15600004a0.asp";
    private Context contexto;
    private Map<String,String> params;
    private Map<String,String> paramsDesc;

    private static String retorno = null;

    public EnviaFoco(Context contexto, Map<String,String> params, Map<String,String> paramsDesc){

        this.contexto = contexto;
        this.params = params;
        this.focoAedesDB = new FocoAedesDB(contexto);
        this.paramsDesc = paramsDesc;

    }

    public String executa(){

        String descricao = "";

        if(paramsDesc != null){

            int contador = 1;
            for (Map.Entry<String, String> entry : paramsDesc.entrySet()) {

                Log.d(TAG, "executa - entry.getValue()"+entry.getValue());

                descricao += entry.getValue();

                contador ++;

                if(contador <= paramsDesc.size()){descricao+=", ";}


            }

            params.put("txt_ds_solicitacao", params.get("txt_ds_solicitacao") + " " + descricao);
        }



        if(descricao.equals(""))
            fcAedes = new FocoAedes(0,params.get("txt_ds_solicitacao"),"PENDENTE ENVIO");
        else
            fcAedes = new FocoAedes(0,descricao,"PENDENTE ENVIO");

        ImageView image = (ImageView) ((Activity) contexto).findViewById(R.id.img_foto);

        bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

        File file = new File(contexto.getCacheDir(), "foto.png");

        try {

            file.createNewFile();


            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

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
                Log.d(TAG, "onResponse: " + error.toString());
                retorno = null;
                new AlertDialog.Builder(contexto)
                        .setMessage("Ocorreu um erro no envio! Tente novamente.")
                        .show();
            }
        },new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                pd.dismiss();

                try {
                    retorno = new RetornoCadFoco().executa(response);
                } catch (XmlPullParserException e) {
                    Log.d(TAG,"Response.Listener - XmlPullParserException: "+e.toString());
                    new AlertDialog.Builder(contexto)
                    .setMessage("Solicitação incluida! Não foi possivel obter o numero da solicitação.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((Activity) contexto).finish();
                        }
                    })
                    .show();
                } catch (IOException e) {
                    Log.d(TAG,"Response.Listener - XmlPullParserException: "+e.toString());
                    new AlertDialog.Builder(contexto)
                            .setMessage("Solicitação incluida! Não foi possivel obter o numero da solicitação.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ((Activity) contexto).finish();
                                }
                            })
                            .show();
                }

                if(retorno == null){
                    Log.d(TAG,"Response.Listener - retorno == null");
                    new AlertDialog.Builder(contexto)
                            .setMessage("Solicitação incluida! Não foi possivel obter o numero da solicitação.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ((Activity) contexto).finish();
                                }
                            })
                            .show();
                }

                new AlertDialog.Builder(contexto)
                        .setMessage("Solicitação Nº "+retorno
                                +" incluida com sucesso! A Prefeitura de Goiânia agradece o seu contato.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            fcAedes.setCdFocoAedes(Integer.parseInt(retorno));
                            fcAedes.setStatus("ABERTA");
                            fcAedes.setImgLocal(DbBitmapUtility.getBytes(bitmap));

                            focoAedesDB.addFocoAedes(fcAedes);

                            try {
                                new Home().atualisaLista(contexto);
                            } catch (ExecutionException e) {
                                Toast.makeText(contexto, "Não foi possivel consultar as solicitações!", Toast.LENGTH_SHORT).show();
                            } catch (InterruptedException e) {
                                Toast.makeText(contexto, "Não foi possivel consultar as solicitações!", Toast.LENGTH_SHORT).show();
                            }

                            ((Activity) contexto).finish();
                            }
                        })
                        .show();
            }
        },file, params);

        pd = new ProgressDialog(contexto);

        pd.setMessage("Enviando dados...");
        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(contexto);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

        return retorno;

    }

}
