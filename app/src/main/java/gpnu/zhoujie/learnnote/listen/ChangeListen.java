package gpnu.zhoujie.learnnote.listen;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import gpnu.zhoujie.learnnote.R;

public class ChangeListen implements TextWatcher {

    EditText editText = null;

    public ChangeListen(EditText editText)
    {
        this.editText = editText;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(editable.length() > 0)
        {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.x,0);
        }
        else
        {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, 0,0);
        }
    }
}
