
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.DbQueries
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


class Earthquake (
    val place : String,
    val date : String
)

class MainModel()
{
    var from = MutableStateFlow("2024-05-01")
    var to = MutableStateFlow("2024-05-02")
    var earthquakes: MutableState<List<Earthquake>> = mutableStateOf(emptyList())
}

class MainViewModel(router:Router, dbQueries: DbQueries)
{
    val router = router
    val dbQueries = dbQueries


    fun isDisplay() : Boolean
    {
        return router.getType() == ViewType.Main
    }

    val mainModel = MainModel()

    private val _from = mainModel.from
    val from : StateFlow<String> = _from

    private val _to = mainModel.to
    val to : StateFlow<String> = _to

    fun setFrom(value: String)
    {
        _from.value = value
    }

    fun setTo(value: String)
    {
        _to.value = value
    }

    fun openHistory()
    {
        router.openHistory()
    }

    fun request1()
    {
        runBlocking {
            val client = HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.INFO
                }
            }

            try {
                val response: HttpResponse = client.get("https://earthquake.usgs.gov/fdsnws/event/1/query"){
                    url {
                        parameters.append("format", "geojson")
                        parameters.append("starttime", from.value)
                        parameters.append("endtime", to.value)
                    }
                }

                val json = Json { ignoreUnknownKeys = true }
                val featureCollection: FeatureCollection = json.decodeFromString(response.bodyAsText())

                mainModel.earthquakes.value = emptyList()
                featureCollection.features.iterator().forEach {
                    val erth = Earthquake("${it.properties.place}", "${convertMillisToDate(it.properties.time)}")
                    mainModel.earthquakes.value = mainModel.earthquakes.value.toMutableList().apply { add(erth) }
                }

                dbQueries.insertObj(from.value, to.value)
            }
            catch (e: Exception) {
                println("Failed to fetch data: ${e.message}")
            }
            finally {
                client.close()
            }

        }
    }
    fun request()
    {
        Thread {
            request1()
        }.start()
    }
}


@Composable
fun MainView(viewModel: MainViewModel)
{
    if(!viewModel.isDisplay()) return

    Column {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyDatePickerView(
                "from",
                {
                    viewModel.setFrom(it)
                })
            MyDatePickerView(
                "to",
                {
                    viewModel.setTo(it)
                })
            Button(onClick =
            {
                println("Search")
                viewModel.request()

            }) {
                Text(text = "Search")
            }
        }
        Button(
            onClick =
            {
                println("History")
                viewModel.openHistory()
            }
        )
        {
            Text(text = "History")
        }
        EarthquakeScreen(viewModel.mainModel.earthquakes)
    }
}