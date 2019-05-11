package com.example.wayforlove.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wayforlove.factory.DataBase;
import com.example.wayforlove.modelo.Usuario;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuarioDao {


    private DataBase dataBase;
    private SQLiteDatabase helper;
    private ContentValues contentValues;
    private Long result;

    public UsuarioDao(Context context) {
        this.dataBase = new DataBase(context);
    }

    public static final String TAG = "UsuarioDaoLOG";
    private List<Usuario> usuarios;

    public boolean save(Usuario usuario){

        try {

            helper = dataBase.getWritableDatabase();
            contentValues = getContentValues(usuario);
            result = helper.insert(DataBase.TABELA, null, contentValues);
            Log.e(TAG, "Sucesso ");

            return true;

        } catch (Exception e) {
            Log.e(TAG, "Erro ao inserir", e);
        }finally {
            helper.close();
        }

        return false;
    }

    public List<Usuario> selecionaUsuario(){
        usuarios = new ArrayList<>();
        Cursor cursor;
        String[] columns ={dataBase.ID, dataBase.NOME, dataBase.SEXO, dataBase.COR, dataBase.TIPO_FISICO};
        cursor = helper.query(dataBase.TABELA, columns, null, null, null, null, null);

        while (cursor.moveToNext()){

            Usuario usuario = new Usuario();
            usuario.setId(cursor.getString(0));
            usuario.setNome(cursor.getString(1));
            usuario.setSexo(cursor.getString(2));
            usuario.setCor(cursor.getString(3));
            usuario.setTipoFisico(cursor.getString(4));
            usuarios.add(usuario);
        }

        return usuarios;

    }

    private ContentValues getContentValues(Usuario usuario) {

        ContentValues values = new ContentValues();
        values.put(DataBase.NOME, usuario.getNome());
        values.put(DataBase.SEXO, usuario.getSexo());
        values.put(DataBase.COR, usuario.getCor());
        values.put(DataBase.TIPO_FISICO, usuario.getTipoFisico());
        values.put(DataBase.POSICAO, usuario.getPocisao().toString());

        return values;
    }

}
