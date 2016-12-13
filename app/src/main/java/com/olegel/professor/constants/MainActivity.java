package com.olegel.professor.constants;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.ListView;
import android.widget.TextView;

import com.olegel.professor.constants.adapters.FriendListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView text;
    private AutoCompleteTextView textView;
    private FriendListAdapter friendListAdapter;
    private ArrayList<ElectrostaticPermeability> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        textView = (AutoCompleteTextView) findViewById(R.id.auto);

        List<ElectrostaticPermeability> list = new ParserXml().parseTable();
        friendList = (ArrayList<ElectrostaticPermeability>) list;
        for (ElectrostaticPermeability s : list) {
            Log.d(TAG, "onCreate: " + s.getSubstance() + " " + s.getValue());
            CharSequence a = Html.fromHtml(s.getValue());
            //  text.setText(a);
        }
        CharSequence a = Html.fromHtml(list.get(2).getValue());
        text.setText(a);
        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        //new AutoComplete(this, list)
        textView.setThreshold(0);
        textView.setAdapter(adapter);
        initFriendList();
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                textView.showDropDown();
                textView.requestFocus();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        inflater.inflate(R.menu.menu,menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView =
                (SearchView)  MenuItemCompat.getActionView(searchItem);
        Log.d(TAG, "onCreateOptionsMenu: "+MenuItemCompat.getActionView(searchItem));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: "+query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendListAdapter.getFilter().filter(newText);
                Log.d(TAG, "onQueryTextChange: "+newText);
                return true;
            }
        });

        return true;

    }
//http://stackoverflow.com/questions/23422072/searchview-in-listview-having-a-custom-adapter
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.searcher:
                Log.d(TAG, "onOptionsItemSelected: "+item.getItemId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initFriendList() {

        friendListAdapter = new FriendListAdapter(this, friendList);

        // add header and footer for list

    }
}
