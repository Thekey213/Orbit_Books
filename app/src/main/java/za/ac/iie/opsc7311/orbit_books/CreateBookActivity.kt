package za.ac.iie.opsc7311.orbit_books

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import za.ac.iie.opsc7311.orbit_books.databinding.ActivityCreateBookBinding

class CreateBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBookBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        registerClickEvents()
    }

    private fun registerClickEvents() {
        binding.uploadBtn.setOnClickListener {
            uploadImage()
        }

        binding.bookBtn.setOnClickListener {
            startActivity(Intent(this, BookActivity::class.java))
        }

        binding.selecImageBtn.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.imageCardView.setImageURI(it)
    }


    private fun initVars() {

        storageRef = FirebaseStorage.getInstance().reference.child("Books")
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun uploadImage() {
        binding.progressBar.visibility = View.VISIBLE
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        val title = binding.booktitle.text.toString()
                        val author = binding.author.text.toString()
                        val descrip = binding.description.text.toString()

                        val book = BookData(title,descrip,uri.toString(),author)

                        val map = HashMap<String, Any>()
                        map["book"] = book

                        firebaseFirestore.collection("Books").add(book).addOnCompleteListener { firestoreTask ->


                            if (firestoreTask.isSuccessful){

                                binding.booktitle.text.clear()
                                binding.author.text.clear()
                                binding.description.text.clear()
                                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()

                            }else{
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()

                            }
                            binding.progressBar.visibility = View.GONE
                            binding.imageCardView.setImageResource(R.drawable.frame)

                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    binding.imageCardView.setImageResource(R.drawable.frame)
                }
            }
        }
    }
}