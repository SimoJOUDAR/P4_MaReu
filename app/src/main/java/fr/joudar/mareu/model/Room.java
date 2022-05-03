package fr.joudar.mareu.model;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.io.Serializable;

public class Room implements Serializable {

    private final String name;

    private final int icon;

    public Room(String  name,@DrawableRes int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getRoomName() {
        return name;
    }

    public int getRoomIcon() {
        return icon;
    }
}
