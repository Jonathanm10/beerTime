package xavier.baudevin.beertime;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavierbaudevin on 04/02/16.
 */
public class DBBeerQuery {

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
        //On crée la BDD et sa table
        DBBeer = new DBBeer(context, NAME_BDD, null, VERSION_BDD);
    }

    public void open() {
        //on ouvre la BDD en écriture
        bdd = DBBeer.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public long insertBeer(tBeer beer) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NAME, beer.getName());
        values.put(COL_TYPE, beer.getType());
        values.put(COL_ML, beer.getMl());
        values.put(COL_ALCOOL, beer.getAlcool());
        values.put(COL_RATING, beer.getRating());
        values.put(COL_COMMENT, beer.getComment());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_BEER, null, values);
    }

    public int updateLivre(int id, tBeer beer) {
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
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

        //Suppression d'un livre de la BDD grâce à l'ID
        return this.bdd.delete(TABLE_BEER, COL_ID + "=" + idBeer, null);
    }

    public tBeer getBeerWithId(int idBeer) {
        Cursor c = bdd.query(TABLE_BEER, new String[]
                {COL_ID, COL_NAME, COL_TYPE, COL_ML, COL_ALCOOL, COL_RATING, COL_COMMENT},
                COL_ID + " = " + idBeer , null, null, null, null);
        return cursorToBeer(c);
    }

    public tBeer getBeer(){
        Cursor c =  bdd.rawQuery("SELECT * FROM " + TABLE_BEER, null);
        return cursorToBeer(c);
    }

    public List<tBeer> getBeerList() {
        Cursor c =  bdd.rawQuery("SELECT * FROM " + TABLE_BEER, null);
        return cursorList(c);
    }

    //Cette méthode permet de convertir un cursor en une biere
    private tBeer cursorToBeer(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        tBeer beer = new tBeer();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        beer.setIdBeer(c.getInt(NUM_COL_ID));
        beer.setName(c.getString(NUM_COL_NAME));
        beer.setType(c.getString(NUM_COL_TYPE));
        beer.setMl(c.getInt(NUM_COL_ML));
        beer.setAlcool(c.getDouble(NUM_COL_ALCOOL));
        beer.setRating(c.getInt(NUM_COL_RATING));
        beer.setComment(c.getString(NUM_COL_COMMENT));
        //On ferme le cursor
        c.close();

        //On retourne le livre
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
                beer.setMl(c.getInt(NUM_COL_ML));
                beer.setRating(c.getInt(NUM_COL_RATING));
                beerList.add(beer);
                c.moveToNext();
            }
        }
        return beerList;
    }
}