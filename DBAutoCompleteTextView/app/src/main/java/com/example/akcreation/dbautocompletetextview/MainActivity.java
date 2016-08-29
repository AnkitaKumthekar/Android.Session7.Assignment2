package com.example.akcreation.dbautocompletetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomAutoCompleteView autoCompleteView;
    ArrayAdapter<String> adapter;
    DatabaseHandler dbHandler;
    String[] item=new String []{"Search"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            // instantiate database handler
            dbHandler = new DatabaseHandler(MainActivity.this);

            // put sample data to database
            insertSampleData();

            // autocompletetextview is in activity_main.xml
            autoCompleteView = (CustomAutoCompleteView) findViewById(R.id.autocompleteview);

            // add the listener so it will tries to suggest while the user types
            autoCompleteView.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

            // set our adapter
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            autoCompleteView.setAdapter(adapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertSampleData(){

        // CREATE
        dbHandler.create( new MyObject("HP Laptop") );
        dbHandler.create( new MyObject("HP Notebook") );
        dbHandler.create( new MyObject("HP Printer") );
        dbHandler.create( new MyObject("Mac") );
        dbHandler.create( new MyObject("DEll Laptop") );
        dbHandler.create( new MyObject("Lenova Laptop") );
    }

    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = dbHandler.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }
}
