package za.ac.iie.opsc7311.orbit_books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import android.content.Context
import android.content.Intent

import za.ac.iie.opsc7311.orbit_books.databinding.EachItemBinding

class AuthorAdapter(
    var authorList: List<BookData>,
    private val context: Context
) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val book = authorList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return authorList.size
    }

    inner class AuthorViewHolder(private val binding: EachItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(book: BookData) {
            binding.title.text = book.title
            binding.description.text = book.description
            Picasso.get().load(book.img).into(binding.imageView)
            itemView.tag = book
        }

        override fun onClick(view: View) {
            val book = view.tag as BookData
            val intent = Intent(context, BookDetailsActivity::class.java).apply {
                putExtra("title", book.title)
                putExtra("description", book.description)
                putExtra("img", book.img)
                putExtra("pdfUrl", book.pdfUrl)
            }
            context.startActivity(intent)
        }
    }
}



