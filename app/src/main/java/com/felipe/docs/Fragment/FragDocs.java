package com.felipe.docs.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.felipe.docs.Adapter.DocsAdapter;
import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Model.Documentos;
import com.felipe.docs.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class FragDocs extends Fragment {

    private static DBConfig db;
    private static ArrayList<Documentos> documentos;
    private RecyclerView lvDocs;
    private static DocsAdapter adapter;
    private static AlertDialog dialog;
    private static Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_docs, container, false);
        db = new DBConfig(getContext());
        db.open();
        activity = getActivity();
        try {
            JSONArray jsonArray = new JSONArray("[" + db.Fields.Docs + "]");
            documentos = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Documentos d = new Documentos();
                d.setDescricao(jsonArray.getJSONObject(i).getString("descricao"));
                d.setNumero(jsonArray.getJSONObject(i).getString("num"));
                documentos.add(d);
            }
            adapter = new DocsAdapter(getContext(), documentos);
            lvDocs = v.findViewById(R.id.lv_docs);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            lvDocs.setLayoutManager(linearLayoutManager);
            lvDocs.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static void MsgConfirmar(final int position) {
        db = new DBConfig(activity);
        db.open();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_docs, null, false);
        final EditText etDescricao = view.findViewById(R.id.et_outros_descricao);
        final EditText etNum = view.findViewById(R.id.et_edit_docs_numero);
        etDescricao.setText(documentos.get(position).getDescricao());
        etNum.setText(documentos.get(position).getNumero());
        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        documentos.get(position).setDescricao(etDescricao.getText().toString());
                        documentos.get(position).setNumero(etNum.getText().toString());
                        for (int i = 0; i < documentos.size(); i++) {
                            if (i == 0) {
                                json = "{\"descricao\":\"" + documentos.get(i).getDescricao() + "\",\"num\":\"" + documentos.get(i).getNumero() + "\"}";
                            } else {
                                json += ",{\"descricao\":\"" + documentos.get(i).getDescricao() + "\",\"num\":\"" + documentos.get(i).getNumero() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Docs = json.trim();
                        db.update(1);
                        db.close();
                        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etNum.getWindowToken(), 0);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public static void MsgDeletar(final int position) {
        db = new DBConfig(activity);
        db.open();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_simples, null, false);
        final TextView tvMsg = view.findViewById(R.id.tv_msg_dialog_simples);
        tvMsg.setText("Deseja apagar o " + documentos.get(position).getDescricao() + " ?");
        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        documentos.remove(position);
                        for (int i = 0; i < documentos.size(); i++) {
                            if (i == 0) {
                                json = "{\"descricao\":\"" + documentos.get(i).getDescricao() + "\",\"num\":\"" + documentos.get(i).getNumero() + "\"}";
                            } else {
                                json += ",{\"descricao\":\"" + documentos.get(i).getDescricao() + "\",\"num\":\"" + documentos.get(i).getNumero() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Docs = json.trim();
                        db.update(1);
                        db.close();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}
