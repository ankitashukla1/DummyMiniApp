package com.example.formvalidation.uii

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.formvalidation.model.Country
import com.example.formvalidation.viewmodel.CountryViewModel

@Composable
fun CountryListScreen(
    viewModel: CountryViewModel = viewModel(),
    onCountryClick: (Country) -> Unit
) {
    val countries by viewModel.countries.collectAsState()
    val error by viewModel.error.collectAsState()

    if (error != null) {
        Text(text = error ?: "Unknown error")
    } else {
        LazyColumn {
            items(countries) { country ->
                Text(
                    text = country.name.common,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCountryClick(country) }
                        .padding(16.dp)
                )
            }
        }
    }
}
