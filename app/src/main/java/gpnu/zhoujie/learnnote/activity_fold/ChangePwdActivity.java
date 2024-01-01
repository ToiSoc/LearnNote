package gpnu.zhoujie.learnnote.activity_fold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.utils.SpareTool;

public class ChangePwdActivity extends AppCompatActivity {

    public SpareTool spareTool = new SpareTool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);

        Intent intent = getIntent();
        String account = intent.getStringExtra("account");

        //界面返回
        Toolbar toolbar = findViewById(R.id.toolbar_pwd);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent1 = new Intent(ChangePwdActivity.this,ManageActivity.class);
//                intent1.putExtra("account",account);
//                startActivity(intent1);
            }
        });

        //设置清除按钮，清空边框内容
        EditText change_pwd = findViewById(R.id.change_pwd);
        EditText change_up_pwd = findViewById(R.id.change_up_pwd);

        new SpareTool().setBtnClear(change_pwd);
        new SpareTool().setBtnClear(change_up_pwd);

        Button change_endure_pwd = findViewById(R.id.btn_change_endure);
        change_endure_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_input = change_pwd.getText().toString().trim();
                String second_input = change_up_pwd.getText().toString().trim();

                if(first_input.isEmpty() || second_input.isEmpty())
                {
                    spareTool.showToast(ChangePwdActivity.this, MessageConstant.INPUT_PWD);
                    return;
                }

                if(!first_input.equals(second_input))
                {
                    spareTool.showToast(ChangePwdActivity.this, MessageConstant.PWD_DIFFERENT);
                    return;
                }

                AdminDao adminDao = new AdminDao();
                int res = adminDao.changePassword(account,first_input);
                if(res == 0)
                {
                    spareTool.showToast(ChangePwdActivity.this, MessageConstant.PWD_CHANGE_FAIL);
                }
                else if(res >= 1)
                {
                    spareTool.showToast(ChangePwdActivity.this, MessageConstant.PWD_CHANGE_SUCCESS);
//                    Intent intent1 = new Intent(ChangePwdActivity.this,ManageActivity.class);
//                    intent1.putExtra("account",account);
//                    startActivity(intent1);
                    finish();
                }
                else
                {
                    spareTool.showToast(ChangePwdActivity.this, MessageConstant.UNKNOWN_ERROR);
                }
            }
        });
    }
}