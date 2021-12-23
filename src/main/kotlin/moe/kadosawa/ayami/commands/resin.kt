package moe.kadosawa.ayami.commands

import dev.minn.jda.ktx.interactions.option
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.interfaces.Command
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import kotlin.time.Duration
import dev.minn.jda.ktx.interactions.Command as commandData


class ResinCommand : Command {
    override val data = commandData("resin", "Calculate when you'll have enough resin in genshin") {
        option<Int>("current", "Your current amount of resin", true) {
            setRequiredRange(0, 160)
        }

        option<Int>("needed", "How much resin you want to have", true) {
            setRequiredRange(1, 160)
        }
    }

    override suspend fun execute(event: SlashCommandEvent) {
        event.deferReply().await()

        val current = event.getOption("current")!!.asLong
        val needed = event.getOption("needed")!!.asLong

        val deltaMinutes = (needed - current) * 8

        val now = Clock.System.now()
        val then = now.plus(deltaMinutes, DateTimeUnit.MINUTE)

        val fullTimestamp = "<t:${then.epochSeconds}:F>"
        val relativeTimestamp = "<t:${then.epochSeconds}:R>"

        event.hook.sendMessage("You will have **$needed** resin at $fullTimestamp ($relativeTimestamp)").await()
    }
}