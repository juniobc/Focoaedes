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

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private static final String TAG = "AutoCompleteAdapter";

    private LayoutInflater mInflater;
    private int tpConsulta;

    public AutoCompleteAdapter(Context context, int tpConsulta) {
        super(context, -1);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.tpConsulta = tpConsulta;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final TextView tv;
        if (convertView != null) {
            tv = (TextView) convertView;
        } else {
            tv = (TextView) mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        tv.setText(getItem(position));
        return tv;
    }

    @Override
    public Filter getFilter() {

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Endereco> enderecos = null;

                if (constraint != null) {

                    enderecos = new ConsultaEndereco(constraint, tpConsulta).executa();

                }

                if (enderecos == null) {
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
                    add(enderecos.getNmBairro());
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

    private String formataEndereco(final Endereco endereco) {
        return endereco.getNmBairro();
    }

}
