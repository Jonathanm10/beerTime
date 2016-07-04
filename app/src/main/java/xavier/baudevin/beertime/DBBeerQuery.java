/**
 * @autor:      Xavier Baudevin <xavier.bau@hotmail.fr>
 * @date:       04/02/16
 * @project:    BeerTime
 * @desciption: the query to interact with the db
 */

package xavier.baudevin.beertime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DBBeerQuery {

    // set the db
    private static final int VERSION_BDD = 4;
    private static final String NAME_BDD = "beer.db";
    private static final String TABLE_BEER = "tBeer";

    private static final String COL_ID = "IdBeer";
    private static final int NUM_COL_ID = 0;

    private static final String COL_NAME = "Name";
    private static final int NUM_COL_NAME = 1;

    private static final String COL_TYPE = "Type";
    private static final int NUM_COL_TYPE = 2;

    private static final String COL_ML = "Ml";
    private static final int NUM_COL_ML = 4;

    private static final String COL_ALCOOL = "Alcool";
    private static final int NUM_COL_ALCOOL = 5;

    private static final String COL_RATING = "Rate";
    private static final int NUM_COL_RATING = 3;

    private static final String COL_COMMENT = "Comment";
    private static final int NUM_COL_COMMENT = 6;

    private SQLiteDatabase bdd;

    private DBBeer DBBeer;

    public DBBeerQuery(Context context) {
        DBBeer = new DBBeer(context, NAME_BDD, null, VERSION_BDD);
    }

    public void open() {
        bdd = DBBeer.getWritableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public long insertBeer(tBeer beer) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, beer.getName());
        values.put(COL_TYPE, beer.getType());
        values.put(COL_ML, beer.getMl());
        values.put(COL_ALCOOL, beer.getAlcool());
        values.put(COL_RATING, beer.getRating());
        values.put(COL_COMMENT, beer.getComment());
        return bdd.insert(TABLE_BEER, null, values);
    }

    public int updateLivre(int id, tBeer beer) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, beer.getName());
        values.put(COL_TYPE, beer.getType());
        values.put(COL_ML, beer.getMl());
        values.put(COL_ALCOOL, beer.getAlcool());
        values.put(COL_RATING, beer.getRating());
        values.put(COL_COMMENT, beer.getComment());
        return bdd.update(TABLE_BEER, values, COL_ID + " = " + id, null);
    }

    public int deleteBeerID(int idBeer) {
        return this.bdd.delete(TABLE_BEER, COL_ID + "=" + idBeer, null);
    }

    public tBeer getBeerWithId(String idBeer) {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_BEER + " WHERE " + COL_ID + " = " + idBeer, null);
        return cursorToBeer(c);
    }

    public List<tBeer> getBeerList() {
        Cursor c =  bdd.rawQuery("SELECT * FROM " + TABLE_BEER, null);
        return cursorList(c);
    }

    private tBeer cursorToBeer(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        tBeer beer = new tBeer();
        beer.setIdBeer(c.getInt(NUM_COL_ID));
        beer.setName(c.getString(NUM_COL_NAME));
        beer.setType(c.getString(NUM_COL_TYPE));
        beer.setMl(c.getInt(NUM_COL_ML));
        beer.setAlcool(c.getDouble(NUM_COL_ALCOOL));
        beer.setRating(c.getInt(NUM_COL_RATING));
        beer.setComment(c.getString(NUM_COL_COMMENT));
        c.close();

        return beer;
    }

    private List<tBeer> cursorList(Cursor c){

        List<tBeer> beerList = new ArrayList<tBeer>();

        if (c.moveToFirst()) {

            while (c.isAfterLast() == false) {
                tBeer beer = new tBeer();
                beer.setIdBeer(c.getInt(NUM_COL_ID));
                beer.setName(c.getString(NUM_COL_NAME));
                beer.setType(c.getString(NUM_COL_TYPE));
                beer.setAlcool(c.getDouble(NUM_COL_ALCOOL));
                beer.setMl(c.getInt(NUM_COL_ML));
                beer.setRating(c.getInt(NUM_COL_RATING));
                beer.setComment(c.getString(NUM_COL_COMMENT));
                beerList.add(beer);
                c.moveToNext();
            }
        }
        return beerList;
    }
}