package com.example.databasehelm.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTransaksi {

    @SerializedName("no")
    @Expose
    private Integer no;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("harga_pokok")
    @Expose
    private Integer hargaPokok;
    @SerializedName("untung")
    @Expose
    private Integer untung;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("helm_id")
    @Expose
    private Integer helmId;
    @SerializedName("nama_helm")
    @Expose
    private String namaHelm;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getHargaPokok() {
        return hargaPokok;
    }

    public void setHargaPokok(Integer hargaPokok) {
        this.hargaPokok = hargaPokok;
    }

    public Integer getUntung() {
        return untung;
    }

    public void setUntung(Integer untung) {
        this.untung = untung;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getHelmId() {
        return helmId;
    }

    public void setHelmId(Integer helmId) {
        this.helmId = helmId;
    }

    public String getNamaHelm() {
        return namaHelm;
    }

    public void setNamaHelm(String namaHelm) {
        this.namaHelm = namaHelm;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
