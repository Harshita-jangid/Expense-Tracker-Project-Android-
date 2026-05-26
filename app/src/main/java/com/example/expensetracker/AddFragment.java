package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddFragment extends Fragment {
    Button btn;
    EditText title , amount , category , date ;
    DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_fragment, container, false);
        title = view.findViewById(R.id.EtTitle);
        amount= view.findViewById(R.id.EtAmount);
        category = view.findViewById(R.id.EtCategory);
        date = view.findViewById(R.id.EtDate);
        btn = view.findViewById(R.id.save);
        db = new DatabaseHelper(getContext());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        },
                        year, month, day
                );

                datePickerDialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title = title.getText().toString().trim();
                String Amount = amount.getText().toString().trim();
                String Date = date.getText().toString().trim();
                String Category= category.getText().toString().trim();


                if (Title.isEmpty()) {
                    title.setError("Enter expense title");
                    title.requestFocus();
                }
                else if (Amount.isEmpty()) {
                    amount.setError("Enter amount");
                    amount.requestFocus();
                }
                else if (Category.isEmpty()) {
                    category.setError("Enter category");
                    category.requestFocus();
                }
                else if (Date.isEmpty()) {
                    date.setError("Select date");
                    date.requestFocus();
                }

                else {
                    db.insertExpense(
                            title.getText().toString(),
                            amount.getText().toString(),
                            category.getText().toString(),
                            date.getText().toString()
                    );
                    Toast.makeText(getContext(), "Data Saved", Toast.LENGTH_LONG).show();
                    title.setText("");
                    amount.setText("");
                    category.setText("");
                    date.setText("");
                }
            }
        });
        return view;
    }
}