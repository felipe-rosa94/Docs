package com.felipe.docs.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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
}
