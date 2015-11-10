package com.dcs.appcompte;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    DetailActivity detailActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // declaration des variables de la vue //
        final EditText currentLabel = (EditText) findViewById(R.id.label);
        final EditText currentMontant = (EditText) findViewById(R.id.montant);
        final Button bSauvegarde = (Button) findViewById(R.id.sauvegarde);
        final CheckBox cSoustraire = (CheckBox) findViewById(R.id.soustraire);
        final CheckBox cAddition = (CheckBox) findViewById(R.id.addition);

        // recuperations des variables de MainActivity //
        final Intent intent = getIntent();
        currentLabel.setHint(intent.getStringExtra("label"));
        currentMontant.setHint(String.valueOf(intent.getIntExtra("montant", 0)));
        if (intent.getBooleanExtra("addition", true)) {
            cAddition.setChecked(true);
            cSoustraire.setChecked(false);
        } else {
            cAddition.setChecked(false);
            cSoustraire.setChecked(true);
        }

        // checkbox 1 seul choix possible
        cAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cSoustraire.setChecked(false);
                cAddition.setChecked(true);
            }
        });
        cSoustraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cAddition.setChecked((false));
                cSoustraire.setChecked(true);
            }
        });

        bSauvegarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialisation des variables a envoyer //
                String backLabel = currentLabel.getHint().toString();
                String temp = currentMontant.getHint().toString();
                int backMontant = Integer.parseInt(temp);

                // intent de retour //
                Intent retourMain = new Intent(detailActivity, MainActivity.class);

                // variable de retour //
                if (currentLabel.getText().toString().trim().length() != 0) {
                    Log.e("toto", "ok");
                    backLabel = currentLabel.getText().toString();
                }
                if (currentMontant.getText().toString().trim().length() != 0) {
                    String tempMontant = currentMontant.getText().toString();
                    try {
                        backMontant = Integer.parseInt(tempMontant);
                    } catch (NumberFormatException e) {
                        Log.e("Detail activity: ", String.valueOf(e));
                    }
                }
                boolean backAddition = cAddition.isChecked();
                int backPosition = intent.getIntExtra("position", -1);

                // retour //
                retourMain.putExtra("label", backLabel);
                retourMain.putExtra("montant", backMontant);
                retourMain.putExtra("addition", backAddition);
                retourMain.putExtra("position", backPosition);
                setResult(0, retourMain);
                finish();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
