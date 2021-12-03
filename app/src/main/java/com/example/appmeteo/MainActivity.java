package com.example.appmeteo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;

import com.example.appmeteo.ui.dialog.MyDialog;
import com.example.appmeteo.ui.home.HomeFragment;
import com.example.appmeteo.ui.layoutDef.AreaCircLayout;
import com.example.appmeteo.ui.layoutDef.AreaRetLayout;
import com.example.appmeteo.ui.layoutDef.CodicePostLayout;
import com.example.appmeteo.ui.layoutDef.CoordLayout;
import com.example.appmeteo.ui.layoutDef.LuogoLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmeteo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ConstraintLayout mainLayout; //layout su cui vengono inserite determinate View a seconda dell'opzione di ricerca selezionata
    private View viewInflate; //View da inserire all'interno di mainLayout
    private LayoutInflater inflater;
    private ProgressBar progressBar;
    public final static String API_KEY = "b1ff0fb01e94bcb378697e44b5b98f68";
    public final static String UNITS = "metric"; //temperatura in gradi centigradi
    public final static String LANG = "it";
    public final static String URL_W = "http://api.openweathermap.org/data/2.5/weather?appid="+API_KEY+"&units="+UNITS+"&lang="+LANG; //parte invariata (endpoint weather) dell'url da passare alla richiesta HTTP
    public final static String URL_C = "http://api.openweathermap.org/data/2.5/box/city?appid="+API_KEY+"&units="+UNITS+"&lang="+LANG; //parte invariata (endpoint city) dell'url da passare alla richiesta HTTP
    public final static String URL_F = "http://api.openweathermap.org/data/2.5/find?appid="+API_KEY+"&units="+UNITS+"&lang="+LANG; //parte invariata (endpoint find) dell'url da passare alla richiesta HTTP

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","onCreate");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        mainLayout = findViewById(R.id.contentLayout);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }//protected void onCreate(Bundle savedInstanceState) {

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.main, menu);
        return true;*/
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //NavigationView.OnNavigationItemSelectedListener
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch(item.getItemId()){
            case R.id.nav_home:
                if(mainLayout != null && viewInflate != null){
                    mainLayout.removeView(viewInflate);
                }
                HomeFragment.textView.setText("Benvenuto. Con questa app puoi vedere le previsioni meteo di una località a tua scelta!");
                break;
            case R.id.nav_luogo: //Ricerca per luogo
                Log.d("onNavigationItemSel","nav_luogo");
                HomeFragment.textView.setText("");
                if(mainLayout != null && viewInflate != null){
                    mainLayout.removeView(viewInflate);
                }
                viewInflate = inflater.inflate(R.layout.luoghi_layout,mainLayout,false);
                mainLayout.addView(viewInflate);
                LuogoLayout luogo = new LuogoLayout(MainActivity.this, this,progressBar);
                break;
            case R.id.nav_coordinate: //ricerca per coordinate geografiche
                Log.d("onNavigationItemSel","nav_cordinate");
                HomeFragment.textView.setText("");
                if(mainLayout != null && viewInflate != null){
                    mainLayout.removeView(viewInflate);
                }
                viewInflate = inflater.inflate(R.layout.coordinate_layout,mainLayout,false);
                mainLayout.addView(viewInflate);
                CoordLayout coord = new CoordLayout(MainActivity.this,this,progressBar);
                break;
            case R.id.nav_zip: //Ricerca per codice postale
                Log.d("onNavigationItemSel","nav_zip");
                HomeFragment.textView.setText("");
                if(mainLayout != null && viewInflate != null){
                    mainLayout.removeView(viewInflate);
                }
                viewInflate = inflater.inflate(R.layout.codicepost_layout,mainLayout,false);
                mainLayout.addView(viewInflate);
                CodicePostLayout codicePost = new CodicePostLayout(MainActivity.this,this,progressBar);
                break;
            case R.id.nav_zoneR: //Ricerca in un'area rettangolare
                Log.d("onNavigationItemSel","nav_zoneR");
                HomeFragment.textView.setText("");
                if(mainLayout != null && viewInflate != null){
                    mainLayout.removeView(viewInflate);
                }
                viewInflate = inflater.inflate(R.layout.arearettangolo_layout,mainLayout,false);
                mainLayout.addView(viewInflate);
                AreaRetLayout aRet = new AreaRetLayout(MainActivity.this,this,progressBar);
                break;
            case R.id.nav_zoneC: //Ricerca in un area circolare
                Log.d("onNavigationItemSel","nav_zoneC");
                HomeFragment.textView.setText("");
                if(mainLayout != null && viewInflate != null){
                    mainLayout.removeView(viewInflate);
                }
                viewInflate = inflater.inflate(R.layout.areacerchio_layout,mainLayout,false);
                mainLayout.addView(viewInflate);
                AreaCircLayout aCirc = new AreaCircLayout(MainActivity.this,this,progressBar);
                break;
            case R.id.nav_exit://esci dall'app
                MyDialog myd = new MyDialog(this,"Esci dall'app","Sei sicuro di voler uscire?",R.drawable.ic_warning);
                myd.setDialog();
                break;
            default:
                break;
        }
        return false;
    }//public boolean onNavigationItemSelected(@NonNull MenuItem item) {
}