package nasirli.tool.rxandroidandkotlinflows.domain.models

data class TradeResponse(
    val data: List<TradeData>,
    val type: String
)

data class TradeData(
    val c: Any?,      // You can replace `Any?` with the expected type if you know it
    val p: Double,    // Price
    val s: String,    // Symbol
    val t: Long,      // Timestamp
    val v: Double     // Volume
)
