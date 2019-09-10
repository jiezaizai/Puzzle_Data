package com.example.gj.puzzle_data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private MyDatabaseHelper myDatabaseHelper;
    public SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper = new MyDatabaseHelper(this, "play.db", 1);
        sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("排行榜");
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);
        (toolbar.findViewById(R.id.refresh_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              MainActivity.this.setAdapter();
            }
        });
        myListView = findViewById(R.id.myList);
    }

    private void setAdapter() {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from player", null);
        List<Map<String, Object>> players = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()) {
            HashMap<String, Object> playerHashMap = new HashMap<String, Object>();
            playerHashMap.put("id", cursor.getString(0));
            playerHashMap.put("name", cursor.getString(1));
            playerHashMap.put("score", cursor.getString(2));
            playerHashMap.put("difficulty", cursor.getString(3));
            players.add(playerHashMap);
        }
        cursor.close();
        if (!players.isEmpty()) {
            MySimpleAdapter mySimpleAdapter = new MySimpleAdapter(
                    this,
                    players,
                    R.layout.player_list_items,
                    new String[]{"id","name","score","difficulty"},
                    new int[]{R.id.pid,R.id.pname,R.id.pscore,R.id.pdiff}
            );
            myListView.setAdapter(mySimpleAdapter);
        }else{
            Toast.makeText(MainActivity.this,"数据库为空",Toast.LENGTH_SHORT).show();

        }
    }
}
