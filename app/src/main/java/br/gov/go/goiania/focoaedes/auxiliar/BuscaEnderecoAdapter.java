package br.gov.go.goiania.focoaedes.auxiliar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.gov.go.goiania.focoaedes.entidades.Endereco;
import br.gov.go.goiania.focoaedes.rede.ConsultaEndereco;

public class BuscaEnderecoAdapter extends ArrayAdapter<Endereco> implements Filterable {

    private static final String TAG = "BuscaEnderecoAdapter";

    private LayoutInflater mInflater;
    private int tpConsulta;
    private int cdBairro;

    public BuscaEnderecoAdapter(Context context, int tpConsulta) {
        super(context, -1);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.tpConsulta = tpConsulta;
    }

    public BuscaEnderecoAdapter(Context context, int tpConsulta, int cdBairro) {
        super(context, -1);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.tpConsulta = tpConsulta;
        this.cdBairro = cdBairro;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final TextView tv;
        if (convertView != null) {
            tv = (TextView) convertView;
        } else {
            tv = (TextView) mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        switch(tpConsulta){
            case 0:
                tv.setText(getItem(position).getNmBairro());
                break;
            case 1:
                tv.setText(getItem(position).getNmLogr());
                break;
        }

        return tv;
    }

    @Override
    public Filter getFilter() {

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Endereco> enderecos = null;

                if (constraint != null && constraint.length() >= 3) {

                    switch(tpConsulta){
                        case 0:
                            enderecos = new ConsultaEndereco(constraint, tpConsulta).executa();
                            break;
                        case 1:
                            enderecos = new ConsultaEndereco(constraint, tpConsulta, cdBairro).executa();
                            break;
                    }

                }

                if(enderecos == null){
                    enderecos = new ArrayList<Endereco>();
                }

                final FilterResults filterResults = new FilterResults();
                filterResults.values = enderecos;
                filterResults.count = enderecos.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                clear();

                for (Endereco enderecos : (List<Endereco>) results.values) {
                    add(enderecos);
                }
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }

            }

        };

        return myFilter;

    }

}
