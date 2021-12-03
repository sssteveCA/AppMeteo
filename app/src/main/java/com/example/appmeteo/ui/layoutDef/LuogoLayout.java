package com.example.appmeteo.ui.layoutDef;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

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


public class LuogoLayout implements SearchView.OnQueryTextListener, VolleyRequest.PassaRisultato, AdapterView.OnItemClickListener {

    private Context context;
    private Activity activity;
    private SearchView sv_luogo;
    private ProgressBar progressBar;
    private ListView lv_luoghi;
    private String urlRequest; //url completo da passare alla JsonObjectRequest

    public LuogoLayout(Context ctx, Activity act, ProgressBar pb){
        Log.d("LuogoLayout","costruttore");
        context = ctx;
        activity = act;
        progressBar = pb;
        sv_luogo = activity.findViewById(R.id.sv_luoghi);
        lv_luoghi = activity.findViewById(R.id.lv_luoghi);
        sv_luogo.setQueryHint("Inserisci una località e premi Invio per iniziare la ricerca");
        sv_luogo.setOnQueryTextListener(this);
        lv_luoghi.setOnItemClickListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //SearchView.OnQueryTextListener
        urlRequest = MainActivity.URL_W+"&q="+query;
        //Log.d("LuogoLayout","onQueryTextSubmit => "+urlRequest);
        if(Utils.connesso(context)){
            VolleyRequest vr = new VolleyRequest(context,urlRequest,progressBar);
            vr.passa = this;
        }
        else{
            Toast.makeText(context,"Collegati ad internet e riprova",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //SearchView.OnQueryTextListener
        //Log.d("LuogoLayout","onQueryTextChange");
        return false;
    }

    @Override
    public void onSuccess(Object result) {
        //VolleyRequest.PassaRisultato
        JSONObject datiJson = (JSONObject) result;
        Log.d("luogoLayout", "onSuccess result => "+datiJson.toString());
        try {
            int code = datiJson.getInt("cod");
            if(code == 200){
                Log.d("onResponse","code = 200");
                //la ricerca ha prodotto dei risultati
                ListMaker<Luogo> listMaker = new ListMaker<>(datiJson);
                List<Luogo> listaLuoghi = listMaker.getList();
                LuoghiAdapter luoghiAdapter = new LuoghiAdapter(context,R.layout.riga_luogo,listaLuoghi);
                lv_luoghi.setAdapter(luoghiAdapter);
            }
            else{
                Log.d("onResponse","code = "+code);
                //Errore
                String msg = "";
                if(datiJson.has("message")){
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
        Log.d("LuogoLayout","onItemClick");
        switch(adapterView.getId()){
            case R.id.lv_luoghi:
                Log.d("onItemClick","lv_luoghi");
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
