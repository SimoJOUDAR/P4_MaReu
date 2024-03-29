package fr.joudar.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Model object representing a Meeting
 */
public class Meeting implements Parcelable {

    /** Identifier */
    private final int id;

    /** Meeting's topic */
    @NonNull
    private final String topic;

    /** Meeting's date */
    @NonNull
    private final LocalDate date;

    /** Meeting's start time */
    @NonNull
    private final LocalTime startTime;

    /** Meeting's finish time */
    @NonNull
    private final LocalTime finishTime;

    /** Meeting room */
    @NonNull
    private final Room room;

    /** Meeting's participants email list */
    @NonNull
    private final String[] participants;

    /**
     * Constructor.
     * @param id
     * @param topic
     * @param date
     * @param startTime
     * @param finishTime
     * @param room
     * @param participants
     */
    public Meeting(int id, @NonNull String topic, @NonNull LocalDate date, @NonNull LocalTime startTime,
                   @NonNull LocalTime finishTime, @NonNull Room room, @NonNull String[] participants) {
        this.id = id;
        this.topic = topic;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.room = room;
        this.participants = participants;
    }



    protected Meeting(Parcel in) {
        id = in.readInt();
        topic = in.readString();
        date = (LocalDate) in.readSerializable();
        startTime = (LocalTime) in.readSerializable();
        finishTime = (LocalTime) in.readSerializable();
        room = (Room) in.readSerializable();
        participants = in.createStringArray();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    /**
     * Get value of id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get value of topic
     * @return topic
     */
    @NonNull
    public String getTopic() {
        return topic;
    }

    /**
     * Get value of date
     * @return date
     */
    @NonNull
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get value of date formatted and converted into String
     * @return date as String
     */

    public String getDateAsString(){
        return date.format(DateTimeFormatter.ofPattern("dd' 'MMM", Locale.FRENCH));
    }

    /**
     * Get value of startTime
     * @return startTime
     */
    @NonNull
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Get value of startTime formatted and converted into String
     * @return startTime as String
     */

    @NonNull
    public String getStartTimeAsString(){
        // return DateFormat.getTimeInstance( DateFormat.SHORT ).format(startTime);
        return startTime.format(DateTimeFormatter.ofPattern("HH'h'mm", Locale.FRENCH));
    }

    /**
     * Get value of finishTime
     * @return finishTime
     */
    @NonNull
    public LocalTime getFinishTime() {
        return finishTime;
    }

    /**
     * Get value of finishTime formatted and converted into String
     * @return finishTime as String
     */

    @NonNull
    public String getFinishTimeAsString(){
        // return DateFormat.getTimeInstance( DateFormat.SHORT ).format(finishTime);
        return finishTime.format(DateTimeFormatter.ofPattern("HH'h'mm", Locale.FRENCH));
    }

    /**
     * Get value of room
     * @return room
     */
    @NonNull
    public Room getRoom() {
        return room;
    }

    /**
     * Get value of participants converted into String
     * @return participants as String
     */
    @NonNull
    public String[] getParticipantsAsStringArray() {
        return participants;
    }

    public String getParticipantsAsString() {
        String participantsAsString = "";
        for (int i=0; i<participants.length; i++){
            participantsAsString += participants[i];
            if (i!=participants.length-1){
                participantsAsString += ", ";
            }
        }
        return participantsAsString;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Meeting meeting = (Meeting) obj;
        //return Integer.valueOf(id).equals(Integer.valueOf(meeting.id));
        return id == meeting.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(topic);
        parcel.writeSerializable(date);
        parcel.writeSerializable(startTime);
        parcel.writeSerializable(finishTime);
        parcel.writeSerializable(room);
        parcel.writeStringArray(participants);
    }
}
