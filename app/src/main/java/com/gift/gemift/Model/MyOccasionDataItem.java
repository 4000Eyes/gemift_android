package com.gift.gemift.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyOccasionDataItem{

	@SerializedName("occasions")
	private List<MyOccasionDataItems> occasions;

	@SerializedName("contributor_invites")
	private List<DataModel>contributor_invites;

	@SerializedName("unapproved_occasions")
	private List<UnapprovedOccasion>unapproved_occasions;

	@SerializedName("contributor_approval")
	private List<ContributorApproval>contributor_approval;


	public List<DataModel> getContributor_invites() {
		return contributor_invites;
	}

	public List<UnapprovedOccasion> getUnapproved_occasions() {
		return unapproved_occasions;
	}

	public void setUnapproved_occasions(List<UnapprovedOccasion> unapproved_occasions) {
		this.unapproved_occasions = unapproved_occasions;
	}

	public List<ContributorApproval> getContributor_approval() {
		return contributor_approval;
	}

	public void setContributor_approval(List<ContributorApproval> contributor_approval) {
		this.contributor_approval = contributor_approval;
	}

	public void setApproval(List<UnapprovedOccasion> approval) {
		this.unapproved_occasions = approval;
	}

	public void setContributor_invites(List<DataModel> contributor_invites) {
		this.contributor_invites = contributor_invites;
	}

	public void setOccasions(List<MyOccasionDataItems> occasions){
		this.occasions = occasions;
	}

	public List<MyOccasionDataItems> getOccasions(){
		return occasions;
	}


}