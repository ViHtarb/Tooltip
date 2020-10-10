package com.tooltip.sample;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tooltip.Tooltip;
import com.tooltip.sample.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    private Tooltip mTooltip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

/*        View view = findViewById(R.id.container);
        Tooltip.Builder builderContainer = new Tooltip.Builder(view, R.style.Tooltip2)
                .setCancelable(true)
                .setDismissOnClick(false)
                .setCornerRadius(20f)
                .setText("TESTTTTTTTTTTTTTTTTT");
        builderContainer.show();*/

        Tooltip.Builder builder = new Tooltip.Builder(binding.buttonTest)
                .setGravity(Gravity.START)
                .setCancelable(false)
                .setCornerRadius(20f)
                .setText("TEST");
        Tooltip test = builder.show();
        binding.buttonTest.setOnClickListener(v -> {
            Tooltip.Builder builder1 = new Tooltip.Builder(binding.buttonTest)
                    .setCancelable(true)
                    .setDismissOnClick(true)
                    .setCornerRadius(20f)
                    .setText(R.string.tooltip_hello_world);
            builder1.show();

/*            Balloon balloon = new Balloon.Builder(v.getContext())
                    .setArrowOrientation(ArrowOrientation.RIGHT)
                    .setArrowConstraints(ArrowConstraints.ALIGN_ANCHOR)
                    .setArrowVisible(true)
                    //.setArrowPosition(0.5f)
                    .setTextSize(15f)
                    //.setCornerRadius(4f)
                    .setAlpha(0.9f)
                    .setText("TEST sdasda sdada asdasd asd")
                    .setLifecycleOwner(MainActivity.this)
                    .build();
            balloon.showAlignLeft(v);*/
        });

        binding.recyclerView.setAdapter(new RecyclerAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SpannableString text = new SpannableString("I`m on the bottom of menu item X");
        text.setSpan(new ForegroundColorSpan(Color.RED), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Drawable d = ContextCompat.getDrawable(getBaseContext(), R.mipmap.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        text.setSpan(span, 31, 32, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        MenuItem menuItem = menu.findItem(R.id.action_test2);
        Tooltip.Builder builder = new Tooltip.Builder(menuItem, R.style.Tooltip)
                .setCornerRadius(10f)
                .setGravity(Gravity.BOTTOM)
                .setText(text);
        builder.show();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_test:
                if (mTooltip == null) {
                    mTooltip = new Tooltip.Builder((View) findViewById(R.id.action_test), R.style.Tooltip)
                            .setDismissOnClick(true)
                            .setGravity(Gravity.BOTTOM)
                            .setText("I`m on the bottom of first menu item and showing dynamically on menu item click")
                            .show();
                } else {
                    if (mTooltip.isShowing()) {
                        mTooltip.dismiss();
                    } else {
                        mTooltip.show();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
