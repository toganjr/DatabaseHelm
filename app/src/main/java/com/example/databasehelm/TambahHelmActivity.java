package com.example.databasehelm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.databasehelm.Data.MsgInfo_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahHelmActivity extends AppCompatActivity {

    BaseAPIService mApiService;
    Context mContext;

    EditText etNama,etHarga,etJumlah,etKet;
    Button btnTambah,btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_helm);
        mApiService = UtilsApi.getClient().create(BaseAPIService.class);
        mContext = this;
        getSupportActionBar().setTitle("Tambah Helm");
        etNama = (EditText) findViewById(R.id.editText1);
        etHarga = (EditText) findViewById(R.id.editText2);
        etJumlah = (EditText) findViewById(R.id.editText3);
        etKet = (EditText) findViewById(R.id.editText4);
        btnTambah = (Button) findViewById(R.id.button1);
        btnKembali = (Button) findViewById(R.id.button2);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNama.getText().toString().isEmpty())  {

                    if (!etHarga.getText().toString().isEmpty())  {

                        if (!etJumlah.getText().toString().isEmpty()) {

                            tambahHelm(etNama.getText().toString(),Integer.valueOf(etHarga.getText().toString()),Integer.valueOf(etJumlah.getText().toString()),etKet.getText().toString());
                            MainActivity.MainActivity.finish();
                            finish();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(mContext, "Berhasil Menambah Helm", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext, "Silahkan masukkan stock helm", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Silahkan masukkan harga pokok", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Silahkan masukkan Nama Helm", Toast.LENGTH_SHORT).show();
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

    public void tambahHelm(String nama, int harga_pokok, int stock, String keterangan) {
        Call<MsgInfo_Response> tambahDataHelm = mApiService.tambahDataHelm(
                nama,
                harga_pokok,
                stock,
                keterangan
        );
        tambahDataHelm.enqueue(new Callback<MsgInfo_Response>() {
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
