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
        headers : {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    let result = await call.json();
    if(!result.error) {
        firstNameField.value = result.user.firstName;
        lastNameField.value = result.user.lastName;
        birthdateField.value = result.user.birthdate;
    } else {
        alert(result.message);
    }
    switchMainSettingFields(true);
    switchMainSettingButtons('block', 'none', 'none');
}
async function saveMainEdit() {
    const firstNameField = document.getElementById("firstName");
    const lastNameField = document.getElementById("lastName");
    const birthdateField = document.getElementById("birthdate");
    let edit = {
        firstname: firstNameField.value,
        lastname: lastNameField.value,
        birthdate: birthdateField.value
    };
    //save data to backend, replca fields with new data
    let call = await fetch('profileSettings/saveMainEdit', {
        method: 'POST',
        headers : {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(edit)
    });
    let result = await call.json();
    if(!result.error) {
        firstNameField.value = result.user.firstName;
        lastNameField.value = result.user.lastName;
        birthdateField.value = result.user.birthdate;
    } else {
        alert(result.message);
    }
    switchMainSettingFields(true);
    switchMainSettingButtons('block', 'none', 'none');

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
    const currentPassword = document.getElementById("currentPassword");
    const newPassword = document.getElementById("newPassword");
    let edit = {
        currentPassword: currentPassword.value,
        newPassword: newPassword.value
    };
    let call = await fetch('profileSettings/changePassword', {
        method: 'POST',
        headers : {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(edit)
    });
    let result = await call.json();

    alert(result.message);

    switchPasswordFields('none');
    clearPasswordFields();
    switchPasswordButtons('block', 'none', 'none');
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
    document.getElementById("saveEditAddressButton").setAttribute( "onClick", "javascript: saveEditAddress("+ id + ");" );
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
        headers : {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(addressId)
    });

    let result = await call.json();

    if(!result.error){
        countryField.value = result.address.country;
        cityField.value = result.address.city;
        streetField.value = result.address.street;
        buildingField.value = result.address.building;
        apartmentField.value = result.address.apartment;
        zipcodeField.value = result.address.zip;
    }else{
        alert(result.message);
    }
}
async function deleteAddress(id) {
    const deleteAddressDiv = document.getElementById("div" + id);
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
        alert(result.message);
        deleteAddressDiv.style.display = 'none';
    } else {
        alert(result.message);
    }
}
function addAddress() {
    clearNewAddressForm();
    document.getElementById("newAddressForm").style.display = 'block';
    document.getElementById("addNewAddressButton").style.display = 'none';
    document.getElementById("saveNewAddressButton").style.display = 'block';
    document.getElementById("cancelAddressButton").style.display = 'block';
}
function cancelAddress(){
    clearNewAddressForm();
    document.getElementById("newAddressForm").style.display = 'none';
    document.getElementById("addNewAddressButton").style.display = 'block';
    document.getElementById("cancelAddressButton").style.display = 'none';
    document.getElementById("saveEditAddressButton").style.display = 'none';
    document.getElementById("saveNewAddressButton").style.display = 'none';

}
async function saveNewAddress() {
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
        alert(result.message);
        cancelAddress();
        document.location.reload();

    } else {
        alert(result.message);
        cancelAddress();
    }
}
async function saveEditAddress(id) {
    const countryField = document.getElementById("country");
    const cityField = document.getElementById("city");
    const streetField = document.getElementById("street");
    const buildingField = document.getElementById("building");
    const apartmentField = document.getElementById("apartment");
    const zipcodeField = document.getElementById("zipcode");
    const editingAddressField = document.getElementById('input' + id);
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
        editingAddressField.value = result.address.country + ", " + result.address.city + ", " +
                                    result.address.street + ", " + result.address.building  + ", " +
                                    result.address.apartment  + ", " + result.address.zip;
        cancelAddress();
        alert(result.message);
    } else {
        alert(result.message);
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
function addMore(id){
    const quantity = document.getElementById('quantity' + id);
    if (quantity.textContent<10) quantity.textContent = parseInt(quantity.textContent) + 1;
}
function addLess(id) {
    const quantity = document.getElementById('quantity' + id);
    if (quantity.textContent>1) quantity.textContent = quantity.textContent - 1;
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

    alert(result.message);
}
