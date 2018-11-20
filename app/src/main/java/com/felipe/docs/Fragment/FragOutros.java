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

import com.felipe.docs.Adapter.LoginsAdapter;
import com.felipe.docs.Adapter.OutrosAdapter;
import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Model.Logins;
import com.felipe.docs.Model.Outros;
import com.felipe.docs.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class FragOutros extends Fragment {

    private RecyclerView lvOutros;
    private static ArrayList<Outros> outros;
    private static Activity activity;
    private static DBConfig db;
    private static OutrosAdapter adapter;
    private static AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outros, container, false);
        db = new DBConfig(getContext());
        db.open();
        activity = getActivity();
        try {
            JSONArray jsonArray = new JSONArray("[" + db.Fields.Outros + "]");
            outros = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Outros d = new Outros();
                d.setDescricao(jsonArray.getJSONObject(i).getString("descricao"));
                d.setSenha(jsonArray.getJSONObject(i).getString("senha"));
                outros.add(d);
            }
            adapter = new OutrosAdapter(getContext(), outros);
            lvOutros = view.findViewById(R.id.lv_outros);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            lvOutros.setLayoutManager(linearLayoutManager);
            lvOutros.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public static void MsgConfirmar(final int position) {
        db = new DBConfig(activity);
        db.open();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_outros, null, false);

        final EditText etDescricao = view.findViewById(R.id.et_outros_descricao);
        final EditText etSenha = view.findViewById(R.id.et_outros_numero);

        etDescricao.setText(outros.get(position).getDescricao());
        etSenha.setText(outros.get(position).getSenha());

        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        outros.get(position).setDescricao(etDescricao.getText().toString());
                        outros.get(position).setSenha(etSenha.getText().toString());

                        for (int i = 0; i < outros.size(); i++) {
                            if (i == 0) {
                                json += "{\"descricao\":\"" + etDescricao.getText().toString() + "\"" +
                                        ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                            } else {
                                json += ",{\"descricao\":\"" + etDescricao.getText().toString() + "\"" +
                                        ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Outros = json.trim();
                        db.update(1);
                        db.close();
                        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSenha.getWindowToken(), 0);
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
        tvMsg.setText("Deseja apagar o " + outros.get(position).getDescricao() + " ?");
        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        outros.remove(position);
                        for (int i = 0; i < outros.size(); i++) {
                            if (i == 0) {
                                json += "{\"descricao\":\"" + outros.get(position).getDescricao() + "\"" +
                                        ",\"senha\":\"" + outros.get(position).getSenha() + "\"}";
                            } else {
                                json += ",{\"descricao\":\"" + outros.get(position).getDescricao() + "\"" +
                                        ",\"senha\":\"" + outros.get(position).getSenha() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Outros = json.trim();
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
