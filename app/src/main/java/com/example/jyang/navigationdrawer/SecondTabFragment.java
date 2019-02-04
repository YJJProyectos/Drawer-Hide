package com.example.jyang.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jyang.navigationdrawer.models.Town;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SecondTabFragment extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String json =
                "{"   +
                    "id: 0," +
                    "city: {" +
                        "id: 1," +
                        "name: 'London'" +
                    "}," +
                    "cities: [{" +
                        "id: 1," +
                        "name: 'Roma'" +
                        "}," +
                        "{" +
                            "id: 1," +
                            "name: 'Helsinki'" +
                        "}" +
                        "]," +
                    "name: 'Algo'" +
                "}";

        Gson gson = new GsonBuilder().create();
        Town town = gson.fromJson(json, Town.class);
        Toast.makeText(getContext(), town.getId() + " " + town.getName() + " " + town.getCity().getName() + " " + town.getCiudades().get(0).getName(), Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_tab_fragment, container, false);
    }
}
