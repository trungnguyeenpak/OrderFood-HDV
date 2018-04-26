package com.trungnguyeen.orderfood.table_featrue.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.TableItemClickListener;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/18/18.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private Context context;
    private ArrayList<Table> data;
    private TableItemClickListener itemListener;

    @Override
    public TableAdapter.TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.item_table, parent, false);

        return new TableViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(TableAdapter.TableViewHolder holder, int position) {

        //Get item in data list with position
        //And bind item to view holder
        Table table = this.data.get(position);
        holder.bindView(table);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Table getItem(int id) {
        return this.data.get(id);
    }

    public void setData(ArrayList<Table> data) {
        this.data = data;
    }

    public void setItemListener(TableItemClickListener itemListener) {
        this.itemListener = itemListener;
    }

    public class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;
        private TextView tableNumber;
        private TextView tableStatus;

        TableViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            tableNumber = itemView.findViewById(R.id.table_number);
            tableStatus = itemView.findViewById(R.id.table_status);
            itemView.setOnClickListener(this);
        }

        //Setup data for each item view
        void bindView(Table table) {
            tableNumber.setText(table.getName().toString());
            if(table.getStatus() == 0){ //status = 0 hima Table
                tableStatus.setBackgroundResource(R.drawable.hima_table_shap);
            }
            else{
                //isogashii table
                tableStatus.setBackgroundResource(R.drawable.isogashi_table_shap);
            }
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
