var itemid = [];
var MISSINGREQ = "You are missing one or more of the requirement!";
var atkrand1 = Math.round(Math.random()*5);
var atkrand2 = Math.round(Math.random()*16);
var atkrand3 = Math.round(Math.random()*24);

if (atkrand1 == 0) {
atkrand1 = Math.round(Math.random()*5);
}
if (atkrand2 < 4) {
atkrand2 = Math.round(Math.random()*16);
}
if (atkrand3 < 7) {
atkrand3 = Math.round(Math.random()*24);
}



function start() {
    status = -1;
    action(1, 0, 0);
    }
function action(mode, type, selection) {
    selected = selection;
        if (mode == -1) {
            cm.dispose();
        }
    else {
        if (mode == 0) {
            cm.dispose();
return;
}
if (mode == 1) {
    status++;
        }else{
    status--;
        }
if (status == 0) {
        cm.sendSimple("#eHey #dadventurer#k, I'm #dMr. Thunder#k. I can reinforce the Watk of #rany weapon that you want#k.#b\r\n\r\n#L0#How does it work?#l\r\n#L1#What are the requirement?#l\r\n#L2#Upgrade my weapon!#l");
   } else if (status == 1) {
    if (selection == 0) {
    cm.sendOk("#eYou equip the weapon you want to be upgraded.  You will also need a couple of requirement depending on which #rgamble package#k you want. You'll get a #rrandom amount#k of Watk depending on which one you chose.");
    cm.dispose();
    } else if (selection == 1) {
    cm.sendOk("#eGamble Package - #gGood\r\n1 - 5 watk #k#n\r\n#v4010007# x27\r\n#v4005002# x3\r\n#v4021006# x7\r\n\r\n\r\n#k#eGamble Package - #bProfessional\r\n4 - 16 watk#k#n\r\n#v4010007# x33\r\n#v4005000# x5\r\n#v4021005# x14\r\n\r\n\r\n#k#eGamble Package - #rUltimate\r\n7 - 24 watk \r\n\r\n #v4005000# x10 \r\n#v4005001# x10 \r\n#v4005002# x10 \r\n#v4005003# x10 \r\n#v4005004# x10 \r\n");
    cm.dispose();
    } else if (selection == 2) {
    cm.sendSimple("#e#bWhich package would you like?#k \r\n\r\n #L3#Good Package#l\r\n#L4#Proffessional Package#l\r\n#L5#Ultimate Package#l");
    }
    } else if (status == 2) {
        if (selection == 3) {
            if (cm.haveItem(4010007, 17) && cm.haveItem(4005002, 3) && cm.haveItem(4021006, 7)) {
                    cm.upgradeWepAtk(atkrand1);
                    cm.gainItem(4010007, -17)
                    cm.gainItem(4005002, -3)
                    cm.gainItem(4021006, -7)
                    cm.playSound(true, "Dojang/start");
                    cm.worldNotice(6, "Mr. Thunder", ""+cm.getPlayer().getName()+" has gotten "+atkrand1+" weapon attack using the Good Package!");
                    cm.reloadChar();
                    cm.sendOk("#eYour weapon watk has been increased by #r"+atkrand1+"#!");
                    cm.dispose();
                        }else{
                    cm.sendOk(MISSINGREQ); 
                    cm.dispose();
                }
            } else if (selection == 4) {
                        if (cm.haveItem(4010007, 33) && cm.haveItem(4005000, 5) && cm.haveItem(4021005, 14)) {
                    cm.upgradeWepAtk(atkrand2);
                    cm.gainItem(4010007, -33)
                    cm.gainItem(4005000, -5)
                    cm.gainItem(4021005, -14)
                    cm.playSound(true, "Dojang/start");
                    cm.worldNotice(6, "Mr. Thunder", ""+cm.getPlayer().getName()+" has gotten "+atkrand2+" weapon attack using the Professional Package!");
                    cm.reloadChar();
                    cm.sendOk("#eYour weapon watk has been increased by #r"+atkrand2+"#k!");
                    cm.dispose();
                        }else{
                    cm.sendOk(MISSINGREQ); 
                    cm.dispose();
                }
                } else if (selection == 5) {
            if (cm.haveItem(4005000, 10) && cm.haveItem(4005001, 10) && cm.haveItem(4005002, 10) && cm.haveItem(4005003, 10) && cm.haveItem(4005004, 10)) {
                    cm.upgradeWepAtk(atkrand3);
                    cm.gainItem(4005000, -10)
                    cm.gainItem(4005001, -10)
                    cm.gainItem(4005002, -10)
                    cm.gainItem(4005003, -10)
                    cm.gainItem(4005004, -10)
                    cm.playSound(true, "Dojang/start");
                    cm.worldNotice(6, "Mr. Thunder", ""+cm.getPlayer().getName()+" has gotten "+atkrand3+" weapon attack using the Ultimate Package!");
                    cm.reloadChar();
                    cm.sendOk("#eYour weapon watk has been increased by #r"+atkrand3+"#k!");
                    cm.dispose();
                        }else{
                    cm.sendOk(MISSINGREQ); 
                    cm.dispose();
                }
            }
        }
    }
}