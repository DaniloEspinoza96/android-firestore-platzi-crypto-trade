package com.platzi.android.firestore.adapter

import com.platzi.android.firestore.model.Crypto

interface CryptosAdapterListener{
    //this interface is used to buy crypto coins, one by one
 fun onBuyCryptoClicked(cryptos: Crypto)
}