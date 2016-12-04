function start() {
    if (cm.getPlayer().getMapId() == 970010000) {
        cm.sendYesNo("#eWould you like to go back to #bhenesys#k now?");
    } else {
        cm.sendYesNo("#eHey, would you like to be warped to the maple hill and donate some suns to aramia? When the max amount is reached, the tree explode and valuable shit spread everywhere for a while such as diamond, topaz and other needed stuff for upgrading your items weapon attack with Mr. Thunder!\r\n\r\n #rSo, would you like to help ?#k"); 
    }
}

function action(m,t,s) {
    if (m > 0) {
        if (cm.getPlayer().getMapId() == 970010000) {
            cm.warp(100000000)
            cm.sendOk("#eThanks for helping the #rmaple tree#k growing!");
            cm.dispose();
        } else {
            cm.warp(970010000);
            cm.sendOk("#eWalk to your right to see Aramia and the tree on top of the hill. Use the portal to the left to go back to henesys.");
            cm.dispose();
        }
    } else {
        if (cm.getPlayer().getMapId() == 970010000) {
            cm.sendOk("#eAlright, re-use the portal at anytime if you'd like to exit.");
            cm.dispose();
        } else {
            cm.sendOk("#eAlright come back at any time!");
            cm.dispose();
        }
    }
}