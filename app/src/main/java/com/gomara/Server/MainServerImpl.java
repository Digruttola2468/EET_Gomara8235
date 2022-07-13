package com.gomara.Server;

import androidx.annotation.NonNull;

import com.gomara.Presenter.MainPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainServerImpl implements MainServer{

    private FirebaseFirestore cloud = FirebaseFirestore.getInstance();

    private MainPresenter presenter;
    public MainServerImpl(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getUser(String uid) {
        cloud.collection("User").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String nombre = task.getResult().getString("nombre");
                    String email = task.getResult().getString("email");
                    String apellido = task.getResult().getString("apellido");
                    String anio = task.getResult().getString("anio");
                    String curso = task.getResult().getString("curso");
                    boolean isAlumnado = false;

                    if(task.getResult().contains("alumnado"))
                        isAlumnado = task.getResult().getBoolean("alumnado");

                    String mensaje = "";
                    if(isAlumnado) {
                        mensaje = "Nombre: " + nombre + "\n" +
                                "Apellido: " + apellido + "\n" +
                                "Email: " + email + "\n" +
                                "Alumnado: " + isAlumnado + "\n" +
                                "Año: " + anio + " Curso: " + curso.toUpperCase();
                    }
                    else{
                        mensaje = "Nombre: " + nombre + "\n" +
                                "Apellido: " + apellido + "\n" +
                                "Email: " + email + "\n" +
                                "Año: " + anio + " Curso: " + curso.toUpperCase();
                    }

                    presenter.showUser(mensaje);

                }
            }
        });

    }
    /*
    @Override
    public void getUserisAlumnado(String uid) {
        cloud.collection("User").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    boolean isAlumnado = false;
                    if(task.getResult().contains("alumnado"))
                        isAlumnado = task.getResult().getBoolean("alumnado");

                    //presenter.isAlumnado(isAlumnado);
                }
            }
        });
    }*/

}
