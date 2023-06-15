package za.ac.iie.opsc7311.orbit_books


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import za.ac.iie.opsc7311.orbit_books.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import za.ac.iie.opsc7311.orbit_books.databinding.LoginUserBinding





class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginUserBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        binding.usrloginBtn.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val password = binding.passwordET.text.toString().trim()


                checkUserExists(email, password)

        }

        binding.signupBtnTap.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        // Perform your validation logic here
        // Return true if the input is valid, otherwise return false
        return true
    }

    private fun checkUserExists(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseFirestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                } else {
                    loginUser(email, password)

                    if(email == "Bob@gmail.com"){

                            val intent = Intent(this, Category::class.java)
                            startActivity(intent)
                    } else{


                        val intent = Intent(this, UserCategoryActivity::class.java)
                        startActivity(intent)


                    }

                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to check user existence: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                // TODO: Proceed to the main activity or desired destination
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Login failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
