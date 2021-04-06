package com.example.databasehelm.DataList;

public class DataNote {
    int id,harga_pokok,stock;
    String nama;

    public DataNote(int id,String nama, int harga_pokok, int stock) {
        this.id = id;
        this.nama = nama;
        this.harga_pokok = harga_pokok;
        this.stock = stock;

    }

    public int getId() { return id;}

    public int getHarga_pokok() { return harga_pokok;}

    public int getStock() {
        return stock;
    }

    public String getNama() {
        return nama;
    }

}