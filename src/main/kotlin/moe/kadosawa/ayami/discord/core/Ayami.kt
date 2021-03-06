package moe.kadosawa.ayami.discord.core

import kotlinx.coroutines.*
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.ApplicationInfo
import net.dv8tion.jda.api.requests.GatewayIntent
import kotlin.properties.Delegates


object Ayami {
    private val logger = KotlinLogging.logger {}

    /**
     * Coroutine scope used to launch coroutines
     * from [net.dv8tion.jda.api.JDA] listeners
     */
    val listenerScope = CoroutineScope(Dispatchers.Default)

    /**
     * Completes once [jda] is ready
     */
    val readyDeferred = CompletableDeferred<Unit>()

    /**
     * Instance of [net.dv8tion.jda.api.JDA]
     */
    val jda by lazy {
        JDABuilder.createLight(Config.Bot.token)
            .addEventListeners(MainListener())
            .enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_MESSAGES
            )
            .build()
    }

    /**
     * Discord Application info
     */
    var appInfo by Delegates.notNull<ApplicationInfo>()

    /**
     * Sends list of available slash
     * commands to the Discord API
     */
    suspend fun refreshCommands(refreshGlobal: Boolean = true, refreshDebugGuild: Boolean = true): Boolean {
        jda.awaitReady()

        if (refreshGlobal) {
            jda.updateCommands().addCommands(Slashes.globalData).await()
        }

        if (refreshDebugGuild) {
            jda.getGuildById(Config.Bot.guild)!!.updateCommands().addCommands(Slashes.combinedData).await()
        }

        logger.info { "Global and debug guild commands were re-added!" }
        return true
    }
}