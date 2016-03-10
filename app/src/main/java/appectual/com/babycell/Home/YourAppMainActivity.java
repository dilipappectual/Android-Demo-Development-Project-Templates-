package appectual.com.babycell.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import javax.inject.Inject;

import appectual.com.babycell.BuildConfig;
import appectual.com.babycell.Libs.security.SecurityUtils;
import appectual.com.babycell.Libs.security.UserHelper;
import appectual.com.babycell.Libs.security.UserSessionCallback;
import appectual.com.babycell.NavigationController;
import appectual.com.babycell.R;
import appectual.com.babycell.UI.navdrawer.NavigationDrawerFragment;
import appectual.com.babycell.YourApplication;

public class YourAppMainActivity extends AppCompatActivity implements UserSessionCallback {

    @Inject
    NavigationController navController;

    @Inject
    UserHelper mUserHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((YourApplication) getApplication()).inject(this);
         setContentView(R.layout.main);

        // toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        // For debug
        if ( BuildConfig.DEBUG) {
            Log.d(YourApplication.LOG_TAG, "HashKey: " + SecurityUtils.logHashKey(this));
        }



        // init fragment
        if (savedInstanceState == null) {
            this.navController.goHomeFragment(this);

        }


    }

    @Override
    public void onBackPressed() {
        NavigationDrawerFragment navigationDrawerFragment = findNavDrawerFragment();
        if ( navigationDrawerFragment == null || !navigationDrawerFragment.onBackPressed()) {
            // See bug: http://stackoverflow.com/questions/13418436/android-4-2-back-stack-behaviour-with-nested-fragments/14030872#14030872
            // If the fragment exists and has some back-stack entry
            FragmentManager fm = getSupportFragmentManager();
            Fragment currentFragment = fm.findFragmentById(R.id.content_frame);
            if (currentFragment != null && currentFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                currentFragment.getChildFragmentManager().popBackStack();
            }
            // Else, nothing in the direct fragment back stack
            else {
                if ( currentFragment != null && !NavigationController.HOME_FRAGMENT_TAG.equals(currentFragment.getTag())) {
                    this.navController.goHomeFragment(this);
                }
                else {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        //mAdView.destroy();
        super.onDestroy();

    }

    @Override
    public void onLogin() {
        YourAppNavigationFragment navFragment = findNavDrawerFragment();
        navFragment.setupHeaderView();
    }

    public YourAppNavigationFragment findNavDrawerFragment() {
        return (YourAppNavigationFragment) getSupportFragmentManager().findFragmentByTag("navdrawer_fragment");
    }

    @Override
    public void onLogout() {
        YourAppNavigationFragment navigationDrawerFragment = findNavDrawerFragment();
        navigationDrawerFragment.setupHeaderView();
        navigationDrawerFragment.showPrimaryMenu();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        NavigationDrawerFragment navigationDrawerFragment = findNavDrawerFragment();
        return navigationDrawerFragment.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


}
