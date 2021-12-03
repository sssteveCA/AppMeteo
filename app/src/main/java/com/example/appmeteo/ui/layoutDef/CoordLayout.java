package com.example.appmeteo.ui.layoutDef;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appmeteo.LuogoActivity;
import com.example.appmeteo.MainActivity;
import com.example.appmeteo.R;
import com.example.appmeteo.ui.utils.Utils;
import com.example.appmeteo.ui.volley.VolleyRequest;
import com.example.appmeteo.ui.lista.ListMaker;
import com.example.appmeteo.ui.lista.LuoghiAdapter;
import com.example.appmeteo.ui.lista.Luogo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CoordLayout implements View.OnClickListener, VolleyRequest.PassaRisultato, AdapterView.OnItemClickListener {

    private Context context;
    private Activity activity;
    private ProgressBar progressBar;
    private EditText et_coord_lat;
    private EditText et_coord_lon;
    private Button bt_coord;
    private ListView lv_coord;
    private String urlRequest; //url completo da passare alla JsonObjectRequest

    public CoordLayout(Context ctx, Activity act, ProgressBar pb){
        Log.d("CoordLayout","costruttore");
        context = ctx;
        activity = act;
        progressBar = pb;
        et_coord_lat = activity.findViewById(R.id.et_coord_lat);
        et_coord_lon = activity.findViewById(R.id.et_coord_lon);
        bt_coord = activity.findViewById(R.id.bt_coord);
        lv_coord = activity.findViewById(R.id.lv_coord);
        bt_coord.setOnClickListener(this);
        lv_coord.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //View.OnClickLIstener
        Log.d("CoordLayout","onClick");
        switch(view.getId()){
            case R.id.bt_coord:
                String lat = et_coord_lat.getText().toString();
                String lon = et_coord_lon.getText().toString();
                urlRequest = MainActivity.URL_W+"&lat="+lat+"&lon="+lon;
                Log.d("CoordLayout","urlRequest => "+urlRequest);
                if(Utils.connesso(context)){
                    VolleyRequest vr = new VolleyRequest(context,urlRequest,progressBar);
                    vr.passa = this;
                }
                else{
                    Toast.makeText(context,"Collegati ad internet e riprova",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object result) {
        //VolleyRequest.PassaRisultato
        JSONObject datiJson = (JSONObject) result;
        Log.d("coordLayout", "onSuccess result => "+datiJson.toString());
        try {
            int code = datiJson.getInt("cod");
            if(code == 200){
                Log.d("onResponse","code = 200");
                //la ricerca ha prodotto dei risultati
                //inserisco i dati ottenuti in una lista
                ListMaker<Luogo> listMaker = new ListMaker<>(datiJson);
                //ottengo la lista di oggetti "Luogo"
                List<Luogo> listaLuoghi = listMaker.getList();
                //Creo un layout per ogni elemento 'Luogo' all'interno della lista
                LuoghiAdapter luoghiAdapter = new LuoghiAdapter(context,R.layout.riga_luogo,listaLuoghi);
                //Visualizzo la lista degli elementi 'Luogo' all'interno della ListView
                lv_coord.setAdapter(luoghiAdapter);
            }
            else{
                Log.d("onResponse","code = "+code);
                //Errore
                String msg = "";
                if(datiJson.has("messaggio")){
                    msg = datiJson.getString("message");
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //AdapterView.OnItemClickListener
        Log.d("CoordLayout","onItemClick");
        switch(adapterView.getId()){
            case R.id.lv_coord:
                Luogo luogo = (Luogo)adapterView.getItemAtPosition(i);
                Intent luogoIntent = new Intent(context, LuogoActivity.class);
                luogoIntent.putExtra("luogo",luogo);
                context.startActivity(luogoIntent);
                break;
            default:
                break;
        }
    }
}
