package com.felipe.docs.Cadastro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.R;

public class CadastraOutros extends Fragment {

    private EditText etDescricao;
    private EditText etSenha;
    private Button btnSalvar;
    private String json;
    private DBConfig db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastra_outros, container, false);
        inicia(view);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDescricao.getText().toString().isEmpty() && etSenha.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Preencha as informações.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (json.equals("")) {
                        json += "{\"descricao\":\"" + etDescricao.getText().toString() + "\"" +
                                ",\"senha\":\"" + etSenha.getText().toString() + "\"}";

                    } else {
                        json += ",{\"descricao\":\"" + etDescricao.getText().toString() + "\"" +
                                ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                    }
                    db.Fields.Outros = json.trim();
                    db.update(1);
                    etDescricao.setText("");
                    etSenha.setText("");
                    Toast.makeText(getContext(), "Os dados foram salvos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void inicia(View view) {
        etDescricao = view.findViewById(R.id.et_outros_descricao);
        etSenha = view.findViewById(R.id.et_outros_senha);
        btnSalvar = view.findViewById(R.id.btn_salvar_outros);

        db = new DBConfig(getContext());
        db.open();

        if (!db.Fields.Outros.equals("")) {
            json = db.Fields.Emails;
        } else {
            json = "";
        }
    }
}
