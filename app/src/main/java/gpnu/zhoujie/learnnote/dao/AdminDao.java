package gpnu.zhoujie.learnnote.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gpnu.zhoujie.learnnote.constant.MessageConstant;
import gpnu.zhoujie.learnnote.dao.impl.AdminDaoImpl;
import gpnu.zhoujie.learnnote.entity.AvatarImage;
import gpnu.zhoujie.learnnote.entity.Note;
import gpnu.zhoujie.learnnote.entity.PersonMsg;
import gpnu.zhoujie.learnnote.utils.DBUtils;
import gpnu.zhoujie.learnnote.utils.OperaDB;

public class AdminDao implements AdminDaoImpl {

    SQLiteDatabase db = DBUtils.db;

    public static OperaDB operaDB = new OperaDB();

    public String userTableName = "d_admin";

    public String noteTableName = "d_note";

    public String avatarTableName = "d_avatar";

    /**
     * 注册
     * @param data
     * @return
     */
    public int registerAdmin(String ...data) {
        try
        {
            StringBuffer stringBuffer = new StringBuffer();
            for(String i:data)
            {
                stringBuffer.append(i + ' ');
            }
            Log.i("账号注册：",stringBuffer.toString());
            db.execSQL("INSERT INTO " + userTableName + " VALUES(?,?,?,?,?,?)",data);
            return 1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 登录
     * @param data
     * @return
     */
    public int loginAdmin(String ...data) {
        Cursor cursor = operaDB.selectCountById(db,data);
//        operaDB.selectList(db, null);
        int count = 0;
        while(cursor.moveToNext())
        {
            count ++;
        }

        if(count == 0)
        {
            return MessageConstant.COUNT_ZERO_ERR;
        }

        return 200;
    }


    /**
     * 修改密码
     * @param data
     * @return
     */
    public int changePassword(String ...data) {
        Log.i("修改密码",data[1]);
        ContentValues values = new ContentValues();
        values.put("s_pwd",data[1]);
        new OperaDB().showInfoAll(db);
        try{
            int res = db.update(userTableName,values,"s_id=?",new String[]{data[0]});
            new OperaDB().showInfoAll(db);
            return res;
        }
        catch (Exception e){
            return -1;
        }
    }

    /**
     * 修改个人信息
     * @param id
     * @param values
     * @return
     */
    public int changeMessage(String id, ContentValues values) {

        Log.i("修改个人信息",String.valueOf(values.keySet()) + " id:" + id);
        try
        {
            int res = db.update(userTableName,values,"s_id=?",new String[]{id});
            new OperaDB().showInfoAll(db);
            return res;
        }
        catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取个人信息
     * @param id
     * @return
     */
    public List<PersonMsg> getMessage(String id)
    {
        return operaDB.selectList(db,id);
    }

    /**
     * 删除笔记
     * @param data
     * @return
     */
    public int delNote(String... data) {
        Log.i("删除笔记","用户：" + data[1] + " uuid：" + data[0]);
        try
        {
            db.execSQL("delete from " + noteTableName+" where s_uid=? and s_account=?",data);
            return 1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 保存笔记
     * @param data
     * @return
     */
    public int saveNote(String... data) {   //  s_uid s_account s_title s_time s_note
        String s = "";
        for(String item:data)
        {
            s += item + " ";
        }
        Log.i("保存笔记",s);


        operaDB.showInfoAll(db,"note");

        int num = operaDB.selectCountUuidAccount(db, new String[]{data[0],data[1]});
        Log.i("查询到数目",String.valueOf(num));
        try{
            if(num > 0)
            {
                //  s_title s_time s_note s_uid s_account
                String newData[] = {data[2],data[3],data[4],data[0],data[1]};
                db.execSQL("UPDATE " + noteTableName + " SET s_title=? , s_time=? , s_note=? where s_uid=? and s_account=?",newData);
            }
            else
            {
                db.execSQL("INSERT INTO " + noteTableName + " VALUES (?,?,?,?,?);",data);
            }
            operaDB.showInfoAll(db,"note");
            return 1;
        }catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * 读取数据
     * @param account
     * @return
     */
    public List<Note> getListNote(String account) {
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + noteTableName + " where s_account=?",new String[]{account});
        while(cursor.moveToNext())
        {
            String uuid_s = cursor.getString(0);
            String account_s = cursor.getString(1);
            String title_s = cursor.getString(2);
            String time_s = cursor.getString(3);
            String note_s = cursor.getString(4);
            Note note = new Note(uuid_s,account_s,title_s,time_s,note_s);
            noteList.add(note);
        }
        return noteList;
    }

    /**
     * 获取笔记信息
     * @param id
     * @return
     */
    public List<Note> showNote(String id)
    {
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + noteTableName + " where s_uid=?",new String[]{id});
        while(cursor.moveToNext())
        {
            String uuid_s = cursor.getString(0);
            String account_s = cursor.getString(1);
            String title_s = cursor.getString(2);
            String time_s = cursor.getString(3);
            String note_s = cursor.getString(4);
            Note note = new Note(uuid_s,account_s,title_s,time_s,note_s);
            noteList.add(note);
        }
        return noteList;
    }

    /**
     * 获取图标信息
     * @param
     * @return
     */
    public List<AvatarImage> getAvatarId(String account)
    {
        List<AvatarImage> avatarImageList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + avatarTableName + " where s_account=?",new String[]{account});
        while(cursor.moveToNext())
        {
            String account_s = cursor.getString(0);
            String avatar_s = cursor.getString(1);
            AvatarImage avatarImage = new AvatarImage(account,avatar_s);
            avatarImageList.add(avatarImage);
        }
        return avatarImageList;
    }


    /**
     * 保存图标信息
     * @param
     * @return
     */
    public int saveAvatarId(String account,String imageId)
    {
        List<AvatarImage> avatarImageList = getAvatarId(account);
        if(avatarImageList.isEmpty())
        {
            db.execSQL("INSERT INTO " + avatarTableName + " VALUES (?,?);",new String[]{account,imageId});
        }
        else
        {
            db.execSQL("UPDATE " + avatarTableName + " SET s_avatar=? where s_account=?",new String[]{imageId,account});
        }
        return 1;
    }

}

