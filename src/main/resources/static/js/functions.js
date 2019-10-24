toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": true,
    "progressBar": true,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

$(function () {
    $("form").submit(function (event) {
        event.preventDefault();
    });

    jQuery.validator.addMethod("lettersOnly", function (value, element) {
        return this.optional(element) || /^[a-z]+$/i.test(value);
    }, "Letters only");
    jQuery.validator.addMethod("creditName", function (value, element) {
        return this.optional(element) || /^[a-z_\s]+$/i.test(value);
    }, "Letters only");
    jQuery.validator.addMethod("lettersAndNumbersOnly", function (value, element) {
        return this.optional(element) || /^[a-z0-9-]+$/i.test(value);
    }, "Letters and numbers only");
    jQuery.validator.addMethod("password_rule", function (value, element) {
        return this.optional(element) || /^[a-z0-9"!"#$%&'()*+,-./:;<=>?@[^_`{|}~"]+$/i.test(value);
    }, "Password rule");
    $("form").each(function () {
        $(this).validate({
            rules: {
                username: {
                    required: true,
                    minlength: 2,
                    maxlength: 24,
                    lettersAndNumbersOnly: true,
                },
                firstName: {
                    required: true,
                    minlength: 2,
                    maxlength: 24,
                    lettersOnly: true,
                },
                lastName: {
                    required: true,
                    minlength: 2,
                    maxlength: 24,
                    lettersOnly: true,
                },
                birthdate: {
                    date: true,
                    required: true,
                },
                password: {
                    required: true,
                    password_rule: true,
                    minlength: 4,
                    maxlength: 24,
                },
                confirmPassword: {
                    required: true,
                    password_rule: true,
                    minlength: 4,
                    maxlength: 24,
                    equalTo: "#password",
                },
                newPassword: {
                    required: true,
                    password_rule: true,
                    minlength: 4,
                    maxlength: 24,
                },
                email: {
                    required: true,
                    email: true,
                    minlength: 4,
                },
                country: {
                    required: true,
                    minlength: 3,
                    maxlength: 24,
                    lettersAndNumbersOnly: true
                },
                city: {
                    required: true,
                    minlength: 4,
                    maxlength: 24,
                    lettersAndNumbersOnly: true
                },
                street: {
                    required: true,
                    minlength: 4,
                    maxlength: 24,
                    lettersAndNumbersOnly: true
                },
                building: {
                    required: true,
                    minlength: 1,
                    maxlength: 24,
                    lettersAndNumbersOnly: true
                },
                apartment: {
                    maxlength: 6,
                    lettersAndNumbersOnly: true
                },
                zipcode: {
                    required: true,
                    minlength: 4,
                    maxlength: 24,
                    digits: true,
                },
                creditCardOwner: {
                    required: true,
                    minlength: 2,
                    maxlength: 34,
                    creditName: true,
                },
                creditCardCvv: {
                    required: true,
                    minlength: 3,
                    maxlength: 3,
                    digits: true,
                },
                cardNumber: {
                    required: true,
                },
            }
        });
    });
});

/*
Login and registration function
 */
async function doLogin() {
    if ($("#loginForm").valid()) {
        let form = document.getElementById('loginForm');
        form.action = '/login';
        form.method = 'POST';
        form.submit();
    }
}

async function register() {
    if ($("#registrationForm").valid()) {
        let form = document.getElementById('registrationForm');
        form.submit();
    }
}

/*
Profile main setting editing
 */
function editMain() {
    switchMainSettingFields(false);
    switchMainSettingButtons('none', 'block', 'block');
}

async function cancelMainEdit() {
    const firstNameField = document.getElementById("firstName");
    const lastNameField = document.getElementById("lastName");
    const birthdateField = document.getElementById("birthdate");

    //load data from backend, replace field with old data
    let call = await fetch('profileSettings/cancelMainEdit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    let result = await call.json();
    if (!result.error) {
        firstNameField.value = result.user.firstName;
        lastNameField.value = result.user.lastName;
        birthdateField.value = result.user.birthdate;
    } else {
        toastr.error(result.message);
    }
    switchMainSettingFields(true);
    switchMainSettingButtons('block', 'none', 'none');
}

async function saveMainEdit() {

    if ($("#mainSettingsForm").valid()) {
        const firstNameField = document.getElementById("firstName");
        const lastNameField = document.getElementById("lastName");
        const birthdateField = document.getElementById("birthdate");

        let edit = {
            firstname: firstNameField.value,
            lastname: lastNameField.value,
            birthdate: birthdateField.value
        };
        let call = await fetch('profileSettings/saveMainEdit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(edit)
        });
        let result = await call.json();
        if (!result.error) {
            firstNameField.value = result.user.firstName;
            lastNameField.value = result.user.lastName;
            birthdateField.value = result.user.birthdate;
        } else {
            toastr.error(result.message);
        }
        switchMainSettingFields(true);
        switchMainSettingButtons('block', 'none', 'none');
    }

}

function switchMainSettingFields(readonlyBool) {
    const firstNameField = document.getElementById("firstName");
    const lastNameField = document.getElementById("lastName");
    const birthdateField = document.getElementById("birthdate");

    firstNameField.readOnly = readonlyBool;
    lastNameField.readOnly = readonlyBool;
    birthdateField.readOnly = readonlyBool;

    firstNameField.classList.toggle("form-control");
    lastNameField.classList.toggle("form-control");
    birthdateField.classList.toggle("form-control");
    firstNameField.classList.toggle("form-control-plaintext");
    lastNameField.classList.toggle("form-control-plaintext");
    birthdateField.classList.toggle("form-control-plaintext");

}

function switchMainSettingButtons(edit, cancel, save) {
    const editMainButton = document.getElementById("editMainButton");
    const cancelMainButton = document.getElementById("cancelMainButton");
    const saveMainButton = document.getElementById("saveMainButton");
    editMainButton.style.display = edit;
    cancelMainButton.style.display = cancel;
    saveMainButton.style.display = save;
}

/*
Profile password change
 */
function editPassword() {
    switchPasswordFields('block');
    switchPasswordButtons('none', 'block', 'block');
}

function cancelPasswordEdit() {
    switchPasswordFields('none');
    clearPasswordFields();
    switchPasswordButtons('block', 'none', 'none');
}

async function savePasswordEdit() {
    if ($("#passwordForm").valid()) {
        const currentPassword = document.getElementById("currentPassword");
        const newPassword = document.getElementById("newPassword");
        let edit = {
            currentPassword: currentPassword.value,
            newPassword: newPassword.value
        };
        let call = await fetch('profileSettings/changePassword', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(edit)
        });
        let result = await call.json();

        if(!result.error) toastr.success(result.message);
        else toastr.error(result.message);


        switchPasswordFields('none');
        clearPasswordFields();
        switchPasswordButtons('block', 'none', 'none');
    }
}

function switchPasswordButtons(edit, cancel, save) {
    const editPasswordButton = document.getElementById("editPasswordButton");
    const cancelPasswordButton = document.getElementById("cancelPasswordButton");
    const savePasswordButton = document.getElementById("savePasswordButton");
    editPasswordButton.style.display = edit;
    cancelPasswordButton.style.display = cancel;
    savePasswordButton.style.display = save;
}

function switchPasswordFields(display) {
    const currentPassword = document.getElementById("currentPassword");
    const newPassword = document.getElementById("newPassword");
    const currentPasswordLabel = document.getElementById("currentPasswordLabel");
    const newPasswordLabel = document.getElementById("newPasswordLabel");

    currentPassword.style.display = display;
    newPassword.style.display = display;
    currentPasswordLabel.style.display = display;
    newPasswordLabel.style.display = display;

}

function clearPasswordFields() {
    const currentPassword = document.getElementById("currentPassword");
    const newPassword = document.getElementById("newPassword");

    currentPassword.value = null;
    newPassword.value = null;
}

/*
Addresses functions
 */
async function editAddress(id) {
    clearNewAddressForm();
    document.getElementById("newAddressForm").style.display = 'block';
    document.getElementById("addNewAddressButton").style.display = 'none';
    document.getElementById("saveEditAddressButton").style.display = 'block';
    document.getElementById("cancelAddressButton").style.display = 'block';
    document.getElementById("saveEditAddressButton").setAttribute("onClick", "javascript: saveEditAddress(" + id + ");");
    const countryField = document.getElementById("country");
    const cityField = document.getElementById("city");
    const streetField = document.getElementById("street");
    const buildingField = document.getElementById("building");
    const apartmentField = document.getElementById("apartment");
    const zipcodeField = document.getElementById("zipcode");

    let addressId = {
        id: id
    };
    let call = await fetch('profileSettings/getAddressById', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(addressId)
    });

    let result = await call.json();

    if (!result.error) {
        countryField.value = result.address.country;
        cityField.value = result.address.city;
        streetField.value = result.address.street;
        buildingField.value = result.address.building;
        apartmentField.value = result.address.apartment;
        zipcodeField.value = result.address.zip;
    } else {
        toastr.error(result.message);
    }
}

async function deleteAddress(id) {
    const deleteAddressRow = document.getElementById("row" + id);
    let deleteId = {
        id: id
    };
    let call = await fetch('profileSettings/deleteAddress', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(deleteId)
    });
    let result = await call.json();
    if (!result.error) {
        toastr.success(result.message);
        deleteAddressRow.parentNode.removeChild(deleteAddressRow);
    } else {
        toastr.error(result.message);
    }
}

function addAddress() {
    clearNewAddressForm();
    document.getElementById("newAddressForm").style.display = 'block';
    document.getElementById("addNewAddressButton").style.display = 'none';
    document.getElementById("saveNewAddressButton").style.display = 'block';
    document.getElementById("cancelAddressButton").style.display = 'block';
}

function cancelAddress() {
    clearNewAddressForm();
    document.getElementById("newAddressForm").style.display = 'none';
    document.getElementById("addNewAddressButton").style.display = 'block';
    document.getElementById("cancelAddressButton").style.display = 'none';
    document.getElementById("saveEditAddressButton").style.display = 'none';
    document.getElementById("saveNewAddressButton").style.display = 'none';

}

async function saveNewAddress() {
    if ($("#addressesForm").valid()) {
        const countryField = document.getElementById("country");
        const cityField = document.getElementById("city");
        const streetField = document.getElementById("street");
        const buildingField = document.getElementById("building");
        const apartmentField = document.getElementById("apartment");
        const zipcodeField = document.getElementById("zipcode");

        let newAddress = {
            country: countryField.value,
            city: cityField.value,
            street: streetField.value,
            building: buildingField.value,
            apartment: apartmentField.value,
            zip: zipcodeField.value
        };
        let call = await fetch('profileSettings/saveNewAddress', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(newAddress)
        });

        let result = await call.json();

        if (!result.error) {
            toastr.success(result.message);
            cancelAddress();
            document.location.reload();

        } else {
            toastr.error(result.message);
            cancelAddress();
        }
    }
}

async function saveEditAddress(id) {
    if ($("#addressesForm").valid()) {
        const countryField = document.getElementById("country");
        const cityField = document.getElementById("city");
        const streetField = document.getElementById("street");
        const buildingField = document.getElementById("building");
        const apartmentField = document.getElementById("apartment");
        const zipcodeField = document.getElementById("zipcode");
        const table = document.getElementById("addressTable");
        const deleteAddressRow = document.getElementById("row" + id);
        let edit = {
            id: id,
            country: countryField.value,
            city: cityField.value,
            street: streetField.value,
            building: buildingField.value,
            apartment: apartmentField.value,
            zip: zipcodeField.value
        };
        let call = await fetch('profileSettings/saveEditAddress', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(edit)
        });

        let result = await call.json();

        if (!result.error) {
            deleteAddressRow.parentNode.removeChild(deleteAddressRow);
            var NewRow = table.insertRow(-1);
            NewRow.id = "row" + id;
            var Newcell1 = NewRow.insertCell(0);
            var Newcell2 = NewRow.insertCell(1);
            var Newcell3 = NewRow.insertCell(2);
            var Newcell4 = NewRow.insertCell(3);
            var Newcell5 = NewRow.insertCell(4);
            var Newcell6 = NewRow.insertCell(5);
            var Newcell7 = NewRow.insertCell(6);
            var Newcell8 = NewRow.insertCell(7);
            Newcell1.innerHTML = result.address.country;
            Newcell2.innerHTML = result.address.city;
            Newcell3.innerHTML = result.address.street;
            Newcell4.innerHTML = result.address.building;
            Newcell5.innerHTML = result.address.apartment;
            Newcell6.innerHTML = result.address.zip;
            Newcell7.innerHTML = "<button type=\"button\" class=\"row btn btn-primary\" onclick=\"editAddress('" + id + "');\">Edit</button>";
            Newcell8.innerHTML = "<button type=\"button\" class=\"row btn btn-danger\" onclick=\"deleteAddress('" + id + "');\">Delete</button>";

            cancelAddress();
            toastr.success(result.message);
        } else {
            toastr.error(result.message);
        }
    }
}

function clearNewAddressForm() {
    document.getElementById("country").value = null;
    document.getElementById("city").value = null;
    document.getElementById("street").value = null;
    document.getElementById("building").value = null;
    document.getElementById("zipcode").value = null;
    document.getElementById("apartment").value = null;
}

/*
Books page functions
 */
function addMore(id) {
    const quantity = document.getElementById('quantity' + id);
    if (quantity.textContent < 5) quantity.textContent = parseInt(quantity.textContent) + 1;
}

function addLess(id) {
    const quantity = document.getElementById('quantity' + id);
    if (quantity.textContent > 1) quantity.textContent = quantity.textContent - 1;
}

async function addToCart(id) {
    const quantity = document.getElementById('quantity' + id);
    let addItem = {
        id: id,
        quantity: quantity.textContent
    };

    let call = await fetch("/addToCart", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(addItem)
    });

    let result = await call.json();
    toastr.success(result.message);
}

/*
Cart page functions
 */
async function deleteFromCart(id) {
    const deleteRow = document.getElementById("row" + id);
    const totalField = document.getElementById("total");
    const table = document.getElementById("table");
    const div = document.getElementById("div");
    let deleteId = {
        id: id
    };
    let call = await fetch("/deleteFromCart", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(deleteId)
    });

    let result = await call.json();

    if (!result.error) {
        toastr.success(result.message);
        deleteRow.parentNode.removeChild(deleteRow);
        totalField.textContent = result.total + "$";
        if (result.total === 0) {
            table.style.display = 'none';
            div.innerHTML = "<h3>Your cart is empty.</h3>"
        }
    } else {
        toastr.error(result.message);
    }

}

async function toCheckOut() {
    let call = await fetch("/checkStocks", {
        method: 'POST',
        headers: {
            'Accept': 'application/json'
        },
    });
    let result = await call.json();
    if(!result.error){
        document.location.href = '/checkout';
    }else{
        toastr.error (result.message);
    }
}

/*
Checkout page functions
 */
async function toDelivery() {
    showDivs('block', 'block', 'none', 'none', 'none');
}

async function toPayment() {
    showDivs('block', 'none', 'none', 'block', 'none');
}

function onChangePayment() {
    let payment = document.getElementById("paymentMethod");
    let creditCardForm = document.getElementById("creditCardForm");
    let finishOrderCreditButton = document.getElementById("finishOrderCreditButton");
    let finishOrderCashButton = document.getElementById("finishOrderCashButton");

    if (payment.value === "CREDIT") {
        creditCardForm.style.display = 'block';
        finishOrderCashButton.style.display = 'none';
        finishOrderCreditButton.style.display = 'block';
    } else {
        creditCardForm.style.display = 'none';
        finishOrderCashButton.style.display = 'block';
        finishOrderCreditButton.style.display = 'none';
    }
}

function showDivs(cartDiv, deliveryDiv, chosenAddressDiv, paymentDiv, finalDiv) {
    let cart = document.getElementById("cartDiv");
    let delivery = document.getElementById("deliveryDiv");
    let payment = document.getElementById("paymentDiv");
    let final = document.getElementById("finalDiv");
    let chosen = document.getElementById("addressChosenDiv");

    cart.style.display = cartDiv;
    delivery.style.display = deliveryDiv;
    payment.style.display = paymentDiv;
    final.style.display = finalDiv;
    chosen.style.display = chosenAddressDiv;
}

function chooseAddress(addressId) {
    showDivs('block', 'none', 'block', 'none', 'none');
    document.getElementById("hiddenAddressIdForDto").value = addressId;
    let chosenTable = document.getElementById("choosenAddressTable");
    for (let i = 1; i < chosenTable.rows.length; i++) {
        chosenTable.deleteRow(i);
    }
    let chosenRow = document.getElementById("row" + addressId);
    let copy = chosenRow.cloneNode(true);
    chosenTable.appendChild(copy);
    chosenTable.rows[1].deleteCell(6);
}

function finishOrderCredit() {
    if ($("#creditCardForm").valid()) {
        document.getElementById("hiddenCreditCardOwner").value = document.getElementById("owner").value;
        document.getElementById("hiddenCreditCardCvv").value = document.getElementById("cvv").value;
        document.getElementById("hiddenCreditCardNumber").value = document.getElementById("cardNumber").value;
        document.getElementById("hiddenCreditCardDateMonth").value = document.getElementById("dateMonth").value;
        document.getElementById("hiddenCreditCardDateYear").value = document.getElementById("dateYear").value;
        finishOrder();
    }
}

async function finishOrder() {
    document.getElementById("hiddenPaymentMethod").value = document.getElementById("paymentMethod").value;
    document.getElementById("hiddenDeliveryMethod").value = document.getElementById("deliveryMethod").value;

    let orderInfo = {
        cardNumber: document.getElementById("hiddenCreditCardNumber").value,
        cardOwner: document.getElementById("hiddenCreditCardOwner").value,
        addressId: document.getElementById("hiddenAddressIdForDto").value,
        creditCardDateMonth: document.getElementById("hiddenCreditCardDateMonth").value,
        creditCardDateYear: document.getElementById("hiddenCreditCardDateYear").value,
        creditCardCvv: document.getElementById("hiddenCreditCardCvv").value,
        paymentMethod: document.getElementById("hiddenPaymentMethod").value,
        deliveryMethod: document.getElementById("hiddenDeliveryMethod").value,
    };

    let call = await fetch("/checkout/processOrder", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(orderInfo)
    });

    let result = await call.json();

    if(!result.error){
        toastr.success(result.message);
        showDivs('block', 'none', 'none', 'none', 'block');
    }else{
        toastr.error(result.message);
    }
}

/*
Orders Page functions
 */
async function repeatOrder(orderId){
    let call = await fetch("/repeatOrder", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(orderId)
    });

    let result = await call.json();
    if(!result.error) toastr.success(result.message);
    else toastr.error(result.message);

}
/*
Admins page functions
 */
async function onChangePaymentStatus(orderId){
    let paymentStatusSelector = document.getElementById("paymentStatus" + orderId);
    let newStatus = paymentStatusSelector.options[paymentStatusSelector.selectedIndex].text;

    let paymentStatusDto = {
        orderId: orderId,
        newStatusName: newStatus,
    };

    let call = await fetch("/adminPage/changePaymentStatus", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(paymentStatusDto)
    });

    let result = await call.json();

    if(!result.error){
        toastr.success(result.message);
    }else{
        toastr.error(result.message);
    }
}
async function onChangeOrderStatus(orderId){
    let orderStatusSelector = document.getElementById("orderStatus" + orderId);
    let newStatus = orderStatusSelector.options[orderStatusSelector.selectedIndex].text;

    let orderStatusDto = {
        orderId: orderId,
        newStatusName: newStatus,
    };

    let call = await fetch("/adminPage/changeOrderStatus", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(orderStatusDto)
    });

    let result = await call.json();

    if(!result.error){
        toastr.success(result.message);
    }else{
        toastr.error(result.message);
    }
}
function changeStats(){
    let usersTable = document.getElementById("topUsers");
    let booksTable = document.getElementById("topBooks");
    let button = document.getElementById("switchButton");
    if(usersTable.style.display === 'block'){
        usersTable.style.display = 'none';
        booksTable.style.display = 'block';
        button.textContent = 'To top users';
    }
    else{
        usersTable.style.display = 'block';
        booksTable.style.display = 'none';
        button.textContent = 'To top books';
    }
}
function sortByName(tableColumn, tableId) {
    let table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById(tableId);
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[tableColumn];
            y = rows[i + 1].getElementsByTagName("TD")[tableColumn];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount ++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}
function sortByValue(tableColumn, tableId){
    let table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById(tableId);
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[tableColumn];
            y = rows[i + 1].getElementsByTagName("TD")[tableColumn];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                if (parseFloat(x.textContent) > parseFloat(y.textContent)) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (parseFloat(x.textContent) < parseFloat(y.textContent)) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount ++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}
