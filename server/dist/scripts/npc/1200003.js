/**
	Konpei - Near the Hideout(801040000)
*/

function start() {
    cm.sendYesNo("So have you finish your business in Rien?");
}

function action(mode, type, selection) {
    if (mode == 0) {
	cm.sendOk("Have a good ride!");
    } else {
	cm.warp(104000000,0);
    }
    cm.dispose();
}