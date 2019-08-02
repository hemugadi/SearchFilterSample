package com.android.searchfiltersample.model;

import com.google.gson.annotations.SerializedName;

public class BankDetails {
    @SerializedName("ifsc")
    private String ifsc;
    @SerializedName("bank_id")
    private int bankId;
    @SerializedName("branch")
    private String branch;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("district")
    private String district;
    @SerializedName("state")
    private String state;
    @SerializedName("bank_name")
    private String bankName;


    public BankDetails(String ifsc, int bankId, String branch, String address, String city,
                                            String district, String state, String bankName) {
        this.ifsc = ifsc;
        this.bankId = bankId;
        this.branch = branch;
        this.address = address;
        this.city = city;
        this.district = district;
        this.state = state;
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
