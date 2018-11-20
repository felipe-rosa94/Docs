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
import com.felipe.docs.Adapter.LoginsAdapter;
import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Model.Documentos;
import com.felipe.docs.Model.Logins;
import com.felipe.docs.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class FragEmails extends Fragment {

    private RecyclerView lvLogins;
    private static Activity activity;
    private static DBConfig db;
    private static ArrayList<Logins> logins;
    private static LoginsAdapter adapter;
    private static AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_emails, container, false);
        db = new DBConfig(getContext());
        db.open();
        activity = getActivity();
        try {
            JSONArray jsonArray = new JSONArray("[" + db.Fields.Emails + "]");
            logins = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Logins d = new Logins();
                d.setAplicacao(jsonArray.getJSONObject(i).getString("aplicacao"));
                d.setLogins(jsonArray.getJSONObject(i).getString("login"));
                d.setSenha(jsonArray.getJSONObject(i).getString("senha"));
                logins.add(d);
            }
            adapter = new LoginsAdapter(getContext(), logins);
            lvLogins = v.findViewById(R.id.lv_email);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            lvLogins.setLayoutManager(linearLayoutManager);
            lvLogins.setAdapter(adapter);
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
        View view = inflater.inflate(R.layout.dialog_logins, null, false);

        final EditText etAplicacao = view.findViewById(R.id.et_auto_aplicacao_logins);
        final EditText etLogins = view.findViewById(R.id.et_emails_logins);
        final EditText etSenha = view.findViewById(R.id.et_senha_logins);


        etAplicacao.setText(logins.get(position).getAplicacao());
        etLogins.setText(logins.get(position).getLogins());
        etSenha.setText(logins.get(position).getSenha());

        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        logins.get(position).setAplicacao(etAplicacao.getText().toString());
                        logins.get(position).setLogins(etLogins.getText().toString());
                        logins.get(position).setSenha(etSenha.getText().toString());

                        for (int i = 0; i < logins.size(); i++) {
                            if (i == 0) {
                                json += "{\"aplicacao\":\"" + etAplicacao.getText().toString() + "\"" +
                                        ",\"login\":\"" + etLogins.getText().toString() + "\"" +
                                        ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                            } else {
                                json += ",{\"aplicacao\":\"" + etAplicacao.getText().toString() + "\"" +
                                        ",\"login\":\"" + etLogins.getText().toString() + "\"" +
                                        ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Emails = json.trim();
                        db.update(1);
                        db.close();
                        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etLogins.getWindowToken(), 0);
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
        tvMsg.setText("Deseja apagar o " + logins.get(position).getAplicacao() + " ?");
        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        logins.remove(position);
                        for (int i = 0; i < logins.size(); i++) {
                            if (i == 0) {
                                json += "{\"aplicacao\":\"" + logins.get(position).getAplicacao() + "\"" +
                                        ",\"login\":\"" + logins.get(position).getLogins() + "\"" +
                                        ",\"senha\":\"" + logins.get(position).getSenha() + "\"}";
                            } else {
                                json += ",{\"aplicacao\":\"" + logins.get(position).getAplicacao() + "\"" +
                                        ",\"login\":\"" + logins.get(position).getLogins() + "\"" +
                                        ",\"senha\":\"" + logins.get(position).getSenha() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Emails = json.trim();
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
