package ar.com.sourcesistemas.Services;

import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import ar.com.sourcesistemas.Activities.MainActivity;
import ar.com.sourcesistemas.Activities.R;


/**
 * Created by CapitanEmpanada on 04/02/2017.
 */

public class DrawerLogin extends MainActivity
{
    View headerView;

    public DrawerLogin(NavigationView context)
    {
        headerView =context.getHeaderView(0);
    }

    public void setDrawer(Profile profile)
    {
        if(profile != null)
        {
            setProfileName(profile.getCurrentProfile().getName(), headerView);
            setProfilePicture(profile.getId(), headerView);
        }else
        {
            setProfileName("Not Logged", headerView);
            setProfilePicture("", headerView);
        }

    }

    private void setProfileName(String name, View view)
    {

        TextView nameHeader = (TextView) view.findViewById(R.id.profileFullname);
        nameHeader.setText(name);
        Log.i(TAG_FACEBOOK, "Configurando DrawLogin - " + name);
    }

    private void setProfilePicture(String id, View view)
    {
        ProfilePictureView profilePic;
        profilePic = (ProfilePictureView) view.findViewById(R.id.profilePicture);
        profilePic.setProfileId(id);
    }
}
