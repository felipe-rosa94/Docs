package com.felipe.docs.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.felipe.docs.R;

public class CriarConta extends AppCompatActivity {

    private EditText etDescricao, etParcela, etValorTotal;
    private Spinner spTipoConta;
    private Button btnSalvar;
    private RadioButton rbCredito, rbDebito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
        iniciar();

        rbCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etParcela.setVisibility(View.VISIBLE);
            }
        });

        rbDebito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etParcela.setVisibility(View.GONE);
            }
        });
    }

    private void iniciar() {
        etDescricao = findViewById(R.id.et_descricao_conta);
        etParcela = findViewById(R.id.et_parcelas);
        etValorTotal = findViewById(R.id.et_valor_total_conta);
        spTipoConta = findViewById(R.id.sp_tipo_conta);
        rbCredito = findViewById(R.id.rb_credito);
        rbDebito = findViewById(R.id.rb_debito);
        btnSalvar = findViewById(R.id.btn_salvar_conta_finaceira);
        rbDebito.setChecked(true);
        etParcela.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoConta.setAdapter(adapter);
    }
}
