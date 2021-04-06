package com.example.databasehelm.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataHasil_Response {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("Total Bulan")
    @Expose
    private String totalBulan;
    @SerializedName("Total Hari")
    @Expose
    private String totalHari;
    @SerializedName("Untung Bulan")
    @Expose
    private String untungBulan;
    @SerializedName("Untung Hari")
    @Expose
    private String untungHari;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getTotalBulan() {
        return totalBulan;
    }

    public void setTotalBulan(String totalBulan) {
        this.totalBulan = totalBulan;
    }

    public String getTotalHari() {
        return totalHari;
    }

    public void setTotalHari(String totalHari) {
        this.totalHari = totalHari;
    }

    public String getUntungBulan() {
        return untungBulan;
    }

    public void setUntungBulan(String untungBulan) {
        this.untungBulan = untungBulan;
    }

    public String getUntungHari() {
        return untungHari;
    }

    public void setUntungHari(String untungHari) {
        this.untungHari = untungHari;
    }
}
