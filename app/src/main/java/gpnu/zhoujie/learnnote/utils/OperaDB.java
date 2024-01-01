package gpnu.zhoujie.learnnote.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gpnu.zhoujie.learnnote.entity.PersonMsg;

public class OperaDB {

    String admin_table_name = "d_admin";

    String note_table_name = "d_note";

    public Cursor selectCountById(SQLiteDatabase db, String ...data)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + admin_table_name + " WHERE s_id=? and s_pwd=?",data);
        return cursor;
    }

    public int selectCountUuidAccount(SQLiteDatabase db, String ...data)
    {
        Log.i("查询执行",data[0] + " " + data[1]);
        Cursor cursor = db.rawQuery("SELECT * FROM " + note_table_name + " WHERE s_uid=? and s_account=?", data);
        int count = 0;
        while(cursor.moveToNext())
        {
            String uuid = cursor.getString(0);
            String account = cursor.getString(1);
            String title = cursor.getString(2);
            String time = cursor.getString(3);
            String note = cursor.getString(4);

            String info = "\n\nUUID：" + uuid + "\n\t账号：" +  account + "\n\t标题：" + title
                    + "\n\t时间：" + time + "\n\t笔记：" + note + "\n\n";
            Log.i("查询数据",info);

            count ++;
        }
        return count;
    }

    public void showInfoAll(SQLiteDatabase db,String noteName)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + admin_table_name, null);
        while(cursor.moveToNext()) {
            String uuid = cursor.getString(0);
            String account = cursor.getString(1);
            String title = cursor.getString(2);
            String time = cursor.getString(3);
            String note = cursor.getString(4);

            String info = "\n[----------------++++-----------------]\n[----------------++++-----------------]\n" +
                    "\tUUID：" + uuid + "\t账号：" +  account + "\t标题：" + title
                    + "\n\t时间：" + time + "\t笔记：" + note + "\n[----------------++++-----------------]" +
                    "\n[----------------++++-----------------]\n";
            Log.i("查询数据", info);
        }
    }

    public void showInfoAll(SQLiteDatabase db)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + admin_table_name, null);
        while(cursor.moveToNext()) {
            String account = cursor.getString(0);
            String pwd = cursor.getString(1);
            String name = cursor.getString(2);
            String sex = cursor.getString(3);
            String phone = cursor.getString(4);
            String age = cursor.getString(5);

            String info = "\n[----------------++++-----------------]\n[----------------++++-----------------]\n\t账号："
                    + account + "\t密码：" + pwd + "\t姓名：" + name
                    + "\n\t性别：" + sex + "\t电话：" + phone + "\t年龄："
                    + age + "\n[----------------++++-----------------]\n\n[----------------++++-----------------]";
            Log.i("查询数据", info);
        }
    }

    public List<PersonMsg> selectList(SQLiteDatabase db, String id)
    {
//        Log.i("查询执行",id);
        Cursor cursor = db.rawQuery("SELECT * FROM " + admin_table_name + " WHERE s_id=?", new String[]{id});
        List<PersonMsg> list = new ArrayList<>();
        while(cursor.moveToNext())
        {
            String account = cursor.getString(0);
            String pwd = cursor.getString(1);
            String name = cursor.getString(2);
            String sex = cursor.getString(3);
            String phone = cursor.getString(4);
            String age = cursor.getString(5);

            String info = "账号：" + account + " 密码：" +  pwd + " 姓名：" + name
                    + " 性别：" + sex + " 电话：" + phone + " 年龄：" + age;
            Log.i("查询数据",info);

            PersonMsg personMsg = new PersonMsg(account,pwd,name,sex,phone,age);
            list.add(personMsg);
        }
        return list;
    }

}
