package za.ac.iie.opsc7311.orbit_books

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import za.ac.iie.opsc7311.orbit_books.databinding.ActivityImagesBinding

class ImagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagesBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: AuthorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bookBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        initVars()
        retrieveBooks()
    }

    private fun initVars() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        adapter = AuthorAdapter(emptyList()) { book ->
            navigateToBookDetails(book)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun retrieveBooks() {
        firebaseFirestore.collection("Books").get()
            .addOnSuccessListener { documents ->
                val authorList = mutableListOf<BookData>()
                for (document in documents) {
                    val book = document.toObject(BookData::class.java)
                    authorList.add(book)
                }
                adapter.authorList = authorList
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error retrieving books: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToBookDetails(book: BookData) {
        val intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra("title", book.title)
        intent.putExtra("description", book.description)
        intent.putExtra("img", book.img)
        intent.putExtra("author", book.author)
        startActivity(intent)
    }
}


