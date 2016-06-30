package xavier.baudevin.beertime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.util.ArrayList;


public class Detail extends AppCompatActivity {

    private final DBBeerQuery dbbeer = new DBBeerQuery(this);
    private int idBeer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<EditText> txtField = new ArrayList<EditText>();

        txtField.add((EditText) findViewById(R.id.inputName));
        txtField.add((EditText) findViewById(R.id.inputType));
        txtField.add((EditText) findViewById(R.id.inputMl));
        txtField.add((EditText) findViewById(R.id.inputDegre));
        txtField.add((EditText) findViewById(R.id.inputRate));
        txtField.add((EditText) findViewById(R.id.inputComment));

        this.dbbeer.open();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getBoolean("empty")) {
            this.idBeer = 0;
        } else {
            this.idBeer = Integer.parseInt(bundle.getString("idBeer"));
            tBeer beer = dbbeer.getBeerWithId(idBeer);

            txtField.get(0).setText(beer.getName());
            txtField.get(1).setText(beer.getType());
            txtField.get(2).setText(String.valueOf(beer.getMl()));
            txtField.get(3).setText(String.valueOf(beer.getAlcool()));
            txtField.get(4).setText(String.valueOf(beer.getRating()));
            txtField.get(5).setText(beer.getComment());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            finish ();
        }

        if (id == R.id.action_save) {

            String inputName = ((EditText) findViewById(R.id.inputName)).getText().toString();
            String inputType = ((EditText) findViewById(R.id.inputType)).getText().toString();
            int inputMl = Integer.parseInt(((EditText) findViewById(R.id.inputMl)).getText().toString());
            Double inputDegre = Double.parseDouble(((EditText) findViewById(R.id.inputDegre)).getText().toString());
            int inputRate = Integer.parseInt(((EditText) findViewById(R.id.inputRate)).getText().toString());
            String inputComment = ((EditText) findViewById(R.id.inputComment)).getText().toString();

            tBeer beerToSave = new tBeer(inputName, inputType, inputMl, inputDegre, inputRate, inputComment);

            if (this.idBeer == 0) {
                this.dbbeer.insertBeer(beerToSave);
            }
            else {
                this.dbbeer.updateLivre(this.idBeer, beerToSave);

            }

            this.dbbeer.close();

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
