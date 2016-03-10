package appectual.com.babycell.Libs;


import appectual.com.babycell.Libs.db.sqlite.SQLiteDatabaseFactory;
import appectual.com.babycell.Libs.security.UserHelper;
import dagger.Module;

@Module(injects = {
        SQLiteDatabaseFactory.class,
        UserHelper.class,

    })
public class MCXModule {

}
