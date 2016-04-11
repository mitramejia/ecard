package unibe.ecard;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText editTextUserFullName = null;
    EditText editTextUserDirection = null;
    EditText editTextUserPhone = null;
    EditText editTextUserEmail = null;
    EditText editTextUserHability1 = null;
    EditText editTextUserHability2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // First we load our User's data from the database
        ContactsCrud contactsCrud = new ContactsCrud(getApplicationContext());

        // Start at Arraty Index at 1 because we dont want to display the user id on the profile, right :P
        ArrayList<String> currentContactFields = contactsCrud.getContactById("1");
        String currentUserFullName = currentContactFields.get(1);
        String currentUserPhone = currentContactFields.get(2);
        String currentUserEmail = currentContactFields.get(3);
        String currentUserDirection = currentContactFields.get(4);
        String currentUserHability1 = currentContactFields.get(5);
        String currentUserHability2 = currentContactFields.get(6);

        // Paint the layout before acontactsCrudessing any UI component
        setContentView(R.layout.activity_main);

        // Update all EditText's
        editTextUserFullName = ((EditText) findViewById(R.id.editTextUserFullName));
        editTextUserDirection = ((EditText) findViewById(R.id.editTextUserDirection));
        editTextUserEmail = ((EditText) findViewById(R.id.editTextUserEmail));
        editTextUserPhone = ((EditText) findViewById(R.id.editTextUserPhone));
        editTextUserHability1 = ((EditText) findViewById(R.id.editTextUserHability1));
        editTextUserHability2 = ((EditText) findViewById(R.id.editTextUserHability2));

        editTextUserFullName.setText(currentUserFullName);
        editTextUserDirection.setText(currentUserDirection);
        editTextUserEmail.setText(currentUserEmail);
        editTextUserPhone.setText(currentUserPhone);
        editTextUserHability1.setText(currentUserHability1);
        editTextUserHability2.setText(currentUserHability2);

    }


    public void updateUserProfile(View view) {

        // First we load our User's data from the database
        ContactsCrud contactsCrud = new ContactsCrud(view.getContext());

        boolean contactUpdated = contactsCrud.updateContact(
                "1",
                this.getEditTextUserFullName().getText().toString(),
                this.getEditTextUserPhone().getText().toString(),
                this.getEditTextUserDirection().getText().toString(),
                this.getEditTextUserEmail().getText().toString(),
                this.getEditTextUserHability1().getText().toString(),
                this.getEditTextUserHability2().getText().toString()
        );

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if (contactUpdated)
            Toast.makeText(view.getContext(),
                    R.string.text_update_successful, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(view.getContext(),
                    R.string.text_update_unsuccessful, Toast.LENGTH_LONG).show();
    }


    public EditText getEditTextUserFullName() {
        return editTextUserFullName;
    }

    public EditText getEditTextUserDirection() {
        return editTextUserDirection;
    }

    public EditText getEditTextUserEmail() {
        return editTextUserEmail;
    }

    public EditText getEditTextUserHability1() {
        return editTextUserHability1;
    }

    public EditText getEditTextUserHability2() {
        return editTextUserHability2;
    }

    public EditText getEditTextUserPhone() {
        return editTextUserPhone;
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
