package ar.com.sourcesistemas.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.sourcesistemas.Activities.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatabaseInteraction extends Fragment
{


    public DatabaseInteraction()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_database_interaction, container, false);
    }

}
