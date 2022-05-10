package fr.joudar.mareu.ui;

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

import fr.joudar.mareu.R;
import fr.joudar.mareu.model.Room;

public class RoomsListSpinnerAdapter extends ArrayAdapter<Room> {

    public RoomsListSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Room> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initCustomView(position, convertView, parent);
    }

    @NonNull
    private View initCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rooms_list_spinner_item, parent, false);
        }
        ImageView icon = convertView.findViewById(R.id.rooms_list_spinner_item_icon);
        TextView name = convertView.findViewById(R.id.rooms_list_spinner_item_name);
        Room room = getItem(position);
        icon.setImageResource(room.getRoomIcon());
        name.setText(room.getRoomName());
        return convertView;
    }
}
