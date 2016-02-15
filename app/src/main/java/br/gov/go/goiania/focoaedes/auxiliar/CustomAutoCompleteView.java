package br.gov.go.goiania.focoaedes.auxiliar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;

public class CustomAutoCompleteView extends AutoCompleteTextView {

    {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /** no-op */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /** no-op */
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSelectionFromPopUp = false;
            }
        });
    }

    private boolean mSelectionFromPopUp;

    public CustomAutoCompleteView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    // this is how to disable AutoCompleteTextView filter
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {

        String filterText;

        if(text.length() < 3)
            filterText = "";
        else
            filterText = text.toString();

        super.performFiltering(filterText, keyCode);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getAdapter() != null) {
            performFiltering(getText(), 0);
        }

        return super.onTouchEvent(event);
    }

    /*
     * after a selection we have to capture the new value and append to the existing text
     */
    @Override
    protected void replaceText(final CharSequence text) {
        super.replaceText(text);

        mSelectionFromPopUp = true;

    }

    public boolean isSelectionFromPopUp() {
        return mSelectionFromPopUp;
    }

}
