package unibe.ecard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText editTextUserFullName = null;
    EditText editTextUserDirection = null;
    EditText editTextUserPhone = null;
    EditText editTextUserEmail = null;
    EditText editTextUserHability1 = null;
    EditText editTextUserHability2 = null;

    static final String API_KEY = "c191657f9d64c0ab";
    static final String API_URL = "https://api.fullcontact.com/v2/person.json?";


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

        // Excecute API call to retrieve user info
        new RetrieveFeedTask().execute();

    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

//        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//            responseView.setText("");
//        }

        protected String doInBackground(Void... urls) {
            ContactsCrud cc = new ContactsCrud(getApplicationContext());
//            String email = editTextUserEmail.getText().toString();
            String email = cc.getContactField("1", Contacts.ContactColumns.COLUMN_NAME_EMAIL);

            // Do some validation here

            try {
                URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
//            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
//            responseView.setText(response);


            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                // Fetch photo
                JSONArray p = object.getJSONArray("photos");
                JSONObject photos = (JSONObject)p.get(0);

                JSONArray s = object.getJSONArray("socialProfiles");
                JSONObject socialProfile =  (JSONObject)s.get(0);

                String profilePictureUrl = photos.getString("url");
                String socialNetworkName = socialProfile.getString("typeName");
                final String socialNetworkUrl = socialProfile.getString("url");

                Button button = ((Button)findViewById(R.id.buttonSocialNetworkLink));
                button.setText(socialNetworkName);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Uri uri = Uri.parse(socialNetworkUrl); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
                // Set the profile picture if it exists®
                if(profilePictureUrl.length() > 0) {
                    ImageView profileImage = (ImageView) findViewById(R.id.profilePicture);
                    new DownloadImageTask(profileImage).execute(profilePictureUrl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Bitmap mutableBitmap = result.copy(Bitmap.Config.ARGB_8888, true);
            bmImage.setImageBitmap(mutableBitmap);
        }
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
         @Examples_on_how_to_use_database_methods ContactsCrud cc = new ContactsCrud(view.getContext());
         cc.createContact("Mitra","809 124 5422", "Jose Contreras", "CSS", "HTML");
         cc.updateContactField("1", ContactColumns.COLUMN_NAME_HABILITY_2, "iOS Design");
         cc.getAllContacts();
         */

        Intent intent = new Intent(this, ShareViaNfcActivity.class);
        startActivity(intent);

    }

}
