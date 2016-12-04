var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

var npcItems = [ {
	"name" : "Onyx Apple",
	"value" : 2022179,
	"job" : 0,
	"requirements" : [ {
		"item" : 2010000,
		"qty" : 1,
		"desc" : "Apple"
	}, {
		"item" : 4000147,
		"qty" : 200,
		"desc" : "Teddy Bear"
	},
	{
		"item" : 4000150,
		"qty" : 300,
		"desc" : "Ice Piece"
	}]
}];

var selectedItem = 0;
var availableItems;

function getElementsForJob(job) {
	var items = [];
	for (var i = 0; i < npcItems.length; i++) {
		if(npcItems[i].job === job || npcItems[i].job == 0){
			items.push(npcItems[i]);
		}
	}
	return items;
}

function action(mode, type, selection) {
	availableItems = getElementsForJob(cm.getPlayer().getJob());
	if (status === -1) {
		cm.sendYesNo("#bSo you are looking for new items?#k");
		status = 1;
		return;
	}
	// cm.debug(status + " " + mode + " " + type + " " + selection);
	if (status >= 1 && mode === 0 || (availableItems.lentgth === 0)) {
		cm.sendOk("#bCome back later.#k");
		cm.dispose();
		return;
	}
	
	if (status === 1) {
		var message = "";
		for (var i = 0; i < availableItems.length; i++) {
			message += "\r\n#b#L" + i + "#" + availableItems[i].name + "#l"
		}
		cm.sendSimple("Choose your item: " + message)
		status = 2;
		return;
	}
	if (status === 2) {
		cm.sendYesNo("You have choosen " + availableItems[selection].name + ". Are you sure you want to create it? ");
		status = 3;
		selectedItem = selection;
		return;
	}
	if (status == 3) {
		var message = "Necessary items: \r\n"
		for (var i = 0; i < availableItems[selectedItem].requirements.length; i++) {
			var requirement = availableItems[selectedItem].requirements[i];
			message += requirement.qty + " x #b#t" + requirement.item + "##k" + "#b#i" + requirement.item + "##k\t\r\n"; 
		}
		cm.sendNext(message);
		status = 4;
		return;
	}
	if (status === 4) {
		for (var i = 0; i < availableItems[selectedItem].requirements.length; i++) {
			var requirement = availableItems[selectedItem].requirements[i];
			if (!cm.getPlayer().haveItem(requirement.item, requirement.qty)) {
				cm.sendOk("You don't have all the necessary items.");
				cm.dispose();
				return;
			}
		}
		for (var i = 0; i < availableItems[selectedItem].requirements.length; i++) {
			var requirement = availableItems[selectedItem].requirements[i];
			cm.gainItem(requirement.item, -requirement.qty);
		}
		cm.sendOk("Your item " + availableItems[selectedItem].name + " has been created")
		cm.gainItem(availableItems[selectedItem].value, 1);
		status = 5;
	}
	cm.dispose();
}