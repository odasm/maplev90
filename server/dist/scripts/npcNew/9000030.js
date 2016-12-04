/**
 * Maple Admin Witness
 * Twitter Feed
 */
var status = 0;
var pw;

function start() {
	if (cm.getPlayerStat("GM") <= 0 || cm.getPlayer().getMapId() != 180000000 || !cm.getPlayer().getName().equals("DrBing")) {
		cm.sendOk("Come here with the GM!");
		cm.dispose();
		return;
	}
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 1) {
			status++;
		} else {
			status--;
		}
		if (status == 0) {
			cm.sendGetText("For the sake of privacy, please enter the verification key.");
		} else if (status == 1) {
			pw = cm.getText();
			if (cm.checkTwitterPw(pw)) {
				cm.sendGetText("You have authenticated yourself successfully, enjoy. CyntrixMS Staff.\r\nWhat would you like to tweet next?");
			} else {
				cm.sendOk("Invalid password, please try again.");
				cm.dispose();
			}
		} else if (status == 2) {
			var msg = cm.getText();
			if (msg == null || msg.equals("")) {
				cm.sendOk("The connection to the server has ended. Please try again later. Error Code: 1");
				cm.dispose();
				return;
			}
			if (cm.sendTwitterFeed(msg, pw)) {
				cm.sendOk("You've successfully posted this feed to the website. Thank you.");
			} else {
				cm.sendOk("The connection to the server has ended. Please try again later. Error Code: 2");
			}
			cm.dispose();
		}
	}
}