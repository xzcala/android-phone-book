package edu.missouri.phonebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private RecyclerViewAdapter recyclerViewAdapter;

    private static final int ADD_CONTACT_REQUEST_CODE = 1;
    private static final int EDIT_CONTACT_REQUEST_CODE = 2;
    private static final int DELETE_CONTACT_REQUEST_CODE = 3;

    private int editingPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.setTitle("A12 - Phone Book");

        recyclerView = findViewById(R.id.phoneBookRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        contactList = new ArrayList<>();

        Contact contact1 = new Contact("Hie", 1234567890);
        contactList.add(contact1);

        recyclerViewAdapter = new RecyclerViewAdapter(contactList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                Contact contact = contactList.get(position);
                editingPosition = position;
                intent.putExtra("CONTACT_EXTRA", contact);
                startActivityForResult(intent, EDIT_CONTACT_REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdd:
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CONTACT_REQUEST_CODE && resultCode == RESULT_OK) {
            Contact contact = (Contact) data.getSerializableExtra("CONTACT_EXTRA");
            recyclerViewAdapter.addContact(contact);
        } else if (requestCode == EDIT_CONTACT_REQUEST_CODE && resultCode == RESULT_OK) {
            Contact newContact = (Contact) data.getSerializableExtra("CONTACT_EXTRA");
            recyclerViewAdapter.updateContact(editingPosition, newContact);
        } else if (requestCode == DELETE_CONTACT_REQUEST_CODE && resultCode == RESULT_OK) {
            recyclerViewAdapter.deleteContact(editingPosition);
        }
    }
}
