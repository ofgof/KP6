package com.example

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class DbQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    id: Long,
    start_date: String,
    end_date: String,
    created_at: String?,
  ) -> T): Query<T> = Query(1_770_552_508, arrayOf("EarthquakeRequests"), driver, "Db.sq",
      "selectAll", """
  |SELECT EarthquakeRequests.id, EarthquakeRequests.start_date, EarthquakeRequests.end_date, EarthquakeRequests.created_at
  |FROM EarthquakeRequests
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)
    )
  }

  public fun selectAll(): Query<EarthquakeRequests> = selectAll { id, start_date, end_date,
      created_at ->
    EarthquakeRequests(
      id,
      start_date,
      end_date,
      created_at
    )
  }

  public fun <T : Any> selectById(id: Long, mapper: (
    id: Long,
    start_date: String,
    end_date: String,
    created_at: String?,
  ) -> T): Query<T> = SelectByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)
    )
  }

  public fun selectById(id: Long): Query<EarthquakeRequests> = selectById(id) { id_, start_date,
      end_date, created_at ->
    EarthquakeRequests(
      id_,
      start_date,
      end_date,
      created_at
    )
  }

  public fun insert(EarthquakeRequests: EarthquakeRequests) {
    driver.execute(-1_575_529_374, """
        |INSERT INTO EarthquakeRequests(id, start_date, end_date, created_at)
        |VALUES (?, ?, ?, ?)
        """.trimMargin(), 4) {
          bindLong(0, EarthquakeRequests.id)
          bindString(1, EarthquakeRequests.start_date)
          bindString(2, EarthquakeRequests.end_date)
          bindString(3, EarthquakeRequests.created_at)
        }
    notifyQueries(-1_575_529_374) { emit ->
      emit("EarthquakeRequests")
    }
  }

  public fun insertObj(start_date: String, end_date: String) {
    driver.execute(-1_192_891_083, """
        |INSERT INTO EarthquakeRequests(start_date, end_date, created_at)
        |VALUES (?,?,CURRENT_TIMESTAMP)
        """.trimMargin(), 2) {
          bindString(0, start_date)
          bindString(1, end_date)
        }
    notifyQueries(-1_192_891_083) { emit ->
      emit("EarthquakeRequests")
    }
  }

  public fun deleteAll() {
    driver.execute(-1_167_148_627, """DELETE FROM EarthquakeRequests""", 0)
    notifyQueries(-1_167_148_627) { emit ->
      emit("EarthquakeRequests")
    }
  }

  private inner class SelectByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("EarthquakeRequests", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("EarthquakeRequests", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-947_405_801, """
    |SELECT EarthquakeRequests.id, EarthquakeRequests.start_date, EarthquakeRequests.end_date, EarthquakeRequests.created_at
    |FROM EarthquakeRequests
    |WHERE id = ?
    """.trimMargin(), mapper, 1) {
      bindLong(0, id)
    }

    override fun toString(): String = "Db.sq:selectById"
  }
}
