import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
import java.util.*

fun main() {
    val tableName = "test_table"

    val itemValues = HashMap<String, AttributeValue>()
    itemValues["Name"] = AttributeValue("geunyoung")
    itemValues["Age"] = AttributeValue("20")
    itemValues["Phone"] = AttributeValue("010-7417-1626")
    itemValues["Home"] = AttributeValue("Asia/Seoul")

    val ddb: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)
        .build()

    try {
        ddb.putItem(tableName, itemValues)
    } catch (e: ResourceNotFoundException) {
        println("Error: The table \"${tableName}\" can't be found.\n")
        return
    } catch (e: AmazonServiceException) {
        println(e.message)
        return
    }
    println("Done!")
}