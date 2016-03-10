package appectual.com.babycell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.transition.TransitionInflater;

import javax.inject.Inject;
import javax.inject.Singleton;

import appectual.com.babycell.Home.LoginActivity;
import appectual.com.babycell.Home.MainFragment;
import appectual.com.babycell.Home.YourAppMainActivity;
import appectual.com.babycell.UI.changelog.ChangeLogHelper;
import appectual.com.babycell.UI.navdrawer.NavigationDrawerFragment;

@Singleton
public class NavigationController {

    public static final String HOME_FRAGMENT_TAG = "home";

	@Inject public NavigationController() {
		
	}
	
	public void startAppRating(Context context) {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
	}

    public void goHomeFragment( YourAppMainActivity activity) {
        NavigationDrawerFragment fragment = activity.findNavDrawerFragment();
        fragment.setTitleWithDrawerTitle();
        fragment.resetSelection();
        MainFragment fg = new MainFragment();
        addFragmentTransition(activity, fg);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fg, HOME_FRAGMENT_TAG).commit();
    }

    public void addFragmentTransition(Activity activity, Fragment fg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fg.setEnterTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.slide_top));
            fg.setExitTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.slide_right));
        }
    }





    public void showLogin(FragmentActivity activity) {
        Intent oIntent = new Intent(activity, LoginActivity.class);
        activity.startActivity(oIntent);
        // no animation
        activity.overridePendingTransition(0,0);
    }
}
