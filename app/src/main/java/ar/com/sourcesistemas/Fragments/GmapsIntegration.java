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
public class GmapsIntegration extends Fragment
{


    public GmapsIntegration()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gmaps_integration, container, false);
    }

}
