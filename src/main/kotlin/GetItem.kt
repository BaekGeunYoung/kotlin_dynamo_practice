import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import java.util.*

fun main() {
    val tableName = "test_table"
    val name = "geunyoung"

    val key = HashMap<String, AttributeValue>()
    key["Name"] = AttributeValue(name)
    val request = GetItemRequest()
            .withKey(key)
            .withTableName(tableName)

    val ddb: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)
        .build()

    try {
        val item = ddb.getItem(request).item
        if (item != null) {
            val keys: Set<String> = item.keys
            for (key in keys) {
                println("$key, ${item[key].toString()}")
            }
        } else {
            println("No item found with the key $name")
        }
    } catch (e: AmazonServiceException) {
        println(e.errorMessage)
    }
}