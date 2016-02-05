package br.gov.go.goiania.focoaedes.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.FocoAedes;

import br.gov.go.goiania.focoaedes.R;

public class ListaFocoAedes extends ArrayAdapter<FocoAedes> {

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

        LinearLayout root = (LinearLayout) convertView.findViewById(R.id.row);

        FocoAedes object = data.get(position);

        ImageView imgFoco = (ImageView) convertView.findViewById(R.id.img_foto);

        imgFoco.setImageBitmap(DbBitmapUtility.getImage(object.getImgLocal()));

        return convertView;

    }

}
