package com.platzi.android.firestore.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.R
import com.platzi.android.firestore.adapter.CryptosAdapter
import com.platzi.android.firestore.adapter.CryptosAdapterListener
import com.platzi.android.firestore.model.Crypto
import com.platzi.android.firestore.model.User
import com.platzi.android.firestore.network.Callback
import com.platzi.android.firestore.network.FirestoreService
import com.platzi.android.firestore.network.RealtimeDataListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_trader.*
import java.lang.Exception



class TraderActivity : AppCompatActivity(), CryptosAdapterListener {
    //lets configure our recyclerview 1
    //instance of firestore service 3
    lateinit var firestoreService: FirestoreService
    //once we have our service ready, we call the functions of cryptos we created previously 5
    private val cryptosAdapter: CryptosAdapter = CryptosAdapter(this) //here we have to implement the interface
    //(onBuyCry...) 6


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trader)
        //after we set the content view, we initialize the firestore service 4
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
        //set a method to configure the Rv 2
        configureRecyclerView()
        //now we add another fun that will load the crypto data 7
        loadCryptos()

        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.generating_new_cryptos), Snackbar.LENGTH_SHORT)
                .setAction("Info", null).show()
        }

    }

    private fun loadCryptos() {
        firestoreService.getCryptos(object: Callback<List<Crypto>>{
            override fun onSuccess(result: List<Crypto>?) {
                //this will run the specific actions on the UI thread
                this@TraderActivity.runOnUiThread {
                    cryptosAdapter.cryptoList = result!!
                    cryptosAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailed(exception: Exception) {
                Log.d("TraderActivity", "error loading cryptos", exception)
                showGeneralServerErrorMessage()
            }

        })
    }

    private fun configureRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this) //this refers to the activity
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = cryptosAdapter //this will use the cryptos adapter, and will wrap all those items with
        //the data from firebase
    }


    fun showGeneralServerErrorMessage() {
        Snackbar.make(fab, getString(R.string.error_while_connecting_to_the_server), Snackbar.LENGTH_LONG)
            .setAction("Info", null).show()
    }

    override fun onBuyCryptoClicked(cryptos: Crypto) {//this will be called when we click on buy

    }

}