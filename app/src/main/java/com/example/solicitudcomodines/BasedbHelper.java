package com.example.solicitudcomodines;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BasedbHelper extends SQLiteOpenHelper {

    String sqlCreateGESTOR= "CREATE TABLE GESTOR (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, GESTOR TEXT, ID_ANDROID TEXT)";
    String sqlCreateCLIENTES= "CREATE TABLE CLIENTES (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, CLIENTE TEXT)";
    String sqlCreateCOMODINES= "CREATE TABLE COMODINES (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, COMODIN TEXT ,DNI TEXT)";
    String sqlCreateSOLICITUD = "CREATE TABLE SOLICITUD (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,GESTOR TEXT,ID_ANDROID TEXT,FECHA TEXT, CLIENTE TEXT, HORA_ENTRADA TEXT, HORA_SALIDA TEXT, COMODIN TEXT, OBSERVACION TEXT)";

    public BasedbHelper(Context context) {
        super(context, "DBSOLICITUDCOMODINES", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreateGESTOR);
        db.execSQL(sqlCreateCLIENTES);
        db.execSQL(sqlCreateCOMODINES);
        db.execSQL(sqlCreateSOLICITUD);

        Log.i(this.getClass().toString(), "BASE COMODINES CREADA");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//Se elimina la version anterior de la tabla

        if (newVersion > oldVersion) {
            db.execSQL(sqlCreateCOMODINES);
            //db.execSQL("ALTER TABLE FORMACIONFIRMADAS ADD COLUMN FORMADOR TEXT");
        }
    }
}
