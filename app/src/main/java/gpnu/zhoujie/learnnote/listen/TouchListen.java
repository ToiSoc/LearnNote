package gpnu.zhoujie.learnnote.listen;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class TouchListen implements View.OnTouchListener {
    EditText editText = null;

    public TouchListen(EditText editText)
    {
        this.editText = editText;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int start = editText.getWidth() - editText.getPaddingEnd()- editText.getPaddingRight();
        int end = editText.getWidth();      //整个文本的宽

        float x = motionEvent.getX();

        boolean isButtonClicked = x>start && x < end;
        if(isButtonClicked)
        {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP)
            {
                editText.setText("");
                return true;
            }
        }
        return false;
    }
}
