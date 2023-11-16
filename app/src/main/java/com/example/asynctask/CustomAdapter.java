package com.example.asynctask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ListItem> {

    private Context context;
    private List<ListItem> itemList;

    public CustomAdapter(@NonNull Context context, List<ListItem> itemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the convertView is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_item_layout, parent, false);
        }

        // Get the current item from the list
        ListItem currentItem = getItem(position);

        // Get references to the views in the layout
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);

        // Set the data to the views
        if (currentItem != null) {
            imageView.setImageBitmap(currentItem.getImageResource());
            titleTextView.setText(currentItem.getTitle());
            descriptionTextView.setText(currentItem.getDescription());
        }

        return convertView;
    }
}

