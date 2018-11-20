package com.felipe.docs.Cadastro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class CadastraContas extends Fragment {

    private AutoCompleteTextView etNomeBanco;
    private EditText etAgencia;
    private EditText etNumeroConta;
    private AutoCompleteTextView etTipo;
    private Button btnSalvar;
    private DBConfig db;
    private String json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastra_contas, container, false);
        iniciar(view);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNomeBanco.getText().toString().isEmpty() && etAgencia.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Preencha as informações.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (json.equals("")) {
                        json += "{\"nome\":\"" + etNomeBanco.getText().toString() + "\"" +
                                ",\"agencia\":\"" + etAgencia.getText().toString() + "\"" +
                                ",\"numero\":\"" + etNumeroConta.getText().toString() + "\"" +
                                ",\"tipo\":\"" + etTipo.getText().toString() + "\"}";
                    } else {
                        json += ",{\"nome\":\"" + etNomeBanco.getText().toString() + "\"" +
                                ",\"agencia\":\"" + etAgencia.getText().toString() + "\"" +
                                ",\"numero\":\"" + etNumeroConta.getText().toString() + "\"" +
                                ",\"tipo\":\"" + etTipo.getText().toString() + "\"}";
                    }
                    db.Fields.Contas = json.trim();
                    db.update(1);
                    etNomeBanco.setText("");
                    etAgencia.setText("");
                    etNumeroConta.setText("");
                    etTipo.setText("");
                    Toast.makeText(getContext(), "Os dados foram salvos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void iniciar(View view) {
        etNomeBanco = view.findViewById(R.id.et_auto_nome_banco);
        etAgencia = view.findViewById(R.id.et_agencia_contas);
        etNumeroConta = view.findViewById(R.id.et_numero_contas);
        etTipo = view.findViewById(R.id.et_auto_tipo_contas);
        btnSalvar = view.findViewById(R.id.btn_salvar_contas);

        ArrayAdapter<String> bancos = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, new String[]{
                "Banco do Brasil", "Bradesco", "Banrisul", "Caixa", "Itaú", "HSBC", "NuBank"});

        etNomeBanco.setAdapter(bancos);

        ArrayAdapter<String> tipos = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, new String[]{
                "Conta corrente", "Poupança", "Conta salário"});

        etTipo.setAdapter(tipos);

        db = new DBConfig(getContext());
        db.open();
        if (!db.Fields.Contas.equals("")) {
            json = db.Fields.Contas;
        } else {
            json = "";
        }
    }
}
