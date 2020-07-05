function selectedItem(id) {
    var oldItems = JSON.parse(localStorage.getItem('selectedItem')) || [];
    if (!oldItems.includes(id)) {
        if (oldItems.length > 2) {
            oldItems.shift();
        }
        oldItems.push(id);
        localStorage.setItem("selectedItem", JSON.stringify(oldItems));
    }
    return oldItems;
}

function getSelectedItem() {
    var items = JSON.parse(localStorage.getItem('selectedItem')) || [];
    return items;
}

function removeSelectedItem(id) {
    var oldItems = JSON.parse(localStorage.getItem('selectedItem')) || [];
    const index = oldItems.indexOf(id);
    if (index > -1) {
        oldItems.splice(index, 1);
    }
    localStorage.setItem("selectedItem", JSON.stringify(oldItems));
    return oldItems;
}

function clearItems() {
    localStorage.removeItem("selectedItem");
}
