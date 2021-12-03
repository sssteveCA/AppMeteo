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

public class CodicePostLayout implements SearchView.OnQueryTextListener, VolleyRequest.PassaRisultato, AdapterView.OnItemClickListener {
    private Context context;
    private Activity activity;
    private ProgressBar progressBar;
    private SearchView sv_codPost;
    private ListView lv_codPost;
    private String urlRequest; //url completo da passare alla JsonObjectRequest

    public CodicePostLayout(Context ctx, Activity act,ProgressBar pb){
        Log.d("CodicePostLayout","costruttore");
        context = ctx;
        activity = act;
        progressBar = pb;
        sv_codPost = activity.findViewById(R.id.sv_codPost);
        lv_codPost = activity.findViewById(R.id.lv_codPost);
        sv_codPost.setOnQueryTextListener(this);
        lv_codPost.setOnItemClickListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        //SearchView.OnQueryTextListener
        urlRequest = MainActivity.URL_W+"&zip="+s;
        Log.d("CodicePostLayout","onQueryTextSubmit => "+urlRequest);
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
    public boolean onQueryTextChange(String s) {
        //Log.d("CodicePostLayout","onQueryTextChange");
        //SearchView.OnQueryTextListener
        return false;
    }

    @Override
    public void onSuccess(Object result) {
        //VolleyResult.PassaRisultato
        JSONObject datiJson = (JSONObject) result;
        Log.d("CodicePostLayout", "onSuccess result => "+datiJson.toString());
        try {
            int code = datiJson.getInt("cod");
            if(code == 200){
                Log.d("onResponse","code = 200");
                //la ricerca ha prodotto dei risultati
                ListMaker<Luogo> listMaker = new ListMaker<>(datiJson);
                List<Luogo> listaLuoghi = listMaker.getList();
                LuoghiAdapter luoghiAdapter = new LuoghiAdapter(context,R.layout.riga_luogo,listaLuoghi);
                lv_codPost.setAdapter(luoghiAdapter);
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
        Log.d("CodicePostLayout","onItemClick");
        switch(adapterView.getId()){
            case R.id.lv_codPost:
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
