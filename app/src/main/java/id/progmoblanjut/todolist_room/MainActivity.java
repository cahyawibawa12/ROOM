package id.progmoblanjut.todolist_room;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.progmoblanjut.todolist_room.Adapters.ToDoAdapter;
import id.progmoblanjut.todolist_room.RoomDatabase.ToDo;
import id.progmoblanjut.todolist_room.RoomDatabase.ToDoDao;
import id.progmoblanjut.todolist_room.RoomDatabase.ToDoDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton faButton;
    private ToDo newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToDoDatabase db = Room.databaseBuilder(MainActivity.this, ToDoDatabase.class, "db_todo").allowMainThreadQueries().build();
        ToDoDao toDoDao = db.toDoDao();
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(toDoDao, MainActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        faButton = findViewById(R.id.fab);
        tasksAdapter.retrieveTasks();
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = new EditText(MainActivity.this);
                alertDialog.setTitle("Masukkan Note anda :");
                alertDialog.setView(input);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        newNote = new ToDo();
                        newNote.setTask(input.getText().toString());
                        newNote.setStatus(0);
                        toDoDao.insert(newNote);
                        tasksAdapter.retrieveTasks();
                    }
                });
                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }
}
