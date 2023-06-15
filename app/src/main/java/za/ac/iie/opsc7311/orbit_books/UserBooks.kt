package za.ac.iie.opsc7311.orbit_books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import za.ac.iie.opsc7311.orbit_books.databinding.ActivityUserBooksBinding

class UserBooks : AppCompatActivity() {

    private lateinit var binding: ActivityUserBooksBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityUserBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initVars()
        retrieveBooks()
    }

    private fun initVars() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        adapter = BookAdapter(emptyList(), this)
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
                adapter.bookList = authorList
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error retrieving books: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

