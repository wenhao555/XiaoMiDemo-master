package com.example.asus.xiaomidemo;


/**
 * Created by realation on 2019/4/12.
 */
//用户对象
public class User
{
    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRoadName()
    {
        return roadName;
    }

    public void setRoadName(String roadName)
    {
        this.roadName = roadName;
    }

    public String getDoorNum()
    {
        return doorNum;
    }

    public void setDoorNum(String doorNum)
    {
        this.doorNum = doorNum;
    }

    public String getRoomNum()
    {
        return roomNum;
    }

    public void setRoomNum(String roomNum)
    {
        this.roomNum = roomNum;
    }

    private int userId;
    private String name;
    private String password;
    private String roadName;
    private String doorNum;
    private String roomNum;
}
