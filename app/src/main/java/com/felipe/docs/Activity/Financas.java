package com.felipe.docs.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.felipe.docs.Adapter.FinancasAdapter;
import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Banco.DBSalario;
import com.felipe.docs.Model.CriaContaFinaceira;
import com.felipe.docs.R;
import com.felipe.docs.Util.Util;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Financas extends AppCompatActivity {

    private static TextView tvRestante, tvGasto;
    private RecyclerView rvContas;

    private DBSalario dbSalario;
    private DBConfig dbConfig;
    private Util util;

    private FinancasAdapter adapter;
    private ArrayList<CriaContaFinaceira> contas;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private double valorTotal;
    private double valorTotalGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financas);
        iniciar();
    }

    private void iniciar() {
        dbSalario = new DBSalario(this);
        dbSalario.open();

        dbConfig = new DBConfig(this);
        dbConfig.open();
        contas = new ArrayList<>();

        util = new Util(this);

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        tvRestante = findViewById(R.id.tv_salario_restante);
        tvGasto = findViewById(R.id.tv_gasto);
        tvRestante.setText(dbSalario.Fields.SalarioTotal);

        rvContas = findViewById(R.id.rv_contas);
        rvContas.setLayoutManager(new LinearLayoutManager(this));

        Dados();

        dbSalario.close();
        dbConfig.close();
    }

    private void Dados() {
        databaseReference.child(dbConfig.Fields.Nome).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contas.clear();
                valorTotal = Double.parseDouble(dbSalario.Fields.SalarioTotal);
                for (DataSnapshot obj : dataSnapshot.getChildren()) {
                    CriaContaFinaceira c = obj.getValue(CriaContaFinaceira.class);
                    contas.add(c);
                    TrocarValor(c.getTipoPagamento(), c.getValorTotal(), c.getData(), c.getParcela());
                }
                adapter = new FinancasAdapter(getBaseContext(), contas, Double.parseDouble(dbSalario.Fields.SalarioTotal));
                rvContas.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void TrocarValor(String tipo, double valor, String data, int parcela) {
        dbSalario.open();
        if (tipo.equals("Débito")) {
            valorTotal = valorTotal - valor;
            valorTotalGasto = valorTotalGasto + valor;
        } else {
            int dia = Integer.parseInt(data.substring(0, 2));
            int fechamento = Integer.parseInt(dbSalario.Fields.DiaFechamento);

            data = data.substring(3, 10);
            String atual = util.data();
            atual = atual.substring(3, 10);
            if (atual.equals(data) && dia < fechamento) {
                valorTotal = valorTotal - (valor / parcela);
                valorTotalGasto = valorTotalGasto + (valor / parcela);
            }
        }
        tvRestante.setText(String.valueOf(valorTotal));
        tvGasto.setText(String.valueOf(valorTotalGasto));
        dbSalario.close();
    }
}
