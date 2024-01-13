package gpnu.zhoujie.learnnote.activity_fold;

import static android.text.Selection.getSelectionEnd;
import static android.text.Selection.getSelectionStart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gpnu.zhoujie.learnnote.R;
import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.dao.AdminDao;
import gpnu.zhoujie.learnnote.listen.ChangeListen;
import gpnu.zhoujie.learnnote.utils.ImageInsert;
import gpnu.zhoujie.learnnote.utils.ScreenUtils;
import gpnu.zhoujie.learnnote.utils.SpareTool;

public class AddNoteActivity extends AppCompatActivity {

    public String uuid = null;  //生成一个随机ID

    public String account_id = null;    //绑定用户

    public EditText addNoteTitle = null;

    public TextView addNoteTime = null;

    static final int IMAGE_CODE = 99;
    private static final int PICK_IMAGE_REQUEST = 1;
    public EditText addNoteMsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //加载菜单条
        Toolbar toolbar = findViewById(R.id.toolbar_add_note);
        setSupportActionBar(toolbar);

        //获取账号
        Intent intent = getIntent();



        //时间根据下方的文更新自动刷新
        addNoteTitle = findViewById(R.id.add_note_title);  //标题
        addNoteTime = findViewById(R.id.add_note_time);    //时间
        addNoteMsg = findViewById(R.id.add_note_text);     //内容


        //判断是添加界面还是修改界面
        account_id = intent.getStringExtra("account");
        String sta = intent.getStringExtra("sta");

        //添加界面
        if(sta == null)
        {
            uuid = UUID.randomUUID().toString();
        }
        else    //编辑界面
        {
            uuid = intent.getStringExtra("uuid");
            addNoteTitle.setText(intent.getStringExtra("title"));
            addNoteTime.setText(intent.getStringExtra("time"));

            if(intent.getStringExtra("note") != null && getNote(intent.getStringExtra("note")))
            {
                Log.i("笔记内容为：",intent.getStringExtra("note")+" 解析成功");
            }
            else
            {
                addNoteMsg.setText(intent.getStringExtra("note"));
                Log.i("图片加载失败","PATH不对应");
            }

        }


        addNoteMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //获取当前月份和时间
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY年MM月dd日 aa HH:mm", Locale.CHINESE);
                String curTime = dateFormat.format(calendar.getTime());

                curTime += "  |  ";

                String text_t = addNoteMsg.getText().toString();
                String len = String.valueOf(text_t.length());
                curTime += len + "字";

                addNoteTime.setText(curTime);

                String title_t = addNoteTitle.getText().toString();

                int res = new AdminDao().saveNote(uuid, account_id, title_t, curTime, text_t);


            }
        });

        // 编辑内容
        // addNoteMsg

        findViewById(R.id.add_note_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGallery();
            }
        });


    }



    public boolean getNote(String note)
    {
        Pattern p = Pattern.compile("<img\\s+src\\s*=\\s*\"([^\"]+)\"\\s*/?>");
        Matcher m = p.matcher(note);

        // 创建一个SpannableString
        SpannableString spannable = new SpannableString(" "+note);

        while(m.find())
        {
            Log.i("截取到内容",m.group(0));
            String s = m.group();
            int start = m.start();
            int end = m.end();
            int width = ScreenUtils.getScreenWidth(AddNoteActivity.this);
            int height = ScreenUtils.getScreenHeight(AddNoteActivity.this);

            String path = s.replaceAll("\\<img src=\"|\"\\/\\>","").trim();

            Log.i("原始字符",s);
            Log.i("Path长度为：",String.valueOf(path.length()));
            Log.i("处理Path后",path);


            Bitmap bitmap = new ImageInsert().getSmallBitmap(path,480,500);
//            // 加载图片并创建一个Bitmap
//            Bitmap bitmap = BitmapFactory.decodeFile(path);

            if(bitmap == null)
            {
                Log.i("获取失败","图片PATH解析失败");
                return false;
            }

//            // 调整图片大小
//            int targetWidth = 100; // 设置目标宽度
//            int targetHeight = (int) (bitmap.getHeight() * (targetWidth / (double) bitmap.getWidth()));

            // 将Bitmap转换为Drawable
//            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true));

            ImageSpan imageSpan = new ImageSpan(this, bitmap);
            spannable.setSpan(imageSpan,start,end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        }
        addNoteMsg.setText(spannable);
        addNoteMsg.append(" ");
        return true;
    }



    //region 调用图库
    private void callGallery(){
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum,IMAGE_CODE);
    }
    //endregion



    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //参考网址：
        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        if(requestCode == IMAGE_CODE){
            try{
//                // 获得图片的uri
//                Uri originalUri = data.getData();
//                bm = MediaStore.Images.Media.getBitmap(resolver,originalUri);
//
//
//                String[] proj = {MediaStore.Images.Media.DATA};
//                // 好像是android多媒体数据库的封装接口，具体的看Android文档
//                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
//                // 按我个人理解 这个是获得用户选择的图片的索引值
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//                Log.i("获取到的图片信息：",String.valueOf(column_index));
//                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
//                cursor.moveToFirst();
//                // 最后根据索引值获取图片路径
//                String path = cursor.getString(column_index);

                Uri uri = data.getData();

                //指定解析出来的值，路径和大小，因为我是需要上传文件的，但是也不能过大，所以只要这两个值，需要其他的自行设定，都是在这个数组内设置，这是视频，图片直接更改Video为Image(MediaStore.Image.Media.DATA)
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                cursor.moveToFirst();

                String path = cursor.getString(0); // 图片文件路径

                Log.i("上传图片","上传的图片:"+path);
                insertImg(path);
                //Toast.makeText(AddFlagActivity.this,path,Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(AddNoteActivity.this,"图片插入失败",Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void addImageToEditText(Bitmap bitmap) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        Editable editable = addNoteMsg.getEditableText();
        builder.append(addNoteMsg.getText());
        if (editable != null) {
            int cursorPosition = addNoteMsg.getSelectionStart();

            builder.append(" "); // 添加一个空格，确保图片后面有一些文本，否则图片可能无法显示

//            // 设置目标宽高比例
//            float targetAspectRatio = 1.5f; // 替换为你的目标宽高比例
//            // 计算目标宽度和高度
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            if (width > height) {
//                // 图片宽度大于高度
//                height = (int) (width / targetAspectRatio);
//            } else {
//                // 图片高度大于宽度
//                width = (int) (height * targetAspectRatio);
//            }
//            bitmap = Bitmap.createScaledBitmap(bitmap, , height, true);

            ImageSpan imageSpan = new ImageSpan(this, bitmap);


            builder.setSpan(imageSpan, builder.length() - 1, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(" "); // 添加一个空格，确保图片后面有一些文本，否则图片可能无法显示
            addNoteMsg.setText(builder, EditText.BufferType.SPANNABLE);
        }

        printImageInfo();
    }



    public void printImageInfo()
    {
        Editable editable = addNoteMsg.getText();
        ImageSpan[] imageSpans = editable.getSpans(0, editable.length(), ImageSpan.class);

        for (ImageSpan imageSpan : imageSpans) {
            int start = editable.getSpanStart(imageSpan);
            int end = editable.getSpanEnd(imageSpan);

            // 获取图片的Bitmap
            BitmapDrawable drawable = (BitmapDrawable) imageSpan.getDrawable();
            Bitmap imageBitmap = drawable.getBitmap();

            // 处理你想要执行的操作，比如显示图片信息或者进一步处理图片

            // 输出图片信息示例
            Log.d("ImageInfo", "Start: " + start + ", End: " + end +
                    ", Image Width: " + imageBitmap.getWidth() +
                    ", Image Height: " + imageBitmap.getHeight());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_note_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if(itemId == R.id.add_note_save)
        {
            String curTime = addNoteTime.getText().toString().trim();
            String title_t = addNoteTitle.getText().toString().trim();
            String msg = addNoteMsg.getText().toString().trim();
            int res = new AdminDao().saveNote(uuid,account_id,title_t,curTime,msg);

            if(res == 1)
            {
                new SpareTool().showToast(AddNoteActivity.this,MessageConstant.NOTE_SAVE_SUCCESS);
            }
            else
            {
                new SpareTool().showToast(AddNoteActivity.this,MessageConstant.NOTE_SAVE_FAIL);
            }
        }
        else if(itemId == R.id.add_note_del)
        {
            int res = new AdminDao().delNote(uuid,account_id);
            if(res == 1)
            {
                new SpareTool().showToast(AddNoteActivity.this, MessageConstant.NOTE_DEL_SUCCESS);
            }
            else
            {
                new SpareTool().showToast(AddNoteActivity.this, MessageConstant.NOTE_DEL_FAIL);
            }
        }

        Intent intent = new Intent(AddNoteActivity.this,ManageActivity.class);
        intent.putExtra("account",account_id);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    //region 插入图片
    private void insertImg(String path){
        String tagPath = "<img src=\""+path+"\"/>";//为图片路径加上<img>标签
        Log.i("添加标签",tagPath);
        Bitmap bitmap = null;
        if(Environment.isExternalStorageManager())
        {
            bitmap = BitmapFactory.decodeFile(path);
        }
        else
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            startActivity(intent);
        }

        if(bitmap != null){
            SpannableString ss = getBitmapMime(path,tagPath);
            insertPhotoToEditText(ss);
            addNoteMsg.append("\n");
            Log.d("YYPT", addNoteMsg.getText().toString());
        }
    }
    //endregion

    //region 将图片插入到EditText中
    private void insertPhotoToEditText(SpannableString ss){
        Editable et = addNoteMsg.getText();
        int start = addNoteMsg.getSelectionStart();
        et.insert(start,ss);
        addNoteMsg.setText(et);
        addNoteMsg.setSelection(start+ss.length());
        addNoteMsg.setFocusableInTouchMode(true);
        addNoteMsg.setFocusable(true);
    }
    //endregion

    private SpannableString getBitmapMime(String path,String tagPath) {
        SpannableString ss = new SpannableString(tagPath);//这里使用加了<img>标签的图片路径

        int width = ScreenUtils.getScreenWidth(AddNoteActivity.this);
        int height = ScreenUtils.getScreenHeight(AddNoteActivity.this);



        Bitmap bitmap = new ImageInsert().getSmallBitmap(path,480,500);
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        ss.setSpan(imageSpan, 0, tagPath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private void initContent(String content){
        //input是获取将被解析的字符串
        String input = content;
        //将图片那一串字符串解析出来,即<img src=="xxx" />
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);

        //使用SpannableString了，这个不会可以看这里哦：http://blog.sina.com.cn/s/blog_766aa3810100u8tx.html#cmt_523FF91E-7F000001-B8CB053C-7FA-8A0
        SpannableString spannable = new SpannableString(input);
        while(m.find()){
            //Log.d("YYPT_RGX", m.group());
            //这里s保存的是整个式子，即<img src="xxx"/>，start和end保存的是下标
            String s = m.group();
            int start = m.start();
            int end = m.end();
            //path是去掉<img src=""/>的中间的图片路径
            String path = s.replaceAll("\\<img src=\"|\"\\/>","").trim();
            //Log.d("YYPT_AFTER", path);

            //利用spannableString和ImageSpan来替换掉这些图片
            int width = ScreenUtils.getScreenWidth(AddNoteActivity.this);
            int height = ScreenUtils.getScreenHeight(AddNoteActivity.this);

            Bitmap bitmap = new ImageInsert().getSmallBitmap(path,width,480);
            ImageSpan imageSpan = new ImageSpan(this, bitmap);
            spannable.setSpan(imageSpan,start,end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        addNoteMsg.setText(spannable);
        addNoteMsg.append("\n");
        //Log.d("YYPT_RGX_SUCCESS",content.getText().toString());
    }

}