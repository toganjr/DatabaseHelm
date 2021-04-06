package com.example.databasehelm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasehelm.Data.ListTransaksi;
import com.example.databasehelm.Data.ListTransaksi_Response;
import com.example.databasehelm.Data.MsgInfo_Response;
import com.example.databasehelm.DataHistory.DataNote;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    BaseAPIService mApiService;
    ListAdapter mListadapter;

    RecyclerView listview;
    TextView txtNihil;
    Spinner spinner_sort;

    Integer[] id,harga_pokok,untung,jumlah,helm_id;
    String[] keterangan,nama_helm,jenis,tanggal;

    Integer[] idSearch,harga_pokokSearch,untungSearch,jumlahSearch,helm_idSearch;
    String[] keteranganSearch,nama_helmSearch,jenisSearch,tanggalSearch;




    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Transaksi");
        mApiService = UtilsApi.getClient().create(BaseAPIService.class);

        listview = (RecyclerView) v.findViewById(R.id.historyTrans);
        txtNihil = (TextView) v.findViewById(R.id.history_txtNihil);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);

        spinner_sort = v.findViewById(R.id.spinner1);
        String[] items = new String[]{"Semua", "Barang Keluar", "Barang Masuk"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner_sort.setAdapter(adapter);

        initListView(1,"");

        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch(position){
                    //Case statements
                    case 0: initListView(1,"");
                        break;
                    case 1: initListView2(1,"",position);
                        break;
                    case 2: initListView2(1,"",position);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing happen
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void SelectChoice(int noTransaksi, String jenis){
        final CharSequence[] items={"Hapus", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilihan");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Hapus")) {

                    if (jenis.equalsIgnoreCase("masuk")) {

                        hapusTransaksiMasuk(noTransaksi);
                    } else {

                        hapusTransaksiKeluar(noTransaksi);
                    }
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("STATUS", 1);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Berhasil Menghapus Transaksi", Toast.LENGTH_SHORT).show();

                } else {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (spinner_sort.getSelectedItemPosition() == 0){
                    initListView(1,query);

                } else if (spinner_sort.getSelectedItemPosition() == 1) {
                    initListView2(1,query,1);
                } else {
                    initListView2(1,query,2);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (spinner_sort.getSelectedItemPosition() == 0){
                    initListView(1,newText);

                } else if (spinner_sort.getSelectedItemPosition() == 1) {
                    initListView2(1,newText,1);
                } else {
                    initListView2(1,newText,2);
                }
                return true;
            }

        });

        View closeButton = searchView.findViewById(getResources().getIdentifier("android:id/search_close_btn", null, null));
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listview.setVisibility(View.VISIBLE);
                txtNihil.setVisibility(View.INVISIBLE);
                searchView.clearFocus();
                searchView.setQuery("", false); // clear the text
                searchView.setIconified(true); // close the search editor and make search icon again
                if (spinner_sort.getSelectedItemPosition() == 0) {
                    initListView(1,"");
                } else if (spinner_sort.getSelectedItemPosition() == 1) {
                    initListView2(1,"",1);
                } else {
                    initListView2(1,"",2);
                }
            }
        });
    }

    public void hapusTransaksiMasuk(int no) {
        Call<MsgInfo_Response> hapusTransaksiMasuk = mApiService.hapusTransaksiMasuk(
                no
        );
        hapusTransaksiMasuk.enqueue(new Callback<MsgInfo_Response>() {
            @Override
            public void onResponse(Call<MsgInfo_Response> call, Response<MsgInfo_Response> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<MsgInfo_Response> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hapusTransaksiKeluar(int no) {
        Call<MsgInfo_Response> hapusTransaksiKeluar = mApiService.hapusTransaksiKeluar(
                no
        );
        hapusTransaksiKeluar.enqueue(new Callback<MsgInfo_Response>() {
            @Override
            public void onResponse(Call<MsgInfo_Response> call, Response<MsgInfo_Response> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<MsgInfo_Response> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initListView2(int no, final String text, int id_jenis){
        Call<ListTransaksi_Response> getTransaksibyJenis = mApiService.getTransaksibyJenis(
                no,
                id_jenis
        );
        getTransaksibyJenis.enqueue(new Callback<ListTransaksi_Response>() {
            @Override
            public void onResponse(Call<ListTransaksi_Response> call, Response<ListTransaksi_Response> response) {
                boolean iserror_ = response.body().getError();
                if (iserror_ == false) {
                    List<ListTransaksi> list = new ArrayList<>();
                    list = response.body().getListTransaksi();
                    id = new Integer[list.size()];
                    keterangan = new String[list.size()];
                    harga_pokok = new Integer[list.size()];
                    untung = new Integer[list.size()];
                    jumlah = new Integer[list.size()];
                    helm_id = new Integer[list.size()];
                    nama_helm = new String[list.size()];
                    jenis = new String[list.size()];
                    tanggal = new String[list.size()];
                    idSearch = new Integer[list.size()];
                    keteranganSearch = new String[list.size()];
                    harga_pokokSearch = new Integer[list.size()];
                    untungSearch = new Integer[list.size()];
                    jumlahSearch = new Integer[list.size()];
                    helm_idSearch = new Integer[list.size()];
                    nama_helmSearch = new String[list.size()];
                    jenisSearch = new String[list.size()];
                    tanggalSearch = new String[list.size()];
                    for (int i =0;i<list.size();i++) {
                        id[i] = list.get(i).getNo();
                        keterangan[i] = list.get(i).getKeterangan();
                        harga_pokok[i] = list.get(i).getHargaPokok();
                        untung[i] = list.get(i).getUntung();
                        jumlah[i] = list.get(i).getJumlah();
                        helm_id[i] = list.get(i).getHelmId();
                        nama_helm[i] = list.get(i).getNamaHelm();
                        jenis[i] = list.get(i).getJenis();
                        tanggal[i] = list.get(i).getTanggal();
                    }
                    if(text.isEmpty()){
                        for (int cc=0; cc < list.size(); cc++){
                            ArrayList data = new ArrayList<DataNote>();
                            for (int i = 0; i < list.size(); i++)
                            {
                                data.add(
                                        new DataNote
                                                (
                                                        id[i],
                                                        keterangan[i],
                                                        harga_pokok[i],
                                                        untung[i],
                                                        jumlah[i],
                                                        helm_id[i],
                                                        nama_helm[i],
                                                        jenis[i],
                                                        tanggal[i]
                                                ));
                            }

                            mListadapter = new ListAdapter(data);
                            listview.setAdapter(mListadapter);
                        }
                    } else{
                        for (int cc=0; cc < list.size(); cc++){
                            if(nama_helm[cc].toLowerCase().contains(text.toLowerCase())) {
                                idSearch[cc] = id[cc];
                                keteranganSearch[cc] = keterangan[cc];
                                harga_pokokSearch[cc] = harga_pokok[cc];
                                untungSearch[cc] = untung[cc];
                                jumlahSearch[cc] = jumlah[cc];
                                helm_idSearch[cc] = helm_id[cc];
                                nama_helmSearch[cc] = nama_helm[cc];
                                jenisSearch[cc] = jenis[cc];
                                tanggalSearch[cc] = tanggal[cc];
                            }
                        }

                        ArrayList data = new ArrayList<DataNote>();
                        for (int i = 0; i < list.size(); i++)
                        {
                            if ((idSearch[i] != null)) {

                                data.add(
                                        new DataNote
                                                (
                                                        idSearch[i],
                                                        keteranganSearch[i],
                                                        harga_pokokSearch[i],
                                                        untungSearch[i],
                                                        jumlahSearch[i],
                                                        helm_idSearch[i],
                                                        nama_helmSearch[i],
                                                        jenisSearch[i],
                                                        tanggalSearch[i]
                                                ));

                            } else {

                            }
                        }

                        if (!data.isEmpty()) {
                            listview.setVisibility(View.VISIBLE);
                            txtNihil.setVisibility(View.INVISIBLE);
                        } else {
                            listview.setVisibility(View.INVISIBLE);
                            txtNihil.setVisibility(View.VISIBLE);
                        }

                        mListadapter = new ListAdapter(data);
                        listview.setAdapter(mListadapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ListTransaksi_Response> call, Throwable t) { ;
                Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initListView(int no, final String text){
        Call<ListTransaksi_Response> getTransaksi = mApiService.getTransaksi(
                no
        );
        getTransaksi.enqueue(new Callback<ListTransaksi_Response>() {
            @Override
            public void onResponse(Call<ListTransaksi_Response> call, Response<ListTransaksi_Response> response) {
                boolean iserror_ = response.body().getError();
                if (iserror_ == false) {
                    List<ListTransaksi> list = new ArrayList<>();
                    list = response.body().getListTransaksi();
                    id = new Integer[list.size()];
                    keterangan = new String[list.size()];
                    harga_pokok = new Integer[list.size()];
                    untung = new Integer[list.size()];
                    jumlah = new Integer[list.size()];
                    helm_id = new Integer[list.size()];
                    nama_helm = new String[list.size()];
                    jenis = new String[list.size()];
                    tanggal = new String[list.size()];
                    idSearch = new Integer[list.size()];
                    keteranganSearch = new String[list.size()];
                    harga_pokokSearch = new Integer[list.size()];
                    untungSearch = new Integer[list.size()];
                    jumlahSearch = new Integer[list.size()];
                    helm_idSearch = new Integer[list.size()];
                    nama_helmSearch = new String[list.size()];
                    jenisSearch = new String[list.size()];
                    tanggalSearch = new String[list.size()];
                    for (int i =0;i<list.size();i++) {
                        id[i] = list.get(i).getNo();
                        keterangan[i] = list.get(i).getKeterangan();
                        harga_pokok[i] = list.get(i).getHargaPokok();
                        untung[i] = list.get(i).getUntung();
                        jumlah[i] = list.get(i).getJumlah();
                        helm_id[i] = list.get(i).getHelmId();
                        nama_helm[i] = list.get(i).getNamaHelm();
                        jenis[i] = list.get(i).getJenis();
                        tanggal[i] = list.get(i).getTanggal();
                    }
                    if(text.isEmpty()){
                        for (int cc=0; cc < list.size(); cc++){
                            ArrayList data = new ArrayList<DataNote>();
                            for (int i = 0; i < list.size(); i++)
                            {
                                data.add(
                                        new DataNote
                                                (
                                                        id[i],
                                                        keterangan[i],
                                                        harga_pokok[i],
                                                        untung[i],
                                                        jumlah[i],
                                                        helm_id[i],
                                                        nama_helm[i],
                                                        jenis[i],
                                                        tanggal[i]

                                                ));
                            }

                            mListadapter = new ListAdapter(data);
                            listview.setAdapter(mListadapter);
                        }
                    } else{
                        for (int cc=0; cc < list.size(); cc++){
                            if(nama_helm[cc].toLowerCase().contains(text.toLowerCase())) {
                                idSearch[cc] = id[cc];
                                keteranganSearch[cc] = keterangan[cc];
                                harga_pokokSearch[cc] = harga_pokok[cc];
                                untungSearch[cc] = untung[cc];
                                jumlahSearch[cc] = jumlah[cc];
                                helm_idSearch[cc] = helm_id[cc];
                                nama_helmSearch[cc] = nama_helm[cc];
                                jenisSearch[cc] = jenis[cc];
                                tanggalSearch[cc] = tanggal[cc];
                            }
                        }

                        ArrayList data = new ArrayList<DataNote>();
                        for (int i = 0; i < list.size(); i++)
                        {
                            if ((idSearch[i] != null)) {

                                data.add(
                                        new DataNote
                                                (
                                                        idSearch[i],
                                                        keteranganSearch[i],
                                                        harga_pokokSearch[i],
                                                        untungSearch[i],
                                                        jumlahSearch[i],
                                                        helm_idSearch[i],
                                                        nama_helmSearch[i],
                                                        jenisSearch[i],
                                                        tanggalSearch[i]
                                                ));

                            } else {

                            }
                        }

                        if (!data.isEmpty()) {
                            listview.setVisibility(View.VISIBLE);
                            txtNihil.setVisibility(View.INVISIBLE);
                        } else {
                            listview.setVisibility(View.INVISIBLE);
                            txtNihil.setVisibility(View.VISIBLE);
                        }

                        mListadapter = new ListAdapter(data);
                        listview.setAdapter(mListadapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ListTransaksi_Response> call, Throwable t) { ;
                Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataNote> dataList;

        public ListAdapter(ArrayList<DataNote> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewNamaOut;
            TextView textViewJumlahOut;
            TextView textViewNamain;
            TextView textViewHargain;
            TextView textViewKetin;
            TextView textViewJumlahin;
            TextView textViewTanggalOut;
            TextView textViewTanggalin;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewNamaOut = (TextView) itemView.findViewById(R.id.TV_transnamaout);
                this.textViewJumlahOut = (TextView) itemView.findViewById(R.id.TV_transjumlahout);
                this.textViewNamain = (TextView) itemView.findViewById(R.id.TV_transnama);
                this.textViewHargain = (TextView) itemView.findViewById(R.id.TV_transharga);
                this.textViewKetin = (TextView) itemView.findViewById(R.id.TV_transket);
                this.textViewJumlahin = (TextView) itemView.findViewById(R.id.TV_transjumlah);
                this.textViewTanggalOut = (TextView) itemView.findViewById(R.id.TV_TransTanggalout);
                this.textViewTanggalin = (TextView) itemView.findViewById(R.id.TV_Transtanggal);
            }
        }

        @Override
        public int getItemViewType(int position) {
            String tipe = dataList.get(position).getJenis();
            if (tipe.equalsIgnoreCase("masuk")) {
                return  0;
            } else {
                return 1;
            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_transmasuk, parent, false);

                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_transkeluar, parent, false);

                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;
            }
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            if (dataList.get(position).getJenis().equalsIgnoreCase("masuk")) {
                String uangHarga = NumberFormat.getNumberInstance(Locale.US).format(dataList.get(position).getHarga_pokok()); //add
                holder.textViewNamain.setText(dataList.get(position).getNama_helm());
                holder.textViewJumlahin.setText(String.valueOf(dataList.get(position).getJumlah()));
                holder.textViewHargain.setText(String.valueOf("Rp. "+uangHarga));
                holder.textViewKetin.setText(dataList.get(position).getKeterangan());
                holder.textViewTanggalin.setText(dataList.get(position).getTanggal());
            }  else {
                holder.textViewNamaOut.setText(dataList.get(position).getNama_helm());
                holder.textViewJumlahOut.setText(String.valueOf(dataList.get(position).getJumlah())+" buah");
                holder.textViewTanggalOut.setText(dataList.get(position).getTanggal());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SelectChoice(dataList.get(position).getNo(),dataList.get(position).getJenis());

                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }

}
