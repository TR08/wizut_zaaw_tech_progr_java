package com.company;

public class WorkerDirector extends MyWorker{
    private String cardNum;
    private int businessAllowance, costLimit;

    public void setBusinessAllowance(int val){
        businessAllowance = val;
    }

    public void setCostLimit(int val){
        costLimit = val;
    }

    public void setCardNum(String val){
        cardNum = val;
    }

    public int getBusinessAllowance(){
        return businessAllowance;
    }

    public int getCostLimit(){
        return costLimit;
    }

    public String getCardNum(){
        return cardNum;
    }
}
