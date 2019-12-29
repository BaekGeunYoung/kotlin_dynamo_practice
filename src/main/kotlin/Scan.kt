import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import table.Event

//scan : 해당 table의 모든 item들을 가져옴.
fun main() {
    val ddb: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)
        .build()

    nativeScan(ddb)
}

fun nativeScan(ddb: AmazonDynamoDB) {
    val tableName = "test_table"

    val request = ScanRequest()
        .withTableName(tableName)

    try {
        val items = ddb.scan(request).items
        if (items != null) {
            for(item in items) {
                val keys: Set<String> = item.keys
                for (key in keys) {
                    println("$key, ${item[key].toString()}")
                }
            }
        } else {
            println("No item found in the table $tableName")
        }
    } catch (e: AmazonServiceException) {
        println(e.errorMessage)
    }
}