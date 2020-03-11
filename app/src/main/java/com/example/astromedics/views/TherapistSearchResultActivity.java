package com.example.astromedics.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.TherapistSearchResultAdapter;
import com.example.astromedics.model.TherapistSearchResult;

import java.util.ArrayList;

public class TherapistSearchResultActivity extends AppCompatActivity {

    private ListView listView;
    private TherapistSearchResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_search_result);

        listView = (ListView) findViewById(R.id.therapist_search_result);

        ArrayList<TherapistSearchResult> therapists = new ArrayList<>();
        therapists.add(new TherapistSearchResult("Nombre1 Apellido1", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre2 Apellido2", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre3 Apellido3", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre4 Apellido4", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre5 Apellido5", 150, 4.5));

        mAdapter = new TherapistSearchResultAdapter(this, therapists);
        listView.setAdapter(mAdapter);
    }
}
