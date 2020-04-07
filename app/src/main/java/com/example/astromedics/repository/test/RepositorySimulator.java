package com.example.astromedics.repository.test;

import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Appointment;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.EvaluationQuestion;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.model.Person;
import com.example.astromedics.model.Therapist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepositorySimulator {
    private static RepositorySimulator instance;
    private List<Person> persons;

    public static RepositorySimulator getInstance() {
        if (instance == null) {
            instance = new RepositorySimulator();
            instance.init();
        }

        return instance;
    }

    public List<Person> getPersons() {
        return persons;
    }

    private void init() {
        persons = new ArrayList<>();
        Therapist therapist1 = getTestTherapist1();
        Therapist therapist2 = getTestTherapist2();
        Pacient pacient1 = getPacient1();
        Pacient pacient2 = getPacient2();

        MedicalConsultation medicalConsultation = new MedicalConsultation(1,
                                                                          null,
                                                                          null,
                                                                          new Localization(4.629,
                                                                                           -74.0642,
                                                                                           "Pontificia Universidad Javeriana"),
                                                                          0.0,
                                                                          "Fonoaudiologia");
        therapist1.getAppointments()
                  .get(1)
                  .setMedicalConsultation(medicalConsultation);
        medicalConsultation.setAppointment(therapist1.getAppointments()
                                                     .get(0));
        pacient1.addMedicalHistory(medicalConsultation);

        persons.add(therapist1);
        persons.add(therapist2);
        persons.add(pacient1);
        persons.add(pacient2);
    }

    private Therapist getTestTherapist1() {
        List<String> emphasis = new ArrayList<>();
        emphasis.add("Fonoaudiologia");
        emphasis.add("Terapia Ocupacional");
        List<EducationalFormation> educationalFormation = new ArrayList<>();
        educationalFormation.add(new EducationalFormation(1,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        educationalFormation.add(new EducationalFormation(2,
                                                          "Fisioterapeuta",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(1,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                05,
                                                                                10,
                                                                                14,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                05,
                                                                                10,
                                                                                16,
                                                                                0),
                                         null));
        appointments.add(new Appointment(2,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                16,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                18,
                                                                                0),
                                         null));
        Therapist therapist = new Therapist(1014276873,
                                            "Carlos Jaime Cobaleda Cruz",
                                            "https://image.shutterstock.com/z/stock-photo-male-doctor-standing-with-folder-isolated-on-white-background-1535685956.jpg",
                                            4101365,
                                            3003619443L,
                                            "Carrera 71D # 55-54",
                                            "terapeuta",
                                            "terapeuta",
                                            new Date(),
                                            emphasis,
                                            educationalFormation,
                                            appointments);

        return therapist;
    }

    private Therapist getTestTherapist2() {
        List<String> emphasis = new ArrayList<>();
        emphasis.add("Fonoaudiologia");
        emphasis.add("Terapia Ocupacional");
        List<EducationalFormation> educationalFormation = new ArrayList<>();
        educationalFormation.add(new EducationalFormation(3,
                                                          "Fonoaudiologo",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        educationalFormation.add(new EducationalFormation(4,
                                                          "Fisioterapeuta",
                                                          "Pontificia Universidad Javeriana",
                                                          new ApplicationDateFormat().createDate(2014,
                                                                                                 1,
                                                                                                 1),
                                                          new ApplicationDateFormat().createDate(2018,
                                                                                                 12,
                                                                                                 1),
                                                          true));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(3,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                14,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                16,
                                                                                0),
                                         null));
        appointments.add(new Appointment(4,
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                16,
                                                                                0),
                                         new ApplicationDateFormat().createDate(2020,
                                                                                04,
                                                                                10,
                                                                                18,
                                                                                0),
                                         null));
        Therapist therapist = new Therapist(123456789,
                                            "Paula Alejandra Jaime Londoño",
                                            "https://image.shutterstock.com/z/stock-photo-confident-female-doctor-sitting-at-office-desk-and-smiling-at-camera-health-care-and-prevention-292534811.jpg",
                                            4101365,
                                            3003619443L,
                                            "Carrera 71D # 55-54",
                                            "pjaime@gmail.com",
                                            "test1",
                                            new Date(),
                                            emphasis,
                                            educationalFormation,
                                            appointments);

        return therapist;
    }

    private Pacient getPacient1() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(1,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(2,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(3,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(1,
                                                        "AB+",
                                                        65,
                                                        1.65,
                                                        evaluationQuestions);
        List<MedicalConsultation> medicalHistory = new ArrayList<>();
        Pacient pacient = new Pacient(928374059,
                                      "Juan Camilo Cardenas Cruz",
                                      "https://image.shutterstock.com/z/stock-photo-friendly-looking-positive-afro-american-stylish-man-with-funky-hair-and-bristle-smiling-broadly-640011877.jpg",
                                      3457698,
                                      3003746587L,
                                      "Carrera 1 # 2-3",
                                      "paciente",
                                      "paciente",
                                      new Date(),
                                      medicalRecord,
                                      medicalHistory);
        return pacient;
    }

    private Pacient getPacient2() {
        List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
        evaluationQuestions.add(new EvaluationQuestion(3,
                                                       "¿Pregunta 1?",
                                                       "Respuesta 1"));
        evaluationQuestions.add(new EvaluationQuestion(4,
                                                       "¿Pregunta 2?",
                                                       "Respuesta 2"));
        evaluationQuestions.add(new EvaluationQuestion(5,
                                                       "¿Pregunta 3?",
                                                       "Respuesta 4"));
        MedicalRecord medicalRecord = new MedicalRecord(2,
                                                        "AB+",
                                                        65,
                                                        1.65,
                                                        evaluationQuestions);
        List<MedicalConsultation> medicalHistory = new ArrayList<>();
        Pacient pacient = new Pacient(928374059,
                                      "Sara Esperanza Rodriguez Bravo",
                                      "https://image.shutterstock.com/image-photo/portrait-young-beautiful-cute-cheerful-600w-666258808.jpg",
                                      4658293,
                                      3004529384L,
                                      "Carrera 2 # 4-5",
                                      "srodriguez@gmail.com",
                                      "test1",
                                      new Date(),
                                      medicalRecord,
                                      medicalHistory);
        return pacient;
    }
}
