package ru.netology.repository

import androidx.lifecycle.LiveData
import ru.netology.dbo.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}