package com.example.astromedics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.DownloadImageTask;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.util.List;

public class MedicalConsultationAdapter extends ArrayAdapter<MedicalConsultation> {
    private int resourceLayout;

    public MedicalConsultationAdapter(Context context, int resource, List<MedicalConsultation> items) {
        super(context,
              resource,
              items);
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(this.resourceLayout,
                                          null);
        }
        MedicalConsultation medicalConsultation = getItem(position);
        Therapist therapist = Repository.getInstance()
                                        .getTherapistRepository()
                                        .getTherapist(medicalConsultation);

        if (medicalConsultation != null && therapist != null && medicalConsultation.getAppointment() != null) {
            ((TextView) view.findViewById(R.id.medical_consultation_name))
                    .setText(therapist.getName());

            ((TextView) view.findViewById(R.id.medical_consultation_emphasis))
                    .setText(medicalConsultation.getEmphasis());

            ((TextView) view.findViewById(R.id.medical_consultation_date))
                    .setText(new ApplicationDateFormat().toString(medicalConsultation.getAppointment().getStartDate()));

            new DownloadImageTask((ImageView) view.findViewById(R.id.medical_consultation_photo))
                    .execute(therapist.getPhotoURL());
        }
        return view;
    }
}
