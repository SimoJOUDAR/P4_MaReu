package fr.joudar.mareu.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Model object representing a Room
 */
public class Room implements Serializable {

    /** Room's name */
    private final String name;

    /** Room's icon */
    private final int icon;

    /**
     * Constructor.
     * @param name
     * @param icon
     */
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Room) {
            return Objects.equals(((Room) obj).name, this.name);
        }
        return false;
    }
}
