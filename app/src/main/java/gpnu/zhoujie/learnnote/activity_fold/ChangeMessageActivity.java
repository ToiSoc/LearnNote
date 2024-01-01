package gpnu.zhoujie.learnnote.activity_fold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.utils.SpareTool;

public class ChangeMessageActivity extends AppCompatActivity {

    public AdminDao adminDao = new AdminDao();

    public SpareTool spareTool = new SpareTool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_message);

        Intent intent = getIntent();
        String account_id = intent.getStringExtra("account");
        Log.i("修改个人信息界面是否拿到ID",account_id);

        Toolbar toolbar = findViewById(R.id.toolbar_change_msg);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent1 = new Intent(ChangeMessageActivity.this, MessageShowActivity.class);
//                intent1.putExtra("account",account_id);
//                startActivity(intent1);
            }
        });


        EditText change_name = findViewById(R.id.change_msg_name);         //姓名

        RadioButton change_man = findViewById(R.id.change_msg_man);        //性别按钮
        RadioButton change_women = findViewById(R.id.change_msg_women);    //性别女
        change_man.setChecked(true);

        EditText change_phone = findViewById(R.id.change_msg_phone);        //联系方式
        EditText change_age = findViewById(R.id.change_msg_age);            //年龄


        new SpareTool().setBtnClear(change_name);
        new SpareTool().setBtnClear(change_phone);
        new SpareTool().setBtnClear(change_age);


        Button btn_change_msg_endure = findViewById(R.id.btn_change_msg_endure);
        btn_change_msg_endure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                String name_c = change_name.getText().toString();
                String phone_c = change_phone.getText().toString();
                String age_c = change_age.getText().toString();

                if(!name_c.isEmpty())
                {
                    values.put("s_name",name_c);
                }
                if(!phone_c.isEmpty())
                {
                    values.put("s_phone",phone_c);
                }
                if(!age_c.isEmpty())
                {
                    values.put("s_age",age_c);
                }

                String sex = change_man.isChecked() ? "男" : "女";
                values.put("s_sex",sex);

                if(values.isEmpty())
                {
                    spareTool.showToast(ChangeMessageActivity.this, MessageConstant.MESSAGE_NO_UPDATE);
                    return;
                }

                int status_id = adminDao.changeMessage(account_id,values);

                Intent intent1=null;

                switch(status_id)
                {
                    case 0:
                        spareTool.showToast(ChangeMessageActivity.this, MessageConstant.MESSAGE_NO_UPDATE);
//                        finish();
                        intent1 = new Intent(ChangeMessageActivity.this, MessageShowActivity.class);
                        intent1.putExtra("account",account_id);
                        startActivity(intent1);
                        break;
                    case 1:
                        spareTool.showToast(ChangeMessageActivity.this, MessageConstant.MESSAGE_UPDATE_SUCCESS);
//                        finish();
                        intent1 = new Intent(ChangeMessageActivity.this, MessageShowActivity.class);
                        intent1.putExtra("account",account_id);
                        startActivity(intent1);
                        break;
                    default:
                        spareTool.showToast(ChangeMessageActivity.this, MessageConstant.MESSAGE_UPDATE_FAIL);
                        break;
                }
            }
        });
    }
}