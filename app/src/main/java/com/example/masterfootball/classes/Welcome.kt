package com.example.masterfootball.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.Serializable

@Serializable
data class Welcome (
    val success: Long,
    val result: List<Result>
)

@Serializable
data class Result (
    @SerialName("team_key")
    val teamKey: Long,

    @SerialName("team_name")
    val teamName: String,

    @SerialName("team_logo")
    val teamLogo: String,

    val players: List<Player>,
    val coaches: List<Coach>
)

@Serializable
data class Coach (
    @SerialName("coach_name")
    val coachName: String,

    @SerialName("coach_country")
    val coachCountry: JsonElement? = null,

    @SerialName("coach_age")
    val coachAge: JsonElement? = null
)

@Serializable
data class Player (
    @SerialName("player_key")
    val playerKey: Long,

    @SerialName("player_image")
    val playerImage: String,

    @SerialName("player_name")
    val playerName: String,

    @SerialName("player_number")
    val playerNumber: String,

    @SerialName("player_country")
    val playerCountry: JsonElement? = null,

    @SerialName("player_type")
    val playerType: String,

    @SerialName("player_age")
    val playerAge: String,

    @SerialName("player_match_played")
    val playerMatchPlayed: String,

    @SerialName("player_goals")
    val playerGoals: String,

    @SerialName("player_yellow_cards")
    val playerYellowCards: String,

    @SerialName("player_red_cards")
    val playerRedCards: String,

    @SerialName("player_injured")
    val playerInjured: String,

    @SerialName("player_substitute_out")
    val playerSubstituteOut: String,

    @SerialName("player_substitutes_on_bench")
    val playerSubstitutesOnBench: String,

    @SerialName("player_assists")
    val playerAssists: String,

    @SerialName("player_birthdate")
    val playerBirthdate: String,

    @SerialName("player_is_captain")
    val playerIsCaptain: String,

    @SerialName("player_shots_total")
    val playerShotsTotal: String,

    @SerialName("player_goals_conceded")
    val playerGoalsConceded: String,

    @SerialName("player_fouls_committed")
    val playerFoulsCommitted: String,

    @SerialName("player_tackles")
    val playerTackles: String,

    @SerialName("player_blocks")
    val playerBlocks: String,

    @SerialName("player_crosses_total")
    val playerCrossesTotal: String,

    @SerialName("player_interceptions")
    val playerInterceptions: String,

    @SerialName("player_clearances")
    val playerClearances: String,

    @SerialName("player_dispossesed")
    val playerDispossesed: String,

    @SerialName("player_saves")
    val playerSaves: String,

    @SerialName("player_inside_box_saves")
    val playerInsideBoxSaves: String,

    @SerialName("player_duels_total")
    val playerDuelsTotal: String,

    @SerialName("player_duels_won")
    val playerDuelsWon: String,

    @SerialName("player_dribble_attempts")
    val playerDribbleAttempts: String,

    @SerialName("player_dribble_succ")
    val playerDribbleSucc: String,

    @SerialName("player_pen_comm")
    val playerPenComm: String,

    @SerialName("player_pen_won")
    val playerPenWon: String,

    @SerialName("player_pen_scored")
    val playerPenScored: String,

    @SerialName("player_pen_missed")
    val playerPenMissed: String,

    @SerialName("player_passes")
    val playerPasses: String,

    @SerialName("player_passes_accuracy")
    val playerPassesAccuracy: String,

    @SerialName("player_key_passes")
    val playerKeyPasses: String,

    @SerialName("player_woordworks")
    val playerWoordworks: String,

    @SerialName("player_rating")
    val playerRating: String
)