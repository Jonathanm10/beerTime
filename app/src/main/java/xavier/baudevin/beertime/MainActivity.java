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
import java.util.StringTokenizer;

import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int pos;
    public MainActivity me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create first row in DB
        DBBeerQuery dbbeer = new DBBeerQuery(this);

        //tBeer beer1 = new tBeer("Kwak", "Ambrée", 50, 8.4, 8, "Kwakkkkkk");
        //tBeer beer2 = new tBeer("Cuvée des Trolls", "Blonde", 25, 7.0, 8, "tololo");

        dbbeer.open();

        List<tBeer> beerFromDB = dbbeer.getBeerList();

        //Récupération de la listview créée dans le fichier main.xml
        final ListView maListViewPerso = (ListView) findViewById(R.id.list);

        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;

        //On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView

        for (int i = 0; i < beerFromDB.size(); i++) {
            map = new HashMap<String, String>();
            tBeer beerIndex = beerFromDB.get(i);
            map.put("idBeer", String.valueOf(beerIndex.getIdBeer()));
            map.put("name", beerIndex.getName());
            map.put("type", beerIndex.getType());
            map.put("ml", String.valueOf(beerIndex.getMl()));
            map.put("rate", String.valueOf(beerIndex.getRating()));
            listItem.add(map);
        }

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.row_list,
                new String[]{"idBeer", "name", "type", "ml", "rate"}, new int[]{R.id.idBeer, R.id.name, R.id.type, R.id.mlBeer, R.id.rate});

        //On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);

        dbbeer.close();

        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {

            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {

                AlertDialog show = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setTitle("choix")
                        .setMessage("que voulez vous faire ?")
                        .setPositiveButton("modifier", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(pos);
                                startActivity(new Intent(MainActivity.this, Detail.class).putExtra("empty", false).putExtra("idBeer", map.get("idBeer")));

                            }

                        })
                        .setNegativeButton("supprimer", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DBBeerQuery dbbeer = new DBBeerQuery(me);
                                dbbeer.open();

                                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(pos);
                                int idBeer = Integer.parseInt(map.get("idBeer"));
                                dbbeer.deleteBeerID(idBeer);

                                dbbeer.close();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(MainActivity.this, Detail.class).putExtra("empty", true));
        }

        return super.onOptionsItemSelected(item);
    }

}
