package com.company;

public class WorkerTrader extends MyWorker{
    private int provision, provLimit;

    public void setProvision(int val){
        provision = val;
    }

    public void setProvLimit(int val){
        provLimit = val;
    }

    public int getProvision(){
        return provision;
    }

    public int getProvLimit(){
        return provLimit;
    }
}
