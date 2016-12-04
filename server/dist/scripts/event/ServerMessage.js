var Message = new Array(
    "Look for Mimi in Henesys to buy special items",
	"You can earn cash by killing monsters, achievements and Mu Lung Dojo",
    "You can help save the princess in Mushurom Kingdom",
    "Please do not use foul language, harass or scam other players. We would like to keep this community clean & friendly.",
    "Gather your friends and enjoy the fun of our Party Quests !",
    "Please report any bugs/glitches at our forum.",
    "Use @ea if you cant speak to a NPC.",
    "Follow system added! You can follow other players around in the map.",
    "Make a party with your friends and conquer Mulung Dojo! Take down the bosses and receive points to exchange for belts",
    "We have full cash shop working! Purchase cash items to create your unique character look!",
    "Fairy pendant lasts 24hrs, when equipped = 10%, 1hr = 20%, 2hr = 30% bonus exp available through BossPartyQuest.",
    "There will be Channel limit for certain bosses. You can only fight the bosses in the stated channel.",
    "Now, there will be a random gain of a-cash when you kill a monster!",
    "Friendship rings/friendship shirt are working! ",
    "Gather your guildmates and try out the GuildPartyQuest!",
    "Look for Mar the Fairy at Ellinia with the rock of evolution to evolve your pet dragon or pet robo.",
    "Look for Mar the Fairy to get a pet Snail Roon that auto loots meso/drops for you.",
    "Please report any bugs you are facing immediately in the forums!",
    "The rates from level 1 to 10 is 1x",
    "You can use the boats to other continents",
    "Cygnus first job is GMS like",
    "Gachapon can give you timeless equipment and 50% scrolls",
    "After first job you will auto advance to the next job.",
    "To become a dualblade you should complete the quest that appears at level 2 and finish the necessary quests ",
    "During beta skill scrolls are available at npc Inkwell and Agent E. Special scrolls for Evan and Dualblades are available in cash shop.",
    "We are in alpha test phase, please report any bug!");

var setupTask;

function init() {
    scheduleNew();
}

function scheduleNew() {
    setupTask = em.schedule("start", 900000);
}

function cancelSchedule() {
	setupTask.cancel(false);
}

function start() {
    scheduleNew();
    em.broadcastYellowMsg("[NeoMS] " + Message[Math.floor(Math.random() * Message.length)]);
}