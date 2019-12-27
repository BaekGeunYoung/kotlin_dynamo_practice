import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.*

//createTable : table 생성
fun main() {
    val tableName = "test_table"
    val request: CreateTableRequest = CreateTableRequest()
        .withAttributeDefinitions(
            AttributeDefinition(
                "Name", ScalarAttributeType.S
            )
        )
        .withKeySchema(KeySchemaElement("Name", KeyType.HASH))
        .withProvisionedThroughput(ProvisionedThroughput(10, 10))
        .withTableName(tableName)

    val ddb: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)
        .build()

    try {
        val result = ddb.createTable(request)
        println(result.tableDescription.tableName)
    } catch (e: AmazonServiceException) {
        println(e.errorMessage)
    }
}