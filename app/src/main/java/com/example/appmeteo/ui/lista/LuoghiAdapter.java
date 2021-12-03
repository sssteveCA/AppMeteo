package com.example.appmeteo.ui.lista;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appmeteo.R;

import java.util.List;

public class LuoghiAdapter extends ArrayAdapter<Luogo> {
    public LuoghiAdapter(@NonNull Context context, int resource, @NonNull List<Luogo> objects) {
        super(context, resource, objects);
        Log.d("Luoghi Adapter","costruttore");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Log.d("Luoghi Adapter","getView");
        return customGetView(position,convertView,parent);
    }

    public View customGetView(int pos, View cv, ViewGroup vg){
        ViewHolder vh = null;
        if(cv == null){
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cv = li.inflate(R.layout.riga_luogo,null);
            vh = new ViewHolder();
            vh.tv_riga_luogo = cv.findViewById(R.id.tv_riga_luogo);
            cv.setTag(vh);
        }//if(cv == null){
        else{
            vh = (ViewHolder) cv.getTag();
        }
        Luogo luogo = getItem(pos);
        vh.tv_riga_luogo.setText(luogo.getName());
        return cv;
    }

    private class ViewHolder{
        public TextView tv_riga_luogo;
    }
}
