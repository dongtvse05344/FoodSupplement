

function selected(id) {
    alert("Đã thêm sản phẩm để so sánh");
    var items = selectedItem(id);
    document.getElementById("btnComparison").innerHTML = "Đến trang so sánh (" + items.length + ")";
}

function getSelected() {
    var items = getSelectedItem();
    document.getElementById("btnComparison").innerHTML = "Đến trang so sánh (" + items.length + ")";
}

document.addEventListener('DOMContentLoaded', (event) => {
    //the event occurred
    getSelected();
})

function goToComparison() {
    var items = getSelectedItem();

    location.replace("http://localhost:8080/FoodSupplement/ComparisonServlet?ids=[" + items + "]");
}

function removeId(id) {
    var items = removeSelectedItem(id);
    location.replace("http://localhost:8080/FoodSupplement/ComparisonServlet?ids=[" + items + "]");
}

function clearSelected() {
    clearItems();
    document.getElementById("btnComparison").innerHTML = "Đến trang so sánh (0)";
}