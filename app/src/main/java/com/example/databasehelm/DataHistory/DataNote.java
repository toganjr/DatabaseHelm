package com.example.databasehelm.DataHistory;

public class DataNote {
    Integer no,harga_pokok,untung,jumlah,helm_id;
    String keterangan,nama_helm,jenis,tanggal;

    public DataNote(Integer no,String keterangan, Integer harga_pokok, Integer untung, Integer jumlah, Integer helm_id, String nama_helm, String jenis, String tanggal ) {
        this.no = no;
        this.keterangan = keterangan;
        this.harga_pokok = harga_pokok;
        this.untung = untung;
        this.jumlah = jumlah;
        this.helm_id = helm_id;
        this.nama_helm = nama_helm;
        this.jenis = jenis;
        this.tanggal = tanggal;
    }

    public Integer getNo() { return no;}

    public Integer getHarga_pokok() { return harga_pokok;}

    public Integer getUntung() {
        return untung;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public Integer getHelm_id() { return helm_id; }

    public String getKeterangan() { return keterangan; }

    public String getNama_helm() { return nama_helm; }

    public String getJenis() { return jenis; }

    public String getTanggal() { return tanggal; }

}