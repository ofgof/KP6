import kotlinx.serialization.Serializable

@Serializable
data class FeatureCollection(
    val type: String,
    val metadata: Metadata,
    val features: List<Feature>
)

@Serializable
data class Metadata(
    val generated: Long,
    val url: String,
    val title: String,
    val status: Int,
    val api: String,
    val count: Int
)

@Serializable
data class Feature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry,
    val id: String
)

@Serializable
data class Properties(
    val mag: Double,
    val place: String,
    val time: Long,
    val updated: Long,
    val tz: String? = null,
    val url: String,
    val detail: String,
    val felt: Int? = null,
    val cdi: Double? = null,
    val mmi: Double? = null,
    val alert: String? = null,
    val status: String,
    val tsunami: Int,
    val sig: Int,
    val net: String,
    val code: String,
    val ids: String,
    val sources: String,
    val types: String,
    val nst: Int? = null,
    val dmin: Double? = null,
    val rms: Double? = null,
    val gap: Double? = null,
    val magType: String,
    val title: String
)

@Serializable
data class Geometry(
    val type: String,
    val coordinates: List<Double>
)
