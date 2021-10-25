package id.progmoblanjut.todolist_room.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ToDo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "status")
    private int status;
    @ColumnInfo(name="task")
    private String task;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
}
