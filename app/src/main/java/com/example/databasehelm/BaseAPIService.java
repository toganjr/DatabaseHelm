package com.example.databasehelm;



import com.example.databasehelm.Data.DataHasil_Response;
import com.example.databasehelm.Data.ListHelm_Response;
import com.example.databasehelm.Data.ListTransaksi_Response;
import com.example.databasehelm.Data.MsgInfo_Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseAPIService {

    @FormUrlEncoded
    @POST("get_listhelm.php")
    Call<ListHelm_Response> getHelm(@Field("no") int no);

    @FormUrlEncoded
    @POST("get_transaksi.php")
    Call<ListTransaksi_Response> getTransaksi(@Field("no") int no);

    @FormUrlEncoded
    @POST("get_transaksibyJenis.php")
    Call<ListTransaksi_Response> getTransaksibyJenis(@Field("no") int no,
                                                     @Field("jenis") int jenis
                                                     );

    @FormUrlEncoded
    @POST("get_transaksibyHelm.php")
    Call<ListTransaksi_Response> getTransaksibyHelm(@Field("id") int id);

    @FormUrlEncoded
    @POST("transaksimasuk_tambah.php")
    Call<MsgInfo_Response> tambahTransaksiMasuk(@Field("id") int id,
                                                @Field("harga") int harga,
                                                @Field("jumlah") int jumlah,
                                                @Field("keterangan") String keterangan
                                                );

    @FormUrlEncoded
    @POST("transaksikeluar_tambah.php")
    Call<MsgInfo_Response> tambahTransaksiKeluar(@Field("id") int id,
                                                @Field("harga_jual") int harga,
                                                @Field("jumlah") int jumlah
    );

    @FormUrlEncoded
    @POST("transaksimasuk_hapus.php")
    Call<MsgInfo_Response> hapusTransaksiMasuk(@Field("no") int id
    );

    @FormUrlEncoded
    @POST("transaksikeluar_hapus.php")
    Call<MsgInfo_Response> hapusTransaksiKeluar(@Field("no") int id
    );

    @FormUrlEncoded
    @POST("edit_helm.php")
    Call<MsgInfo_Response> editDataHelm(@Field("id") int id,
                                        @Field("nama") String nama,
                                        @Field("harga_pokok") int harga_pokok,
                                        @Field("stock") int stock
    );

    @FormUrlEncoded
    @POST("tambah_helm.php")
    Call<MsgInfo_Response> tambahDataHelm(@Field("nama") String nama,
                                          @Field("harga_pokok") int harga_pokok,
                                          @Field("stock") int stock,
                                          @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("get_hasil.php")
    Call<DataHasil_Response> getHasil(@Field("tahun") String tahun,
                                      @Field("bulan") String bulan,
                                      @Field("tanggal") String tanggal
    );

}
