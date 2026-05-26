package com.example.expensetracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder>{
    Context context;
    ArrayList<Expense> list;
    DatabaseHelper db;

    TextView Total;
    public ExpenseAdapter(Context context , ArrayList<Expense> list){
        this.list = list;
        this.context = context;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = list.get(position);
        int pos = position;
        holder.title.setText(expense.getTitle());
        holder.amount.setText(expense.getAmount());
        holder.date.setText(expense.getDate());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int currentPos = holder.getBindingAdapterPosition();
                if (currentPos == RecyclerView.NO_POSITION) return false;
                Expense removedItem = list.get(currentPos);
                int result = db.deleteExpense(removedItem.getId());
                if(result > 0){
                    list.remove(currentPos);
                    notifyItemRemoved(currentPos);
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Not deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title , amount, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            amount = itemView.findViewById(R.id.tvAmount);
            date = itemView.findViewById(R.id.tvDate);
        }
    }
}
