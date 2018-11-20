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

import com.felipe.docs.Adapter.CartoesAdapter;
import com.felipe.docs.Adapter.DocsAdapter;
import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Model.Cartoes;
import com.felipe.docs.Model.Documentos;
import com.felipe.docs.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class FragCartoes extends Fragment {

    private RecyclerView lvCartoes;
    private static CartoesAdapter adapter;
    private static ArrayList<Cartoes> cartoes;
    private static AlertDialog dialog;
    private static Activity activity;
    private static DBConfig db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cartoes, container, false);
        db = new DBConfig(getContext());
        db.open();
        activity = getActivity();
        try {
            JSONArray jsonArray = new JSONArray("[" + db.Fields.Cartao + "]");
            cartoes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Cartoes c = new Cartoes();
                c.setDescricao(jsonArray.getJSONObject(i).getString("descricao"));
                c.setNome(jsonArray.getJSONObject(i).getString("nome"));
                c.setNumero(jsonArray.getJSONObject(i).getString("numero"));
                c.setData(jsonArray.getJSONObject(i).getString("data"));
                c.setCvc(jsonArray.getJSONObject(i).getString("cvc"));
                c.setSenha(jsonArray.getJSONObject(i).getString("senha"));
                cartoes.add(c);
            }
            adapter = new CartoesAdapter(getContext(), cartoes);
            lvCartoes = v.findViewById(R.id.lv_cartoes);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            lvCartoes.setLayoutManager(linearLayoutManager);
            lvCartoes.setAdapter(adapter);
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
        View v = inflater.inflate(R.layout.dialog_carts, null, false);
        final EditText etDescricao, etNome, etNumero, etData, etCvc, etSenha;

        etDescricao = v.findViewById(R.id.et_descricao_cartao);
        etNome = v.findViewById(R.id.et_nome_cartao);
        etNumero = v.findViewById(R.id.et_numero_cartao);
        etData = v.findViewById(R.id.et_data_cartao);
        etCvc = v.findViewById(R.id.et_cvc_cartao);
        etSenha = v.findViewById(R.id.et_senha_cartao);

        etDescricao.setText(cartoes.get(position).getDescricao());
        etNome.setText(cartoes.get(position).getNome());
        etNumero.setText(cartoes.get(position).getNumero());
        etData.setText(cartoes.get(position).getData());
        etCvc.setText(cartoes.get(position).getCvc());
        etSenha.setText(cartoes.get(position).getSenha());

        builder.setView(v)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        cartoes.get(position).setDescricao(etDescricao.getText().toString());
                        cartoes.get(position).setNome(etNome.getText().toString());
                        cartoes.get(position).setNumero(etNumero.getText().toString());
                        cartoes.get(position).setData(etData.getText().toString());
                        cartoes.get(position).setCvc(etCvc.getText().toString());
                        cartoes.get(position).setSenha(etSenha.getText().toString());
                        for (int i = 0; i < cartoes.size(); i++) {
                            if (i == 0) {
                                json += "{\"descricao\":\"" + etDescricao.getText().toString() + "\"" +
                                        ",\"nome\":\"" + etNome.getText().toString() + "\"" +
                                        ",\"numero\":\"" + etNumero.getText().toString() + "\"" +
                                        ",\"data\":\"" + etData.getText().toString() + "\"" +
                                        ",\"cvc\":\"" + etCvc.getText().toString() + "\"" +
                                        ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                            } else {
                                json += ",{\"descricao\":\"" + etDescricao.getText().toString() + "\"" +
                                        ",\"nome\":\"" + etNome.getText().toString() + "\"" +
                                        ",\"numero\":\"" + etNumero.getText().toString() + "\"" +
                                        ",\"data\":\"" + etData.getText().toString() + "\"" +
                                        ",\"cvc\":\"" + etCvc.getText().toString() + "\"" +
                                        ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Cartao = json.trim();
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

    public static void MsgDeletar(final int position) {
        db = new DBConfig(activity);
        db.open();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_simples, null, false);
        final TextView tvMsg = view.findViewById(R.id.tv_msg_dialog_simples);
        tvMsg.setText("Deseja apagar o " + cartoes.get(position).getDescricao() + " ?");
        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        cartoes.remove(position);
                        for (int i = 0; i < cartoes.size(); i++) {
                            if (i == 0) {
                                json = "{\"descricao\":\"" + cartoes.get(i).getDescricao() + "\",\"senha\":\"" + cartoes.get(i).getSenha() + "\"}";
                            } else {
                                json += ",{\"descricao\":\"" + cartoes.get(i).getDescricao() + "\",\"senha\":\"" + cartoes.get(i).getSenha() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Cartao = json.trim();
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
