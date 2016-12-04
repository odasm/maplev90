/**
	Konpei - Near the Hideout(801040000)
*/

function start() {
    cm.sendYesNo("So you want to visit Rien?");
}

function action(mode, type, selection) {
    if (mode == 0) {
    	cm.sendOk("Lets go!");
    } else {
    	cm.warp(140000000,0);
    }
    cm.dispose();
}