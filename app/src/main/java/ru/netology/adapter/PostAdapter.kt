package ru.netology.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.R
import ru.netology.databinding.PostCardBinding
import ru.netology.dbo.Post

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostAdapter(private val onLikeListener: OnLikeListener, private val onShareListener: OnShareListener ): ListAdapter<Post, PostViewHolder>(PostDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding,onLikeListener,onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
 }

class PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            txtFavorite.text = convertCountToString(post.likeCount)
            txtShare.text = convertCountToString(post.shareCount)
            txtTitle.text = post.author
            txtAbout.text = post.content
            txtCurrentDate.text = post.published
            imgbtnFavorite.setImageResource(
                if (post.likedByMe) R.drawable.ic_baseline_favorite_red_24 else R.drawable.ic_baseline_favorite_24
            )

            imgbtnFavorite.setOnClickListener {
                //countFavorite = if (post.likedByMe) countFavorite - 1 else countFavorite + 1
                //txtFavorite.text = convertCountToString(countFavorite)
                onLikeListener(post)
            }

            binding.imgbtnShare.setOnClickListener{
                //countShared += 1
                //txtShare.text = convertCountToString(countShared)
                onShareListener(post)
            }
        }
    }

    private fun convertCountToString(count: Int): String {

        return when {
            (count < 1000) -> count.toString()
            ((count > 999) && (count < 10_000)) -> getThousands(count)
            ((count > 9_999) && (count < 1_000_000)) -> getHundredThousands(count)
            (count > 999_999) -> getMillions(count)
            else -> count.toString()
        }
    }

    private fun getThousands(count: Int): String {
        val res = if (count % 1000 == 0) {
            (count/1000).toString()
        } else {
            val toRound = (count.toDouble()/1000).toString()
            if (toRound.substring(2,3) == "0"){
                (count/1000).toString()
            } else {
                toRound.substring(0, 3)
            }
        }

        return "$res K"
    }


    private fun getHundredThousands(count: Int): String {

        return (count/1000).toString() + " K"
    }

    private fun getMillions(count: Int): String {

        val res = if (count % 1_000_000 == 0) {
            (count/1_000_000).toString()
        } else {
            if (count.toString().substring(count.toString().length-6, count.toString().length -5) == "0"){
                (count/1_000_000).toString()
            } else {
                count.toString().substring(0, count.toString().length-6) + "." + count.toString().substring(count.toString().length-6, count.toString().length-5)
            }
        }

        return "$res M"
    }
}