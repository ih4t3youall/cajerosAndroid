package ar.com.sourcesistemas.cajeros;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CapitanEmpanada on 04/02/2017.
 */

public class databaseActivity extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {



        return inflater.inflate(R.layout.content_database, container, false);

    }
}
