package com.example.khachsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    public HoaDonAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public HoaDonAdapter(@NonNull Context context, int resource, @NonNull List<HoaDon> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v==null){
            LayoutInflater vi=LayoutInflater.from(getContext());
            v=vi.inflate(R.layout.hoadon_item,null);// lấy từ layout ta thiết kế

        }
        HoaDon sv=getItem(position);
        if(sv!= null){

            TextView hoten= v.findViewById(R.id.TenTxt);
            TextView sophong=v.findViewById(R.id.PhongTxt);
            TextView tongtien=v.findViewById(R.id.TongTienTxt);
            hoten.setText(sv.getHoTen());
            sophong.setText(String.valueOf(sv.getSoPhong()));
            tongtien.setText(String.valueOf(sv.getDonGia()*sv.getSoNgay()));
        }
        return v;
    }
}
