package appectual.com.babycell.Libs.db.sqlite;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;


import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SQLiteDatabaseFactory {
	
	/**
	 * SQLite Open Helper
	 */
	private SQLiteDatabaseHelper helper;

	/**
	 * SQLite Database
	 */
	private SQLiteDatabase database ;
	
	@Inject public SQLiteDatabaseFactory() {
		
	}
	
	/**
	 * @throws NameNotFoundException
	 */
	public void init(Context context, boolean writable, boolean enableFK) throws NameNotFoundException {
		this.helper = new SQLiteDatabaseHelper(context, "database", null,
				1);
		// open and close database to init database (creation/upgrade)
		if ( writable ) {
			this.database = this.helper.getWritableDatabase();
			if ( enableFK ) {
				this.database.execSQL("PRAGMA foreign_keys = ON");
			}
		}
		else {
			this.database = this.helper.getReadableDatabase();
		}
	}

	/**
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return this.database;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if ( this.database != null && this.database.isOpen()) {
			this.database.close();
		}
	} 
	
	/**
	 * 
	 */
	public void closeDatabase() {
		this.database.close();
	}
}
