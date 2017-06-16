package com.app.expenses;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeElements();
    }

    public void initializeElements() {
        initCalendar();
        initCategory();
    }

    private void initCalendar() {
        final MonthSelectionFragment dialog = new MonthSelectionFragment();
        final RelativeLayout main = (RelativeLayout) findViewById(R.id.main);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isAdded()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.left, R.animator.rigth);
                    ft.remove(dialog);
                    ft.commit();
                }
            }
        });

        final ImageButton calendar = (ImageButton) findViewById(R.id.calendar);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialog.isAdded()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.left, R.animator.rigth);
                    ft.replace(R.id.fragment_container, dialog);
                    ft.commit();
                }
            }
        });
    }

    private void initCategory() {
        final ImageButton category = (ImageButton) findViewById(R.id.category_image);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.super.getApplicationContext(), CategoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}

