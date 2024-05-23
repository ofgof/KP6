

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.Database
import com.example.EarthquakeRequests
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test
import kotlin.test.assertEquals

class DatabaseTest {

    private lateinit var database: Database
    lateinit var instrumentationContext: Context

    @BeforeTest
    fun setupDatabase() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        var driverFactory = DriverFactory(instrumentationContext)
        var driver = driverFactory.createDriver()
        val database = Database(driver)
    }

    @Test
    fun testInsertAndSelectAll() {
        database.dbQueries.insertObj("2022-01-01", "2022-01-02")
        val allRequests = database.dbQueries.selectAll().executeAsList()
        assertEquals(1, allRequests.size)
        assertEquals("2022-01-01", allRequests[0].start_date)
        assertEquals("2022-01-02", allRequests[0].end_date)
    }

    @Test
    fun testInsertAndDeleteAll() {
        database.dbQueries.insertObj("2022-01-01", "2022-01-02")
        database.dbQueries.deleteAll()
        val allRequests = database.dbQueries.selectAll().executeAsList()
        assertEquals(0, allRequests.size)
    }

    @Test
    fun testInsertObj() {
        database.dbQueries.insertObj("2022-01-01", "2022-01-02")
        val allRequests = database.dbQueries.selectAll().executeAsList()
        assertEquals(1, allRequests.size)
        assertEquals("2022-01-01", allRequests[0].start_date)
        assertEquals("2022-01-02", allRequests[0].end_date)
    }

    @Test
    fun testDeleteAll() {
        database.dbQueries.deleteAll()
        val allRequests = database.dbQueries.selectAll().executeAsList()
        assertEquals(0, allRequests.size)
    }

    @Test
    fun testInsertSuccess() {
        database.dbQueries.deleteAll()
        database.dbQueries.insertObj("2022-01-01", "2022-01-02")
        val allRequests = database.dbQueries.selectAll().executeAsList()
        assertEquals(1, allRequests.size)
        assertEquals("2022-01-01", allRequests[0].start_date)
        assertEquals("2022-01-02", allRequests[0].end_date)
    }

    @Test
    fun testInsertFail() {
        database.dbQueries.deleteAll()
        database.dbQueries.insertObj("2024-01-01", "2024-01-02")
        database.dbQueries.insert(EarthquakeRequests(0, "2022-01-01", "2022-01-02", "0"))
        val allRequests = database.dbQueries.selectAll().executeAsList()
        assertEquals(2, allRequests.size)
        assertEquals("2024-01-01", allRequests[0].start_date)
        assertEquals("2024-01-02", allRequests[0].end_date)
        assertEquals("2022-01-01", allRequests[1].start_date)
        assertEquals("2022-01-02", allRequests[1].end_date)
    }

    @Test
    fun testSelectSuccess() {
        database.dbQueries.deleteAll()
        database.dbQueries.insertObj("2022-01-01", "2022-01-02")
        val allRequests = database.dbQueries.selectAll().executeAsList()
        assertEquals(1, allRequests.size)
        assertEquals("2022-01-01", allRequests[0].start_date)
        assertEquals("2022-01-02", allRequests[0].end_date)
    }

    @Test
    fun testSelectFail() {
        database.dbQueries.deleteAll()
        database.dbQueries.insertObj("2024-01-01", "2025-01-02")
        val allRequests = database.dbQueries.selectById(1).executeAsList()
        assertEquals(1, allRequests.size)
        assertEquals("2024-01-01", allRequests[0].start_date)
        assertEquals("2024-01-02", allRequests[0].end_date)
    }
}
