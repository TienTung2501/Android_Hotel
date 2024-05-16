package com.example.khachsan;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private int idHoaDon,position;
    private Sqlite myDb;
    private ListView listViewHoaDon;
    private ArrayList<HoaDon> hoadons;
    private ArrayAdapter<HoaDon> adapter;
    TextInputEditText searchTxt;
    ImageView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        eventListener();
    }
    @Override
    protected void onStart() {
        super.onStart();
        myDb.openDb();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDb.closeDb();
    }

    private void eventListener() {

        listViewHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                HoaDon hoadon=hoadons.get(i);
                idHoaDon=hoadon.getId();
                position=i;
                ArrayList<HoaDon> dem=new ArrayList<>();
                double tongtien=hoadon.getDonGia()*hoadon.getSoNgay();
                for (HoaDon hoadon1:hoadons){
                    if(hoadon1.getSoNgay()*hoadon1.getDonGia()>=tongtien)
                        dem.add(hoadon);
                }
                Toast.makeText(MainActivity.this,hoadon.getHoTen()+" co tong " +String.valueOf(dem.size())+ " hoa don co so tien lon hon",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HoaDon> hoaDonsTmp = new ArrayList<>(hoadons); // Tạo một bản sao của hoadons
                ArrayList<HoaDon> list = new ArrayList<>(); // Tạo một bản sao của hoadons
                double searchvalue;
                try {
                    searchvalue = Double.parseDouble(searchTxt.getText().toString().trim());
                    if(searchvalue==0){
                        displayData();
                    }
                    else {
                        for (HoaDon hoadon : hoaDonsTmp) {
                            if (hoadon.getSoNgay() * hoadon.getDonGia() >= searchvalue)
                                list.add(hoadon);
                        }
                        if (list.size() > 0) {
                            reloadData(list);
                        } else {
                            Toast.makeText(MainActivity.this, "Khong co hoa don thoa man", Toast.LENGTH_SHORT).show();
                            reloadData(list);
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void initComponent() {
        myDb=new Sqlite(this);
        hoadons=new ArrayList<>();
        listViewHoaDon=findViewById(R.id.ListViewHoaDon);
        //addBtn=findViewById(R.id.addBtn);
        searchBtn=findViewById(R.id.SearchBtn);
        searchTxt=findViewById(R.id.SearchTxt);
        displayData();

    }
    private void sortHoaDonsBySoPhong() {
        Collections.sort(hoadons, new Comparator<HoaDon>() {
            @Override
            public int compare(HoaDon hoadon1, HoaDon hoadon2) {
                // Sắp xếp giảm dần theo số phòng
                return hoadon2.getSoPhong() - hoadon1.getSoPhong();
            }
        });
    }
    private ArrayList<HoaDon> sortHoaDonsBySoPhongList(ArrayList<HoaDon> inputList) {
        ArrayList<HoaDon> sortedList = new ArrayList<>(inputList);
        Collections.sort(sortedList, new Comparator<HoaDon>() {
            @Override
            public int compare(HoaDon hoadon1, HoaDon hoadon2) {
                // Sắp xếp giảm dần theo số phòng
                return hoadon2.getSoPhong() - hoadon1.getSoPhong();
            }
        });
        return sortedList;
    }



    private void displayData(){
        hoadons.clear();
        fetchData();
        sortHoaDonsBySoPhong();
        adapter=new HoaDonAdapter(MainActivity.this, R.layout.hoadon_item, hoadons);
        listViewHoaDon.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Cập nhật dữ liệu trong Adapter
    }
    private void reloadData(ArrayList<HoaDon> list){
        ArrayList<HoaDon> hoaDonNews= sortHoaDonsBySoPhongList(list);
        adapter=new HoaDonAdapter(MainActivity.this, R.layout.hoadon_item, hoaDonNews);
        listViewHoaDon.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Cập nhật dữ liệu trong Adapter
    }
    private  void fetchData(){
        Cursor cursor= myDb.DisplayAll();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndexOrThrow(Sqlite.getId()));
            String _hoten=cursor.getString(cursor.getColumnIndexOrThrow(Sqlite.getHoTen()));
            int _sophong=cursor.getInt(cursor.getColumnIndexOrThrow(Sqlite.getSoPhong()));
            double _dongia=cursor.getDouble(cursor.getColumnIndexOrThrow(Sqlite.getDonGia()));
            int _songay=cursor.getInt(cursor.getColumnIndexOrThrow(Sqlite.getSoNgay()));
            HoaDon c=new HoaDon(id,_hoten,_sophong,_songay,_dongia);
            hoadons.add(c);
        }

    }




}