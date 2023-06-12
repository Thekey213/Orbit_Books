package za.ac.iie.opsc7311.orbit_books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class AuthorAdapter(var authorList: List<BookData>, private val onItemClick: (BookData) -> Unit) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {

    class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(book: BookData, onItemClick: (BookData) -> Unit) {
            titleTextView.text = book.title
            descriptionTextView.text = book.description

            // Load image using Picasso or any other image loading library
            Picasso.get().load(book.img).into(imageView)

            itemView.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return AuthorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val currentItem = authorList[position]
        holder.bind(currentItem, onItemClick)
    }

    override fun getItemCount() = authorList.size
}

