package com.tooltip.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tooltip.Tooltip;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.text);

        Button button = (Button) findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip.Builder builder = new Tooltip.Builder(MainActivity.this, textView)
                        .setCancelable(false)
                        .setDismissOnClick(true)
                        .setCornerRadius(20f)
                        .setGravity(Gravity.END)
                        .setElevation(50f)
                        .setText("TEESSSTTT");
                builder.show();
            }
        });


        //AlertDialog.Builder a = new AlertDialog.Builder(this);

    }
}
