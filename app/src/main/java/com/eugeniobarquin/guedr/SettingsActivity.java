package com.eugeniobarquin.guedr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;


public class SettingsActivity extends AppCompatActivity {

    public static final String EXTRA_UNITS = "units";
    //Sacamos el radioGroup como atributo global
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);


        //No necesitamos crear una variable porque descienden de una vista
        findViewById(R.id.accept_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptSettings();
            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSettings();
            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.units_rg);

        //Selecciono las unidades que me hayan mandado con el intent y si no recibe nada por defecto selecciona el de farenheit
        int radioSelected = getIntent().getIntExtra(SettingsActivity.EXTRA_UNITS,R.id.farenheit_rb);
        mRadioGroup.check(radioSelected);
    }

    private void cancelSettings() {
        //Indicamos que cancelamos el envio de datos
        setResult(RESULT_CANCELED);
        //Matamos esta actividad y volvemos
        finish();
    }

    private void acceptSettings() {

        //Creamos un intent con los datos de salida (sin contextos, ni clases pues solo va a devolver la información)
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_UNITS, mRadioGroup.getCheckedRadioButtonId());

        //Indicamos que todo ha ido bien y que haga caso a los datos de salida
        setResult(RESULT_OK, returnIntent);

        //Matamos esta actividad y volvemos
        finish();

    }
    
    
}
