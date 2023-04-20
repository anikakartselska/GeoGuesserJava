package com.example.geoguesserjava.ui.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class UnknownCityToGuessCityLatLng implements Parcelable {
    /**
     * the location of the unknown city to be guessed
     */
    private final LatLng unknownCityLatLng;

    /**
     * the location of the user's guess.
     */
    private LatLng guessCityLatLng;

    /**
     * the name of the unknown city to be guessed
     */
    private String unknownCityName;

    public UnknownCityToGuessCityLatLng(String unknownCityName, LatLng unknownCityLatLng) {
        this.unknownCityName = unknownCityName;
        this.unknownCityLatLng = unknownCityLatLng;
    }

    public LatLng getUnknownCityLatLng() {
        return unknownCityLatLng;
    }

    public LatLng getGuessCityLatLng() {
        return guessCityLatLng;
    }

    public void setGuessCityLatLng(LatLng guessCityLatLng) {
        this.guessCityLatLng = guessCityLatLng;
    }

    public String getUnknownCityName() {
        return unknownCityName;
    }

    /**
     * Constructs a new UnknownCityToGuessCityLatLng object from the given parcel.
     * @param in the given parcel
     */
    protected UnknownCityToGuessCityLatLng(Parcel in) {
        unknownCityLatLng = in.readParcelable(LatLng.class.getClassLoader());
        guessCityLatLng = in.readParcelable(LatLng.class.getClassLoader());
        unknownCityName = in.readString();
    }

    /**
     * Creator object that provides methods for creating an instance of the UnknownCityToGuessCityLatLng
     * class from a Parcel object. It implements the createFromParcel() and newArray() methods, which create
     * a new instance of the class from a Parcel object and an array of instances of the class, respectively.
     */
    public static final Creator<UnknownCityToGuessCityLatLng> CREATOR = new Creator<UnknownCityToGuessCityLatLng>() {
        /**
         * Create a new instance of the class from a Parcel object and an array of instances of the class.
         * @param in The Parcel to read the object's data from.
         * @return
         */
        @Override
        public UnknownCityToGuessCityLatLng createFromParcel(Parcel in) {
            return new UnknownCityToGuessCityLatLng(in);
        }

        @Override
        public UnknownCityToGuessCityLatLng[] newArray(int size) {
            return new UnknownCityToGuessCityLatLng[size];
        }
    };

    /**
     * Describes any special objects contained in the parcel.
     *
     * @return 0 since there are no special objects
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes unknownCityLatLng, guessCityLatLng, and unknownCityName to Parcel
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(unknownCityLatLng, flags);
        dest.writeParcelable(guessCityLatLng, flags);
        dest.writeString(unknownCityName);
    }
}
