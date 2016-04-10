package unibe.ecard;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import  unibe.ecard.Contacts.ContactColumns;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // http://developer.android.com/tools/building/multidex.html
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
