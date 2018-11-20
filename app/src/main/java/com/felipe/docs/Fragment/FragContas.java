package com.felipe.docs.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.felipe.docs.Adapter.ContasAdapter;
import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Model.Contas;
import com.felipe.docs.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class FragContas extends Fragment {

    private RecyclerView lv_contas;
    private static ArrayList<Contas> contas;
    private static DBConfig db;
    private static Activity activity;
    private static ContasAdapter adapter;
    private static AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contas, container, false);
        db = new DBConfig(getContext());
        db.open();
        activity = getActivity();
        try {
            JSONArray jsonArray = new JSONArray("[" + db.Fields.Contas + "]");
            contas = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Contas c = new Contas();
                c.setNome(jsonArray.getJSONObject(i).getString("nome"));
                c.setAgencia(jsonArray.getJSONObject(i).getString("agencia"));
                c.setNumeroConta(jsonArray.getJSONObject(i).getString("numero"));
                c.setTipo(jsonArray.getJSONObject(i).getString("tipo"));
                contas.add(c);
            }
            adapter = new ContasAdapter(getContext(), contas);
            lv_contas = v.findViewById(R.id.lv_contas);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            lv_contas.setLayoutManager(linearLayoutManager);
            lv_contas.setAdapter(adapter);
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
        View v = inflater.inflate(R.layout.dialog_contas, null, false);
        final EditText etNome, etNumero, etAgencia, etTipo;

        etNome = v.findViewById(R.id.et_auto_nome_banco);
        etAgencia = v.findViewById(R.id.et_agencia_contas);
        etNumero = v.findViewById(R.id.et_numero_contas);
        etTipo = v.findViewById(R.id.et_auto_tipo_contas);

        etNome.setText(contas.get(position).getNome());
        etAgencia.setText(contas.get(position).getAgencia());
        etNumero.setText(contas.get(position).getNumeroConta());
        etTipo.setText(contas.get(position).getTipo());

        builder.setView(v)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        contas.get(position).setNome(etNome.getText().toString());
                        contas.get(position).setNumeroConta(etNumero.getText().toString());
                        contas.get(position).setAgencia(etAgencia.getText().toString());
                        contas.get(position).setTipo(etTipo.getText().toString());

                        for (int i = 0; i < contas.size(); i++) {
                            if (i == 0) {
                                json += "{\"nome\":\"" + etNome.getText().toString() + "\"" +
                                        ",\"agencia\":\"" + etAgencia.getText().toString() + "\"" +
                                        ",\"numero\":\"" + etNumero.getText().toString() + "\"" +
                                        ",\"tipo\":\"" + etTipo.getText().toString() + "\"}";
                            } else {
                                json += ",{\"nome\":\"" + etNome.getText().toString() + "\"" +
                                        ",\"agencia\":\"" + etAgencia.getText().toString() + "\"" +
                                        ",\"numero\":\"" + etNumero.getText().toString() + "\"" +
                                        ",\"tipo\":\"" + etTipo.getText().toString() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Contas = json.trim();
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
        tvMsg.setText("Deseja apagar o " + contas.get(position).getNome() + " ?");
        builder.setView(view)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String json = "";
                        contas.remove(position);
                        for (int i = 0; i < contas.size(); i++) {
                            if (i == 0) {
                                json += "{\"nome\":\"" + contas.get(i).getNome() + "\"" +
                                        ",\"agencia\":\"" + contas.get(i).getAgencia() + "\"" +
                                        ",\"numero\":\"" + contas.get(i).getNumeroConta() + "\"" +
                                        ",\"tipo\":\"" + contas.get(i).getTipo() + "\"}";
                            } else {
                                json += ",{\"nome\":\"" + contas.get(i).getNome() + "\"" +
                                        ",\"agencia\":\"" + contas.get(i).getAgencia() + "\"" +
                                        ",\"numero\":\"" + contas.get(i).getNumeroConta() + "\"" +
                                        ",\"tipo\":\"" + contas.get(i).getTipo() + "\"}";
                            }
                        }
                        adapter.notifyDataSetChanged();
                        db.Fields.Contas = json.trim();
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
