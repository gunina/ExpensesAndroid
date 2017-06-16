package com.app.expenses.adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.expenses.R;
import com.app.expenses.listeners.OnCheckedChangeListener;
import com.app.expenses.listeners.OnSubjectChangeListener;
import com.app.expenses.listeners.OnSumChangeListener;
import com.app.expenses.models.Entry;
import com.app.expenses.models.Group;

import java.util.List;

public class GroupAdapter extends BaseExpandableListAdapter implements OnSubjectChangeListener, OnCheckedChangeListener, OnSumChangeListener {
    private final List<Group> groups;
    private LayoutInflater inflater;
    private Context context;

    private OnSubjectChangeListener onSubjectChangeListener;
    private OnCheckedChangeListener onCheckedChangeListener;
    private OnSumChangeListener onSumChangeListener;

    public GroupAdapter(Activity act, List<Group> groups) {
        this.groups = groups;
        inflater = act.getLayoutInflater();
        context = act;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public Entry getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getEntries().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((Integer)childPosition).longValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Entry item = getChild(groupPosition, childPosition);
        View view = convertView;
        if (view == null) {
            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.item_entry, parent, false);
        }

        final EditText subject = ((EditText) view.findViewById(R.id.entry_subject));
        final EditText amount = ((EditText) view.findViewById(R.id.entry_sum));
        final CheckBox cb = ((CheckBox) view.findViewById(R.id.entry_checkBox));

        subject.setText(item.getSubject());
        amount.setText(item.getSum().toString());
        cb.setChecked(item.isChecked());

        if (item.isPaid()) {
            subject.setTextColor(Color.GRAY);
            amount.setTextColor(Color.GRAY);
            subject.setEnabled(false);
            amount.setEnabled(false);
        } else {
            subject.setTextColor(Color.BLACK);
            amount.setTextColor(context.getColor(R.color.colorPrimary));
            subject.setEnabled(true);
            amount.setEnabled(true);
        }


        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Entry item = getChild(groupPosition, childPosition);
                if (item != null) {
                    onCheckedChangeEvent(item.getId(), isChecked);
                }
            }
        });

        subject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (getChildrenCount(groupPosition) <= 0) {
                        return;
                    }

                    Entry item = getChild(groupPosition, childPosition);
                    if (item != null &&! subject.getText().toString().equals(item.getSubject())) {
                        onSubjectChangeEvent(item.getId(), subject.getText().toString());
                    }
                }
            }
        });

        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Entry item = getChild(groupPosition, childPosition);
                    Double sum = amount.getText() == null || amount.getText().toString().isEmpty() ? 0.0 : Double.valueOf(amount.getText().toString());
                    if (item != null && !sum.equals(item.getSum())) {
                        onSumChangeEvent(item.getId(), sum);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getEntries().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ((Integer)groupPosition).longValue();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category, null);
        }
        Group group = (Group) getGroup(groupPosition);
        TextView text = (TextView) convertView.findViewById(R.id.category_name);
        text.setText(group.getCategory().getName());

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setOnEventListener(OnSubjectChangeListener listener) {
        onSubjectChangeListener = listener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

    public void setOnSumChangeListener(OnSumChangeListener listener) {
        onSumChangeListener = listener;
    }

    @Override
    public void onSubjectChangeEvent(Integer id, String subject) {
        if (onSubjectChangeListener != null) {
            onSubjectChangeListener.onSubjectChangeEvent(id, subject);
        }
    }

    @Override
    public void onCheckedChangeEvent(Integer id, Boolean isChecked) {
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChangeEvent(id, isChecked);
        }
    }

    @Override
    public void onSumChangeEvent(Integer id, Double sum) {
        if (onSumChangeListener != null) {
            onSumChangeListener.onSumChangeEvent(id, sum);
        }
    }
}
