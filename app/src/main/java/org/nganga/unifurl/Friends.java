package org.nganga.unifurl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Friends extends Fragment {


    private RecyclerView friends_list;
    FriendsRecyclerAdapter adapter;
    public Friends() {
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
        View layout = inflater.inflate(R.layout.fragment_friends, container, false);
        friends_list = (RecyclerView) layout.findViewById(R.id.friends_list);

        return layout;
    }



}
