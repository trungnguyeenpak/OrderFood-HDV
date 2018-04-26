package com.trungnguyeen.orderfood.table_featrue.view;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.BottomSheetItemClick;

/**
 * Created by trungnguyeen on 4/18/18.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private LinearLayout order_item;
    private LinearLayout tinhTien_item;
    private Table table;
    private BottomSheetItemClick bottomSheetItemClick;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setBottomSheetItemClick(BottomSheetItemClick bottomSheetItemClick) {
        this.bottomSheetItemClick = bottomSheetItemClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_bottom_sheet, container, false);
        order_item = view.findViewById(R.id.fragment_order);
        tinhTien_item = view.findViewById(R.id.fragment_tinh_tien);
        setEvents(view);

        return view;
    }

    private void setEvents(View view) {
        order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callback startactivity order
                bottomSheetItemClick.onClickOrder(table);
            }
        });

        tinhTien_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callback tinh tien to table activity
                bottomSheetItemClick.onClickItemThanhToan(table);

            }
        });
    }
}

