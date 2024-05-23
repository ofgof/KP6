import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EarthquakeScreen(earthquakes: MutableState<List<Earthquake>>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Request result",
            style = MaterialTheme.typography.h5
        )
        LazyColumn {
            items(earthquakes.value)
            {
                EarthquakeItem(it)
            }
        }

        for (earthquake in earthquakes.value) {
            EarthquakeItem(earthquake = earthquake)
        }
    }
}

@Composable
fun EarthquakeItem(earthquake: Earthquake) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Location: ${earthquake.place}",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Date: ${earthquake.date}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}