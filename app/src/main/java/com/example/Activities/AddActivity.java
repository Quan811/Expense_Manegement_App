package com.example.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dal.SQLiteHelper;
import com.example.demo_sqlite.R;
import com.example.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btAdd, btCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btAdd.setOnClickListener(this);
        btCancle.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.eTitle);
        ePrice = findViewById(R.id.ePrice);
        eDate = findViewById(R.id.eDate);
        btAdd = findViewById(R.id.btAdd);
        btCancle = findViewById(R.id.btCancle);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
    }
    @Override
    public void onClick(View v) {
        if(v==eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date = "";
                    if(month>8){
                        date = day+"/"+(m+1)+"/"+y;
                    }
                    else{
                        date = day+"/"+"0"+(m+1)+"/"+y;
                    }
                }
            },year,month,day);
            dialog.show();
        }
        if(v==btCancle){
            finish();
        }
        if(v==btAdd){
            String t = eTitle.getText().toString().trim();
            String p = ePrice.getText().toString().trim();
            String s = sp.getSelectedItem().toString().trim();
            String d = eDate.getText().toString().trim();
            if(!t.isEmpty() && p.matches("\\d+")){
                Item i = new Item(t,s,p,d);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(i);
                finish();
            }
        }
    }
}