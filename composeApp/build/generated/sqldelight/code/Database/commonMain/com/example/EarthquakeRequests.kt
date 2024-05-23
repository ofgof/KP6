package com.example

import kotlin.Long
import kotlin.String

public data class EarthquakeRequests(
  public val id: Long,
  public val start_date: String,
  public val end_date: String,
  public val created_at: String?,
)
