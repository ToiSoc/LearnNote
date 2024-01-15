package gpnu.zhoujie.learnnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gpnu.zhoujie.learnnote.activity_fold.AddNoteActivity;
import gpnu.zhoujie.learnnote.activity_fold.AvatarChangeActivity;
import gpnu.zhoujie.learnnote.activity_fold.ChangeMessageActivity;
import gpnu.zhoujie.learnnote.activity_fold.ChangePwdActivity;
import gpnu.zhoujie.learnnote.activity_fold.ManageActivity;
import gpnu.zhoujie.learnnote.activity_fold.MessageShowActivity;
import gpnu.zhoujie.learnnote.activity_fold.RegisterActivity;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.utils.DBUtils;

public class MainActivity extends AppCompatActivity {

    private String account_s = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 连接数据库
        DBUtils dbUtils = new DBUtils(MainActivity.this);
        SQLiteDatabase db = dbUtils.getWritableDatabase();
        DBUtils.db = db;

        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);

        EditText account = findViewById(R.id.edit_account);
        EditText pwd = findViewById(R.id.edit_pwd);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account_s = account.getText().toString();
                String pwd_s = pwd.getText().toString();

                if (account_s.isEmpty() || pwd_s.isEmpty())
                {
                    if(account_s.isEmpty())
                    {
                        Toast.makeText(MainActivity.this, MessageConstant.ACCOUNT_ISNULL,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(MainActivity.this, MessageConstant.PWD_ISNULL,Toast.LENGTH_SHORT).show();
                    return;
                }

                AdminDao adminDao = new AdminDao();
                int count = adminDao.loginAdmin(account_s,pwd_s);

                if(count == MessageConstant.COUNT_ZERO_ERR)
                {
                    Toast.makeText(MainActivity.this, MessageConstant.NOT_ACCOUNT_ERR_PWD,Toast.LENGTH_SHORT).show();
                    return ;
                }
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                intent.putExtra("account",account_s);
                startActivity(intent);
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("account",account_s);
                startActivity(intent);
            }
        });

    }
}

