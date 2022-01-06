package moe.kadosawa.ayami.commands.character

import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.genshin.GenshinCharacters
import moe.kadosawa.ayami.interfaces.SlashExecutor
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.apache.commons.text.similarity.FuzzyScore
import java.util.*

object CharacterMaterialsSlash : SlashExecutor() {
    override val path = "character/materials"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()
        val query = event.getOption("query")!!.asString

        val results = mutableListOf<Pair<Int, GenshinCharacters>>()
        val score = FuzzyScore(Locale.ENGLISH)

        GenshinCharacters.values().forEach { c ->
            val scored = score.fuzzyScore(c.fullname, query) ?: 0
            if (scored > 0) {
                results.add(Pair(scored, c))
            }
        }

        val biggestScore = results.maxOfOrNull { it.first }
        val closestResults = results.filter { it.first == biggestScore }

        if (closestResults.isEmpty()) {
            event.hook.sendMessage("I tried really hard, but there's nothing similar in my database :(").await()
            return
        }

        if (closestResults.size > 1) {
            event.hook.sendMessage("There are multiple results:\n${closestResults.joinToString("\n")}").await()
        } else {
            event.hook.sendMessage(closestResults.joinToString("\n")).await()

        }
    }
}