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

public class AreaRetLayout implements View.OnClickListener, VolleyRequest.PassaRisultato, AdapterView.OnItemClickListener{

    private Context context;
    private Activity activity;
    private ProgressBar progressBar;
    private EditText et_areaR_xl;
    private EditText et_areaR_yb;
    private EditText et_areaR_xr;
    private EditText et_areaR_yt;
    private EditText et_areaR_zoom;
    private Button bt_areaR;
    private ListView lv_areaR;
    private String urlRequest; //url completo da passare alla JsonObjectRequest

    public AreaRetLayout(Context ctx, Activity act, ProgressBar pb){
        Log.d("AreaRetLayout","costruttore");
        context = ctx;
        activity = act;
        progressBar = pb;
        et_areaR_xl = activity.findViewById(R.id.et_areaR_xl);
        et_areaR_yb = activity.findViewById(R.id.et_areaR_yb);
        et_areaR_xr = activity.findViewById(R.id.et_areaR_xr);
        et_areaR_yt = activity.findViewById(R.id.et_areaR_yt);
        et_areaR_zoom = activity.findViewById(R.id.et_areaR_zoom);
        bt_areaR = activity.findViewById(R.id.bt_areaR);
        lv_areaR = activity.findViewById(R.id.lv_areaR);
        bt_areaR.setOnClickListener(this);
        lv_areaR.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //View.OnClickListener
        switch(view.getId()){
            case R.id.bt_areaR:
                Log.d("AreaRetLayout","bt_areaR onClick");
                String lon_left = et_areaR_xl.getText().toString();
                String lat_bottom = et_areaR_yb.getText().toString();
                String lon_right = et_areaR_xr.getText().toString();
                String lat_top = et_areaR_yt.getText().toString();
                String zoom = et_areaR_zoom.getText().toString();
                urlRequest = MainActivity.URL_C+"&bbox="+lon_left+","+lat_bottom+","+lon_right+","+lat_top+","+zoom;
                Log.d("AreaRetLayout","urlRequest => "+urlRequest);
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
        //VolleyResult.PassaRisultato
        JSONObject datiJson = (JSONObject) result;
        Log.d("CodicePostLayout", "onSuccess result => "+datiJson.toString());
        try {
            if(datiJson.has("cod")){
                //se ha il codice di stato HTTP
                int code = datiJson.getInt("cod");
                if(code == 200){
                    Log.d("onResponse","code = 200");
                    //la ricerca ha prodotto dei risultati
                    ListMaker<Luogo> listMaker = new ListMaker<>(datiJson);
                    List<Luogo> listaLuoghi = listMaker.getList();
                    LuoghiAdapter luoghiAdapter = new LuoghiAdapter(context,R.layout.riga_luogo,listaLuoghi);
                    lv_areaR.setAdapter(luoghiAdapter);
                }
                else {
                    //Errore
                    String msg = "";
                    if (datiJson.has("message")) {
                        msg = datiJson.getString("message");
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    }
                }
            }//if(datiJson.has("cod")){
            else{
                //l'array JSON è vuoto
                Toast.makeText(context,"La ricerca non ha prodotto alcun risultato",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //AdapterView.OnItemClickListener
        Log.d("AreaRetLayout","onItemClick");
        switch(adapterView.getId()){
            case R.id.lv_areaR:
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
