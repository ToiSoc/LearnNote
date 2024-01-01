package gpnu.zhoujie.learnnote.activity_fold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.entity.AvatarImage;
import gpnu.zhoujie.learnnote.utils.SpareTool;

public class AvatarChangeActivity extends AppCompatActivity {

    private ImageView selectAvatarImage = null;
    private View borderView = null;
    private ImageView avatarMainImage = null;
    private SpareTool spareTool = new SpareTool();
    private String account_id = null;
    private String image_id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_change);

        Intent intent = getIntent();
        avatarMainImage = findViewById(R.id.avatar_main_image);
        account_id = intent.getStringExtra("account");
        image_id = intent.getStringExtra("image_id");

        if(image_id != null)
        {
            int imageId = getResources().getIdentifier("avatar_"+image_id,"drawable",getPackageName());
            Log.i("资源名",String.valueOf(imageId));
            avatarMainImage.setImageResource(imageId);
        }
        else
        {
            avatarMainImage = findViewById(R.id.avatar_main_image);
        }


        Button btn_change_avatar_image = findViewById(R.id.btn_change_avatar);
        btn_change_avatar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(avatarMainImage == null)
                {
                    return;
                }

                String resourceEntryName = getResources().getResourceEntryName(selectAvatarImage.getId());
                String num = resourceEntryName.substring(7,9);
                Log.i("图片ID",num);
                int statusId = new AdminDao().saveAvatarId(account_id,String.valueOf(num));

                switch(statusId)
                {
                    case 0:
                        spareTool.showToast(AvatarChangeActivity.this, MessageConstant.AVATAR_NOT_UPDATE);
                        finish();
                        break;
                    case 1:
                        spareTool.showToast(AvatarChangeActivity.this, MessageConstant.AVATAR_UPDATE_SUCCESS);
                        finish();
                        break;
                    default:
                        spareTool.showToast(AvatarChangeActivity.this, MessageConstant.MESSAGE_UPDATE_FAIL);
                        break;
                }

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_avatar_change_to_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    public void change_avatar_image(View view)
    {
        setHighlight(view);
    }

    private void setHighlight(View imageView) {
        // 设置边框高亮
        String resourceName = "view_";
        String resName = getResources().getResourceEntryName(imageView.getId());
        String num = resName.substring(7,9);
        Log.i("num:",num);
        resourceName += num;

        int viewName = getResources().getIdentifier(resourceName,"id",getPackageName());

        View view = findViewById(viewName);
        view.setVisibility(View.VISIBLE);


        if (imageView != selectAvatarImage) {
            //点击了当前已高亮的 ImageView，可以执行相关操作
            //取消高亮
            removeHighlight();
            selectAvatarImage = (ImageView) imageView;
            avatarMainImage.setImageDrawable(((ImageView) imageView).getDrawable());
            borderView = view;
        }
    }

    private void removeHighlight() {
        // 取消高亮
        if(selectAvatarImage != null)
        {
            borderView.setVisibility(View.INVISIBLE);
        }

        // 重置当前选定的 ImageView
        selectAvatarImage = null;
        borderView = null;
    }
}