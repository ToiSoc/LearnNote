package gpnu.zhoujie.learnnote.activity_fold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import gpnu.zhoujie.learnnote.MainActivity;
import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.adapter.NoteAdapter;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.entity.AvatarImage;
import gpnu.zhoujie.learnnote.entity.Note;
import gpnu.zhoujie.learnnote.entity.PersonMsg;

public class ManageActivity extends AppCompatActivity {

    private ListView listView = null;

    private List<Note> noteList = null;

    private NoteAdapter noteAdapter = null;
    private String pre_account = null;

    private long lastClickTime = 0;
    private int backgroundNum = 1;
    private ImageView head_menu_image = null;
    private String image_id = null;
    private String sex_s = null;
    private int num = new Random().nextInt(12);

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        Intent intent = getIntent();
        pre_account = intent.getStringExtra("account");

        //退出功能
        NavigationView navigationView = findViewById(R.id.navigation_view); //引入侧边栏属性
        View headerView = navigationView.getHeaderView(0);
        head_menu_image = headerView.findViewById(R.id.head_menu_image);

        TextView head_menu_name = headerView.findViewById(R.id.head_menu_name);
        TextView head_menu_time = headerView.findViewById(R.id.head_menu_time);


        List<PersonMsg> personMsgList = new AdminDao().getMessage(pre_account);
        sex_s = personMsgList.get(0).getSex();
        String name_s = personMsgList.get(0).getName();
        //姓名
        head_menu_name.setText(name_s);

        //时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 aa HH:mm", Locale.CHINESE);
        String time_s = simpleDateFormat.format(calendar.getTime());
        head_menu_time.setText(time_s);


        List<AvatarImage> avatarImageList = new AdminDao().getAvatarId(pre_account);
        if(avatarImageList.isEmpty() || avatarImageList.get(0).getImageId().isEmpty())
        {
            //头像
            if(sex_s.equals("男"))
            {
                head_menu_image.setImageResource(R.drawable.boy);
            }
            else
            {
                head_menu_image.setImageResource(R.drawable.girls);
            }
        }
        else
        {
            int imageId = getResources().getIdentifier("avatar_"+avatarImageList.get(0).getImageId(),"drawable",getPackageName());
            Log.i("资源名",String.valueOf(imageId));
            image_id=avatarImageList.get(0).getImageId();
            head_menu_image.setImageResource(imageId);

        }


        RelativeLayout relativeLayout = headerView.findViewById(R.id.head_menu_relative);

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch(action)
                {
                    case MotionEvent.ACTION_DOWN:
                        long clickTime = System.currentTimeMillis();
                        if (clickTime - lastClickTime < 500)
                        {
                            //双击事件
                            changeBackground(relativeLayout,backgroundNum);
                        }
                        else
                        {
                            lastClickTime = clickTime;
                        }
                        break;

                }
                return false;
            }
        });


        head_menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ManageActivity.this,AvatarChangeActivity.class);
                intent1.putExtra("account",pre_account);
                intent1.putExtra("image_id",image_id);
                startActivity(intent1);
                overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                Log.i("是否拿到ID",pre_account);

                if(id == R.id.menu_to_exit)
                {
                    finishAffinity();
                }

                if(id == R.id.menu_to_login)
                {
                    Intent intent1 = new Intent(ManageActivity.this, MainActivity.class);
                    intent1.putExtra("account",pre_account);
                    startActivity(intent1);
                }

                if(id == R.id.menu_mes)
                {
                    Intent intent1 = new Intent(ManageActivity.this, MessageShowActivity.class);
                    intent1.putExtra("account",pre_account);
                    startActivity(intent1);
                }

                if(id == R.id.menu_update_pwd)
                {
                    Intent intent1 = new Intent(ManageActivity.this, ChangePwdActivity.class);
                    intent1.putExtra("account",pre_account);
                    startActivity(intent1);
                }

                return true;
            }
        });

//        Animation scaleDownAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_down)
//
        //添加事件按钮
        FloatingActionButton floatingActionButton = findViewById(R.id.float_btn_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ManageActivity.this, AddNoteActivity.class);
                intent1.putExtra("account",pre_account);
                intent1.putExtra("image_id",image_id);
                startActivity(intent1);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_manage);
        this.setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_manage);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        //加载列表
        listView = findViewById(R.id.manage_show_list);
        noteList = new AdminDao().getListNote(pre_account);

        if(noteList.isEmpty())
        {
            listView.setAdapter(null);      //空就不显示
        }
        else
        {
            noteAdapter = new NoteAdapter(this, noteList);
            listView.setAdapter(noteAdapter);
        }



    }

    public void changeBackground(RelativeLayout relativeLayout,int backgroundNum)
    {

        Log.i("点击背景",String.valueOf(backgroundNum));

        Log.i("数字",String.valueOf(num));
        switch(num)
        {
            case 1:
                relativeLayout.setBackgroundResource(R.drawable.back_00);
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.drawable.back_01);
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.drawable.back_02);
                break;
            case 4:
                relativeLayout.setBackgroundResource(R.drawable.dkkjpg);
                break;
            case 5:
                relativeLayout.setBackgroundResource(R.drawable.back_04);
                break;
            case 6:
                relativeLayout.setBackgroundResource(R.drawable.back_06);
                break;
            case 7:
                relativeLayout.setBackgroundResource(R.drawable.back_07);
                break;
            case 8:
                relativeLayout.setBackgroundResource(R.drawable.back_08);
                break;
            case 9:
                relativeLayout.setBackgroundResource(R.drawable.back_09);
                break;
            case 10:
                relativeLayout.setBackgroundResource(R.drawable.back_10);
                break;
            case 11:
                relativeLayout.setBackgroundResource(R.drawable.back_11);
                break;
        }
        num = (num + 1)%12;

    }

    protected void onResume() {
        super.onResume();
        List<AvatarImage> avatarImageList = new AdminDao().getAvatarId(pre_account);
        if(avatarImageList.isEmpty())
        {
            //头像
            if(sex_s.equals("男"))
            {
                head_menu_image.setImageResource(R.drawable.boy);
            }
            else
            {
                head_menu_image.setImageResource(R.drawable.girls);
            }
        }
        else
        {
            int imageId = getResources().getIdentifier("avatar_"+avatarImageList.get(0).getImageId(),"drawable",getPackageName());
            Log.i("资源名",String.valueOf(imageId));

            head_menu_image.setImageResource(imageId);
        }
    }
}