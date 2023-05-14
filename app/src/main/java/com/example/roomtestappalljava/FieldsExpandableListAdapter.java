package com.example.roomtestappalljava;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FieldsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableTitleList;
    private ArrayList<FieldDBO> fieldExpandableListOfFields;

    // constructor
    public FieldsExpandableListAdapter(Context context, ArrayList<FieldDBO> fieldExpandableListOfFields) {
        this.context = context;
        this.fieldExpandableListOfFields = fieldExpandableListOfFields;
        expandableTitleList = new ArrayList<>();
        expandableTitleList.add("Fields");
    }

    @Override
    // Gets the data associated with the given child within the given group.
    public Object getChild(int lstPosn, int expanded_ListPosition) {
        return this.fieldExpandableListOfFields.get(expanded_ListPosition);
    }

    @Override
    // Gets the ID for the given child within the given group.
    // This ID must be unique across all children within the group. Hence we can pick the child uniquely
    public long getChildId(int listPosition, int expanded_ListPosition) {
        return expanded_ListPosition;
    }

    @Override
    // Gets a View that displays the data for the given child within the given group.
    public View getChildView(int lstPosn, final int expanded_ListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final FieldDBO selectedDBO = (FieldDBO) getChild(lstPosn, expanded_ListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fields_expandable_view_list_item, null);
        }
        TextView fieldNameTextView = (TextView) convertView.findViewById(R.id.field_expandable_list_item_field_name);
        fieldNameTextView.setText(selectedDBO.getFieldName());
        TextView containingFarmTextview = (TextView) convertView.findViewById(R.id.field_expandable_list_item_containing_farm_name);
        containingFarmTextview.setText("Meeshal");
        TextView areaOfFieldTextview = convertView.findViewById(R.id.field_expandable_list_item_area);
        DecimalFormat areaDF = new DecimalFormat("#.##");
        areaOfFieldTextview.setText(areaDF.format(selectedDBO.getAreaInMetresSquared()/(10000)));
        return convertView;
    }

    @Override
    // Gets the number of children in a specified group.
    public int getChildrenCount(int listPosition) {
        return fieldExpandableListOfFields.size();
    }

    @Override
    // Gets the data associated with the given group.
    public Object getGroup(int listPosition) {
        return this.expandableTitleList.get(listPosition);
    }

    @Override
    // Gets the number of groups.
    public int getGroupCount() {
        return this.expandableTitleList.size();
    }

    @Override
    // Gets the ID for the group at the given position. This group ID must be unique across groups.
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    // Gets a View that displays the given group.
    // This View is only for the group--the Views for the group's children
    // will be fetched using getChildView()
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = "Fields";
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.field_row_item, null);
        }
        TextView expandableListTitleTextView = (TextView) convertView.findViewById(R.id.textView);
        expandableListTitleTextView.setTypeface(null, Typeface.BOLD);
        expandableListTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    // Indicates whether the child and group IDs are stable across changes to the underlying data.
    public boolean hasStableIds() {
        return false;
    }

    @Override
    // Whether the child at the specified position is selectable.
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    static class FieldDiff extends DiffUtil.ItemCallback<FieldDBO> {

        @Override
        public boolean areItemsTheSame(@NonNull FieldDBO oldItem, @NonNull FieldDBO newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FieldDBO oldItem, @NonNull FieldDBO newItem) {
            return oldItem.getFieldID() == newItem.getFieldID();
        }
    }
}