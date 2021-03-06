package com.example.astromedics.views.pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.views.common.TherapistDetails;

public class ReportVisualizationActivity extends AppCompatActivity {
    private ReportVisualizationActivity instance = this;
    public static String MEDICAL_CONSULTATION = "medicalConsultation";
    public static String THERAPIST = "therapist";

    private Therapist therapist;
    private MedicalConsultation medicalConsultation;

    TextView emphasisTextView, therapistTextView, consultationDateTextView, creationDateTextView, contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_visualization);
        obtainObjects();
        inflateViews();
        setViewsValues();
        setListeners();
    }

    private void obtainObjects() {
        medicalConsultation = (MedicalConsultation) getIntent().getSerializableExtra(MEDICAL_CONSULTATION);
        therapist = (Therapist) getIntent().getSerializableExtra(THERAPIST);
    }

    private void inflateViews() {
        emphasisTextView = findViewById(R.id.report_visualization_emphasis);
        therapistTextView = findViewById(R.id.report_visualization_written_by);
        consultationDateTextView = findViewById(R.id.report_visualization_date_of_consultation);
        creationDateTextView = findViewById(R.id.report_visualization_report_creation_date);
        contentTextView = findViewById(R.id.report_visualization_content);
    }

    private void setViewsValues() {
        emphasisTextView.setText(Therapist.Emphasis.toString(medicalConsultation.getEmphasis(),
                                                             getApplicationContext()));
        therapistTextView.setText(therapist.getName());
        consultationDateTextView.setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment()
                                                                                                 .getStartDate()));
        creationDateTextView.setText(new ApplicationDateFormat().toString(medicalConsultation.getReport()
                                                                                             .getCreationDate()));
        contentTextView.setText(medicalConsultation.getReport()
                                                   .getContent());
    }

    private void setListeners() {
        therapistTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                                           TherapistDetails.class);
                intent.putExtra(TherapistDetails.THERAPIST,
                                therapist);
                startActivity(intent);
            }
        });
    }
}
