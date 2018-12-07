package com.felipe.docs.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.felipe.docs.Banco.DBSalario;
import com.felipe.docs.R;

public class Config extends AppCompatActivity {

    private EditText etTotal, etQuinzena, etMes, etDataQuinzena, etDataMes;
    private Button btnSalvar;
    private DBSalario dbSalario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        iniciar();
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravar();
            }
        });
    }

    private void iniciar() {
        dbSalario = new DBSalario(getBaseContext());
        etTotal = findViewById(R.id.et_salario_total);
        etQuinzena = findViewById(R.id.et_salario_quinzena);
        etMes = findViewById(R.id.et_salario_mes);
        etDataQuinzena = findViewById(R.id.et_data_quinzena);
        etDataMes = findViewById(R.id.et_data_mes);
        btnSalvar = findViewById(R.id.btn_salvar);
    }

    private void gravar() {
        dbSalario.open();
        if (!etTotal.getText().toString().equals("")){
            dbSalario.Fields.SalarioTotal = etTotal.getText().toString();
            dbSalario.Fields.SalarioQuinzena = etQuinzena.getText().toString();
            dbSalario.Fields.SalarioMes = etMes.getText().toString();
            dbSalario.Fields.DataQuinzena = etDataQuinzena.getText().toString();
            dbSalario.Fields.DataMes = etDataMes.getText().toString();
        } else {
            etTotal.setError("Preencha um salário");
            return;
        }
        dbSalario.update(1);
        dbSalario.close();
        startActivity(new Intent(getBaseContext(), Financas.class));
        finish();
    }
}
