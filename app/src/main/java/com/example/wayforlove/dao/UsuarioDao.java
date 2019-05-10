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


    public List<Usuario> listarAll() {

        List<Usuario> usuarios = new ArrayList<>(Arrays.asList(

                new Usuario("Ednei", "Masculino", "Sarado(a)", "Negro", new LatLng(-22.90, -43.26)),
                new Usuario("Ana", "Feminino", "Fofinha(a)", "Branca", new LatLng(-22.30, -43.16)),
                new Usuario("Jose", "Masculino", "Magro(a)", "Morena", new LatLng(-22.50, -43.36)),
                new Usuario("Maria", "Feminino", "Sarada(a)", "Loira", new LatLng(-22.70, -43.76)),
                new Usuario("Marta", "Feminino", "Magro(a)", "Ruiva", new LatLng(-22.30, -43.06))
        ));

        return usuarios;
    }


    public boolean addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        return this.usuarios.size() > 0 ? true : false;
    }

    public int getSiseLista() {
        return this.usuarios.size();
    }

    public void lista() {
        for (int i = 0; i < this.usuarios.size(); i++)
            Log.e(TAG, this.usuarios.get(i).toString());
        //return  this.usuarios;
    }
}
