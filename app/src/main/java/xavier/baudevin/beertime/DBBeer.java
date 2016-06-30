package xavier.baudevin.beertime;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBBeer extends SQLiteOpenHelper {

    private static final String TABLE_BEER = "tBeer";
    private static final String COL_ID = "IdBeer";
    private static final String COL_NAME = "Name";
    private static final String COL_TYPE = "Type";
    private static final String COL_ML = "Ml";
    private static final String COL_ALCOOL = "Alcool";
    private static final String COL_RATING = "Rate";
    private static final String COL_COMMENT = "Comment";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_BEER + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_TYPE + " TEXT NOT NULL, "
            + COL_RATING + " INTEGER NOT NULL, "
            + COL_ML + " INTEGER NOT NULL, "
            + COL_ALCOOL + " REAL NOT NULL, "
            + COL_COMMENT + " TEXT NOT NULL);";

    public DBBeer(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_BEER + ";");
        onCreate(db);
    }

}