package com.example.formvalidation

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.formvalidation.uii.CountryItem
import com.example.formvalidation.viewmodel.CountryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabScreen(navController: NavController) {
    val viewModel: CountryViewModel = viewModel()
    val countries by viewModel.countries.collectAsState()
    val error by viewModel.error.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredCountries = if (searchQuery.isEmpty()) {
        countries
    } else {
        countries.filter {
            it.name.common.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Select Country",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EA)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search country") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            when {
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                    }
                }

                countries.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredCountries) { country ->
                            CountryItem(
                                name = country.name.common,
                                flagUrl = country.flags.png,
                                onClick = {
                                    val encodedName = Uri.encode(country.name.common)
                                    val encodedFlagUrl = Uri.encode(country.flags.png)
                                    val encodedCapital = Uri.encode(country.capital?.firstOrNull() ?: "N/A")
                                    val encodedRegion = Uri.encode(country.region ?: "N/A")
                                    val encodedPopulation = Uri.encode(country.population?.toString() ?: "N/A")

                                    navController.navigate("detail/$encodedName/$encodedFlagUrl/$encodedCapital/$encodedRegion/$encodedPopulation")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Country(val name: String, val flagUrl: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(name: String, flagUrl: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = flagUrl,
                contentDescription = "$name Flag",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}