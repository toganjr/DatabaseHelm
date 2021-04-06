package com.example.databasehelm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasehelm.Data.MsgInfo_Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStockActivity extends AppCompatActivity {

    BaseAPIService mApiService;
    Calendar calendar;
    Context mContext;

    EditText etNama,etHarga,etJumlah,etKet;
    Button btnTambah,btnKembali;

    int idHelm;
    String namaHelm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        mApiService = UtilsApi.getClient().create(BaseAPIService.class);
        mContext = this;
        getSupportActionBar().setTitle("Tambah Stock");

        idHelm = getIntent().getIntExtra("EXTRA_ID",0);
        namaHelm = getIntent().getStringExtra("EXTRA_NAMA");

        etNama = (EditText) findViewById(R.id.editTextNama);
        etHarga = (EditText) findViewById(R.id.editText1);
        etJumlah = (EditText) findViewById(R.id.editText2);
        etKet = (EditText) findViewById(R.id.editText3);

        etNama.setText(namaHelm);

        btnTambah = (Button) findViewById(R.id.button1);
        btnKembali = (Button) findViewById(R.id.button2);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etHarga.getText().toString().isEmpty())  {

                    if (!etJumlah.getText().toString().isEmpty())  {

                        tambahStock(idHelm,Integer.valueOf(etHarga.getText().toString()),Integer.valueOf(etJumlah.getText().toString()),etKet.getText().toString());
                        MainActivity.MainActivity.finish();
                        finish();
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(mContext, "Berhasil Menambahkan Stock", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, "Silahkan masukkan jumlah", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, "Silahkan masukkan harga pokok", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void tambahStock(int id, int harga, int jumlah, String keterangan) {
        calendar = Calendar.getInstance();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        Call<MsgInfo_Response> tambahTransaksiMasuk = mApiService.tambahTransaksiMasuk(
                id,
                harga,
                jumlah,
                keterangan
                );
        tambahTransaksiMasuk.enqueue(new Callback<MsgInfo_Response>() {
            @Override
            public void onResponse(Call<MsgInfo_Response> call, Response<MsgInfo_Response> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<MsgInfo_Response> call, Throwable t) {
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
