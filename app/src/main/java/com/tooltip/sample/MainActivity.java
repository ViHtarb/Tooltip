package com.tooltip.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tooltip.Tooltip;
import com.tooltip.TooltipActionView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.text);

        final Button button = (Button) findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip.Builder builder = new Tooltip.Builder(MainActivity.this, button)
                        .setCancelable(false)
                        .setDismissOnClick(true)
                        .setCornerRadius(20f)
                        .setGravity(Gravity.BOTTOM)
                        .setText("TETETETETTfdasf");
                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_test2);
        TooltipActionView view = (TooltipActionView) menuItem.getActionView();
        view.setMenuItem(menuItem);

        Tooltip.Builder builder = new Tooltip.Builder(MainActivity.this, view)
                .setCornerRadius(20f)
                .setGravity(Gravity.BOTTOM)
                .setMargin(20f)
                .setText("TETETETETT");
        builder.show();

        return super.onCreateOptionsMenu(menu);
    }
}
