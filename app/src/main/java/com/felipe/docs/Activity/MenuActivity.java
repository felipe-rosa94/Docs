package com.felipe.docs.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.felipe.docs.R;

public class MenuActivity extends AppCompatActivity {

    private ImageButton btnFinancas;
    private ImageButton btnDocs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnFinancas = findViewById(R.id.btn_financas);
        btnDocs = findViewById(R.id.btn_docs);

        btnFinancas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Etapa2.class));
            }
        });

        btnDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Home.class));
            }
        });
    }
}
