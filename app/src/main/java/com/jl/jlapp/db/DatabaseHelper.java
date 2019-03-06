package com.jl.jlapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 柳亚婷 on 2018/1/19 0019.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DB_NAME="history_search";
    // 数据库版本号
    private static Integer Version = 1;
    public static final String ID="nameId";
    public static final String NAME="name";


    /**
     * 构造函数
     * 在SQLiteOpenHelper的子类中，必须有该构造函数
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        // 参数说明
        // context：上下文对象
        // name：数据库名称
        // param：一个可选的游标工厂（通常是 Null）
        // version：当前数据库的版本，值必须是整数并且是递增的状态

        // 必须通过super调用父类的构造函数
        super(context, name, factory, version);
    }
    public DatabaseHelper(Context context){
        this(context,DB_NAME,null,Version);
    }

    /**
     * 复写onCreate（）
     * 调用时刻：当数据库第1次创建时调用
     * 作用：创建数据库 表 & 初始化数据
     * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库1张表
        // 通过execSQL（）执行SQL语句（此处创建了1个名为history_search的表）
        String sql = "create table "+DB_NAME+"(nameId Integer primary key autoincrement,name nvarchar(64))";
        db.execSQL(sql);

        // 注：数据库实际上是没被创建 / 打开的（因该方法还没调用）
        // 直到getWritableDatabase() / getReadableDatabase() 第一次被调用时才会进行创建 / 打开
    }

    /**
     * 复写onUpgrade（）
     * 调用时刻：当数据库升级时则自动调用（即 数据库版本 发生变化时）
     * 作用：更新数据库表结构
     * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本, 若该版本高于之前版本, 就调用onUpgrade()
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 参数说明：
        // db ： 数据库
        // oldVersion ： 旧版本数据库
        // newVersion ： 新版本数据库

        // 使用 SQL的ALTER语句
        String sql = "alter table  "+DB_NAME+" add sex varchar(8)";
        db.execSQL(sql);
    }

    public void add(SQLiteDatabase db,String name){
        ContentValues content=new ContentValues();
        content.put(NAME, name);

        db.insert(DB_NAME, null, content);
    }
    public void delete(SQLiteDatabase db){
        //int i=db.delete(DB_NAME, NAME+"=+?", new String[] {name});
        db.delete(DB_NAME, null,null);
    }
    public void update(SQLiteDatabase db){
        ContentValues content=new ContentValues();
        content.put(NAME, "aaa");
        db.update(DB_NAME, content, NAME+"=?", new String[] {"张三"});
    }
    public Cursor select(SQLiteDatabase db){
        // asc 升序  desc 降序

        //Cursor cursor=db.query(TABLE_NAME, new String[] {STUDENTS_AGE}, STUDENTS_NAME+"=?", new String[] {"aaa"}, null, null, "desc");
	   /* while(cursor.moveToNext()){
	    	System.out.println("^^^^^^^^"+cursor.getString(cursor.getColumnIndex(STUDENTS_AGE)));
	    }*/
        Cursor cursor=db.query(DB_NAME, new String[] {ID,NAME}, null, null, null, null, null);
       /* while(cursor.moveToNext()){
            //System.out.println("^^^^^^^^"+cursor.getString(cursor.getColumnIndex(NAME)));
            Log.d("aaaaaaaaaaaa","---------------"+cursor.getString(cursor.getColumnIndex(NAME)));
        }*/
        return cursor;
    }

}
