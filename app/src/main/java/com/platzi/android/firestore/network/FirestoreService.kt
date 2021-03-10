package com.platzi.android.firestore.network

import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.model.Crypto
import com.platzi.android.firestore.model.User
//this class updates info in firestore

const val CRYPTO_COLLECTION_NAME = "cryptos"
const val USERS_COLLECTION_NAME = "users"

class FirestoreService(val firebaseFirestore: FirebaseFirestore) {
    //this function will save a document
    fun setDocument(
        data: Any,
        collectionName: String,
        id: String,
        callback: com.platzi.android.firestore.network.Callback<Void>){
        //method collection asks for a string, that string is the name of the collection
        //.document asks for an id, that id is from the document name
        //.set is to overwrite data in the specified location, the listeners tells you if it was a success or a failure
        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }
    fun updateUser(user: User, callback: com.platzi.android.firestore.network.Callback<User>){
        //will i have a collection for every user i have?
        //that i believe
        firebaseFirestore.collection(USERS_COLLECTION_NAME).document(user.username)
            .update("cryptosList", user.cryptosList)
            .addOnSuccessListener { result ->
                if(callback != null)callback.onSuccess(user)
                }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
            }
    fun updateCrypto(crypto: Crypto){
        //.document receives the crypto currency name to lowercase
        firebaseFirestore.collection(CRYPTO_COLLECTION_NAME).document(crypto.getDocumentId())
            .update("available", crypto.available)

    }
}