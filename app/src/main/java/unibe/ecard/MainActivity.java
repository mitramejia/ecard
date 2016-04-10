package unibe.ecard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void shareViaNfc(View view) {

        ContactsCrud contactsCrud = new ContactsCrud(getApplicationContext());
        contactsCrud.insert("Manuel", "809 752 4142", "Invi #421", "HTML & CSS", "iOS Development");
//        Intent intent = new Intent(this, ShareViaNfcActivity.class);
//        startActivity(intent);
    }
}
