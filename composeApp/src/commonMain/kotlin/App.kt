
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.example.Database
import com.example.DbQueries
import io.ktor.util.InternalAPI
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview


enum class ViewType {
    Main, History
}

class Router
{
    private val type = mutableStateOf(ViewType.Main)

    fun getType() : ViewType
    {
        return type.value
    }
    private fun setType(t : ViewType)
    {
        type.value = t
    }

    fun openMain()
    {
        setType(ViewType.Main)
    }
    fun openHistory()
    {
        setType(ViewType.History)
    }
}


@OptIn(ExperimentalResourceApi::class, InternalAPI::class)
@Composable
@Preview
fun App(driverFactory : DriverFactory) {

    var driver = driverFactory.createDriver()
    val database = Database(driver)
    val dbQueries: DbQueries = database.dbQueries

    val router = Router()

    val mainViewModel = MainViewModel(router, dbQueries)
    val historyViewModel = HistoryViewModel(router, dbQueries)

    MaterialTheme {
        MainView(mainViewModel)
        EarthquakeRequestsScreen(historyViewModel)
    }
}

