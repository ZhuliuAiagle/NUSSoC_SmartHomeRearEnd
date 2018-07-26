package com.zijinshen.datadef;

public class DeviceCondition {
    private String  conditionId;
    private int     fanOpen;
    private int     fanLevel;
    private int     lightOpen;
    private int     lightLevel;
    public String getConditionId(){
        return this.conditionId;
    }
    public int getFanOpen(){
        return this.fanOpen;
    }
    public int getFanLevel(){
        return this.fanLevel;
    }
    public int getLightOpen(){
        return this.lightOpen;
    }
    public int getLightLevel(){
        return this.lightLevel;
    }
    public void setConditionId(String id){
        this.conditionId = id;
    }
    public void setFanOpen(int fanOpen){
        this.fanOpen = fanOpen;
    }
    public void setFanLevel(int fanLevel){
        this.fanLevel= fanLevel;
    }
    public void setLightOpen(int lightOpen){
        this.lightOpen = lightOpen;
    }
    public void setLightLevel(int lightLevel){
        this.lightLevel = lightLevel;
    }

}
