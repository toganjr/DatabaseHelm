package com.example.databasehelm.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListHelm_Response {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("list_helm")
    @Expose
    private List<ListHelm> listHelm = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<ListHelm> getListHelm() {
        return listHelm;
    }

    public void setListHelm(List<ListHelm> listHelm) {
        this.listHelm = listHelm;
    }

}
