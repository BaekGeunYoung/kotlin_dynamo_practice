import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import table.Event
import table.Reservation
import java.lang.Exception
import java.time.LocalDateTime

//scan : 해당 table의 모든 item들을 가져옴.
fun main() {
    val ddb: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)
        .build()
    val ddbMapper = DynamoDBMapper(ddb)

    scanByPartition(ddb, ddbMapper, 3)
}

fun createEventTable(ddb: AmazonDynamoDB, ddbMapper: DynamoDBMapper) {
    val request = ddbMapper.generateCreateTableRequest(Event::class.java)
        .withProvisionedThroughput(ProvisionedThroughput(10, 10))

    try {
        ddb.createTable(request)
        println("create event table success")
    } catch (e: AmazonServiceException) {
        println(e.errorMessage)
    }
}

fun putItems(ddb: AmazonDynamoDB, ddbMapper: DynamoDBMapper, eventId: Int) {
    val event1 = Event(event_id = eventId, event_name = "START")
    val event2 = Event(event_id = eventId, event_name = "RESERVATION")
    val event3 = Event(event_id = eventId, event_name = "LOGIN")
    val event4 = Event(event_id = eventId, event_name = "PAGE_VIEW")
    val event5 = Event(event_id = eventId, event_name = "LOGOUT")

    try {
        ddbMapper.save(event1)
        ddbMapper.save(event2)
        ddbMapper.save(event3)
        ddbMapper.save(event4)
        ddbMapper.save(event5)

        println("put items success")
    } catch (e: AmazonServiceException) {
        println(e.errorMessage)
    }
}

//partition key를 이용한 search
fun scanByPartition(ddb: AmazonDynamoDB, ddbMapper: DynamoDBMapper, eventId: Int) {
    val eav = HashMap<String, AttributeValue>()
    eav[":eventId"] = AttributeValue().withN(eventId.toString())

    val scanExpression = DynamoDBScanExpression()
        .withFilterExpression("event_id = :eventId")
        .withExpressionAttributeValues(eav)

    val scanList = ddbMapper.scan(Event::class.java, scanExpression)

    for(item in scanList) {
        println("${item.event_id} , ${item.event_name} , ${item.lastLoginDatetime}, ${item.lastPageViewDatetime}")
    }
}

fun createWithRandomId(ddbMapper: DynamoDBMapper) {
    val reservation = Reservation(user_id = "geunyoung", reservation_datetime = LocalDateTime.now())

    try {
        ddbMapper.save(reservation)
        println("success")
    } catch (e : Exception) {
        println(e.message)
    }
}