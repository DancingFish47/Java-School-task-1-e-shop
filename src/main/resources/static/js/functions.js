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