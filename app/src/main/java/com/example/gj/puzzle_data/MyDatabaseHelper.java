package com.example.gj.puzzle_data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by gj
 * Created on 8/25/19
 * Description
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_SQL = "create table if not exists player (id integer PRIMARY KEY AUTOINCREMENT " +
            "NOT NULL,name text,score integer)";

    private Context mcontext;

    //用于初次使用软件生成数据库
    public MyDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        Toast.makeText(mcontext,"创建成功", Toast.LENGTH_SHORT).show();
    }

    //用于升级软件更新数据库表结构
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
