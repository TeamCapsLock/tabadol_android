package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String text;
    private String type;
    private EditText postBody;
    private RadioGroup radioGroup;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postBody = findViewById(R.id.edit_text_details);
        radioGroup = findViewById(R.id.radio_group_type);
        spinner = findViewById(R.id.add_post_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getType(View view){
        boolean isChecked = ((RadioButton)view).isChecked();
        if(view.getId() == R.id.radio_product) {
            type = getResources().getString(R.string.product).toLowerCase();
            Toast.makeText(AddPost.this, " " + type, Toast.LENGTH_LONG).show();
        }
        else if(view.getId() == R.id.radio_service) {
            type = getResources().getString(R.string.service).toLowerCase();
            Toast.makeText(AddPost.this, " " + type, Toast.LENGTH_LONG).show();
        }
        // TODO: make sure to have one button checked and remove this line
        else {
            type = null;
            Toast.makeText(AddPost.this, " " + type, Toast.LENGTH_LONG).show();
        }
    }


}