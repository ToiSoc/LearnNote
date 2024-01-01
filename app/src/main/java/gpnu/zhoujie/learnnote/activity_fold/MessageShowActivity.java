package gpnu.zhoujie.learnnote.activity_fold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.entity.PersonMsg;
import gpnu.zhoujie.learnnote.utils.SpareTool;

public class MessageShowActivity extends AppCompatActivity {

    public AdminDao adminDao = new AdminDao();

    public SpareTool spareTool = new SpareTool();

    public String account_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_show);

        Intent intent = getIntent();
        account_id = intent.getStringExtra("account");

        Toolbar toolbar = findViewById(R.id.toolbar_show_msg_to_home);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1 = new Intent(MessageShowActivity.this, ManageActivity.class);
//                intent1.putExtra("account",account_id);
//                startActivity(intent1);
                finish();
            }
        });

        TextView show_msg_name = findViewById(R.id.show_msg_name_info);
        TextView show_msg_sex = findViewById(R.id.show_msg_sex_info);
        TextView show_msg_phone = findViewById(R.id.show_msg_phone_info);
        TextView show_msg_age = findViewById(R.id.show_msg_age_info);

        List<PersonMsg> list = adminDao.getMessage(account_id);

        if(list.isEmpty())
        {
            spareTool.showToast(MessageShowActivity.this, MessageConstant.UNKNOWN_ERROR);
            return;
        }
        PersonMsg personMsg = list.get(0);
        show_msg_name.setText(personMsg.getName());
        show_msg_sex.setText(personMsg.getSex());
        show_msg_phone.setText(personMsg.getPhone());
        show_msg_age.setText(personMsg.getAge());

        Button btn_show_to_change_msg = findViewById(R.id.btn_show_to_change_msg);
        btn_show_to_change_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MessageShowActivity.this, ChangeMessageActivity.class);
                intent1.putExtra("account",account_id);
                startActivity((intent1));
            }
        });


    }
}