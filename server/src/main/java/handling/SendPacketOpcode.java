/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package handling;

public enum SendPacketOpcode {

    // GENERAL
    PING((short) 17),
    // LOGIN
    LOGIN_STATUS((short) 0),
    SERVERSTATUS((short) 3),
    PIN_OPERATION((short) 6),
    PIN_ASSIGNED((short) 7),
    ALL_CHARLIST((short) 8),
    SERVERLIST((short) 10),
    CHARLIST((short) 11),
    SERVER_IP((short) 12),
    CHAR_NAME_RESPONSE((short) 13),
    ADD_NEW_CHAR_ENTRY((short) 14),
    DELETE_CHAR_RESPONSE((short) 15),
    CHANGE_CHANNEL((short) 16),
    CHANNEL_SELECTED((short) 20),
    RELOG_RESPONSE((short) 22),
    SECONDPW_ERROR((short) 23),
    // CHANNEL
    INVENTORY_OPERATION((short) 30),
    INVENTORY_GROW((short) 31),
    UPDATE_STATS((short) 32),
    TEMP_STATS((short) 33),
    TEMP_STATS_RESET((short) 34),
    FORCED_STATS((short) 35),
    FORCED_STATS_RESET((short) 36),
    SKILLS_UPDATE((short) 37),
    SKILLS_USE((short) 38), // 1 byte only - >update time.. same as the byte for update skills after header
    FAME_RESPONSE((short) 39),
    SHOW_STATUS_INFO((short) 40),
    GAME_PATCHES((short) 41),
    SHOW_NOTES((short) 42),
    TROCK_LOCATIONS((short) 43),
    LIE_DETECTOR((short) 44),
    UPDATE_MOUNT((short) 49),
    SHOW_QUEST_COMPLETION((short) 50),
    ENTRUSTED_SHOP((short) 51),
    USE_SKILL_BOOK((short) 52),
    FINISH_SORT((short) 53), // Ongatheritemresult
    FINISH_GATHER((short) 54), // onsortitemresult
    BBS_OPERATION((short) 60),
    CHARACTER_INFO((short) 62),
    PARTY_OPERATION((short) 63),
    EXPEDITION_OPERATION((short) 65),
    BUDDYLIST((short) 66),
    GUILD_OPERATION((short) 68),
    ALLIANCE_OPERATION((short) 69),
    PORTAL_TOWN((short) 70),
    PORTAL_GATE((short) 71),
    SERVERMESSAGE((short) 72),
    PIGMI_REWARD((short) 73),
    OWL_OF_MINERVA((short) 74), // Shop Scanner Result
    OWL_OF_MINERVA_RESULT((short) 75), // Shop Link Result
    ENGAGE_REQUEST((short) 76),
    ENGAGE_RESULT((short) 77),
    YELLOW_CHAT((short) 81),
    SHOP_DISCOUNT((short) 82), // BYTE((short) %), ROUND UP
    CATCH_MESSAGE((short) 83), // 53 00 01 00 00 00 00 00 00 00 00 
    PLAYER_NPC_RESULT((short) 84),
    PLAYER_NPC((short) 85),
    PLAYER_NPC_DISABLE_INFO((short) 86), // is this the one to remove the text on the head?
    MONSTERBOOK_ADD((short) 87),
    MONSTERBOOK_CHANGE_COVER((short) 89),
    RESET_MINI_MAP((short) 90),
    ENERGY((short) 94), // OnSessionValue
    GHOST_POINT((short) 95), // OnPartyValue
    GHOST_STATUS((short) 96), // OnFieldSetVariable
    BONUS_EXP_CHANGED((short) 97),
    FAMILY_CHART((short) 99),
    FAMILY_INFO((short) 100),
    FAMILY_MESSAGE((short) 101),
    FAMILY_INVITE((short) 102),
    FAMILY_INVITE_RESULT((short) 103),
    FAMILY_JOIN_ACCEPTED((short) 104),
    FAMILY_PRIVILEDGE_LIST((short) 105),
    FAMILY_REP_INC((short) 106),
    FAMILY_LOGGEDIN((short) 107),
    FAMILY_BUFF((short) 108),
    FAMILY_SUMMON_REQUEST((short) 109),
    LEVEL_UPDATE((short) 110),
    MARRIAGE_UPDATE((short) 111),
    JOB_UPDATE((short) 112),
    AVATAR_MEGA((short) 116),
    PENDANT_SLOT((short) 126), // one byte only , 1 = show, 0 = don't show
    FOLLOW_REQUEST((short) 127),
    TOP_MSG((short) 128),
    MAPLE_ADMIN((short) 129),
    UPDATE_JAGUAR((short) -2),
    // inventory full stuffs = 131 v97
    VISITOR((short) 135), // seemed that the visitor effect was removed..    
    SKILL_MACRO((short) 139),
    WARP_TO_MAP((short) 140),
    MTS_OPEN((short) 141),
    CS_OPEN((short) 142),
    CHANGE_MAP_EFFECT((short) 143), // byte(0/1) + int + byte(0~5? place) + int
    CHANGE_MO_VISIBLE((short) 144),
    CLEAR_MAP_EFFECT((short) 145),
    MAP_BLOCKED((short) 146),
    SERVER_BLOCKED((short) 147),
    SHOW_EQUIP_EFFECT((short) 148),
    MULTICHAT((short) 149),
    WHISPER((short) 150),
    SPOUSE_CHAT((short) 151),
    SUMMON_ITEM_UNAVAILABLE((short) 152),
    BOSS_ENV((short) 153),
    MOVE_ENV((short) 154),
    UPDATE_ENV((short) 155),
    MAP_EFFECT((short) 157),
    CASH_SONG((short) 158),
    GM_EFFECT((short) 159),
    OX_QUIZ((short) 160),
    GMEVENT_INSTRUCTIONS((short) 161),
    CLOCK((short) 162),
    BOAT_EFF((short) 163), // OnContiMove // not boat, is used for many stuffs.
    BOAT_EFFECT((short) 164), // OnContiState
    STOP_CLOCK((short) 169),
    ARIANT_SCOREBOARD((short) 170),
    PYRAMID_UPDATE((short) 172), // or could it be 171?
    PYRAMID_RESULT((short) 173),
    QUICK_SLOT((short) 174),
    MOVE_PLATFORM((short) 175),
    SPAWN_PLAYER((short) 177),
    REMOVE_PLAYER_FROM_MAP((short) 178),
    CHATTEXT((short) 179),
    CHALKBOARD((short) 181),
    UPDATE_CHAR_BOX((short) 182),
    SHOW_ITEM_UPGRADE_EFFECT((short) 184),
    SHOW_ITEM_HYPER_UPGRADE_EFFECT((short) 185),
    SHOW_ITEM_OPTION_UPGRADE_EFFECT((short) 186),
    SHOW_ITEM_RELEASE_EFFECT((short) 187),
    SHOW_ITEM_UNRELEASE_EFFECT((short) 188),
    FOLLOW_EFFECT((short) 191),
    PAMS_SONG((short) 194),
    SPAWN_PET((short) 195),
    MOVE_PET((short) 198),
    PET_CHAT((short) 199),
    PET_NAMECHANGE((short) 200),
    PET_EXCEPTION_LIST((short) 201),
    PET_COMMAND((short) 202),
    SPAWN_SUMMON((short) 203),
    REMOVE_SUMMON((short) 204),
    MOVE_SUMMON((short) 205),
    SUMMON_ATTACK((short) 206),
    SUMMON_SKILL((short) 207),
    DAMAGE_SUMMON((short) 208),
    DRAGON_SPAWN((short) 209),
    DRAGON_MOVE((short) 210),
    DRAGON_REMOVE((short) 211),
    MOVE_PLAYER((short) 213),
    CLOSE_RANGE_ATTACK((short) 214),
    RANGED_ATTACK((short) 215),
    MAGIC_ATTACK((short) 216),
    ENERGY_ATTACK((short) 217),
    SKILL_EFFECT((short) 218),
    CANCEL_SKILL_EFFECT((short) 219),
    DAMAGE_PLAYER((short) 220),
    FACIAL_EXPRESSION((short) 221),
    SHOW_ITEM_EFFECT((short) 222),
    SHOW_CHAIR((short) 224),
    UPDATE_CHAR_LOOK((short) 225),
    SHOW_FOREIGN_EFFECT((short) 226),
    GIVE_FOREIGN_BUFF((short) 227),
    CANCEL_FOREIGN_BUFF((short) 228),
    UPDATE_PARTYMEMBER_HP((short) 229),
    LOAD_GUILD_NAME((short) 230),
    LOAD_GUILD_ICON((short) 231),
    CANCEL_CHAIR((short) 233),
    SHOW_ITEM_GAIN_INCHAT((short) 235),
    CURRENT_MAP_WARP((short) 236),
    MESOBAG_SUCCESS((short) 238),
    MESOBAG_FAILURE((short) 239),
    UPDATE_QUEST_INFO((short) 244),
    PLAYER_HINT((short) 247),
    REPAIR_WINDOW((short) 254),
    CYGNUS_INTRO_LOCK((short) 255),
    CYGNUS_INTRO_DISABLE_UI((short) 256),
    SUMMON_HINT((short) 257),
    SUMMON_HINT_MSG((short) 258),
    ARAN_COMBO((short) 259),
    FOLLOW_MESSAGE((short) 266),
    FOLLOW_MOVE((short) 271),
    FOLLOW_MSG((short) 272),
    COOLDOWN((short) 274),
    SPAWN_MONSTER((short) 276),
    KILL_MONSTER((short) 277),
    SPAWN_MONSTER_CONTROL((short) 278),
    MOVE_MONSTER((short) 279),
    MOVE_MONSTER_RESPONSE((short) 280),
    APPLY_MONSTER_STATUS((short) 282),
    CANCEL_MONSTER_STATUS((short) 283),
    DAMAGE_MONSTER((short) 286),
    SHOW_MAGNET((short) 287),
    SHOW_MONSTER_HP((short) 290),
    CATCH_MONSTER((short) 292),
    MONSTER_PROPERTIES((short) 296),
    TALK_MONSTER((short) 298), // OnEscortStopSay
    REMOVE_TALK_MONSTER((short) 299), // OnEscortReturnBefore 
    SPAWN_NPC((short) 303),
    REMOVE_NPC((short) 304),
    SPAWN_NPC_REQUEST_CONTROLLER((short) 305),
    NPC_ACTION((short) 306),
    NPC_SCRIPTABLE((short) 309),
    SPAWN_HIRED_MERCHANT((short) 311),
    DESTROY_HIRED_MERCHANT((short) 312),
    UPDATE_HIRED_MERCHANT((short) 313),
    DROP_ITEM_FROM_MAPOBJECT((short) 314),
    REMOVE_ITEM_FROM_MAP((short) 316),
    SPAWN_MIST((short) 320),
    REMOVE_MIST((short) 321),
    SPAWN_DOOR((short) 322),
    REMOVE_DOOR((short) 323),
    REACTOR_HIT((short) 326),
    REACTOR_SPAWN((short) 328),
    REACTOR_DESTROY((short) 329),
    // CField::SnowBall
    ROLL_SNOWBALL((short) 330), // OnSnowBallState
    HIT_SNOWBALL((short) 331),
    SNOWBALL_MESSAGE((short) 332),
    LEFT_KNOCK_BACK((short) 333), // OnSnowBallTouch
    // CField::Coconut
    HIT_COCONUT((short) 334), // OnCoconutHit
    COCONUT_SCORE((short) 335), // OnCoconutScore
    // CField::GuildBoss
    MOVE_HEALER((short) 336), // header+ one short only
    PULLEY_STATE((short) 337), // header+ byte only
    // CField::MonsterCarnival
    MONSTER_CARNIVAL_START((short) 338),
    MONSTER_CARNIVAL_OBTAINED_CP((short) 339),
    MONSTER_CARNIVAL_PARTY_CP((short) 340),
    MONSTER_CARNIVAL_SUMMON((short) 341), // v7((short) 1 // result stuffs
    MONSTER_CARNIVAL_MESSAGE((short) 342), // v7((short) 0 // result stuffs  ((short) structure header+byte)
    MONSTER_CARNIVAL_DIED((short) 343),
    MONSTER_CARNIVAL_LEAVE((short) 344),
    MONSTER_CARNIVAL_RESULT((short) 345), // structure 1 byte only.
    // CField::AriantArena
    ARIANT_SCORE((short) 346), // OnUserScore
    // CField::Battlefield
    SHEEP_RANCH_INFO((short) 348), // OnScoreUpdate ((short) byte+byte)
    SHEEP_RANCH_CLOTHES((short) 349), // OnTeamChanged ((short) int,charid+byte)

    CHAOS_HORNTAIL_SHRINE((short) 351),
    CHAOS_ZAKUM_SHRINE((short) 352),
    HORNTAIL_SHRINE((short) 353),
    ZAKUM_SHRINE((short) 354),
    NPC_TALK((short) 355),
    OPEN_NPC_SHOP((short) 356),
    CONFIRM_SHOP_TRANSACTION((short) 357),
    OPEN_STORAGE((short) 360),
    MERCH_ITEM_MSG((short) 361),
    MERCH_ITEM_STORE((short) 362),
    RPS_GAME((short) 363),
    MESSENGER((short) 364),
    PLAYER_INTERACTION((short) 365),
    DUEY((short) 367),
    // CField::Wedding
    WEDDING_PROGRESS((short) 371),
    WEDDING_END((short) 372),
    CS_CHARGE_CASH((short) 374),
    CS_UPDATE((short) 375),
    CS_OPERATION((short) 376),
    CS_EXP_PURCHASE((short) 377), // one byte only
    CS_GIFT_RESULT((short) 378),
    CS_NAME_ERROR((short) 379),
    CS_NAME_CHANGE((short) 380),
    CS_GACHAPON_STAMPS((short) 383),
    CS_SURPRISE((short) 384),
    CS_XMAS_SURPRISE((short) 385),
    CS_TWIN_DRAGON_EGG((short) 387),
    KEYMAP((short) 389),
    PET_AUTO_HP((short) 390),
    PET_AUTO_MP((short) 391),
    LUCKY_LOGOUT_GIFT((short) 193),
    //MAPLE_LIFE((short) 404),
    //MAPLE_LIFE_SUBMIT((short) 405),
    ARIANT_PQ_START((short) -2),
    GET_MTS_TOKENS((short) 0x999),
    MTS_OPERATION((short) 0x999),
    VICIOUS_HAMMER((short) 412),
    VEGAS_SCROLL((short) 416), 
    CHANGE_BACKGROUND((short) 143);
    private short code = -2;

    private SendPacketOpcode(short code) {
        this.code = code;
    }

    public short getValue() {
        return code;
    }
}
