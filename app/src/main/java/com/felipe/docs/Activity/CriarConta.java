package com.felipe.docs.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Firebase;
import com.felipe.docs.Model.CriaContaFinaceira;
import com.felipe.docs.R;
import com.felipe.docs.Util.Util;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CriarConta extends AppCompatActivity {

    private static EditText etDescricao, etParcela, etValorTotal, etObservacao;
    private static Spinner spTipoConta;
    private static Button btnSalvar;
    private static RadioButton rbCredito, rbDebito;

    private static Activity activity;
    private Firebase firebase;
    private DBConfig dbConfig;
    private static Util util;
    private static ProgressDialog pd;

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

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gravar();
            }
        });
    }

    private void iniciar() {
        firebase = new Firebase(this);
        dbConfig = new DBConfig(this);
        util = new Util(this);
        activity = CriarConta.this;
        pd = new ProgressDialog(this);

        etDescricao = findViewById(R.id.et_descricao_conta);
        etParcela = findViewById(R.id.et_parcelas);
        etValorTotal = findViewById(R.id.et_valor_total_conta);
        etObservacao = findViewById(R.id.et_observacao_conta);
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

    private void Gravar() {
        pd.setTitle("Salvando");
        pd.show();
        CriaContaFinaceira cc = new CriaContaFinaceira();

        if (etDescricao.getText().length() > 0) {
            cc.setDescricao(etDescricao.getText().toString());
        } else {
            etDescricao.setError("Preencha a descrição do gasto.");
        }

        if (etValorTotal.getText().length() > 0) {
            cc.setValorTotal(Double.parseDouble(etValorTotal.getText().toString()));
        } else {
            etValorTotal.setError("Preencha o valor");
        }

        if (rbDebito.isChecked()) {
            cc.setTipoPagamento("Débito");
        } else {
            cc.setTipoPagamento("Crébito");
        }

        cc.setObservacao(etObservacao.getText().toString());

        try {
            cc.setParcela(Integer.parseInt(etParcela.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cc.setTipo(spTipoConta.getSelectedItemPosition());
        cc.setData(util.dataHora());

        dbConfig.open();
        Firebase.Salvar(dbConfig.Fields.Nome, util.key(), cc, 0);
        dbConfig.close();
    }

    public static void Limpa(boolean retorno) {
        if (retorno) {
            etDescricao.setText("");
            etParcela.setText("");
            etParcela.setVisibility(View.GONE);
            rbDebito.setChecked(true);
            spTipoConta.setSelection(0);
            etValorTotal.setText("");
            pd.dismiss();
            util.alertaDialog(activity, "Dados Salvos", "Sua conta foi salva com sucesso.", "Ok", "");
        } else {
            pd.dismiss();
            util.alertaDialog(activity, "Erro", "Não foi possível salvar sua conta.", "Ok", "");
        }
    }
}
