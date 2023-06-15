package za.ac.iie.opsc7311.orbit_books


import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import za.ac.iie.opsc7311.orbit_books.databinding.ActivityBookDetailsBinding
import android.widget.Toast
import java.io.File
import java.io.IOException
import android.view.View
import okhttp3.*
import java.io.FileOutputStream
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.content.Intent


class BookDetailsActivity : AppCompatActivity() {

    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var binding: ActivityBookDetailsBinding
    private var pdfUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseFirestore = FirebaseFirestore.getInstance()
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val img = intent.getStringExtra("img")
        pdfUrl = intent.getStringExtra("pdfUrl")

        // Use the retrieved data to populate the views in your activity
        binding.name.text = title
        binding.info.text = description
        // Load the image using Picasso or any other image loading library
        Picasso.get().load(img).into(binding.img)

        binding.button.setOnClickListener {
            pdfUrl?.let { url ->
                openPdfUrl(url)
            }
        }

        binding.addToFavoritesButton.setOnClickListener {
            addToFavorites(title, img)
        }


    }

    private fun openPdfUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }


    private fun addToFavorites(title: String?, img: String?) {
        val favoriteBook = hashMapOf(
            "title" to title,
            "image" to img
        )

        firebaseFirestore.collection("favorites")
            .add(favoriteBook)
            .addOnSuccessListener {
                Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to add to Favorites: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}







