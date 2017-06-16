package com.app.expenses;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.expenses.adapters.CategoryListAdapter;
import com.app.expenses.models.Category;
import com.app.expenses.presenters.CategoryPresenter;
import com.app.expenses.presenters.Presenter;

public class CreatingFragment extends DialogFragment {

    private Presenter presenter;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_creating, null);

        CategoryPresenter categoryPresenter = new CategoryPresenter(getContext());
        final CategoryListAdapter adapter = new CategoryListAdapter(getContext(), R.id.spinner_item, categoryPresenter.getAll());
        Spinner categoryList = (Spinner)view.findViewById(R.id.category_list);
        categoryList.setAdapter(adapter);
        categoryList.setSelection(0);

        AlertDialog.Builder builder =  new  AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText subject = (EditText) view.findViewById(R.id.new_entry_subject);
                                EditText sum = (EditText) view.findViewById(R.id.new_entry_sum);
                                presenter.create(subject.getText().toString(), sum.getText().toString().isEmpty() ? 0.0 : Double.valueOf(sum.getText().toString()),
                                        ((Category)categoryList.getSelectedItem()).getId());
                                CreatingFragment.super.dismiss();
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        builder.setView(view);

        return builder.create();
    }

}
