package br.gov.go.goiania.focoaedes.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

import br.gov.go.goiania.focoaedes.R;

public class ListaFocoAedes extends ArrayAdapter<FocoAedes> {

    private static final String TAG ="ListaFocoAedes";
    Context context;
    private int resource;
    private List<FocoAedes> data = null;

    public ListaFocoAedes(Context context, int resource, List<FocoAedes> object) {
        super(context, resource, object);
        this.context = context;
        this.resource = resource;
        this.data = object;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent,false);
        }

        RelativeLayout root = (RelativeLayout) convertView.findViewById(R.id.row);

        FocoAedes object = data.get(position);

        FrameLayout imgFoco = (FrameLayout) convertView.findViewById(R.id.img_foto);
        TextView statusEnvio = (TextView) convertView.findViewById(R.id.status_envio);
        TextView dsFocoAedes = (TextView) convertView.findViewById(R.id.ds_foco_aedes);
        TextView cdSolicitacao = (TextView) convertView.findViewById(R.id.cd_solicitacao);

        //imgFoco.setImageBitmap(getCroppedBitmap(DbBitmapUtility.getImage(object.getImgLocal()),100));
        Drawable background = imgFoco.getBackground();

        if (background instanceof ShapeDrawable) {
            // cast to 'ShapeDrawable'
            ShapeDrawable shapeDackground = (ShapeDrawable)background;

            if(object.getStatus().equals("FECHADA"))
                shapeDackground.getPaint().setColor(ContextCompat.getColor(context, R.color.st_fechada));
            else
                shapeDackground.getPaint().setColor(ContextCompat.getColor(context, R.color.st_aberto));

        } else if (background instanceof GradientDrawable) {

            GradientDrawable gradientDrawable = (GradientDrawable)background;

            if(object.getStatus().equals("FECHADA"))
                gradientDrawable.setColor(ContextCompat.getColor(context, R.color.st_fechada));
            else
                gradientDrawable.setColor(ContextCompat.getColor(context, R.color.st_aberto));
        }



        //statusEnvio.setText(object.getStatus());
        statusEnvio.setText("há 1 dia");
        dsFocoAedes.setText(object.getDsFocoAedes());
        cdSolicitacao.setText("SOLICITAÇÃ: "+String.valueOf(object.getCdFocoAedes()));

        return convertView;

    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if(bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f,
                sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);


        return output;
    }

}
