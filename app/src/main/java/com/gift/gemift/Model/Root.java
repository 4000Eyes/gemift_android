package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Root implements Serializable {

    public ArrayList<OccasionsModel> no_occasions;
    public ArrayList<Object> days_since_occasion;
    public ArrayList<DataModel> interest;
    @SerializedName("no interest")
    public ArrayList<DataModel> noInterest;
    public ArrayList<Relationship> relationship;
    public ArrayList<DataModel> contributor_invites;
    public ArrayList<UnapprovedOccasion> unapproved_occasions;

    public ArrayList<OccasionsModel> getNo_occasions() {
        return no_occasions;
    }

    public void setNo_occasions(ArrayList<OccasionsModel> no_occasions) {
        this.no_occasions = no_occasions;
    }

    public ArrayList<Object> getDays_since_occasion() {
        return days_since_occasion;
    }

    public void setDays_since_occasion(ArrayList<Object> days_since_occasion) {
        this.days_since_occasion = days_since_occasion;
    }

    public ArrayList<DataModel> getInterest() {
        return interest;
    }

    public void setInterest(ArrayList<DataModel> interest) {
        this.interest = interest;
    }

    public ArrayList<DataModel> getNoInterest() {
        return noInterest;
    }

    public void setNoInterest(ArrayList<DataModel> noInterest) {
        this.noInterest = noInterest;
    }

    public ArrayList<Relationship> getRelationship() {
        return relationship;
    }

    public void setRelationship(ArrayList<Relationship> relationship) {
        this.relationship = relationship;
    }

    public ArrayList<DataModel> getContributor_invites() {
        return contributor_invites;
    }

    public void setContributor_invites(ArrayList<DataModel> contributor_invites) {
        this.contributor_invites = contributor_invites;
    }

    public ArrayList<UnapprovedOccasion> getUnapproved_occasions() {
        return unapproved_occasions;
    }

    public void setUnapproved_occasions(ArrayList<UnapprovedOccasion> unapproved_occasions) {
        this.unapproved_occasions = unapproved_occasions;
    }
}


