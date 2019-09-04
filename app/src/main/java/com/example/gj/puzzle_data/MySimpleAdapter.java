package com.example.gj.puzzle_data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

/**
 * Created by gj
 * Created on 8/26/19
 * Description
 */

public class MySimpleAdapter extends SimpleAdapter {
    private Context context;
    private List<Map<String, Object>> lists;
    private SQLiteDatabase sqLiteDatabase;
    private MyDatabaseHelper myDatabaseHelper;

    public MySimpleAdapter(Context context, List<Map<String, Object>> data, int resource, String[] form, int[] to) {
        super(context, data, resource, form, to);
        this.context = context;
        this.lists = data;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        myDatabaseHelper = new MyDatabaseHelper(context, "play.db", 1);
        sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        TextView del = view.findViewById(R.id.del);
        final TextView pid = view.findViewById(R.id.pid);
        del.setTag(this.getItem(position));
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(pid.getText().toString().trim());
                lists.remove(position);
                sqLiteDatabase.execSQL("delete from player where id="+ id +"");
                notifyDataSetChanged();
                Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
