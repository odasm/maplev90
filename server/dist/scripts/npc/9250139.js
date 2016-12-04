// crystanol synthesis
var status = 0;

function start() {
	if (cm.getMapId() == 502030004) {
		status = 99; // start from 100 (pq npc)
	} else {
		status = -1;
	}
    action(1, 0, 0);	
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
		if (status == 1 && mode == 0) {
			cm.sendNext("Come back again later.");
			cm.dispose();
			return;
		}
        if (mode == 1) {
            status++;
		} else {
            status--;
		}
        if (status == 0) {
			cm.sendNext("Not coded.");
			cm.dispose();
		} else if (status == 100) {
			var eim = cm.getEventInstance();
		    if (eim != null && eim.getEventManager() != null) {
				var em = eim.getEventManager();
				var stage = parseInt(eim.getProperty("stage"));
				if (stage == 4) {
					cm.sendNext("You approached your hand to take the cyrtanol..."); // change to show player on >> + no esc
					if (!cm.haveItem(4001459)) {
						cm.gainItem(4001459, 1);
					}
					eim.finishPQ();
				} else if (stage == 5) {
					// already taken..you approadched hand.and seemed nothing left..
				} else {
					//hack
					cm.sendNext("ERROR");
				} 
				cm.dispose();
			} else {
				cm.sendNext("What are you doing here..?");
			}			
			cm.dispose();
		}		
	}
}