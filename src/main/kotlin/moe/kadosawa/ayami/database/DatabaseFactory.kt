/*
 * Copyright 2022 kadosawa (kadosawa.moe)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package moe.kadosawa.ayami.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.CompletableDeferred
import moe.kadosawa.ayami.Config
import moe.kadosawa.ayami.database.tables.Reminders
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.properties.Delegates

private val logger = KotlinLogging.logger {}

object DatabaseFactory {
    var database by Delegates.notNull<Database>()
    val ready = CompletableDeferred<Unit>()

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = Config.Hikari.jdbcUrl()
        username = Config.Hikari.username
        password = Config.Hikari.password
        maximumPoolSize = 10
    }

    val hikariSource = HikariDataSource(hikariConfig)

    suspend fun connect() {
        database = Database.connect(hikariSource)

        newSuspendedTransaction {
            exec("SELECT 1;")
            ready.complete(Unit)
            logger.info { "Database connection established" }
        }
    }

    suspend fun createTables() {
        ready.await()

        newSuspendedTransaction {
            if (!Reminders.exists()) {
                SchemaUtils.create(Reminders)
                logger.info { "Tables were created" }
            }
        }
    }
}

