package com.example.siddhantlad.taqneeq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomTicketSelectAdapter extends RecyclerView.Adapter<CustomTicketSelectAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelClassTicket> items;
    String timeformat, day;
    String nameOfSelected;

    public interface OnButtonClickUpdate {
        void onButtonClick(String id, int num, int cost, String name, int enters);
    }

    private OnButtonClickUpdate btnClick;
    int num = 0;

    public CustomTicketSelectAdapter(Context context, ArrayList<ModelClassTicket> items) {
        this.context = context;
        this.items = items;
        if (context instanceof CustomTicketSelectAdapter.OnButtonClickUpdate) {
            btnClick = (CustomTicketSelectAdapter.OnButtonClickUpdate) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.ticket_select_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {

        holder.itemTitle.setText(items.get(position).getName());
        holder.itemEnters.setText(items.get(position).getEnters());
        holder.itemCost.setText(items.get(position).getCost()+" ₹");
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameOfSelected == null) {
                    nameOfSelected = items.get(position).getId();
                    if (holder.count.getText().toString().equals("10")) {
                    } else {
                        holder.count.setText(Integer.toString(Integer.parseInt(holder.count.getText().toString()) + 1));
                        num++;
                        btnClick.onButtonClick(items.get(position).getId(), num, Integer.parseInt(items.get(position).getCost()), items.get(position).getName(), Integer.parseInt(items.get(position).getEnters()));
                    }
                } else if (nameOfSelected.equals(items.get(position).getId())) {
                    if (holder.count.getText().toString().equals("10")) {
                        btnClick.onButtonClick(items.get(position).getId(), num, Integer.parseInt(items.get(position).getCost()), items.get(position).getName(), Integer.parseInt(items.get(position).getEnters()));
                    } else {
                        holder.count.setText(Integer.toString(Integer.parseInt(holder.count.getText().toString()) + 1));
                        num++;
                        btnClick.onButtonClick(items.get(position).getId(), num, Integer.parseInt(items.get(position).getCost()), items.get(position).getName(), Integer.parseInt(items.get(position).getEnters()));
                    }
                } else {
                    Toast.makeText(context, "Another Ticket Already Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.count.getText().toString().equals("0")) {
                } else if (holder.count.getText().toString().equals("1")) {
                    holder.count.setText(Integer.toString(Integer.parseInt(holder.count.getText().toString()) - 1));
                    num--;
                    nameOfSelected = null;
                    btnClick.onButtonClick(items.get(position).getId(), num, Integer.parseInt(items.get(position).getCost()), items.get(position).getName(), Integer.parseInt(items.get(position).getEnters()));
                } else {
                    holder.count.setText(Integer.toString(Integer.parseInt(holder.count.getText().toString()) - 1));
                    num--;
                    btnClick.onButtonClick(items.get(position).getId(), num, Integer.parseInt(items.get(position).getCost()), items.get(position).getName(), Integer.parseInt(items.get(position).getEnters()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitle, count;
        private TextView itemCost, itemEnters;
        Button increase, decrease;

        public CustomViewHolder(View view) {
            super(view);
            itemCost = view.findViewById(R.id.item_cost);
            itemTitle = view.findViewById(R.id.item_title);
            count = view.findViewById(R.id.textView16);
            itemEnters = view.findViewById(R.id.item_date);
            increase = view.findViewById(R.id.increaseBtn);
            decrease = view.findViewById(R.id.decreaseBtn);
        }
    }

    public int getNum() {
        return num;
    }

    public String getSelectedID() {
        return nameOfSelected;
    }


}
