package moe.kadosawa.ayami.database.tables

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Reminders : Table() {
    val id = long("id").autoIncrement().uniqueIndex()

    val createdAt = timestamp("createdAt").index().defaultExpression(CurrentTimestamp())
    val triggerAt = timestamp("triggerAt").index()
    val authorId = long("authorId")
    val guildId = long("guildId").nullable().default(null)
    val channelId = long("channelId").nullable().default(null)
    val messageId = long("messageId").index().nullable().default(null)
    val content = text("content").default("...")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Reminder(
    val id: Long,
    val createdAt: Instant,
    val triggerAt: Instant,
    val authorId: Long,
    val guildId: Long?,
    val channelId: Long?,
    val messageId: Long?,
    val content: String
)

@Serializable
data class NewReminder(
    val triggerAt: Instant,
    val authorId: Long,
    val guildId: Long? = null,
    val channelId: Long? = null,
    val messageId: Long? = null,
    val content: String = "...",
)