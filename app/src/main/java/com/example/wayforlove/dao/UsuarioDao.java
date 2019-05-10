package com.example.wayforlove.dao;

import android.util.Log;

import com.example.wayforlove.modelo.Usuario;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuarioDao {


    public static final String TAG = "UsuarioDaoLOG";
    private List<Usuario> usuarios;

    public UsuarioDao() {
        this.usuarios = new ArrayList<>();
    }


    public boolean addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        return this.usuarios.size() > 0 ? true : false;
    }


    public List<Usuario> lista() {
        return  this.usuarios.size()> 0 ? this.usuarios : null;
    }
}
