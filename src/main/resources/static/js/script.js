function toggleSidebar() {
	document.getElementById("sidebar").classList.toggle('active');
}

function toggleItemDetails() {
	document.getElementById("itemDetails").classList.toggle('active');

}

function todayTaskListToggle() {
	document.getElementById("a1").classList.toggle('active');
}

function addItem() {
    document.getElementById("addItemButton").onclick = function() {
        window.location.href = "http://localhost:8080/addItem";
    };
}

function gotoItems() {
    document.getElementById("idx").onclick = function() {
        window.location.href = "http://localhost:8080/items";
    };
}