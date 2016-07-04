/**
 * @autor:      Xavier Baudevin <xavier.bau@hotmail.fr>
 * @date:       29/01/16
 * @project:    BeerTime
 * @desciption: the first activity in the application do:
 *                  - list item
 *                  - delete
 *                  - redirect to create or update
 */

package xavier.baudevin.beertime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity {

    public int pos;
    public MainActivity me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize db and open it
        DBBeerQuery dbbeer = new DBBeerQuery(this);
        dbbeer.open();

        // get the list of beer in the db
        List<tBeer> beerFromDB = dbbeer.getBeerList();

        //get the list view in the xml
        final ListView maListViewPerso = (ListView) findViewById(R.id.list);

        // create a list array of an hashmap (item)
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        // for each item in db
        for (int i = 0; i < beerFromDB.size(); i++) {

            // create a new hashmap to store item data
            HashMap<String, String> map = new HashMap<String, String>();
            // get the item from the db query
            tBeer beerIndex = beerFromDB.get(i);

            // put item in the hashmap
            map.put("idBeer", String.valueOf(beerIndex.getIdBeer()));
            map.put("name", beerIndex.getName());
            map.put("type", beerIndex.getType());
            map.put("ml", String.valueOf(beerIndex.getMl()));
            map.put("rate", String.valueOf(beerIndex.getRating()));

            // put item hashmap int the list
            listItem.add(map);
        }

        // use an adopter do adapt item list to the view
        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.row_list,
                new String[]{"idBeer", "name", "type", "ml", "rate"}, new int[]{R.id.idBeer, R.id.name, R.id.type, R.id.mlBeer, R.id.rate});

        // insert item list in list view
        maListViewPerso.setAdapter(mSchedule);

        // close the db
        dbbeer.close();

        // use an listener on item
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {

            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {

                // show an alert to choice beetween delete and update
                AlertDialog show = new AlertDialog.Builder(MainActivity.this)
                        // set the icon, the title, the message
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setTitle("choix")
                        .setMessage("que voulez vous faire ?")
                        // set the button and add an listner to update the item
                        .setPositiveButton("modifier", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // get the item and start the activity to update
                                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(pos);
                                startActivity(new Intent(MainActivity.this, Detail.class).putExtra("empty", false).putExtra("idBeer", map.get("idBeer")));

                            }

                        })
                        // set the button and add an listener to delete the item
                        .setNegativeButton("supprimer", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // open the db
                                DBBeerQuery dbbeer = new DBBeerQuery(me);
                                dbbeer.open();

                                // get the item id
                                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(pos);
                                int idBeer = Integer.parseInt(map.get("idBeer"));

                                // delete the item with the id
                                dbbeer.deleteBeerID(idBeer);

                                // close the db
                                dbbeer.close();

                                // delete the item in the listview
                                maListViewPerso.removeViewsInLayout(pos,1);

                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // if the selection is add
        if (id == R.id.action_add) {
            // start the activity to insert a new element
            startActivity(new Intent(MainActivity.this, Detail.class).putExtra("empty", true));
        }

        return super.onOptionsItemSelected(item);
    }

}
