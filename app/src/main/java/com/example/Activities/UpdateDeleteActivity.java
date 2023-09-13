package com.example.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btUpdate, btRemove, btBack;
    public Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        btBack.setOnClickListener(this);
        eDate.setOnClickListener(this);
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());
        int p=0;
        for(int i=0; i<sp.getCount(); i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())){
                p=i;
                break;
            }
        }
    }
    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.eTitle);
        ePrice = findViewById(R.id.ePrice);
        eDate = findViewById(R.id.eDate);
        btUpdate = findViewById(R.id.btUpdate);
        btRemove = findViewById(R.id.btRemove);
        btBack = findViewById(R.id.btBack);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View v) {
        SQLiteHelper db = new SQLiteHelper(this);
        if(v==eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        if(v==btBack){
            finish();
        }
        if(v==btUpdate){
            String t = eTitle.getText().toString().trim();
            String p = ePrice.getText().toString().trim();
            String s = sp.getSelectedItem().toString().trim();
            String d = eDate.getText().toString().trim();
            if(!t.isEmpty() && p.matches("\\d+")){
                int id = item.getId();
                Item i = new Item(id,t,s,p,d);
                db = new SQLiteHelper(this);
                db.update(i);
                finish();
            }
        }
        if(v==btRemove){
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Cảnh báo!!!");
            builder.setMessage("Xóa là mất cmm luôn, OK?");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                    db.delete(id);
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}