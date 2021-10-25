package id.progmoblanjut.todolist_room.RoomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ToDo.class},version =1)
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract ToDoDao toDoDao();
}
