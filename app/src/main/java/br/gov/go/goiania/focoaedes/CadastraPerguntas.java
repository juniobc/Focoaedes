package br.gov.go.goiania.focoaedes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CadastraPerguntas extends Fragment {

    private Spinner tpLocal;
    private Spinner loteVago;
    private ArrayAdapter<CharSequence> adapter;
    private EditText ds_horario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cadastra_perguntas, container, false);

        tpLocal = (Spinner) view.findViewById(R.id.tp_local);
        loteVago = (Spinner) view.findViewById(R.id.lote_vago);

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.tp_local, android.R.layout.simple_spinner_item);

        tpLocal.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.lote_vago, android.R.layout.simple_spinner_item);

        loteVago.setAdapter(adapter);

        ds_horario = (EditText) view.findViewById(R.id.ds_horario);

        //MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", cpf);


        return view;

    }
}
