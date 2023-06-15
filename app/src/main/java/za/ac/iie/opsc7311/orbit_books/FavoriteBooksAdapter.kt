package za.ac.iie.opsc7311.orbit_books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import za.ac.iie.opsc7311.orbit_books.databinding.ItemFavoriteBookBinding

class FavoriteBooksAdapter(var favoriteBooksList: List<FavBookData>) :
    RecyclerView.Adapter<FavoriteBooksAdapter.FavoriteBookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBookViewHolder {
        val binding =
            ItemFavoriteBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteBookViewHolder, position: Int) {
        val favoriteBook = favoriteBooksList[position]
        holder.bind(favoriteBook)
    }

    override fun getItemCount(): Int {
        return favoriteBooksList.size
    }

    inner class FavoriteBookViewHolder(private val binding: ItemFavoriteBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteBook: FavBookData) {
            binding.title.text = favoriteBook.title
            Picasso.get().load(favoriteBook.img).into(binding.imageFav)
        }
    }
}
