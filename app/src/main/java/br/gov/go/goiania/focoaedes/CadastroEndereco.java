package br.gov.go.goiania.focoaedes;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import br.gov.go.goiania.focoaedes.auxiliar.AutoCompleteAdapter;
import br.gov.go.goiania.focoaedes.auxiliar.CustomAutoCompleteTextChangedListener;
import br.gov.go.goiania.focoaedes.auxiliar.CustomAutoCompleteView;
import br.gov.go.goiania.focoaedes.rede.ConsultaEndereco;

public class CadastroEndereco extends Fragment {

    //CustomAutoCompleteView buscaLogr;
    AutoCompleteTextView busca_bairro;

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cadastra_endereco, container, false);

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(),0);

        //this.buscaLogr = (CustomAutoCompleteView) view.findViewById(R.id.busca_logr);
        this.busca_bairro = (AutoCompleteTextView) view.findViewById(R.id.busca_bairro);
        //ArrayAdapter<String> listaPais = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        this.busca_bairro.setAdapter(adapter);
        //buscaLogr.addTextChangedListener(new CustomAutoCompleteTextChangedListener(getActivity()));

        return view;

    }

}
