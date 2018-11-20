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

import java.util.ArrayList;
import java.util.Arrays;

public class CadastraDocs extends Fragment {

    private EditText etDescricaoDocs;
    private EditText etNumeroDocs;
    private Button btnSalvarDocs;
    private DBConfig db;
    private String json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastra_docs, container, false);
        iniciar(v);
        btnSalvarDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etDescricaoDocs.getText().toString().isEmpty() && etNumeroDocs.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Preencha as informações.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (json.equals("")){
                        json += "{\"descricao\":\""+etDescricaoDocs.getText().toString()+"\",\"num\":\""+etNumeroDocs.getText().toString()+"\"}";
                    } else {
                        json += ",{\"descricao\":\""+etDescricaoDocs.getText().toString()+"\",\"num\":\""+etNumeroDocs.getText().toString()+"\"}";
                    }
                    db.Fields.Docs = json.trim();
                    db.update(1);
                    etDescricaoDocs.setText("");
                    etNumeroDocs.setText("");
                    Toast.makeText(getContext(), "Os dados foram salvos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void iniciar(View view) {

        etDescricaoDocs = view.findViewById(R.id.et_descricao_docs);
        etNumeroDocs = view.findViewById(R.id.et_numero_docs);
        btnSalvarDocs = view.findViewById(R.id.btn_salvar_docs);

        db = new DBConfig(getContext());
        db.open();
        if (!db.Fields.Docs.equals("")){
           json = db.Fields.Docs;
        } else {
            json = "";
        }
    }
}
