package com.example.appmeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appmeteo.ui.lista.Luogo;

public class LuogoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_activity_luogo;
    private Button bt_activity_luogo_ind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luogo);
        Log.d("LuogoActivity","onCreate");
        tv_activity_luogo = findViewById(R.id.tv_activity_luogo);
        bt_activity_luogo_ind = findViewById(R.id.bt_activity_luogo_ind);
        bt_activity_luogo_ind.setOnClickListener(this);
        Intent intent = getIntent();
        Luogo luogo = (Luogo) intent.getSerializableExtra("luogo");
        if(luogo != null){
            Log.d("LuogoActivity","luogo esiste");
            StringBuilder sb = new StringBuilder();
            if(luogo.getName() != null)
                sb.append("Nome località: ").append(luogo.getName()).append("\n");
            if(luogo.getLon() != null)
                sb.append("Longitudine: ").append(luogo.getLon()).append("\n");
            if(luogo.getLat() != null)
                sb.append("Latitudine: ").append(luogo.getLat()).append("\n");
            if(luogo.getMain() != null)
                sb.append("Condizioni meteo: ").append(luogo.getMain()).append("\n");
            if(luogo.getDescription() != null)
                sb.append("Descrizione condizioni meteo: ").append(luogo.getDescription()).append("\n");
            if(luogo.getTemp() != null)
                sb.append("Temperatura: ").append(luogo.getTemp()).append("°C\n");
            if(luogo.getTempFeels() != null)
                sb.append("Temperatura percepita: ").append(luogo.getTempFeels()).append("°C\n");
            if(luogo.getTempMax() != null)
                sb.append("Temperatura massima: ").append(luogo.getTempMax()).append("°C\n");
            if(luogo.getTempMin() != null)
                sb.append("Temperatura minima: ").append(luogo.getTempMin()).append("°C\n");
            if(luogo.getPressure() != null)
                sb.append("Pressione atmosferica: ").append(luogo.getPressure()).append(" hPa\n");
            if(luogo.getHumidity() != null)
                sb.append("Umidità: ").append(luogo.getHumidity()).append("%\n");
            if(luogo.getSeaLevel() != null)
                sb.append("Pressione atmosferica al livello del mare: ").append(luogo.getSeaLevel()).append(" hPa\n");
            if(luogo.getGroundLevel() != null)
                sb.append("Pressione atmosferica sul terreno: ").append(luogo.getGroundLevel()).append(" hPa\n");
            if(luogo.getWindSpeed() != null)
                sb.append("Velocità del vento: ").append(luogo.getWindSpeed()).append(" m/s\n");
            if(luogo.getWindDeg() != null)
                sb.append("Direzione del vento: ").append(luogo.getWindDeg()).append("°\n");
            if(luogo.getClouds() != null)
                sb.append("Nuvolosità: ").append(luogo.getClouds()).append("%\n");
            if(luogo.getCountry() != null)
                sb.append("Prefisso Stato in cui si trova questa località: ").append(luogo.getCountry());
            tv_activity_luogo.setText(sb.toString());
        }//if(luogo != null){
        else{
            Log.d("LuogoActivity","luogo = null");
            tv_activity_luogo.setText("Impossibile mostrare i dati richiesti");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LuogoActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LuogoActivity","onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LuogoActivity","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LuogoActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LuogoActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LuogoActivity","onDestroy");
    }

    @Override
    public void onClick(View view) {
        //View.OnClickListener
        switch(view.getId()){
            case R.id.bt_activity_luogo_ind:
                Intent indietro = new Intent(LuogoActivity.this,MainActivity.class);
                startActivity(indietro);
                break;
            default:
                break;
        }
    }
}