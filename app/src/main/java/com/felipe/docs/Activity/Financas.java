package com.felipe.docs.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.felipe.docs.Banco.DBSalario;
import com.felipe.docs.R;

public class Financas extends AppCompatActivity {

    private DBSalario dbSalario;
    private ImageButton btnCriar, btnLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financas);
        iniciar();
        dbSalario.open();
        if (dbSalario.Fields.SalarioTotal.equals("")) {
            startActivity(new Intent(getBaseContext(), Config.class));
        }
        dbSalario.close();
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), CriarConta.class));
            }
        });
    }

    private void iniciar() {
        dbSalario = new DBSalario(getBaseContext());
        btnCriar = findViewById(R.id.btn_cria_conta);
        btnLista = findViewById(R.id.btn_lista_conta);
    }
}
