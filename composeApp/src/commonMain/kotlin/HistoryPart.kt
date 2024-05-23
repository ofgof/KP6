import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.DbQueries
import com.example.EarthquakeRequests

@Composable
fun EarthquakeRequestsScreen(viewModel: HistoryViewModel)
{
    if(!viewModel.isDisplay()) return

    viewModel.updateHistory()

    Column() {
        Row {
            Button(onClick = {viewModel.cleanHistory()}) {
                Text("Clear History")
            }
            Button(onClick = { viewModel.close() }) {
                Text("Close")
            }
        }

        LazyColumn {
            items(viewModel.history.value.count()) {
                EarthquakeRequestItem(viewModel.history.value[it])
                Divider()
            }
        }
    }
}

@Composable
fun EarthquakeRequestItem(request: EarthquakeRequests) {
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
                text = "ID: ${request.id}",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Start Date: ${request.start_date}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "End Date: ${request.end_date}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Created At: ${request.created_at}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

class HistoryViewModel(router:Router, dbQueries: DbQueries)
{
    val router = router
    val dbQueries = dbQueries

    private val _history = mutableStateOf(getHistory())
    val history : MutableState<List<EarthquakeRequests>> = _history

    fun isDisplay() : Boolean
    {
        return router.getType() == ViewType.History
    }

    fun updateHistory()
    {
        _history.value = getHistory()
    }

    fun getHistory() : List<EarthquakeRequests>
    {
        return dbQueries.selectAll().executeAsList()
    }

    fun cleanHistory()
    {
        dbQueries.deleteAll()
        _history.value = getHistory()
    }

    fun close()
    {
        router.openMain()
    }
}