package com.example.wayforlove.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {


    public static final String NOME_BANCO = " wayforlove.db ";
    public static final String TABELA   = " usuario ";
    public static final String ID       = " id ";
    public static final String NOME     = " nome ";
    public static final String SEXO     = " sexo ";
    public static final String COR      = " cor ";
    public static final String TIPO_FISICO = " tipo_fisico ";
    public static final String POSICAO = " posicao ";
    public static final int VERSAO    = 1;

    public DataBase(Context context){
        super(context, NOME_BANCO,null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE "+TABELA+"("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + SEXO + " text, "
                + COR + " text, "
                + TIPO_FISICO + " text, "
                + POSICAO + " text "
                +")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
