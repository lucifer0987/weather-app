package com.example.kmm_demo.android.ui.weatherlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kmm_demo.android.R
import com.example.kmm_demo.android.data.Resource
import com.example.kmm_demo.android.data.dto.WeatherItem
import com.example.kmm_demo.android.databinding.ActivityMainBinding
import com.example.kmm_demo.android.ui.weatherlist.adapter.WeatherAdapter
import com.example.kmm_demo.android.utils.Utilities.hideLoader
import com.example.kmm_demo.android.utils.Utilities.hideLoaderFailure
import com.example.kmm_demo.android.utils.Utilities.showLoader
import com.example.kmm_demo.android.utils.hideKeyboard
import com.example.kmm_demo.android.utils.observe
import com.example.kmm_demo.android.utils.showGenericToast
import com.example.kmm_demo.android.utils.toGone
import com.example.kmm_demo.android.utils.toVisible

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        initListeners()
        binding.toolbarLayout.textScreenTitle.text = "Weather"
        viewModel.getWeatherListByCity("bangalore")
    }

    private fun initListeners() {
        binding.noInternetButton.setOnClickListener {
            binding.searchEt.setText("")
            viewModel.getWeatherListByCity("bangalore")
        }

        binding.searchBtn.setOnClickListener {
            if (binding.searchEt.text.toString() == "") {
                binding.root.showGenericToast(this@MainActivity, "Please enter name of the city")
            } else {
                viewModel.getWeatherListByCity(binding.searchEt.text.toString().trim().lowercase())
            }
            binding.root.hideKeyboard()
        }
    }

    private fun observeViewModel() {
        observe(viewModel.weatherData, ::handleWeatherResult)
    }

    private fun handleWeatherResult(status: Resource<MutableList<WeatherItem>>) {
        when (status) {
            is Resource.Loading -> {
                showLoader(binding.mainArea, binding.loaderArea)
                binding.noInternetArea.toGone()
            }

            is Resource.NoInternet -> {
                hideLoaderFailure(binding.mainArea, binding.loaderArea)
                binding.noInternetArea.toVisible()
            }

            is Resource.Success -> {
                hideLoader(binding.mainArea, binding.loaderArea)
                binding.noInternetArea.toGone()

                status.data?.let { weatherList ->
                    if (weatherList.size > 0) {
                        bindWeatherRv(weatherList)
                    } else {
                        if (this::adapter.isInitialized) {
                            adapter = WeatherAdapter(mutableListOf())
                            binding.weatherRv.adapter = adapter
                        }
                        binding.root.showGenericToast(
                            this@MainActivity,
                            "There is no weather data for this location"
                        )
                    }
                }
            }

            else -> {
                hideLoader(binding.mainArea, binding.loaderArea)
                binding.noInternetArea.toGone()
                binding.root.showGenericToast(
                    this@MainActivity,
                    this.resources.getString(R.string.default_error)
                )
            }
        }
    }

    private fun bindWeatherRv(data: MutableList<WeatherItem>?) {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.weatherRv.layoutManager = layoutManager
        data?.let {
            adapter = WeatherAdapter(it)
        }
        binding.weatherRv.adapter = adapter
    }
}
