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

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHelmActivity extends AppCompatActivity {

    BaseAPIService mApiService;
    Context mContext;

    EditText etNama,etHarga,etJumlah;
    Button btnSelesai,btnKembali;

    int idHelm,hargaHelm, jumlahHelm;
    String namaHelm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_helm);
        mApiService = UtilsApi.getClient().create(BaseAPIService.class);
        mContext = this;
        getSupportActionBar().setTitle("Edit Helm");

        idHelm = getIntent().getIntExtra("EXTRA_ID",0);
        namaHelm = getIntent().getStringExtra("EXTRA_NAMA");
        hargaHelm = getIntent().getIntExtra("EXTRA_HARGA",0);
        jumlahHelm = getIntent().getIntExtra("EXTRA_JUMLAH",0);

        etNama = (EditText) findViewById(R.id.editText1);
        etHarga = (EditText) findViewById(R.id.editText2);
        etJumlah = (EditText) findViewById(R.id.editText3);

        etNama.setText(namaHelm);
        etHarga.setText(String.valueOf(hargaHelm));
        etJumlah.setText(String.valueOf(jumlahHelm));

        btnSelesai = (Button) findViewById(R.id.button1);
        btnKembali = (Button) findViewById(R.id.button2);

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNama.getText().toString().isEmpty())  {

                    if (!etHarga.getText().toString().isEmpty())  {

                        if (!etJumlah.getText().toString().isEmpty()) {

                            editDataHelm(idHelm,etNama.getText().toString(),Integer.valueOf(etHarga.getText().toString()),Integer.valueOf(etJumlah.getText().toString()));
                            MainActivity.MainActivity.finish();
                            finish();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(mContext, "Berhasil Mengedit Helm", Toast.LENGTH_SHORT).show();

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

    public void editDataHelm(int id, String nama, int harga_pokok, int stock) {
        Call<MsgInfo_Response> editDataHelm = mApiService.editDataHelm(
                id,
                nama,
                harga_pokok,
                stock
        );
        editDataHelm.enqueue(new Callback<MsgInfo_Response>() {
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
