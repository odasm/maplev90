/**
 * Reborn NPC Script
 * (Used by @ reborn)
 */
var status = 0;
var skillid;
var SELECTION_TEXT = "Which of the following class do you want to reborn to? Please select one only. \r\n\r\n #L0#Explorer#l\r\n#L1#Cygnus Knights#l\r\n#L2#Aran#l\r\n#L3#Evan#l\r\n";

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode <= 0) {
		if ((status == 0 || (status == 2 && skillid == 0)) && mode == 0) {
			cm.sendOk("Its alright. You can come back to me once you've decided about it.");
		}
		cm.dispose();
	} else {
		if (mode == 1) {
			status++;
		} else {
			status--;
		}
		if (status == 0) {
			cm.sendYesNo("Hey! Long time no see! Congratulations on achieving Level 200! You wanna reborn uh? Ahh..I can only say that you are allowed to keep only one skill from your current job. Once you've chosen it, it will be a reborn skill and fixed throughout your future reborns. Are you sure you would like to reborn?");
		} else if (status == 1) {
			cm.sendSimple(cm.populateKeymapValues());
		} else if (status == 2) {
			skillid = cm.getSkillIdKey(selection);
			if (skillid > 0) {
				cm.sendYesNo("Are you sure that you would like to select #s" + skillid + "# as a reborn skill?");
			} else if (skillid == 0 || -1) { // 0 = normal job didn't keep any skill and -1 = evan (They can't keep skills)
				cm.sendSimple(SELECTION_TEXT);
			} else {
				cm.sendOk("Are you trying to packet edit right here?");
				cm.dispose();
			}
		} else if (status == 3) {
			if (skillid == 0) { // Evan can't keep skills
				cm.getPlayer().doRB(getJobId(selection));
				cm.getPlayer().wipeSkillsWithException(-1);
				cm.sendOk("Congratulations! Now, you've a total of " + cm.getPlayer().getReborns() + " reborns.");
				cm.dispose();
			} else {
				cm.sendSimple(SELECTION_TEXT);
			}
		} else if (status == 4) {
			cm.getPlayer().doRB(getJobId(selection));
			cm.getPlayer().wipeSkillsWithException(skillid);
			cm.sendOk("Congratulations! Now, you've a total of " + cm.getPlayer().getReborns() + " reborns.");
			cm.dispose();
		}
	}
}

function getJobId(sss) {
	switch (sss) {
	case 1:	// KOC
		return 1000;
	case 2:	// Aran
		return 2000;
	case 3:	// Evan
		return 2001;
	}
	return 0; //Explorer
}