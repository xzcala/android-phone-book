package edu.missouri.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneNumberEditText;
    private Button doneButton;

    private static final int DELETE_CONTACT_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameEditText = findViewById(R.id.nameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                long phoneNumber = 0;
                try {
                    phoneNumber = Long.parseLong(phoneNumberEditText.getText().toString());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

                Contact contact = new Contact(name, phoneNumber);
                Intent intent = new Intent();
                intent.putExtra("CONTACT_EXTRA", contact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra("CONTACT_EXTRA");
        if (contact != null) {
            nameEditText.setText(contact.getName());
            phoneNumberEditText.setText(String.valueOf(contact.getPhoneNumber()).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3"));
        }

        if (nameEditText.length() > 0) {
            super.setTitle(nameEditText.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionDelete:
                Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                startActivityForResult(intent, DELETE_CONTACT_REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
