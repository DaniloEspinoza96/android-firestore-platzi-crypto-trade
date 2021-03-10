package com.platzi.android.firestore.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.R
import com.platzi.android.firestore.model.User
import com.platzi.android.firestore.network.Callback
import com.platzi.android.firestore.network.FirestoreService
import com.platzi.android.firestore.network.USERS_COLLECTION_NAME
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_trader.*
import java.lang.Exception

/**
 * @author Santiago Carrillo
 * github sancarbar
 * 1/29/19.
 */


const val USERNAME_KEY = "username_key"

class LoginActivity : AppCompatActivity() {


    private val TAG = "LoginActivity"

    //with this we get access to the auth module from firebase
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)//here we select the layout
        firestoreService = FirestoreService(FirebaseFirestore.getInstance()) //here we send the firestore instance to the
        //FirestoreService.kt
    }


    fun onStartClicked(view: View) {
        view.isEnabled = false
        auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val username = username.text.toString()
                        val user = User()//this is the user from model
                        user.username = username
                        saveUserAndStartMainActivity(user, view)
                    } else {
                        //with this we show user, that things went wrong
                        showErrorMessage(view)
                        view.isEnabled = true
                    }
                }

    }

    private fun saveUserAndStartMainActivity(user: User, view: View) {
        firestoreService.setDocument(user, USERS_COLLECTION_NAME, user.username, object : Callback<Void> {
            override fun onSuccess(result: Void?) {
                startMainActivity(user.username)
            }

            override fun onFailed(exception: Exception) {
                showErrorMessage(view)
                Log.e(TAG, "error", exception)
            }

        })

    }

    private fun showErrorMessage(view: View) {
        Snackbar.make(view, getString(R.string.error_while_connecting_to_the_server), Snackbar.LENGTH_LONG)
                .setAction("Info", null).show()
    }

    private fun startMainActivity(username: String) {
        val intent = Intent(this@LoginActivity, TraderActivity::class.java)
        intent.putExtra(USERNAME_KEY, username)
        startActivity(intent)
        finish()
    }

}
