package gpnu.zhoujie.learnnote.activity_fold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Space;
import android.widget.Toast;


import java.util.HashMap;

import gpnu.zhoujie.learnnote.MainActivity;
import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.utils.SpareTool;

public class RegisterActivity extends AppCompatActivity {

    public static HashMap normalMap = MessageConstant.normalMap;

    public SpareTool spaceTool = new SpareTool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText account = findViewById(R.id.register_account);     //账号
        EditText pwd = findViewById(R.id.register_pwd);             //密码
        EditText name = findViewById(R.id.register_name);           //姓名
        RadioButton man = findViewById(R.id.register_man);          //男
        RadioButton women = findViewById(R.id.register_women);      //女
        man.setChecked(true);

        EditText phone = findViewById(R.id.register_phone);         //电话
        EditText age = findViewById(R.id.register_age);             //年龄

        Button register = findViewById(R.id.btn_true);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account_s = account.getText().toString();
                String pwd_s = pwd.getText().toString();
                String pwd_endure_s = pwd.getText().toString();
                String name_s = name.getText().toString();
                String phone_s = phone.getText().toString();
                String age_s = age.getText().toString();

                String sex_s = "女";
                if (man.isChecked())
                {
                    sex_s="男";
                }

                if (account_s.isEmpty())
                {
                    spaceTool.showToast(RegisterActivity.this,MessageConstant.ACCOUNT_ISNULL);
                    return;
                }
                else if(pwd_s.isEmpty())
                {
                    spaceTool.showToast(RegisterActivity.this,MessageConstant.PWD_ISNULL);
                    return;
                }
                else if (pwd_endure_s.isEmpty())
                {
                    spaceTool.showToast(RegisterActivity.this,MessageConstant.PWD_ENDURE_ISNULL);
                    return;
                }
                else if(name_s.isEmpty())
                {
                    spaceTool.showToast(RegisterActivity.this,MessageConstant.NAME_ISNULL);
                    return;
                }
                else if(phone_s.isEmpty())
                {
                    spaceTool.showToast(RegisterActivity.this,MessageConstant.PHONE_ISNULL);
                    return;
                }
                else if(age_s.isEmpty())
                {
                    spaceTool.showToast(RegisterActivity.this,MessageConstant.AGE_ISNULL);
                    return;
                }

                if(!pwd_s.equals(pwd_endure_s))
                {
                    spaceTool.showToast(RegisterActivity.this,MessageConstant.PWD_DIFFERENT);
                    return;
                }

                AdminDao adminDao = new AdminDao();
                int A = adminDao.registerAdmin(account_s,pwd_s,name_s,sex_s,phone_s,age_s);

                if(A == 1) {
                    Toast.makeText(RegisterActivity.this, MessageConstant.REGISTER_TRUE, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (A == -1)
                {
                    Toast.makeText(RegisterActivity.this, MessageConstant.REGISTER_FALSE,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}