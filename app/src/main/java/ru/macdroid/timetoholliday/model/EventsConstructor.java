package ru.macdroid.timetoholliday.model;

public class EventsConstructor {

    public String eId;
    public String eName;
    public String eDate, eDateInSql;
    public String eTime;
    public String ePicture;


    public EventsConstructor(String eId, String eName, String eDate, String eDateInSql, String eTime, String ePicture) {
        this.eId = eId;
        this.eName = eName;
        this.eDate = eDate;
        this.eDateInSql = eDateInSql;
        this.eTime = eTime;
        this.ePicture = ePicture;
    }

    public String geteDate() {
        return eDate;
    }


    public String geteTime() {
        return eTime;
    }


    public String geteName() {
        return eName;
    }


    public String getePicture() {
        return ePicture;
    }

}
