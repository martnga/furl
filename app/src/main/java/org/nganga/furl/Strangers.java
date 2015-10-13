package org.nganga.furl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Strangers extends Fragment implements StrangersRecyclerAdapter.ClickListener{
    RecyclerView strangers_list;
    StrangersRecyclerAdapter adapter;

    public Strangers() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_strangers, container, false);
        strangers_list = (RecyclerView) layout.findViewById(R.id.strangers_list);
        adapter = new StrangersRecyclerAdapter(getActivity(),getData());
        adapter.setClickListener(this);
        strangers_list.setAdapter(adapter);
        strangers_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<RecyclerData> getData(){

        List<RecyclerData> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_my_location_black_24dp,R.drawable.ic_my_location_black_24dp,R.drawable.ic_my_location_black_24dp,R.drawable.ic_my_location_black_24dp,R.drawable.ic_my_location_black_24dp};
        String[] userHash = {"Mol35r43wtly","Mqgqg46olly","Molbretqr43ly","Mo34543v43lly","M43543oller4y"};

        for(int i = 0; i < userHash.length && i < icons.length ; i++){

            RecyclerData current = new RecyclerData();
            current.iconId=icons[i];
            current.userNameHashText = userHash[i];
            data.add(current);
        }

        return data;
    }

    @Override
    public void itemClicked(View view, int position) {

        startActivity(new Intent(getActivity(), Settings.class));

    }
}

