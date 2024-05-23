package com.example

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.example.composeApp.newInstance
import com.example.composeApp.schema
import kotlin.Unit

public interface Database : Transacter {
  public val dbQueries: DbQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = Database::class.schema

    public operator fun invoke(driver: SqlDriver): Database = Database::class.newInstance(driver)
    fun connect(s: String, driver: String): Database {

    }
  }
}
