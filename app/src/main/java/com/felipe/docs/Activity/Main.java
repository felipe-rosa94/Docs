package com.felipe.docs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.R;

public class Main extends AppCompatActivity {

    private Toolbar toolbar;
    private DBConfig db;
    private EditText etNome, etSenha, etSenha2;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciar();
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNome.getText().toString().isEmpty()) {
                    etNome.setError("Digite um nome.");
                    return;
                } else if (!etSenha.getText().toString().equals(etSenha2.getText().toString())) {
                    etSenha2.setText("Senhas diferentes");
                    return;
                } else {
                    db.Fields.Nome = etNome.getText().toString();
                    db.Fields.Senha = etSenha.getText().toString();
                    db.Fields.Intro = "1000";
                    db.update(1);
                    Toast.makeText(Main.this, "Senha Salva com sucesso.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), Home.class));
                    finish();
                }
            }
        });
    }

    private void iniciar() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etNome = findViewById(R.id.et_nome);
        etSenha = findViewById(R.id.et_senha);
        etSenha2 = findViewById(R.id.et_senha2);
        btnSalvar = findViewById(R.id.btn_salvar);

        db = new DBConfig(getBaseContext());
        db.open();

        if (!db.Fields.Intro.equals("")) {
            startActivity(new Intent(getBaseContext(), Login.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
