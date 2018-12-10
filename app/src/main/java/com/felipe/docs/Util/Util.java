package com.felipe.docs.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private Context context;
    private boolean retornDialog = false;

    public Util(Context context) {
        this.context = context;
    }

    public Util() {
    }

    public boolean alertaDialog(Activity activity, String titulo, String mensagem, String positivo, String negativo) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
        if (!titulo.isEmpty()) {
            dlg.setTitle(titulo);
        }
        if (!mensagem.isEmpty()) {
            dlg.setMessage(mensagem);
        }
        if (!positivo.isEmpty()) {
            dlg.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    retornDialog = true;
                }
            });
        }
        if (!negativo.isEmpty()) {
            dlg.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    retornDialog = false;
                }
            });
        }
        dlg.show();
        return retornDialog;
    }

    public String data() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String dataHora() {
        // --- formas de formatação --- //
        // dd/MM/yyyy HH:mm:ss:SSS ou yyyy-MM-dd HH:mm:ss ou etc.
        //Você pode formatar na forma mais adequada
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String key() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
