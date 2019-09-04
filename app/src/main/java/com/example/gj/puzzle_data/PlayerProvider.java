package com.example.gj.puzzle_data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.security.Provider;

/**
 * Created by gj
 * Created on 8/26/19
 * Description
 */

public class PlayerProvider extends ContentProvider {

    private static final int PLAYERS=1;
    private static final int PLAYER=2;
    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MyDatabaseHelper myDatabaseHelper;

    @Override
    public boolean onCreate() {
        myDatabaseHelper = new MyDatabaseHelper(this.getContext(), "play.db", 1);
        uriMatcher.addURI(Words.AUTHORITY,"players",PLAYERS);
        uriMatcher.addURI(Words.AUTHORITY,"player/#",PLAYER);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getReadableDatabase();
        return sqLiteDatabase.query("player",projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case PLAYERS:
                long rowId =sqLiteDatabase.insert("player",null,values);
                if(rowId>0){
                    Uri playerUri= ContentUris.withAppendedId(uri,rowId);
                    getContext().getContentResolver().notifyChange(playerUri,null);
                    return  playerUri;
                }
                break;
            default:
                throw new IllegalArgumentException("错误的Uri"+uri);

        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getReadableDatabase();
        return sqLiteDatabase.delete("player",selection,selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getReadableDatabase();
        return sqLiteDatabase.update("player",values,selection,selectionArgs);
    }
}
