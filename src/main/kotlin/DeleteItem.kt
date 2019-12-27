import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest

fun main() {
    val tableName = "test_table"

    val key = HashMap<String, AttributeValue>()
    key["Name"] = AttributeValue("geunyoung")

    val request: DeleteItemRequest = DeleteItemRequest()
        .withKey(key)
        .withTableName(tableName)

    val ddb: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)
        .build()

    try {
        ddb.deleteItem(request)
        println("successfully deleted item with key : ${key["Name"]}")
    } catch (e : AmazonServiceException) {
        println(e.errorMessage)
    }
}