package com.gift.gemift.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Datum implements Serializable {

    public ArrayList<OccasionsModel> occasions;
    public ArrayList<DataModel> interest;
    @SerializedName("no interest")
    public ArrayList<DataModel> noInterest;
    public ArrayList<Relationship> relationship;
    public ArrayList<Object> approval;
    public ArrayList<DataModel> contributor_invites;

    public ArrayList<OccasionsModel> getOccasions() {
        return occasions;
    }

    public void setOccasions(ArrayList<OccasionsModel> occasions) {
        this.occasions = occasions;
    }

    public ArrayList<DataModel> getInterest() {
        return interest;
    }

    public void setInterest(ArrayList<DataModel> interest) {
        this.interest = interest;
    }

    public ArrayList<Relationship> getRelationship() {
        return relationship;
    }

    public void setRelationship(ArrayList<Relationship> relationship) {
        this.relationship = relationship;
    }

    public ArrayList<Object> getApproval() {
        return approval;
    }

    public void setApproval(ArrayList<Object> approval) {
        this.approval = approval;
    }

    public ArrayList<DataModel> getContributor_invites() {
        return contributor_invites;
    }

    public void setContributor_invites(ArrayList<DataModel> contributor_invites) {
        this.contributor_invites = contributor_invites;
    }

    public ArrayList<DataModel> getNoInterest() {
        return noInterest;
    }

    public void setNoInterest(ArrayList<DataModel> noInterest) {
        this.noInterest = noInterest;
    }
}
