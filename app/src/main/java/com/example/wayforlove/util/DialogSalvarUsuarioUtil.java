package com.example.wayforlove.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.wayforlove.MapaActivity;

public class DialogSalvarUsuarioUtil {


    private static AlertDialog alerta;
    private static Context context;

    public DialogSalvarUsuarioUtil(Context context){
        this.context = context;
    }

    public static void showMensagem(String titulo, String mensagem) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }
}
