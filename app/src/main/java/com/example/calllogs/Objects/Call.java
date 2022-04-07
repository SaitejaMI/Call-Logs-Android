package com.example.calllogs.Objects;

public class Call {
    private int duration, type;
    private long date;
    private String number, name;

    public Call(){

    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public long getDate(){
        return date;
    }

    public void setDate(long date){
        this.date = date;
    }

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }
}
