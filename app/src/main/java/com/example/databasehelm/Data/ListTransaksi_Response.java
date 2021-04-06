package com.example.databasehelm.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListTransaksi_Response {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("list_transaksi")
    @Expose
    private List<ListTransaksi> listTransaksi = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<ListTransaksi> getListTransaksi() {
        return listTransaksi;
    }

    public void setListTransaksi(List<ListTransaksi> listTransaksi) {
        this.listTransaksi = listTransaksi;
    }

}
