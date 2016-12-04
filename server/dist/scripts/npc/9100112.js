var status;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    cm.sendSimple("Hello there!");
    cm.dispose();	
}