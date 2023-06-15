package za.ac.iie.opsc7311.orbit_books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast


import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import za.ac.iie.opsc7311.orbit_books.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: FavoriteBooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseFirestore = FirebaseFirestore.getInstance()
        adapter = FavoriteBooksAdapter(emptyList())

        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFavorites.adapter = adapter

        retrieveFavoriteBooks()
    }

    private fun retrieveFavoriteBooks() {
        firebaseFirestore.collection("favorites").get()
            .addOnSuccessListener { documents ->
                val favoriteBooksList = mutableListOf<FavBookData>()
                for (document in documents) {
                    val favoriteBook = document.toObject(FavBookData::class.java)
                    favoriteBooksList.add(favoriteBook)
                }
                adapter.favoriteBooksList = favoriteBooksList
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error retrieving books: ${exception.message}", Toast.LENGTH_SHORT).show()

            }
    }



}
