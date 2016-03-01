package br.gov.go.goiania.focoaedes;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.gov.go.goiania.focoaedes.auxiliar.BuscaEnderecoAdapter;
import br.gov.go.goiania.focoaedes.auxiliar.CustomAutoCompleteView;
import br.gov.go.goiania.focoaedes.entidades.Endereco;

public class CadastroEndereco extends Fragment {

    private static final String TAG = "CadastroEndereco";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private static CustomAutoCompleteView buscaBairro;
    private static CustomAutoCompleteView buscaLogr;
    private static TextView txtCdBairro;
    private static TextView txtCdLogr;
    private ImageView imgFoto;
    private Button btnTiraFoto;

    //private Spinner selTpLogr;
    private ArrayAdapter<CharSequence> adapter;
    private static BuscaEnderecoAdapter adapterLogr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cadastra_endereco, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        this.buscaBairro = (CustomAutoCompleteView) view.findViewById(R.id.busca_bairro);
        this.txtCdBairro = (TextView) view.findViewById(R.id.cd_bairro);
        this.buscaLogr = (CustomAutoCompleteView) view.findViewById(R.id.busca_logr);
        this.txtCdLogr = (TextView) view.findViewById(R.id.cd_logr);
        this.btnTiraFoto = (Button) view.findViewById(R.id.btn_tira_foto);

        this.imgFoto = (ImageView) view.findViewById(R.id.img_foto);

        //selTpLogr = (Spinner) view.findViewById(R.id.sel_tp_logr);


        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sel_tp_logr, android.R.layout.simple_spinner_item);

        //selTpLogr.setAdapter(adapter);

        BuscaEnderecoAdapter adapterBairro = new BuscaEnderecoAdapter(getActivity(),0);

        this.buscaBairro.setAdapter(adapterBairro);


        this.buscaBairro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if ((txtCdBairro.getText().toString().equals("0") || txtCdBairro.getText().toString().equals(""))
                        && !buscaBairro.getText().toString().equals("")) {

                    buscaBairro.setText("");
                    txtCdBairro.setText("");
                    buscaLogr.setText("");
                    txtCdLogr.setText("");

                    new AlertDialog.Builder(getActivity())
                            .setMessage("Bairro não encontrado!")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .show();

                }
            }
        });

        this.buscaBairro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                buscaBairro.setText("");
                txtCdBairro.setText("");
                buscaLogr.setText("");
                txtCdLogr.setText("");

                return false;
            }
        });

        this.buscaBairro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int cdBairro;

                Endereco endereco = (Endereco) parent.getAdapter().getItem(position);
                buscaBairro.setText(endereco.getNmBairro());
                txtCdBairro.setText(String.valueOf(endereco.getCdBairro()));

                if (!txtCdBairro.getText().toString().equals("")) {
                    cdBairro = Integer.parseInt(txtCdBairro.getText().toString());
                } else {
                    cdBairro = 0;
                }

                adapterLogr = new BuscaEnderecoAdapter(getActivity(), 1, cdBairro);

                buscaLogr.setAdapter(adapterLogr);

            }
        });

        this.buscaLogr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Endereco endereco = (Endereco) parent.getAdapter().getItem(position);
                buscaLogr.setText(endereco.getNmLogr());
                txtCdLogr.setText(String.valueOf(endereco.getCdLogr()));

            }
        });

        this.buscaLogr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "buscaLogr.onTouch - txtCdBairro: " + txtCdBairro.getText().toString());
                Log.d(TAG, "buscaLogr.onTouch - buscaLogr.hasFocus(): " + buscaLogr.hasFocus());
                if (txtCdBairro.getText().toString().equals("") || txtCdBairro.getText().toString().equals("0")) {

                    //if (buscaLogr.hasFocus()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Informe o bairro!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                }).show();

                        buscaLogr.setText("");
                        txtCdLogr.setText("");}

                    //}

                }

                return false;
            }
        });

        this.buscaLogr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if ((txtCdLogr.getText().toString().equals("0") || txtCdLogr.getText().toString().equals("")) && !buscaLogr.getText().toString().equals("")) {

                    buscaLogr.setText("");
                    txtCdLogr.setText("");

                    if (!buscaLogr.getText().toString().equals(""))
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Logradouro não encontrado!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .show();

                }
            }
        });

        capturaImagem();

        return view;

    }

    public void capturaImagem(){

        this.btnTiraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imgFoto.setImageBitmap(photo);
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                Toast.makeText(getActivity(), "Ocorreu um erro tente novamente!" +
                        data.getData(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
