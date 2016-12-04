var servername = "NeoMS";
var answer = ["NeoMS Rev 3.0 \r\n\r\n2012/01/04 - This npc is born", "Gtop 100 gold! Feels good to be high again.\r\n\r\n2012/03/02", "Server Name - #rNeoMS#k\r\nRates:\r\n#rXP#k : #b500\r\n#rMeso#k : #b2500\r\n#rDrop#k : #b3#k\r\n\r\n_____FaQ_____\r\n#rQ:#k What was this server originally named ?\r\n#bA: OxyMS.\r\n#rQ:#kSince when do you make maplestory private servers?\r\n#bA: Since 2007, with GMS v53. I couldn't code really though.", "#b@commands / @help - Show you the list of commands\r\n@online - Show you which players are online\r\n@info - Open up the npc you're reading ATM\r\n@genocide <name> - kill the player for the cost of 50 charms of the undead\r\n@anonymousrape - Same as @genocide but it will not show your name and it cost 200 Charms instead.\r\n@dropnx - Open up Tau, the NX item dropper\r\n@home / @henesys - Warp you to henesys o.0\r\n@hhg1 - warp you to henesys hunting ground 1\r\n@fm - warp you to FM\r\n@str / @int / @dex / @luk - Distribute AP faster\r\n@clearslot <eq/use/setup/etc/cash/all> - clear your an inventory\r\n@ea - unstuck yourself if you cannot talk to any npc/change map/loot item\r\n@ranking <rank> <start num> <end num> - check out the ranking duh\r\n\r\n\r\nThis list is updated very rarelly! You may want to do @commands instead to see the updated list.", "Just explore to find out which NPC we have! Our main town for NPC's are Henesys and FM. Henesys contain more of the really useful npcs while on the other hand, FM has misc npcs that aren't too important."];
var randamnt = Math.round(Math.random()*20) : randamnt < 10 ? Math.round(Math.random()*20);

/*
if (randamnt < 10) {
	randamnt = Math.round(Math.random()*20);
}*/

function start() {
    cm.sendSimple("#eHello and welcome to #r"+servername+"#k info npc. You may get various information about the server here.\r\n\r\n#L0#Recent #rupdate#k#l\r\n#L1#Server #rnews#k#l\r\n#L2#Tell me the #rbasic information#k#l\r\n#L3#Tell me more about the #rcommands#k in NeoMS\r\n#L4#What are the #rnpc's#k in NeoMS?\r\n#r#L5#Give me some smegas!#l\r\n#L6#Fix my #rNegative#k EXP!#l");
}

function action(m,t,s) {
    if (m > 0) {
		if(s >= 5) {
			if(s == 5) {
				cm.gainItem(5072000, randamnt);
				cm.sendOk("#eYou have gained #r"+randamnt+"#b smegas!");
				cm.dispose();
			} else if (s == 6) {
				cm.getPlayer().expfix(0); //reset exp to 0
				cm.sendOk("#eI have fixed your negative exp!")
				cm.dispose();
			}
		} else {
            cm.sendOk("#e" + answer[s]);
            cm.dispose();
		}
	} else {
		cm.sendOk("#eEnjoy your stay on NeoMS v90!");
		cm.dispose();
	}
}