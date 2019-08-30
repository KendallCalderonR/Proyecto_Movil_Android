package org.example.final_project_android_mobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminDB extends SQLiteOpenHelper {


    public AdminDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table producto(id int primary key,imagen text, nombre text,precio double,descripcion text,estado boolean)");
        db.execSQL("create table orden(id int primary key, nombrecliente text, total real, mesa int )");
        db.execSQL("create table producto_x_cliente(idCliente int, idProducto int, primary key (idCliente,idProducto))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS producto");
        //db.execSQL("create table producto(id int primary key,imagen text, nombre text, precio double,descripcion text)");
        db.execSQL("DROP TABLE IF EXISTS orden");
        //db.execSQL("create table orden(id int primary key, nombrecliente text, total real, mesa int )");
        db.execSQL("DROP TABLE IF EXISTS producto_x_cliente");
        //db.execSQL("create table producto_x_cliente(idCliente int, idProducto int, primary key (idCliente,idProducto))");
        onCreate(db);
    }

}
