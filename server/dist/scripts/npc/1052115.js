var rand = Math.round(Math.random()*7);

if (rand <= 1) {
rand = Math.round(Math.random()*7);
}
function start() {
status = -1;
action(1, 0, 0);
}
function action(mode, type, selection) {
if (mode == -1) {
cm.dispose();
}
else {
if (mode == 0) {
cm.sendOk("#e#bHave fun in NeoMS!");
cm.dispose();
return;
}
if (mode == 1) {
status++;
}
else {
status--;
}
if (status == 0) {
    cm.sendGetText("#eYou currently have #b"+cm.getDonatorPoints()+"#k Donator Points. \r\n\r\nHello, i can add weapon attack to your current equipped weapon in exchange of donor point. The more you use, the better your chance to get a good weapon attack.\r\n\r\n#rHow many would you like to use?");
    } else if (status == 1) {
        if (cm.getText() < 0) {
        cm.sendOk("#eYeah right fuck you. Next time, don't try to cheat the system.");
        cm.dispose();
        }else if (cm.getText() == 0) {
        cm.sendOk("#eYou cannot input 0!");
        cm.dispose();
        } else {
        cm.sendYesNo("#eAre you sure you want to use #b"+cm.getText()+"#k donator points?");
        }
        } else if (status == 2) {
        if (cm.getDonatorPoints() >= cm.getText()) {
        cm.upgradeWepAtk(rand * cm.getText());
        cm.takeDonatorPoints(cm.getText());
        cm.sendOk("#eYour weapon has gained #b"+rand * cm.getText()+"#k Weapon Attack!");
        cm.dispose();
            }else{
        cm.sendOk("#eYou don't have enough Donator Points to do this!");
        cm.dispose();
            }
        }
    }
}