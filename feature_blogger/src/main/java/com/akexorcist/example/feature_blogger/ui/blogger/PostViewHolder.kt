package com.akexorcist.example.feature_blogger.ui.blogger

import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_blogger.databinding.ViewHolderBloggerPostBinding
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

class PostViewHolder(
    private val binding: ViewHolderBloggerPostBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setTitle(title: String?) {
        title?.let {
            binding.bloggerTextViewTitle.text = it
        } ?: run {
            binding.bloggerTextViewTitle.text = ""
        }
    }

    fun setUpdatedDate(date: String?) {
        date?.let {
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
            binding.bloggerTextViewUpdatedDate.text = formatter.format(OffsetDateTime.parse(it))
        } ?: run {
            binding.bloggerTextViewUpdatedDate.text = ""
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}
