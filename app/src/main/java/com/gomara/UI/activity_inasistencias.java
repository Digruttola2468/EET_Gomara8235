package com.gomara.UI;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gomara.R;
import com.gomara.adapter.Alumno;
import com.gomara.adapter.RecyclerAdapter;
import com.gomara.adapter.getInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class activity_inasistencias extends Activity {

    private RecyclerView recyclerView;
    ArrayList<Alumno> alumnos = new ArrayList<>();
    getInfo info = new getInfo();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inasistencias);

        recyclerView = findViewById(R.id.recycler_datos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        alumnos.add(new Alumno("Ivan Di Gruttola",4.4F));
        alumnos.add(new Alumno("Santiago Torres",5F));
        alumnos.add(new Alumno("Cader Lara",10F));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cursos/6a/Alumnos")
                .get()
                .addOnCompleteListener(completeListener);


    }

    OnCompleteListener<QuerySnapshot> completeListener = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Log.d("", document.getId() + " => " + document.getData());
                        Alumno alumno = new Alumno(document.get("Nombre").toString(),Float.parseFloat(document.get("Inasistencias").toString()));
                        info.getAlumnos(alumno);
                    }
                }else Log.w("","Error getting documents." , task.getException());

                RecyclerAdapter adapter = new RecyclerAdapter(info.getAllAlumnos());
                recyclerView.setAdapter(adapter);
            }
    };

}
