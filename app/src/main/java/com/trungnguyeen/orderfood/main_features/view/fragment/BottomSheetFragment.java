package com.trungnguyeen.orderfood.main_features.view.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Table;

/**
 * Created by trungnguyeen on 4/18/18.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private LinearLayout order_item;
    private LinearLayout tinhTien_item;
    private Table selectedTable;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public void setSelectedTable(Table selectedTable) {
        this.selectedTable = selectedTable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);
        order_item = view.findViewById(R.id.fragment_order);
        tinhTien_item = view.findViewById(R.id.fragment_tinh_tien);

        setEvents(view);

        return view;
    }

    private void setEvents(View view) {
        order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to activity order
                Toast.makeText(getContext(), "Order " + selectedTable.getId(), Toast.LENGTH_SHORT).show();

            }
        });

        tinhTien_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Request tinh tien to service
                Toast.makeText(getContext(), "Tinh tien " + selectedTable.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

