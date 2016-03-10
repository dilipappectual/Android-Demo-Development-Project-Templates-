package appectual.com.babycell.Home;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import appectual.com.babycell.Libs.security.UserHelper;
import appectual.com.babycell.NavigationController;
import appectual.com.babycell.R;
import appectual.com.babycell.UI.navdrawer.NavDrawerActivityConfiguration;
import appectual.com.babycell.UI.navdrawer.NavdrawerHeaderArrowView;
import appectual.com.babycell.UI.navdrawer.NavigationDrawerFragment;

public class YourAppNavigationFragment extends NavigationDrawerFragment {

    @Inject
    NavigationController navController;

    @Inject
    UserHelper mUserHelper;

    private boolean mPrimaryMenuDisplayed = true;

    private boolean mHeaderArrowOpened = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( savedInstanceState != null ) {
            mPrimaryMenuDisplayed = savedInstanceState.getBoolean("primaryMenuDisplayed");
        }
    }

    @Override
    protected NavDrawerActivityConfiguration createNavigurationConfiguration() {
        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration.Builder()
                .layout(R.layout.navdrawer)
                .navigationViewId(R.id.navigation)
                .drawerLayoutId(R.id.drawer_layout)
                .toolbarId(R.id.toolbar)
                .build();
        navDrawerActivityConfiguration.setUpdateTitleWhenMenuItemClick(R.id.navdrawer_tutorial);
        navDrawerActivityConfiguration.setUpdateTitleWhenMenuItemClick(R.id.navdrawer_friends);
        navDrawerActivityConfiguration.setUpdateTitleWhenMenuItemClick(R.id.navdrawer_airport);
        navDrawerActivityConfiguration.setUpdateTitleWhenMenuItemClick(R.id.navdrawer_map);
        navDrawerActivityConfiguration.setUpdateTitleWhenMenuItemClick(R.id.navdrawer_aroundme);


        return navDrawerActivityConfiguration;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if ( !this.mPrimaryMenuDisplayed) {
            mHeaderArrowOpened = true ;
        }

        if ( this.mPrimaryMenuDisplayed ) {
            createMenu(R.menu.navigation_drawer_menu);
        }
        else {
            createMenu(R.menu.navigation_drawer_secondarymenu);

        }
    }

    protected void createMenu( @MenuRes int menu ) {
        this.getNavigationView().getMenu().clear();
        this.getNavigationView().inflateMenu(menu);
        setupHeaderView();
    }

    public void setupHeaderView() {

        View headerView = this.getNavigationView().getHeaderView(0);
        ViewGroup connectedViewGroup = (ViewGroup) headerView.findViewById(R.id.nadrawer_loginheader_connected_viewgroup);
        TextView nameView = (TextView) headerView.findViewById(R.id.nadrawer_loginheader_name);
        TextView mailView = (TextView) headerView.findViewById(R.id.nadrawer_loginheader_email);
        NavdrawerHeaderArrowView arrowView = (NavdrawerHeaderArrowView) headerView.findViewById(R.id.navdrawer_loginheader_arrow);
        ViewGroup disconnectedViewGroup = (ViewGroup) headerView.findViewById(R.id.nadrawer_loginheader_disconnected_viewgroup);
        TextView loginView = (TextView) headerView.findViewById(R.id.nadrawer_loginheader_login);

        if ( mUserHelper.getCurrentUser() == null ) {
            loginView.setOnClickListener(v -> {
                if ( mUserHelper.getCurrentUser() == null ) {
                    navController.showLogin(YourAppNavigationFragment.this.getActivity());
                }
            });
            connectedViewGroup.setVisibility(View.GONE);
            disconnectedViewGroup.setVisibility(View.VISIBLE);
            arrowView.setOnClickListener(null);
        }
        else {
            nameView.setText(mUserHelper.getCurrentUser().getDisplayName());
            mailView.setText(mUserHelper.getCurrentUser().getMail());
            loginView.setOnClickListener(null);
            arrowView.setExpanded(mHeaderArrowOpened);
            arrowView.setOnClickListener(v -> {
                NavdrawerHeaderArrowView view = (NavdrawerHeaderArrowView) v;
                mHeaderArrowOpened = view.switchExpandedState();
                if (view.isExpanded()) {
                    showSecondaryMenu();
                } else {
                    showPrimaryMenu();
                }
            });

            connectedViewGroup.setVisibility(View.VISIBLE);
            disconnectedViewGroup.setVisibility(View.GONE);
        }
    }

    protected void showSecondaryMenu() {
        this.mHeaderArrowOpened = true;
        this.getNavigationView().getMenu().clear();
        this.getNavigationView().inflateMenu(R.menu.navigation_drawer_secondarymenu);
        this.mPrimaryMenuDisplayed = false;
    }

    protected void showPrimaryMenu() {
        this.mHeaderArrowOpened = false;
        this.getNavigationView().getMenu().clear();
        this.getNavigationView().inflateMenu(R.menu.navigation_drawer_menu);
        this.mPrimaryMenuDisplayed = true;
    }

    @Override
    protected void onNavItemSelected(int menuItemId ) {
        switch (menuItemId) {
            /* Here we match id of menu navigation drawer and create as per our fragment */
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("primaryMenuDisplayed", this.mPrimaryMenuDisplayed);
    }
}
