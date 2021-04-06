package com.example.databasehelm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasehelm.Data.DataHasil_Response;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HasilFragment extends Fragment {

    EditText tanggal;
    RecyclerView listHasil;
    TextView hasilBulan,hasilHari,untungBulan,untungHari;

    private Calendar dateTime = Calendar.getInstance();
    private DatePickerDialog mTanggalPilih;
    private String tanggalPilih;

    BaseAPIService mApiService;

    int menu;


    public HasilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hasil, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Hasil");
        mApiService = UtilsApi.getClient().create(BaseAPIService.class);

        tanggal = (EditText) v.findViewById(R.id.editTextTanggal);
        hasilBulan = (TextView) v.findViewById(R.id.TV_hasilbulan);
        hasilHari = (TextView) v.findViewById(R.id.TV_hasilhari);
        untungBulan = (TextView) v.findViewById(R.id.TV_untungbulan);
        untungHari = (TextView) v.findViewById(R.id.TV_untunghari);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        String dateNumber = sd.format(c);

        String monthNumber  = (String) DateFormat.format("MM",    c); // 06
        String yearNumber         = (String) DateFormat.format("yyyy",  c);

        getHasil(dateNumber,monthNumber,yearNumber);

        tanggal.setText(formattedDate);
        tanggal.setInputType(InputType.TYPE_NULL);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTanggalPilih.show();
            }
        });

        initDateTimePickerDialog();

        return v;
    }

    public void getHasil(String tanggal, String bulan, String tahun){
        Call<DataHasil_Response> getHasil = mApiService.getHasil(
                tahun,
                bulan,
                tanggal
        );
        getHasil.enqueue(new Callback<DataHasil_Response>() {
            @Override
            public void onResponse(Call<DataHasil_Response> call, Response<DataHasil_Response> response) {
                boolean iserror_ = response.body().getError();
                if (iserror_ == false) {

                    String uangBulan = NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(response.body().getTotalBulan())); //add
                    String uangHari = NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(response.body().getTotalHari())); //add
                    String uanguntungBulan = NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(response.body().getUntungBulan())); //add
                    String uanguntungHari = NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(response.body().getUntungHari())); //add
                    hasilBulan.setText("Rp. "+uangBulan);
                    hasilHari.setText("Rp. "+uangHari);
                    untungBulan.setText("Rp. "+uanguntungBulan);
                    untungHari.setText("Rp. "+uanguntungHari);
                }
            }
            @Override
            public void onFailure(Call<DataHasil_Response> call, Throwable t) { ;
                Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initDateTimePickerDialog(){
        final SimpleDateFormat dateFormatterText = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        final SimpleDateFormat jsonFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mTanggalPilih = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateTime.set(year, monthOfYear, dayOfMonth);
                tanggalPilih = jsonFormat.format(dateTime.getTime());
                tanggal.setText(dateFormatterText.format(dateTime.getTime()));
                String dateNumber = jsonFormat.format(dateTime.getTime());
                String monthNumber  = (String) DateFormat.format("MM",    dateTime.getTime()); // 06
                String yearNumber       = (String) DateFormat.format("yyyy",  dateTime.getTime());
                getHasil(dateNumber,monthNumber,yearNumber);
            }

        },  dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));

    }

}
