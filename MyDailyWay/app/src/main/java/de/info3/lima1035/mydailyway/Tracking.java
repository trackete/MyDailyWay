package de.info3.lima1035.mydailyway;


import android.location.Location;

import java.util.ArrayList;

public class Tracking {


    public Tracking() {

    }
    public String date;

    public void setDate(String date) {


        this.date = date;
    }

    public String getDate() {
        return date;
    }


    public String duration;

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }


    public String traffic;

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getTraffic() {
        return traffic;
    }

    public String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String location;

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

}
