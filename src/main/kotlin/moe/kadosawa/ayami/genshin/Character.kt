package moe.kadosawa.ayami.genshin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moe.kadosawa.ayami.AyamiException

@Serializable
data class Character(
    @SerialName("ENUM")
    val enum: CharacterType,
    @SerialName("ASCENSION")
    val ascension: String?,
    @SerialName("TALENTS")
    val talents: String?,
    @SerialName("GUIDE")
    val guide: String?
)

fun Character.Companion.fromSimilarName(query: String): Character {
    val similarEnum = characterTypeFromSimilarName(query)
        ?: throw AyamiException("unable to find enum for character by $query")

    return Genshin.characters.find { it.enum == similarEnum }
        ?: throw AyamiException("unable to find character data for $similarEnum")
}