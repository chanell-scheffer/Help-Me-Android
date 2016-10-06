package com.yolandabreakfast.helpme;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button gr10;
    Button gr11;
    Button gr12;
    ImageView calculator;
    TextView moreInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gr10 = (Button) findViewById(R.id.grade10);
        gr10.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Grade10Activity.class);
                startActivity(intent);
            }
        });


        gr11 = (Button) findViewById(R.id.grade11);
        gr11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Grade11Activity.class);
                startActivity(intent1);
            }

        });


        gr12 = (Button) findViewById(R.id.grade12);
        gr12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Grade12Activity.class);
                startActivity(intent2);
            }

        });

        calculator = (ImageView) findViewById(R.id.calcultor);
        calculator.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent3);
            }


        });
        moreInfo = (TextView) findViewById(R.id.moreInfo);
        moreInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wolframalpha.com/examples/Math.html"));
                startActivity(in);
            }
        });

//        startActivity(new Intent(MainActivity.this, this.HomeActivity().getClass;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}






