package com.example.calllogs.Objects;

public class Contact {
    private String name,number;
    private int incomingDuration=0;
    private int outgoingDuration=0;
    private int longestDuration=0;
    private int numberOfOutgoingCalls=0;
    private int numberOfIncomingCalls=0;
    private int numberOfMissedCalls=0;

    public Contact(){}

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

    public void setIncomingDuration(int incomingDuration){ this.incomingDuration = incomingDuration;}
    public int getIncomingDuration(){ return incomingDuration;}

    public void setOutgoingDuration(int outgoingDuration){ this.outgoingDuration = outgoingDuration;}
    public int getOutgoingDuration(){ return outgoingDuration;}

    public int getTotalDuration(){ return incomingDuration+outgoingDuration;}

    public void setLongestDuration(int longestDuration){ this.longestDuration = longestDuration;}
    public int getLongestDuration(){ return longestDuration;}

    public void setNumberOfOutgoingCalls(int numberOfOutgoingCalls){ this.numberOfOutgoingCalls = numberOfOutgoingCalls;}
    public int getNumberOfOutgoingCalls(){ return numberOfOutgoingCalls;}

    public void setNumberOfIncomingCalls(int numberOfIncomingCalls){ this.numberOfIncomingCalls = numberOfIncomingCalls;}
    public int getNumberOfIncomingCalls(){ return numberOfIncomingCalls;}

    public int getNumberOfTotalCalls(){ return numberOfIncomingCalls+numberOfMissedCalls+numberOfOutgoingCalls;}

    public void setNumberOfMissedCalls(int numberOfMissedCalls){ this.numberOfMissedCalls = numberOfMissedCalls;}
    public int getNumberOfMissedCalls(){ return numberOfMissedCalls;}

    public int getAverageDuration(){
        return getTotalDuration() /getNumberOfTotalCalls();}

    public int getNumberOfAverageCalls(int days){ return getNumberOfTotalCalls()/days;}
}
