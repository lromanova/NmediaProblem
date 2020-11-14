
package ru.netology.dbo

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val shareCount: Int = 0,
    val likeCount: Int = 0

)