package gpnu.zhoujie.learnnote.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import gpnu.zhoujie.learnnote.activity_fold.ChangePwdActivity;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.listen.ChangeListen;
import gpnu.zhoujie.learnnote.listen.TouchListen;

public class SpareTool {

    public void setBtnClear(EditText editText)
    {
        editText.addTextChangedListener(new ChangeListen(editText));
        editText.setOnTouchListener(new TouchListen((editText)));
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
    }

    public void showToast(Context context, String info)
    {
        Toast.makeText(context, info ,Toast.LENGTH_SHORT).show();
    }

}
