package com.example.databasehelm.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListHelm {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("harga_pokok")
    @Expose
    private Integer hargaPokok;
    @SerializedName("stock")
    @Expose
    private Integer stock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getHargaPokok() {
        return hargaPokok;
    }

    public void setHargaPokok(Integer hargaPokok) {
        this.hargaPokok = hargaPokok;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
