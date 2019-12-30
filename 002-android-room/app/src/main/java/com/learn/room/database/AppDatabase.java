package com.learn.room.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.learn.room.dao.BookDao;
import com.learn.room.entity.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract BookDao bookDao();
}
