package com.app.expenses;


import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.expenses.models.Month;


public class MonthSelectionFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ArrayAdapter<Month> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, Month.values());

        View view = inflater.inflate(R.layout.activity_calendar, container, false);
        ListView monthList = (ListView)view.findViewById(R.id.month_list);
        monthList.setAdapter(adapter);

        monthList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openPageActivity(adapter.getItem(i));
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openPageActivity(Month selected) {
        Intent intent = new Intent(getContext(), PageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("date", selected.getName());
        intent.putExtra("month", selected.getValue());
        startActivity(intent);
    }

}
