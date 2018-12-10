package com.felipe.docs;

import android.content.Context;
import android.support.annotation.NonNull;

import com.felipe.docs.Model.CriaContaFinaceira;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.felipe.docs.Activity.CriarConta.Limpa;

public class Firebase {

    private Context context;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;

    public Firebase(Context context) {
        this.context = context;
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public static void Salvar(String nomeBanco, String key, CriaContaFinaceira cc, final int classe) {
        databaseReference.child(nomeBanco).child(key).setValue(cc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        switch (classe) {
                            case 0:
                                Limpa(true);
                                break;
                            case 1:
                                break;
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        switch (classe) {
                            case 0:
                                Limpa(false);
                                break;
                            case 1:
                                break;
                        }
                    }
                });
    }
}
