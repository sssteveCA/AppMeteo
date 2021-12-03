package com.example.appmeteo.ui.lista;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ListMaker<T> {

    private JSONObject json; //oggetto Json che contiene i risultati della ricerca
    private List<T> list; //lista che contiene la collezione di oggetti 'Luogo' ottenuti dalla ricerca
    private HashMap<String, Object> hashmap;

    public ListMaker(JSONObject json) {
        Log.d("ListMaker","costruttore");
        this.json = json;
        hashmap = new HashMap<>();
        list = new LinkedList<>();
        if(this.json.has("list")){
            //La ricerca ha dato come risultato più luoghi
            Log.d("ListMaker","json con lista di risultati");
            try {
                JSONArray listaLuoghi = this.json.getJSONArray("list");
                if(listaLuoghi.length() > 0){
                    for(int i = 0; i < listaLuoghi.length(); i++){
                        JSONObject luogo = listaLuoghi.getJSONObject(i);
                        if(luogo.has("name"))
                            hashmap.put("name",luogo.getString("name"));
                        if(luogo.has("coord")){
                            JSONObject coord = luogo.getJSONObject("coord");
                            if(coord.has("lon"))
                                hashmap.put("lon",Float.valueOf(coord.getString("lon")));
                            if(coord.has("lat"))
                                hashmap.put("lat",Float.valueOf(coord.getString("lat")));
                        }//if(luogo.has("coord")){
                        if(luogo.has("main")){
                            JSONObject main = luogo.getJSONObject("main");
                            if(main.has("temp"))
                                hashmap.put("temp",Float.valueOf(main.getString("temp")));
                            if(main.has("temp_feels"))
                                hashmap.put("temp_feels",Float.valueOf(main.getString("feels_like")));
                            if(main.has("temp_min"))
                                hashmap.put("temp_min",Float.valueOf(main.getString("temp_min")));
                            if(main.has("temp_max"))
                                hashmap.put("temp_max",Float.valueOf(main.getString("temp_max")));
                            if(main.has("pressure"))
                                hashmap.put("pressure",main.getInt("pressure"));
                            if(main.has("humidity"))
                                hashmap.put("humidity",main.getInt("humidity"));
                        }//if(luogo.has("main")){
                        if(luogo.has("wind")){
                            JSONObject wind = luogo.getJSONObject("wind");
                            if(wind.has("speed"))
                                hashmap.put("windSpeed",Float.valueOf(wind.getString("speed")));
                            if(wind.has("deg"))
                                hashmap.put("windDeg",wind.getInt("deg"));
                        }//if(luogo.has("wind")){
                        if(luogo.has("clouds")){
                            JSONObject clouds = luogo.getJSONObject("clouds");
                            if(clouds.has("today"))
                                hashmap.put("clouds",clouds.getInt("today"));
                        }//if(luogo.has("clouds")){
                        if(luogo.has("weather")){
                            JSONArray weather = luogo.getJSONArray("weather");
                            if(weather.length() > 0){
                                for(int j = 0; j < weather.length(); j++){
                                    JSONObject wObject = weather.getJSONObject(j);
                                    if(wObject.has("main"))
                                        hashmap.put("main",wObject.getString("main"));
                                    if(wObject.has("description"))
                                        hashmap.put("description",wObject.getString("description"));
                                }
                            }//if(weather.length() > 0){
                        }//if(luogo.has("weather")){
                        if(luogo.has("sys")){
                            JSONObject sys = luogo.getJSONObject("sys");
                            if(sys.has("country"))
                                hashmap.put("country",sys.getString("country"));
                        }//if(this.json.has("sys")){
                        Luogo l = new Luogo(hashmap);
                        list.add((T) l);
                    }//for(int i = 0; i < listaLuoghi.length(); i++){
                }//if(listaLuoghi.length() > 0){
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }//if(this.json.has("list")){
        else{
            Log.d("ListMaker","Json con unico risultato");
            try {
                if(this.json.has("name"))
                    hashmap.put("name", this.json.getString("name"));
                if(this.json.has("coord")){
                    JSONObject coord = this.json.getJSONObject("coord");
                    if(coord.has("lon"))
                        hashmap.put("lon",Float.valueOf(coord.getString("lon")));
                    if(coord.has("lat"))
                        hashmap.put("lat",Float.valueOf(coord.getString("lat")));
                }//if(this.json.has("coord")){
                if(this.json.has("main")){
                    JSONObject main = this.json.getJSONObject("main");
                    if(main.has("temp"))
                        hashmap.put("temp",Float.valueOf(main.getString("temp")));
                    if(main.has("temp_feels"))
                        hashmap.put("temp_feels",Float.valueOf(main.getString("feels_like")));
                    if(main.has("temp_min"))
                        hashmap.put("temp_min",Float.valueOf(main.getString("temp_min")));
                    if(main.has("temp_max"))
                        hashmap.put("temp_max",Float.valueOf(main.getString("temp_max")));
                    if(main.has("pressure"))
                        hashmap.put("pressure",main.getInt("pressure"));
                    if(main.has("humidity"))
                        hashmap.put("humidity",main.getInt("humidity"));
                    if(main.has("sea_level"))
                        hashmap.put("sea_level",main.getInt("sea_level"));
                    if(main.has("grnd_level"))
                        hashmap.put("grnd_level",main.getInt("grnd_level"));
                }//if(this.json.has("main")){
                if(this.json.has("wind")){
                    JSONObject wind = this.json.getJSONObject("wind");
                    if(wind.has("speed"))
                        hashmap.put("windSpeed",Float.valueOf(wind.getString("speed")));
                    if(wind.has("deg"))
                        hashmap.put("windDeg",wind.getInt("deg"));
                }//if(this.json.has("wind")){
                if(this.json.has("clouds")){
                    JSONObject clouds = this.json.getJSONObject("clouds");
                    if(clouds.has("today"))
                        hashmap.put("clouds",clouds.getInt("today"));
                }//if(this.json.has("clouds")){
                if(this.json.has("weather")){
                    JSONArray weather = this.json.getJSONArray("weather");
                    if(weather.length() > 0){
                        for(int j = 0; j < weather.length(); j++){
                            JSONObject wObject = weather.getJSONObject(j);
                            if(wObject.has("main"))
                                hashmap.put("main",wObject.getString("main"));
                            if(wObject.has("description"))
                                hashmap.put("description",wObject.getString("description"));
                        }//for(int j = 0; j < weather.length(); j++){
                    }//if(weather.length() > 0){
                }//if(this.json.has("weather")){
                if(this.json.has("sys")){
                    JSONObject sys = this.json.getJSONObject("sys");
                    if(sys.has("country"))
                        hashmap.put("country",sys.getString("country"));
                }//if(this.json.has("sys")){
                Luogo l = new Luogo(hashmap);
                list.add((T) l);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }//public ListMaker(JSONObject json)

    public JSONObject getJson() {
        return this.json;
    }

    public HashMap<String, Object> getHashmap(){
        return this.hashmap;
    }

    public List<T> getList() {
        return this.list;
    }
}

