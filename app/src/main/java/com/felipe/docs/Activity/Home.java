package com.felipe.docs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Cadastro.CadastraCartoes;
import com.felipe.docs.Cadastro.CadastraContas;
import com.felipe.docs.Cadastro.CadastraDocs;
import com.felipe.docs.Cadastro.CadastraEmails;
import com.felipe.docs.Cadastro.CadastraOutros;
import com.felipe.docs.Fragment.FragCartoes;
import com.felipe.docs.Fragment.FragContas;
import com.felipe.docs.Fragment.FragDocs;
import com.felipe.docs.Fragment.FragEmails;
import com.felipe.docs.Fragment.FragOutros;
import com.felipe.docs.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class Home extends AppCompatActivity {

    private Drawer result = null;
    private Toolbar toolbar;
    private DBConfig db;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        iniciar();
        Drawer(savedInstanceState);
    }

    private void iniciar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DBConfig(getBaseContext());
        db.open();
        bottomNavigationView = findViewById(R.id.bottom_navegation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.item_card) {
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CadastraCartoes()).commit();
                } else if (menuItem.getItemId() == R.id.item_docs) {
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CadastraDocs()).commit();
                } else if (menuItem.getItemId() == R.id.item_contas) {
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CadastraContas()).commit();
                } else if (menuItem.getItemId() == R.id.item_logins) {
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CadastraEmails()).commit();
                } else if (menuItem.getItemId() == R.id.item_outros) {
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CadastraOutros()).commit();
                }
                return false;
            }
        });
        Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CadastraCartoes()).commit();
    }

    private void Drawer(Bundle savedInstanceState) {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.black)
                .addProfiles(
                        new ProfileDrawerItem().withName(db.Fields.Nome).withEmail("").withIcon("")
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIdentifier(0).withIcon(R.drawable.home),
                        new SecondaryDrawerItem().withName("Documentos").withIdentifier(10).withIcon(R.drawable.docs),
                        new SecondaryDrawerItem().withName("Contas Bancarias").withIdentifier(20).withIcon(R.drawable.bank),
                        new SecondaryDrawerItem().withName("Cartões").withIdentifier(40).withIcon(R.drawable.card),
                        new SecondaryDrawerItem().withName("Emails, logins e senhas").withIdentifier(30).withIcon(R.drawable.emails),
                        new SecondaryDrawerItem().withName("Outros").withIdentifier(50).withIcon(R.drawable.outros)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Intent it;
                        switch ((int) drawerItem.getIdentifier()) {
                            case 0:
                                Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CadastraCartoes()).commit();
                                break;
                            case 10:
                                Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragDocs()).commit();
                                break;
                            case 20:
                                Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragContas()).commit();
                                break;
                            case 30:
                                Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragEmails()).commit();
                                break;
                            case 40:
                                Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragCartoes()).commit();
                                break;
                            case 50:
                                Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragOutros()).commit();
                                break;
                        }
                        return false;
                    }
                }).build();
    }
}
