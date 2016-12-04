var status = -1;

function action(mode, type, selection) {
	//cm.debug('mode:' + mode + " type: " +type + " selection: " +selection + " status: " +status);
	if (mode == 1) {
		status++;
    } else {
    	status--;
    }
    if (status == 0) {
	if (cm.haveItem(5220000)) {
	    cm.sendYesNo("You have some #bGachapon Tickets#k there.\r\nWould you like to try your luck?");
	} else {
	    cm.sendOk("You don't have a single ticket with you. Please buy the ticket at the department store before coming back to me. Thank you.");
	    cm.safeDispose();
	}
    } else if (status == 1) {
	 var item;
	 
	 item = cm.gainGachaponItem(9100117);

	if (item != -1) {
		cm.sendOk("You have obtained #b#t" + item + "##k.");
	} else {
	    cm.sendOk("Please check your item inventory and see if you have the ticket, or if the inventory is full.");
	}
	cm.safeDispose();
    }
}