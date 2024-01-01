package gpnu.zhoujie.learnnote.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBUtils extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_note.db";       //数据库名字

    public static SQLiteDatabase db = null;

    private static final int DATABASE_VERSION = 5;

    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,null);
    }

    public DBUtils(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBUtils(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }


    /**
     * 数据库初始化
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("PRAGMA foreign_keys = false");

        db.execSQL("drop table if exists d_admin");
        //建立管理员
        db.execSQL("CREATE TABLE d_admin (" +
                "s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_sex varchar(20)," +
                "s_phone varchar(20)," +
                "s_age varchar(20)"  +
                ")");

        db.execSQL("INSERT INTO d_admin VALUES ('admin','123456','方步亭','男','13546251629','21');");
        db.execSQL("INSERT INTO d_admin VALUES ('root','123456','小云','女','13546252089','18');");


        //建立日记表
        db.execSQL("drop table if exists d_note");

        db.execSQL("CREATE TABLE d_note (" +
                "s_uid varchar(50) primary key," +
                "s_account varchar(255)," +
                "s_title varchar(255)," +
                "s_time varchar(40)," +
                "s_note text" +
                ")");


        db.execSQL("INSERT INTO d_note VALUES ('201544','root','小美好','2023年11月11日 下午 12:01 | 12字','这是一个非常感人的故事，故事讲述了...');");
        db.execSQL("INSERT INTO d_note VALUES ('515154','root','平凡而伟大','2023年11月11日 下午 12:01 | 12字','东方欲知晓，莫道君行早');");


        db.execSQL("drop table if exists d_avatar");

        db.execSQL("CREATE TABLE d_avatar (" +
                "s_account varchar(50) primary key," +
                "s_avatar varchar(255)" +
                ")");

        db.execSQL("PRAGMA foreign_keys = true");

    }


    /**
     * 数据库更新
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS d_admin");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS d_note");

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
