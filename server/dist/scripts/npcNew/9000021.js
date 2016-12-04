/**
 * Gaga
 * Maple Speed Quiz
 */
var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (status == 0 && mode == 0) {
			cm.sendNext("Aye...are you scared of the quiz? Nevermind, but trust me, you will come back again!");
			cm.dispose();
			return;
		}
		if (mode == 1) {
			status++;
		} else {
			status--;
		}
		if (status == 0) {
			cm.sendAcceptDecline("#bHere comes the Maple speed quiz, specially created for 2011 year end holidays! Wanna try? You can try out only once an hour, so think carefully.");
		} else if (status == 1) {
			var text = cm.startSpeedQuiz();
			if (text != null) {
				cm.sendNext(text);
				cm.dispose();
			}
		}
	}
}