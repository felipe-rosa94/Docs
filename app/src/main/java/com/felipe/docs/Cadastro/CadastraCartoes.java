package com.felipe.docs.Cadastro;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastraCartoes extends Fragment {

    private EditText etDescricao;
    private EditText etNome;
    private EditText etNumero;
    private EditText etData;
    private EditText etCvc;
    private EditText etSenha;
    private Button btnSalvar;
    private DBConfig db;
    private String json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastra_cartoes, container, false);
        iniciar(v);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDescricao.getText().toString().isEmpty() && etSenha.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Preencha as informações.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (json.equals("")) {
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
                    db.Fields.Cartao = json.trim();
                    db.update(1);

                    etDescricao.setText("");
                    etNome.setText("");
                    etNumero.setText("");
                    etData.setText("");
                    etCvc.setText("");
                    etSenha.setText("");

                    Toast.makeText(getContext(), "Os dados foram salvos.", Toast.LENGTH_SHORT).show();
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etDescricao.getWindowToken(), 0);
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etNome.getWindowToken(), 0);
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etNumero.getWindowToken(), 0);
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etData.getWindowToken(), 0);
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etCvc.getWindowToken(), 0);
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSenha.getWindowToken(), 0);
                }
            }
        });
        return v;
    }

    private void iniciar(View v) {
        etDescricao = v.findViewById(R.id.et_descricao_cartao);
        etNome = v.findViewById(R.id.et_nome_cartao);
        etNumero = v.findViewById(R.id.et_numero_cartao);
        etData = v.findViewById(R.id.et_data_cartao);
        etCvc = v.findViewById(R.id.et_cvc_cartao);
        etSenha = v.findViewById(R.id.et_senha_cartao);
        btnSalvar = v.findViewById(R.id.btn_salvar_cartao);

        db = new DBConfig(getContext());
        db.open();
        if (!db.Fields.Docs.equals("")) {
            json = db.Fields.Cartao;
        } else {
            json = "";
        }
    }
}
