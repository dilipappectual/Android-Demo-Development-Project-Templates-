package appectual.com.babycell;


import appectual.com.babycell.Home.LoginActivity;
import appectual.com.babycell.Home.MainFragment;
import appectual.com.babycell.Home.YourAppMainActivity;
import appectual.com.babycell.Home.YourAppNavigationFragment;
import appectual.com.babycell.Libs.MCXModule;
import dagger.Module;

@Module(
	    injects = {
            YourApplication.class,

			YourAppMainActivity.class,
			LoginActivity.class,
			YourAppNavigationFragment.class,


			MainFragment.class,
	    },
	    includes = {
	    	MCXModule.class
	    },
	    overrides=true
	)
public class YourAppModule {

}
