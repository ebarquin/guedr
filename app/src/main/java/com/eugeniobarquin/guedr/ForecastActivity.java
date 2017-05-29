package com.eugeniobarquin.guedr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ForecastActivity extends AppCompatActivity {
    protected static String TAG = ForecastActivity.class.getCanonicalName();

    private static final int REQUEST_UNITS = 1;

    protected boolean mShowCelsius = true;
    private Forecast mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        //Creamos un modelo de pega
        Forecast mForecast = new Forecast(25, 10, 35, "Soleado con alguna nube", R.drawable.ico_01);
        updateForecast();


    }


    private void updateForecast() {
        //Accedemos a las vistas de la interfaz
        ImageView forecastImage = (ImageView) findViewById(R.id.forecast_image);
        TextView maxTempText = (TextView) findViewById(R.id.max_temp);
        TextView minTempText = (TextView) findViewById(R.id.min_temp);
        TextView humidityText = (TextView) findViewById(R.id.humidity);
        TextView forecastDescriptionText = (TextView) findViewById(R.id.forecast_description);

        //Calculamos las temperaturas en funcion de las unidades
        float maxTemp = 0;
        float minTemp = 0;
        String unitsToShow = null;
        if(mShowCelsius) {
            maxTemp = mForecast.getMaxTemp(Forecast.CELSIUS);
            minTemp = mForecast.getMinTemp(Forecast.CELSIUS);
            unitsToShow = "ºC";
        }
        else {
            maxTemp = mForecast.getMaxTemp(Forecast.FARENHEIT);
            minTemp = mForecast.getMinTemp(Forecast.FARENHEIT);
            unitsToShow = "F";
        }

        //Actualizamos la vista con el modelo
        forecastImage.setImageResource(mForecast.getIcon());
            //utilizamos el método getString para aplicar un formato al String que tenemos guardado en strings.xml
        maxTempText.setText(getString(R.string.max_temp_format, maxTemp, unitsToShow));
        minTempText.setText(getString(R.string.min_temp_format, minTemp, unitsToShow));
        humidityText.setText(getString(R.string.humidity_format, mForecast.getHumidity()));
        forecastDescriptionText.setText(mForecast.getDescription());
    }

    //Creamos el menú de la aplicación
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //Con Cmd + P veo info de los parametros y escojo la última opción.
        // 0 -> Identificador del grupo
        // 1 -> Identificador de la opción de menú
        // 2 -> Orden de aparición de los menús.

        //Creación a pelo
        //menu.add(0, ID_OPCION1, 0, "Opcción de menú 1");
        //menu.add(0, ID_OPCION2, 0, "Opcción de menú 2");

        //Creación mediante layout
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return true;
    }
    //Realizamos una acción al seleccionar uno de los items del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superReturn = super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_show_settings ) {
            Log.v(TAG, "Se ha pulsado la opción de ajustes");

            //Creamos el intent explicito para abrir la pantalla de ajustes
            Intent settingsIntent = new Intent(this,SettingsActivity.class);

            //pasamos datos a la siguente actividad: las unidades
            //settingsIntent.putExtra(SettingsActivity.EXTRA_UNITS, R.id.celsius_rb );

            //Lanzamos la actividad (En el caso de que no queramos que la actividad de destino nos devuelva datos
            //startActivity(settingsIntent);

            //Esta otra opción la usamos porque esperamos que la actividad de destino nos devuelva datos
            //El REQUEST_UNITS nos indica el tipo de pantalla del que vamos a volver.
            startActivityForResult(settingsIntent, REQUEST_UNITS);
            return true;
        }
        return superReturn;
    }

    //Sobreescribimos el método para recibir los datos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UNITS) {
            //Estamos volviendo de la pantalla de SettingsActivity
            //Miro como ha ido el resultado
            if (resultCode == RESULT_OK) {
                //Todo ha ido bien, hago caso a los datos de entrada (la opción por defecto aqui es absurda pero hay que rellenarla)
                int optionSelected = data.getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.farenheit_rb);
                if (optionSelected == R.id.farenheit_rb) {
                    Toast.makeText(this, "Se ha seleccionado Farenheit", Toast.LENGTH_LONG).show();
                    mShowCelsius = false;
                } else {
                    Toast.makeText(this, "Se ha seleccionado Celsisus", Toast.LENGTH_LONG).show();
                    mShowCelsius = true;
                }
                updateForecast();
            }
            else if (resultCode == RESULT_CANCELED) {
                //No hago nada porque el usuario ha cancelado la selección de unidades
            }
        }
    }

}


