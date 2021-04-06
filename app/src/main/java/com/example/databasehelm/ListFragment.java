package com.example.databasehelm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasehelm.Data.ListHelm;
import com.example.databasehelm.Data.ListHelm_Response;
import com.example.databasehelm.DataList.DataNote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    BaseAPIService mApiService;
    ListAdapter mListadapter;

    int [] id,harga_pokok,stock;
    String [] nama;
    int [] idsearch,harga_pokokSearch,stockSearch;
    String [] namasearch;

    RecyclerView listview;
    TextView txtNihil;
    FloatingActionButton btnAdd;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gudang");
        mApiService = UtilsApi.getClient().create(BaseAPIService.class);

        listview = (RecyclerView) v.findViewById(R.id.listHelm);
        txtNihil = (TextView) v.findViewById(R.id.list_txtNihil);
        btnAdd = (FloatingActionButton) v.findViewById(R.id.fab_add);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);

        initListView(1,"");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TambahHelmActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return v;
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

                initListView(1,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                initListView(1,newText);
                return true;
            }

        });

        View closeButton = searchView.findViewById(getResources().getIdentifier("android:id/search_close_btn", null, null));
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listview.setVisibility(View.VISIBLE);
                txtNihil.setVisibility(View.INVISIBLE);
                initListView(1,"");
                searchView.clearFocus();
                searchView.setQuery("", false); // clear the text
                searchView.setIconified(true); // close the search editor and make search icon again
            }
        });
    }

    private void SelectChoice(int idHelm, String namaHelm, int hargaHelm, int jumlahHelm){
        final CharSequence[] items={"Tambah Stock","Tambah Transaksi","Edit", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilihan");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Tambah Stock")) {
                    Intent intent = new Intent(getActivity(), AddStockActivity.class);
                    intent.putExtra("EXTRA_ID", idHelm);
                    intent.putExtra("EXTRA_NAMA", namaHelm);
                    startActivity(intent);
                } else if (items[i].equals("Tambah Transaksi")) {
                    Intent intent = new Intent(getActivity(), AddTransaksiActivity.class);
                    intent.putExtra("EXTRA_ID", idHelm);
                    intent.putExtra("EXTRA_NAMA", namaHelm);
                    startActivity(intent);
                } else if (items[i].equals("Edit")) {
                    Intent intent = new Intent(getActivity(), EditHelmActivity.class);
                    intent.putExtra("EXTRA_ID", idHelm);
                    intent.putExtra("EXTRA_NAMA", namaHelm);
                    intent.putExtra("EXTRA_HARGA", hargaHelm);
                    intent.putExtra("EXTRA_JUMLAH", jumlahHelm);
                    startActivity(intent);
                } else {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public void initListView(int no, final String text){
        Call<ListHelm_Response> getHelm = mApiService.getHelm(
                no
        );
        getHelm.enqueue(new Callback<ListHelm_Response>() {
            @Override
            public void onResponse(Call<ListHelm_Response> call, Response<ListHelm_Response> response) {
                boolean iserror_ = response.body().getError();
                if (iserror_ == false) {
                    List<ListHelm> list = new ArrayList<>();
                    list = response.body().getListHelm();
                    id = new int[list.size()];
                    nama = new String[list.size()];
                    harga_pokok = new int[list.size()];
                    stock = new int[list.size()];
                    idsearch = new int[list.size()];
                    namasearch = new String[list.size()];
                    harga_pokokSearch = new int[list.size()];
                    stockSearch = new int[list.size()];
                    for (int i =0;i<list.size();i++) {
                        id[i] = list.get(i).getId();
                        nama[i] = list.get(i).getNama();
                        harga_pokok[i] = list.get(i).getHargaPokok();
                        stock[i] = list.get(i).getStock();
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
                                                        nama[i],
                                                        harga_pokok[i],
                                                        stock[i]
                                                ));
                            }

                            mListadapter = new ListAdapter(data);
                            listview.setAdapter(mListadapter);
                        }
                    } else{
                        for (int cc=0; cc < list.size(); cc++){
                            if(nama[cc].toLowerCase().contains(text.toLowerCase())) {
                                idsearch[cc] = id[cc];
                                namasearch[cc] = nama[cc];
                                harga_pokokSearch[cc] = harga_pokok[cc];
                                stockSearch[cc] = stock[cc];
                            }
                        }

                        ArrayList data = new ArrayList<DataNote>();
                        for (int i = 0; i < list.size(); i++)
                        {
                            if ((idsearch[i] != 0)) {

                                data.add(
                                        new DataNote
                                                (
                                                        idsearch[i],
                                                        namasearch[i],
                                                        harga_pokokSearch[i],
                                                        stockSearch[i]
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
            public void onFailure(Call<ListHelm_Response> call, Throwable t) { ;
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
            TextView textViewNama;
            TextView textViewHarga;
            TextView textViewStock;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewHarga = (TextView) itemView.findViewById(R.id.TV_listharga);
                this.textViewNama = (TextView) itemView.findViewById(R.id.TV_listnama);
                this.textViewStock = (TextView) itemView.findViewById(R.id.TV_liststock);
            }
        }


        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_listhelm, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            if (dataList.get(position).getStock() > 0) {
                String uangHarga = NumberFormat.getNumberInstance(Locale.US).format(dataList.get(position).getHarga_pokok()); //add
                holder.textViewNama.setText(dataList.get(position).getNama());
                holder.textViewHarga.setText("Rp. "+uangHarga);
                holder.textViewStock.setText(String.valueOf(dataList.get(position).getStock()));

                holder.textViewNama.setTextColor(getResources().getColor(R.color.realblack));
                holder.textViewStock.setTextColor(getResources().getColor(R.color.realblack));
                holder.textViewHarga.setTextColor(getResources().getColor(R.color.realblack));
            }  else {
                String uangHarga = NumberFormat.getNumberInstance(Locale.US).format(dataList.get(position).getHarga_pokok()); //add
                holder.textViewNama.setText(dataList.get(position).getNama());
                holder.textViewHarga.setText("Rp. "+uangHarga);
                holder.textViewStock.setText(String.valueOf(dataList.get(position).getStock()));

                holder.textViewNama.setTextColor(getResources().getColor(R.color.textColor));
                holder.textViewStock.setTextColor(getResources().getColor(R.color.textColor));
                holder.textViewHarga.setTextColor(getResources().getColor(R.color.textColor));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SelectChoice(dataList.get(position).getId(),dataList.get(position).getNama(),dataList.get(position).getHarga_pokok(),dataList.get(position).getStock());
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }

    // FOR API 24
    static int findIndexOf(int V, int[] arr) {
        return IntStream.range(0, arr.length)
                .filter(i->arr[i]==V)
                .findFirst()
                .getAsInt();
    }

}
