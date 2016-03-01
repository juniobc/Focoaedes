package br.gov.go.goiania.focoaedes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CadastraPerguntas extends Fragment {

    private Spinner tpLocal;
    private Spinner selLoteVago;
    private ArrayAdapter<CharSequence> adapter;
    //private EditText ds_horario;
    private static TextView txtLoteVago;
    private CheckBox stLocal8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cadastra_perguntas, container, false);

        tpLocal = (Spinner) view.findViewById(R.id.tp_local);


        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.tp_local, android.R.layout.simple_spinner_item);

        tpLocal.setAdapter(adapter);

        selLoteVago = (Spinner) view.findViewById(R.id.sel_lote_vago);

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sel_lote_vago, android.R.layout.simple_spinner_item);

        selLoteVago.setAdapter(adapter);

        txtLoteVago = (TextView) view.findViewById(R.id.txt_lote_vago);
        stLocal8 = (CheckBox) view.findViewById(R.id.st_local_8);

        stLocal8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    txtLoteVago.setVisibility(View.VISIBLE);
                    selLoteVago.setVisibility(View.VISIBLE);

                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.sel_lote_vago, android.R.layout.simple_spinner_item);

                    selLoteVago.setAdapter(adapter);

                }else{
                    txtLoteVago.setVisibility(View.GONE);
                    selLoteVago.setVisibility(View.GONE);
                }

            }

        });


        return view;

    }
}
