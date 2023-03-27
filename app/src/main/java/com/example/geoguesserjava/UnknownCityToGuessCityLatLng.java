package com.example.geoguesserjava;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class UnknownCityToGuessCityLatLng implements Parcelable {
    private final LatLng unknownCityLatLng;
    private LatLng guessCityLatLng;

    public UnknownCityToGuessCityLatLng(LatLng unknownCityLatLng) {
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
    protected UnknownCityToGuessCityLatLng(Parcel in) {
        unknownCityLatLng = in.readParcelable(LatLng.class.getClassLoader());
        guessCityLatLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    /**
     * Creator object that provides methods for creating an instance of the UnknownCityToGuessCityLatLng
     * class from a Parcel object. It implements the createFromParcel() and newArray() methods, which create
     * a new instance of the class from a Parcel object and an array of instances of the class, respectively.
     */
    public static final Creator<UnknownCityToGuessCityLatLng> CREATOR = new Creator<UnknownCityToGuessCityLatLng>() {
        @Override
        public UnknownCityToGuessCityLatLng createFromParcel(Parcel in) {
            return new UnknownCityToGuessCityLatLng(in);
        }

        @Override
        public UnknownCityToGuessCityLatLng[] newArray(int size) {
            return new UnknownCityToGuessCityLatLng[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(unknownCityLatLng, flags);
        dest.writeParcelable(guessCityLatLng, flags);
    }
}
