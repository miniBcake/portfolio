document.getElementById('seniorJoin-agreeAll').addEventListener('change', function() {
    var checkboxes = document.querySelectorAll('.seniorJoin-agree input[type="checkbox"]');
    checkboxes.forEach(function(checkbox) {
        checkbox.checked = document.getElementById('seniorJoin-agreeAll').checked;
    });
});

var agreeCheckboxes = document.querySelectorAll('.seniorJoin-agree input[type="checkbox"]');
agreeCheckboxes.forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        var allChecked = true;
        agreeCheckboxes.forEach(function(checkbox) {
            if (!checkbox.checked) {
                allChecked = false;
            }
        });
        document.getElementById('seniorJoin-agreeAll').checked = allChecked;
    });
});
function checkFormValidity() {
    var inputs = document.querySelectorAll('input[type="text"], input[type="password"]');
    var checkboxes = document.querySelectorAll('.seniorJoin-agree input[type="checkbox"]');
    var submitButton = document.querySelector('.seniorJoin-joinButton');

    var allFilled = true;
    inputs.forEach(function(input) {
        if (input.value.trim() === '') {
            allFilled = false;
        }
    });

    var allChecked = true;
    checkboxes.forEach(function(checkbox) {
        if (!checkbox.checked) {
            allChecked = false;
        }
    });

    // If all inputs are filled and all checkboxes are checked, enable the submit button and change its color
    if (allFilled && allChecked) {
        submitButton.disabled = false;
        submitButton.style.backgroundColor = '#FE7A15';
    } else {
        submitButton.disabled = true;
        submitButton.style.backgroundColor = '#DCDCDC';
    }
}

// Call the checkFormValidity function whenever there is a change in input fields or checkboxes
var inputs = document.querySelectorAll('input[type="text"], input[type="password"]');
var checkboxes = document.querySelectorAll('.seniorJoin-agree input[type="checkbox"]');
inputs.forEach(function(input) {
    input.addEventListener('input', checkFormValidity);
});
checkboxes.forEach(function(checkbox) {
    checkbox.addEventListener('change', checkFormValidity);
});

// Initially disable the submit button
checkFormValidity();