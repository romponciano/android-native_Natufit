package ponciano.romulo.natufit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // deletar arqs de pedido
        FuncoesGlobais.deletarTodosArq(getApplicationContext());

        // setando valores
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout cl = (FrameLayout) findViewById(R.id.content_frame);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        // icon hamburguer c/ efeito
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // menu de navegação
        navigationView.setNavigationItemSelectedListener(this);

        cl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment myFragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_saladas) {
            myFragment = new Saladas();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
        else if (id == R.id.nav_bebidas) {
            myFragment = new Bebidas();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
        else if (id == R.id.nav_zeroglutem) {
            myFragment = new ZeroGlutem();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
        else if (id == R.id.nav_congelados) {
            myFragment = new Congelados();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
        else if(id == R.id.nav_low_carb) {
            myFragment = new LowCarb();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
        else if(id == R.id.nav_zero) {
            myFragment = new ZeroAcucar();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
        else if (id == R.id.nav_fb) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/BNF4PJngwk7"));
                startActivity(intent);
            } catch(Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/natufitbp")));
            }
        }
        else if (id == R.id.nav_compartilhar) {
            onClickWhatsApp();
        }
        else if (id == R.id.nav_finalizar_pedido) {
            myFragment = new RevisarPedido();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
        else if (id == R.id.nav_sair) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmação");
            builder.setMessage("Você tem certeza que deseja sair?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // compartilhar no whatsapp
    public void onClickWhatsApp() {
        PackageManager pm=getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text =
                    "Conheça a NATUFIT e faça pedidos saudáveis!\n" +
                    "https://play.google.com/store/apps/details?id=ponciano.romulo.natufit";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp não esta instalado", Toast.LENGTH_LONG)
                    .show();
        }
    }
}