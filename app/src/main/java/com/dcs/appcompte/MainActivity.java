package com.dcs.appcompte;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Calcul;
import model.Content;
import model.EntryAdapter;
import model.Item;
import model.Storage;

public class MainActivity extends ListActivity implements Preference.OnPreferenceClickListener {

    public final static int mainToDetail = 0;
    public EntryAdapter adapter;
    public ListView mainList;
    public MainActivity mainActivity = this;
    public ArrayList<Item> items = new ArrayList<Item>();
    public Calcul calcul = new Calcul();
    private TextView cTotalDepense;
    private TextView cMontantRestant;
    private String STORAGE_FILENAME = "saveAppCompte.txt";
    private Storage storage = new Storage(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // assignation des éléments de la vue //
        this.cTotalDepense = (TextView) findViewById(R.id.vTotalDepense);
        this.cMontantRestant = (TextView) findViewById(R.id.vMontantRestant);
        this.mainList = (ListView) findViewById(android.R.id.list);


        items = storage.getAllItem();


/*

            items.add(new SectionItem("Maison"));
            items.add(new Content("loyer", 530, false));
            items.add(new Content("garage", 50, false));
            items.add(new Content("EDF", 30, false));
            items.add(new Content("assurance maison", 9, false));
            items.add(new Content("internet", 20, false));

            items.add(new SectionItem("Personnel"));
            items.add(new Content("portable", 20, false));
            items.add(new Content("impôts locaux", 50, false));
            items.add(new Content("course", 150, false));
            items.add(new Content("cantine travail", 40, false));

            items.add(new SectionItem("Transport"));
            items.add(new Content("transport en commun", 20, false));
            items.add(new Content("essence", 80, false));
            items.add(new Content("assurance moto", 24, false));
*/

        adapter = new EntryAdapter(this, items, mainActivity);

        // on attache la list a celle de la vue //
        mainList.setAdapter(adapter);


        int tempTotal = calcul.fraisTotaux(items);
        int tempDepense = calcul.restant(items);

        cTotalDepense.setText(String.valueOf(tempTotal));
        cMontantRestant.setText(String.valueOf(tempDepense));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        return false;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        if (!items.get(position).isHeader()) {
            if (items.get(position).isContent()) {
                Content item = (Content) items.get(position);

                // passage de variables a DetailActivity //
                Intent detail = new Intent(this, DetailActivity.class);
                detail.putExtra("label", item.label);
                detail.putExtra("montant", item.montant);
                detail.putExtra("addition", item.operate);
                detail.putExtra("position", position);

                // demarre DetailActivity et attend une réponse //
                startActivityForResult(detail, mainToDetail);
            }
        }
        super.onListItemClick(l, v, position, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // reponse de l'autre activité //
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && data != null) {
            if (resultCode == 0) {
                Content item = (Content) items.get(data.getIntExtra("position", -1));
                item.label = data.getStringExtra("label");
                Log.e("item.label: ", item.label);

                item.montant = data.getIntExtra("montant", -1);
                Log.e("item.montant: ", String.valueOf(item.montant));

                item.operate = data.getBooleanExtra("addition", true);
                Log.e("item.operate: ", String.valueOf(item.operate));
                Log.e("item.operate: ", String.valueOf(data.getIntExtra("position", -1)));
                items.set(data.getIntExtra("position", -1), item);

                // pour recharger la liste avec les nouvelles valeurs //
                setListAdapter(adapter);

                int tempTotal = calcul.fraisTotaux(items);
                int tempDepense = calcul.restant(items);

                cTotalDepense.setText(String.valueOf(tempTotal));
                cMontantRestant.setText(String.valueOf(tempDepense));
            }
        }
    }

    private ArrayList <Item> createList(){

        // création des items de la list //
        storage.insertHeader("Revenu");
        storage.insertContent("salaire net", 1170, true);
        storage.insertContent("prime", 0, true);

        storage.insertHeader("Transport");
        storage.insertContent("transport en commun", 20, false);
        storage.insertContent("essence", 80, false);
        storage.insertContent("assurance moto", 24, false);

        return storage.getAllItem();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
