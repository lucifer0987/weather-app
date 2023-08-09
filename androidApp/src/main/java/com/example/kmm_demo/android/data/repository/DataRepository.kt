package com.example.kmm_demo.android.data.repository

import android.util.Log
import com.example.kmm_demo.android.APIConstants.CURR_TEMP_KEY
import com.example.kmm_demo.android.APIConstants.DATA
import com.example.kmm_demo.android.APIConstants.DB_ROOT
import com.example.kmm_demo.android.APIConstants.DESCRIPTION_KEY
import com.example.kmm_demo.android.APIConstants.MAX_TEMP_KEY
import com.example.kmm_demo.android.APIConstants.MIN_TEMP_KEY
import com.example.kmm_demo.android.data.Resource
import com.example.kmm_demo.android.data.dto.WeatherItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.InetAddress

class DataRepository {

    suspend fun getWeatherListByCity(city: String): Resource<MutableList<WeatherItem>> {
        if (!isInternetAvailable()) {
            return Resource.NoInternet()
        }

        val weatherItemList = mutableListOf<WeatherItem>()
        val db = Firebase.firestore

        val docRef = db.collection(DB_ROOT).document(city)
        val documentSnapshot = docRef.get().await() ?: return Resource.Success(weatherItemList)

        val rootData = (documentSnapshot.data)?.get(DATA)

        rootData?.let {
            try {
                val dataList = rootData as ArrayList<HashMap<String, Any>>

                for (currData in dataList) {
                    val currWeatherItem = WeatherItem(
                        currData[CURR_TEMP_KEY] as Long,
                        currData[MIN_TEMP_KEY] as Long,
                        currData[DESCRIPTION_KEY] as String,
                        currData[MAX_TEMP_KEY] as Long
                    )
                    weatherItemList.add(currWeatherItem)
                }

                return Resource.Success(weatherItemList)

            } catch (e: Exception) {
                Log.e("parsing_data", e.message.toString())
                return Resource.Error()
            }
        } ?: return Resource.Success(weatherItemList)
    }

    private suspend fun isInternetAvailable(): Boolean {
        return try {
            val ipAddress = withContext(Dispatchers.IO) {
                InetAddress.getByName("www.google.com")
            }
            !ipAddress.equals("")
        } catch (e: Exception) {
            false
        }
    }

}