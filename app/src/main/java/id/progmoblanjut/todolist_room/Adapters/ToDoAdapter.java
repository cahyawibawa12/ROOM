package id.progmoblanjut.todolist_room.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Collections;
import java.util.List;

import id.progmoblanjut.todolist_room.MainActivity;
import id.progmoblanjut.todolist_room.R;
import id.progmoblanjut.todolist_room.RoomDatabase.ToDo;
import id.progmoblanjut.todolist_room.RoomDatabase.ToDoDao;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDo> todoList;
    private ToDoDao toDoDao;
    private MainActivity activity;
    private Context context;
    public ToDoAdapter(ToDoDao toDoDao, MainActivity activity) {
        this.toDoDao = toDoDao;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ToDo item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setStatus(1);
                } else {
                    item.setStatus(0);
                }
                toDoDao.update(item);
                retrieveTasks();
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                EditText input = new EditText(getContext());
                input.setText(item.getTask());
                alertDialog.setTitle("Ubah to-do:");
                alertDialog.setView(input);
                // Set it.
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        item.setTask(input.getText().toString());
                        toDoDao.update(item);
                        retrieveTasks();
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
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoDao.delete(item);
                retrieveTasks();
            }
        });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void retrieveTasks(){
        this.todoList= toDoDao.getAll();
        Collections.reverse(this.todoList);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        Button editBtn,deleteBtn;
        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            editBtn=view.findViewById(R.id.button_edit);
            deleteBtn=view.findViewById(R.id.button_delete);
        }
    }
}
