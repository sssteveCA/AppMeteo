package com.example.appmeteo.ui.lista;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

public class Luogo implements Serializable {

    private String name; //nome del luogo
    private Float lon; //longitudine
    private Float lat; //latitudine
    private String main; //Condizioni del tempo(categoria)
    private String description; //Descrizione sulle condizioni del tempo
    private Float temp; //temperatura
    private Float temp_feels; //temperatura percepita
    private Float temp_max; //temperatura massima
    private Float temp_min; //temperatura minima
    private Integer pressure; //pressione atmosferica
    private Integer humidity; //umidità in %
    private Integer sea_level; //pressione atmosferica a livello del mare in ettoPascal
    private Integer grnd_level; //pressione atmosferica a livello del terreno in ettoPascal
    private Float windSpeed; //velocità del vento in metri/secondo
    private Integer windDeg; //direzione del vento
    private Integer clouds; //nuvolosità in %
    private String country; //paese in cui si trova questo luogo

    public Luogo(HashMap<String, Object> hm){
        Log.d("Luogo","costruttore");
        if(hm.containsKey("name"))this.name = (String)hm.get("name");
        if(hm.containsKey("lon"))this.lon = (float)hm.get("lon");
        if(hm.containsKey("lat"))this.lat = (float)hm.get("lat");
        if(hm.containsKey("main"))this.main = (String)hm.get("main");
        if(hm.containsKey("description"))this.description = (String)hm.get("description");
        if(hm.containsKey("temp"))this.temp = (float)hm.get("temp");
        if(hm.containsKey("temp_feels"))this.temp_feels = (float)hm.get("temp_feels");
        if(hm.containsKey("temp_max"))this.temp_max = (float)hm.get("temp_max");
        if(hm.containsKey("temp_min"))this.temp_min = (float) hm.get("temp_min");
        if(hm.containsKey("pressure"))this.pressure = (int)hm.get("pressure");
        if(hm.containsKey("humidity"))this.humidity = (int)hm.get("humidity");
        if(hm.containsKey("sea_level"))this.sea_level = (int)hm.get("sea_level");
        if(hm.containsKey("grnd_level"))this.grnd_level = (int)hm.get("grnd_level");
        if(hm.containsKey("windSpeed"))this.windSpeed = (float)hm.get("windSpeed");
        if(hm.containsKey("windDeg"))this.windDeg = (int)hm.get("windDeg");
        if(hm.containsKey("clouds"))this.clouds = (int)hm.get("clouds");
        if(hm.containsKey("country"))this.country = (String) hm.get("country");
    }

    public String getName(){return this.name;}
    public Float getLon(){return this.lon;}
    public Float getLat(){return this.lat;}
    public String getMain(){return this.main;}
    public String getDescription(){return this.description;}
    public Float getTemp(){return this.temp;}
    public Float getTempFeels(){return this.temp_feels;}
    public Float getTempMax(){return this.temp_max;}
    public Float getTempMin(){return this.temp_min;}
    public Integer getPressure(){return this.pressure;}
    public Integer getHumidity(){return this.humidity;}
    public Integer getSeaLevel(){return this.sea_level;}
    public Integer getGroundLevel(){return this.grnd_level;}
    public Float getWindSpeed() { return this.windSpeed; }
    public Integer getWindDeg() { return this.windDeg; }
    public Integer getClouds() { return this.clouds; }
    public String getCountry() { return this.country; }
}
