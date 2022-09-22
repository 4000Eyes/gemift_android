package com.gift.gemift.Model;

import java.io.Serializable;

public class InitiateFundModel implements Serializable {

    private InitiateFundCreator creator;

    private String request_type;

    private InitiateFundFriends[] friends;

    public InitiateFundModel(){

    }
    public InitiateFundModel(InitiateFundCreator creator, String request_type, InitiateFundFriends[] friends) {
        this.creator = creator;
        this.request_type = request_type;
        this.friends = friends;
    }

    public InitiateFundCreator getCreator ()
    {
        return creator;
    }

    public void setCreator (InitiateFundCreator creator)
    {
        this.creator = creator;
    }

    public String getRequest_type ()
    {
        return request_type;
    }

    public void setRequest_type (String request_type)
    {
        this.request_type = request_type;
    }

    public InitiateFundFriends[] getFriends ()
    {
        return friends;
    }

    public void setFriends (InitiateFundFriends[] friends)
    {
        this.friends = friends;
    }
}
