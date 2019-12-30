package com.learn.room.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.learn.room.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Query("select * from book")
    List<Book> findAll();

}
