package br.gov.go.goiania.focoaedes.rede;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.go.goiania.focoaedes.R;
import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

public class EnviaFocoAedes extends AsyncTask<String, Void, String> {

    private static final String REGISTER_URL = "http://intradesv.goiania.go.gov.br/sistemas/sismp/asp/sismp22222f0.asp";
    private static String resposta;

    Context contexto;
    ProgressDialog pd;
    List<FocoAedes> ListfcAedes;
    String body;
    private static final String TAG = "EnviaFocoAedes";

    public EnviaFocoAedes(Context context){
        this.contexto = context;
        //this.ListfcAedes = ListfcAedes;
    }

    @Override
    protected String doInBackground(String... urls) {

        return chamaConexao();

    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(contexto);
        pd.setMessage("Enviando dados...");
        pd.show();
    }

    @Override
    protected void onPostExecute(String result) {

        pd.dismiss();

        new AlertDialog.Builder(contexto)
                .setTitle("Resposta")
                .setMessage(result)
                .show();

    }

    public String chamaConexao(){

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

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpost = new HttpPost(REGISTER_URL);
        MultipartEntity entity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            entity.addPart("myString", new StringBody("STRING_VALUE"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.addPart("myImageFile", new FileBody(file));
        httpost.setEntity(entity);
        HttpResponse response;
        try {

            response = httpclient.execute(httpost);

            String responseStr = EntityUtils.toString(response.getEntity());

            Log.d(TAG,"chamaConexao - response: "+responseStr);

            return responseStr;

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }



    }

}
