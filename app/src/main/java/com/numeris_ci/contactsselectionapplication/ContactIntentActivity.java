package com.numeris_ci.contactsselectionapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactIntentActivity extends AppCompatActivity {
    private final int PHONE = 0;
    private final int WEBSITE = 1;
    private ListView intentListView;
    private ArrayAdapter<String> adapter;
    private List<ContactObject> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_intent);

        intentListView = (ListView) findViewById(R.id.listView1);

        //Initialize the list
        contactList = new ArrayList<ContactObject>();
        contactList.add(new ContactObject("Android one", "111-1111-1111", "www.androidatc.com"));
        contactList.add(new ContactObject("Android two", "222-2222-2222", "www.androidatc.com"));
        contactList.add(new ContactObject("Android three", "333-3333-3333", "www.androidatc.com"));
        contactList.add(new ContactObject("Android four", "444-4444-4444", "www.androidatc.com"));

        List<String> listName = new ArrayList<String>();
        for (int i=0; i<contactList.size();i++) {
            listName.add(contactList.get(i).getName());
        }

        //Initialize the arrayadapter object
        adapter = new ArrayAdapter<String>(ContactIntentActivity.this, android.R.layout.simple_list_item_1, listName);

        //Set the adapter of the listview
        intentListView.setAdapter(adapter);
        intentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ContactIntentActivity.this, ContactPageActivity.class);
                i.putExtra("Object", contactList.get(position));
                startActivityForResult(i,0);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Bundle resultData = data.getExtras();
        String value = resultData.getString("value");
        switch (resultCode) {
            case PHONE:
                //Implicit intent to make a call
                if (getPackageManager().checkPermission(android.Manifest.permission.CALL_PHONE, getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + value)));
                }
                break;
            case WEBSITE:
                //Implicit intent to visit a website
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + value)));
                break;
        }
    }
}
