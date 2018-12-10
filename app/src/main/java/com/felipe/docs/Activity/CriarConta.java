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
import com.felipe.docs.Util.Firebase;
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

    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;

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

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

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
            if (rbCredito.isChecked()) {
                cc.setDescricao(etDescricao.getText().toString() + " (1/" + etParcela.getText().toString() + ")");
            } else {
                cc.setDescricao(etDescricao.getText().toString());
            }
        } else {
            etDescricao.setError("Preencha a descrição do gasto.");
        }

        if (rbDebito.isChecked()) {
            cc.setTipoPagamento("Débito");
        } else {
            cc.setTipoPagamento("Crébito");
        }

        if (etValorTotal.getText().length() > 0) {
            if (rbCredito.isChecked()) {
                cc.setValorTotal(Double.parseDouble(etValorTotal.getText().toString()) / Double.parseDouble(etParcela.getText().toString()));
            } else {
                cc.setValorTotal(Double.parseDouble(etValorTotal.getText().toString()));
            }

        } else {
            etValorTotal.setError("Preencha o valor");
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

        String atual = util.data();
        atual = atual.substring(3, 10);
        atual = atual.replace("/", "-");

        Firebase.Salvar(dbConfig.Fields.Nome + "(" + atual + ")", util.key(), cc, 0);


        if (rbCredito.isChecked()) {
            double total = Double.parseDouble(etValorTotal.getText().toString());
            int parcelas = Integer.parseInt(etParcela.getText().toString());
            int i = 1;

            cc.setValorTotal(total / parcelas);

            String array[] = atual.split("[-]");
            int mes = Integer.parseInt(array[0]);
            int ano = Integer.parseInt(array[1]);

            while (i < parcelas) {
                mes++;
                if (mes > 12) {
                    ano++;
                    mes = 0;
                    mes++;
                }
                i++;
                cc.setDescricao(etDescricao.getText().toString() + " (" + String.valueOf(i) + "/" + etParcela.getText().toString() + ")");

                databaseReference.child(dbConfig.Fields.Nome + "(" + String.valueOf(mes) + "-" + String.valueOf(ano) + ")").child(util.key()).setValue(cc);
            }
        }

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
