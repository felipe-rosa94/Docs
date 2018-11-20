package com.felipe.docs.Cadastro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.R;

public class CadastraEmails extends Fragment {

    private AutoCompleteTextView etAplicacao;
    private EditText etLogin;
    private EditText etSenha;
    private Button btnSalvar;
    private DBConfig db;
    private String json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastra_emails, container, false);
        iniciar(view);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAplicacao.getText().toString().isEmpty() && etSenha.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Preencha as informações.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (json.equals("")) {
                        json += "{\"aplicacao\":\"" + etAplicacao.getText().toString() + "\"" +
                                ",\"login\":\"" + etLogin.getText().toString() + "\"" +
                                ",\"senha\":\"" + etSenha.getText().toString() + "\"}";

                    } else {
                        json += ",{\"aplicacao\":\"" + etAplicacao.getText().toString() + "\"" +
                                ",\"login\":\"" + etLogin.getText().toString() + "\"" +
                                ",\"senha\":\"" + etSenha.getText().toString() + "\"}";
                    }
                    db.Fields.Emails = json.trim();
                    db.update(1);
                    etAplicacao.setText("");
                    etSenha.setText("");
                    etLogin.setText("");
                    Toast.makeText(getContext(), "Os dados foram salvos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void iniciar(View view) {
        etAplicacao = view.findViewById(R.id.et_auto_aplicacao_logins);
        etLogin = view.findViewById(R.id.et_emails_logins);
        etSenha = view.findViewById(R.id.et_senha_logins);
        btnSalvar = view.findViewById(R.id.btn_salvar_logins);

        ArrayAdapter<String> bancos = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, new String[]{
                "Netflix", "Sites", "Jogos", "Google", "Outlook", "Gmail", "Facebook", "Instagram", "Twitter"});

        etAplicacao.setAdapter(bancos);

        db = new DBConfig(getContext());
        db.open();
        if (!db.Fields.Emails.equals("")) {
            json = db.Fields.Emails;
        } else {
            json = "";
        }
    }
}
