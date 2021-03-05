package com.cyberdesignz.studyup;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class reademailsfromcontacts extends Activity {
    /**
     * Called when the activity is first created.
     */
    ListView lv_contacts;
    ArrayList<String> names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.emailfromcontacts);
        lv_contacts = (ListView) findViewById(R.id.lv_contacts);

        names = new ArrayList<String>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
                        + " = ?", new String[]{id}, null);
                while (cur1.moveToNext()) {
                    // to get the contact names
                    String name = cur1
                            .getString(cur1
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    String email = cur1
                            .getString(cur1
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                    if (email != null) {
                        names.add(email);
                    }
                }
            }

        }
        ArrayAdapter<String> adapter

                = new ArrayAdapter<String>(reademailsfromcontacts.this,

                android.R.layout.simple_list_item_1,

                names);

        lv_contacts.setAdapter(adapter);

    }

    public void OnaddClick(View v) {

        InviteFriends.friendslist.addAll(names);
        Intent i = new Intent(reademailsfromcontacts.this, InviteFriends.class);
        startActivity(i);
        // finish();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        finish();
        super.onStop();
    }

    public void Onbackclick(View v) {

        finish();

    }
}