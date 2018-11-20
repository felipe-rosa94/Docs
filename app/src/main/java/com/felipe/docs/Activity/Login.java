package com.felipe.docs.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.R;
import com.felipe.docs.Util.Util;

public class Login extends AppCompatActivity {

    private Toolbar toolbar;
    private DBConfig db;
    private EditText etSenha;
    private Button btnEntrar;
    private int cont = 0;
    private Util u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciar();
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (!etSenha.getText().toString().equals(db.Fields.Senha)) {
                    Toast.makeText(getBaseContext(), "Senha Incorreta.", Toast.LENGTH_SHORT).show();
                    cont++;
                } else {
                    startActivity(new Intent(getBaseContext(), Home.class));
                    ((InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSenha.getWindowToken(), 0);
                    finish();
                }
                if (cont == 3) {
                    u.alertaDialog(Login.this, "Alerta", "Se errar a senha mais uma vez, seus dados serão apagados.", "Ok", "");
                } else if (cont == 4) {
                    db.emtpyTable();
                    Toast.makeText(getBaseContext(), "Todos os dados foram apagados.", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                }
            }
        });
    }

    private void iniciar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DBConfig(getBaseContext());
        db.open();
        u = new Util(getBaseContext());
        etSenha = findViewById(R.id.et_senha_login);
        btnEntrar = findViewById(R.id.btn_entrar);
    }
}
