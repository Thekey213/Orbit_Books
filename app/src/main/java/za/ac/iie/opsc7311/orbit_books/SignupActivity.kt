package za.ac.iie.opsc7311.orbit_books
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import za.ac.iie.opsc7311.orbit_books.databinding.CreateAccountBinding



class SignupActivity : AppCompatActivity() {
    private lateinit var binding: CreateAccountBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.signupBtn2.setOnClickListener {
            registerUser()
        }

        binding.loginpageBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser() {
        val name = binding.nameET.text.toString().trim()
        val email = binding.emailET.text.toString().trim()
        val password = binding.passwordET.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        binding.signupBtn2.isEnabled = false


        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid

                    val user = User(name, email, password)
                    val usersRef: CollectionReference = db.collection("users")
                    usersRef.document(userId ?: "")
                        .set(user)
                        .addOnCompleteListener { userTask ->
                            binding.signupBtn2.isEnabled = true

                            if (userTask.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Registration successful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to save user data",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    binding.signupBtn2.isEnabled = true

                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


