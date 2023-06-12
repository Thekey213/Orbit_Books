package za.ac.iie.opsc7311.orbit_books


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import za.ac.iie.opsc7311.orbit_books.databinding.ActivityBookDetailsBinding

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val img = intent.getStringExtra("img")


        // Use the retrieved data to populate the views in your activity
        binding.name.text = title
        binding.info.text = description
        // Load the image using Picasso or any other image loading library
        Picasso.get().load(img).into(binding.img)

    }
}
