package table

import com.amazonaws.services.dynamodbv2.datamodeling.*
import java.time.LocalDateTime

@DynamoDBTable(tableName = "event")
data class Event(
    @DynamoDBHashKey(attributeName = "event_id")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    var event_id: String,

    @DynamoDBRangeKey(attributeName = "event_name")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    var event_name: String,

    @DynamoDBAttribute(attributeName = "last_login_datetime")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    var lastLoginDatetime: LocalDateTime? = null,

    @DynamoDBAttribute(attributeName = "last_page_view_datetime")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    var lastPageViewDatetime: LocalDateTime? = null,

    @DynamoDBAttribute(attributeName = "last_reservation_datetime")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    var lastReservationDatetime: LocalDateTime? = null
) {
    constructor() : this(event_id = "", event_name = "")
}