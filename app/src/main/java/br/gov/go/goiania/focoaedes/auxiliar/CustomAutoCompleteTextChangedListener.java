package br.gov.go.goiania.focoaedes.auxiliar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

public class CustomAutoCompleteTextChangedListener implements TextWatcher{

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        /* if you want to see in the logcat what the user types
        Log.e(TAG, "User input: " + userInput);

        mainActivity = ((BuscaMedicamento) context);

        mainActivity.adapter.notifyDataSetChanged();

        if(userInput.length() >= 3){

            new BuscaMedicamento().execute("teste");

            mainActivity.adapter = new ListaMedicamentos(context,R.layout.lista_medicamentos,new String[]{"teste","teste3"});

        }else{

            mainActivity.adapter = new ListaMedicamentos(context,R.layout.lista_medicamentos,new String[]{"Informe 3 caracteres!"});

        }

        mainActivity.myAutoComplete.setAdapter(mainActivity.adapter);*/

    }

}
