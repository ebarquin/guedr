package com.eugeniobarquin.guedr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    protected static String TAG = MainActivity.class.getCanonicalName();

    protected Button changeToStone;
    protected Button changeToDonkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vamos a obtener una referencia al ImageView del offline image
        final ImageView offlineImage = (ImageView) findViewById(R.id.offline_weather_image);

        changeToStone = (Button) findViewById(R.id.change_stone_system);

        //Esto es muy raro que lo hagamos, pero te puede ayudar a entender
        // que son las clases anónimas

        changeToStone.setOnClickListener(new StoneButtonListner(offlineImage));

        changeToDonkey = (Button) findViewById(R.id.change_donkey_system);

        //Esto es lo que más problablemente termines haciendo en tu código
        changeToDonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Lo que sea", "Me han pedido burro");
                offlineImage.setImageResource(R.drawable.offline_weather2);
            }
        });




        Log.v(TAG, "Hola Amundio, he pasado por onCreate");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.v(TAG, "Nos han llamado desde onCreate");
        outState.putString("clave", "valor");
    }

}

class StoneButtonListner implements View.OnClickListener {
    private final ImageView offlineImage;

    public StoneButtonListner(ImageView offlineImage) {
        this.offlineImage = offlineImage;
    }

    public void onClick(View v) {
        Log.v("Lo que sea", "Me han pedido piedra");
        offlineImage.setImageResource(R.drawable.offline_weather);
    }
}