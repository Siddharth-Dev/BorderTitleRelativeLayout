package com.sj.customview.bordertitlerelativelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private BorderTitleRelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        relativeLayout = (BorderTitleRelativeLayout) findViewById(R.id.customRL);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateLayout(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                updateLayout(0);
            }
        });
    }

    private void updateLayout(int option) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        relativeLayout.setTitleText(getString(R.string.title_text));
        relativeLayout.enableBorder(true);
        switch (option) {
            case 0:
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                break;

            case 1:
                layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                break;

            case 2:
                int size = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
                layoutParams.width = size;
                layoutParams.height = size;
                break;

            case 3:
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                break;

            case 4:
                layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                break;

            case 5:
                relativeLayout.setTitleText(null);
                break;

            case 6:
                relativeLayout.enableBorder(false);
                break;
        }
        relativeLayout.setLayoutParams(layoutParams);
    }
}
