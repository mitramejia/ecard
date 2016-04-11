package unibe.ecard;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textUserFullName;
        TextView textUserPhone;
        TextView textUserDirection;
        TextView textUserEmail;
        TextView textUserHability1;
        TextView textUserHability2;

        String currentUserFullName;
        String currentUserPhone;
        String currentUserDirection;
        String currentUserEmail;
        String currentUserHability1;
        String currentUserHability2;

        // First we load our User's data from the database
        ContactsCrud cc = new ContactsCrud(getApplicationContext());
        cc.createContact("Mitra", "809 124 5422", "mitra.mejia@gmail.com", "Jose Contreras", "CSS", "HTML");

        // Start at Arraty Index at 1 because we dont want to display the user id on the profile, right :P
        ArrayList<String> currentContact = cc.getContactById("1");
        currentUserFullName = currentContact.get(1);
        currentUserPhone = currentContact.get(2);
        currentUserEmail = currentContact.get(3);
        currentUserDirection = currentContact.get(4);
        currentUserHability1 = currentContact.get(5);
        currentUserHability2 = currentContact.get(6);

        // Paint the layout before accessing any UI component
        setContentView(R.layout.activity_main);

        // Get all TextViews from the layout
        textUserFullName = (TextView) findViewById(R.id.textUserFullName);
        textUserDirection = (TextView) findViewById(R.id.textUserDirection);
        textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        textUserPhone = (TextView) findViewById(R.id.textUserPhone);
        textUserHability1 = (TextView) findViewById(R.id.textUserHability1);
        textUserHability2 = (TextView) findViewById(R.id.textUserHability2);

        // Fill TextViews with data from the database
        textUserFullName.setText(currentUserFullName);
        textUserPhone.setText(currentUserPhone);
        textUserEmail.setText(currentUserEmail);
        textUserDirection.setText(currentUserDirection);
        textUserHability1.setText(currentUserHability1);
        textUserHability2.setText(currentUserHability2);

    }

    // http://developer.android.com/tools/building/multidex.html
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void editUserProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void shareViaNfc(View view) {

      /**
        @Examples_on_how_to_use_database_methods
        ContactsCrud cc = new ContactsCrud(view.getContext());
        cc.createContact("Mitra","809 124 5422", "Jose Contreras", "CSS", "HTML");
        cc.updateContactField("1", ContactColumns.COLUMN_NAME_HABILITY_2, "iOS Design");
        cc.getAllContacts();
      */

        Intent intent = new Intent(this, ShareViaNfcActivity.class);
        startActivity(intent);

    }
}
