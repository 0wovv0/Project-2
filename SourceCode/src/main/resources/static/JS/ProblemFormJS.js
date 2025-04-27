function addExample() {
    var examplesContainer = document.getElementById("examplesContainer");

    // Create a new example group
    var exampleGroup = document.createElement("div");
    exampleGroup.classList.add("example-group", "form-group");

    // Example Input
    var labelInput = document.createElement("label");
    labelInput.textContent = "Example Input:";
    var inputInput = document.createElement("textarea");
    inputInput.classList.add("form-control", "example-input");
    inputInput.setAttribute("name", "exampleInput[]");
    inputInput.setAttribute("rows", "2");
    inputInput.required = true; // Bắt buộc điền

    // Example Output
    var labelOutput = document.createElement("label");
    labelOutput.textContent = "Example Output:";
    var inputOutput = document.createElement("textarea");
    inputOutput.classList.add("form-control", "example-output");
    inputOutput.setAttribute("name", "exampleOutput[]");
    inputOutput.setAttribute("rows", "2");
    inputOutput.required = true; // Bắt buộc điền

    // Delete button
    var deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.classList.add("btn", "btn-danger");
    deleteButton.textContent = "- Delete Example";
    deleteButton.onclick = function() {
        exampleGroup.remove();
    };

    // Append elements to example group
    exampleGroup.appendChild(labelInput);
    exampleGroup.appendChild(inputInput);
    exampleGroup.appendChild(labelOutput);
    exampleGroup.appendChild(inputOutput);
    exampleGroup.appendChild(deleteButton);

    // Append example group to container
    examplesContainer.appendChild(exampleGroup);
}

function deleteExample(button) {
    var exampleGroup = button.parentElement;
    exampleGroup.remove();
}

function addTestcase() {
    var testcasesContainer = document.getElementById("testcasesContainer");

    // Create a new testcase group
    var testcaseGroup = document.createElement("div");
    testcaseGroup.classList.add("testcase-group", "form-group");

    // Testcase Input
    var labelInput = document.createElement("label");
    labelInput.textContent = "Testcase Input:";
    var inputInput = document.createElement("textarea");
    inputInput.classList.add("form-control", "testcase-input");
    inputInput.setAttribute("name", "testcaseInput[]");
    inputInput.setAttribute("rows", "2");
    inputInput.required = true; // Bắt buộc điền

    // Testcase Output
    var labelOutput = document.createElement("label");
    labelOutput.textContent = "Testcase Output:";
    var inputOutput = document.createElement("textarea");
    inputOutput.classList.add("form-control", "testcase-output");
    inputOutput.setAttribute("name", "testcaseOutput[]");
    inputOutput.setAttribute("rows", "2");
    inputOutput.required = true; // Bắt buộc điền

    // Testcase Valid Time Running
    var labelValidTime = document.createElement("label");
    labelValidTime.textContent = "Valid Time Running:";
    var inputValidTime = document.createElement("input");
    inputValidTime.type = "number";
    inputValidTime.classList.add("form-control", "testcase-valid-time");
    inputValidTime.setAttribute("name", "testcaseValidTime[]");
    inputValidTime.required = true; // Bắt buộc điền

    // Delete button
    var deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.classList.add("btn", "btn-danger");
    deleteButton.textContent = "- Delete Testcase";
    deleteButton.onclick = function() {
        testcaseGroup.remove();
    };

    // Append elements to testcase group
    testcaseGroup.appendChild(labelInput);
    testcaseGroup.appendChild(inputInput);
    testcaseGroup.appendChild(labelOutput);
    testcaseGroup.appendChild(inputOutput);
    testcaseGroup.appendChild(labelValidTime);
    testcaseGroup.appendChild(inputValidTime);
    testcaseGroup.appendChild(deleteButton);

    // Append testcase group to container
    testcasesContainer.appendChild(testcaseGroup);
}

function deleteTestcase(button) {
    var testcaseGroup = button.parentElement;
    testcaseGroup.remove();
}

function validateForm() {
    var isValid = true;
    var form = document.getElementById("problemForm");

    // Check all required fields
    var requiredFields = form.querySelectorAll("[required]");
    requiredFields.forEach(function(field) {
        if (!field.value.trim()) {
            isValid = false;
            field.classList.add("is-invalid");
        } else {
            field.classList.remove("is-invalid");
        }
    });

    if (!isValid) {
        alert("Please fill out all required fields.");
    }

    return isValid;
}


document.addEventListener("DOMContentLoaded", function() {
    var problemForm = document.getElementById("problemForm");

    problemForm.addEventListener("submit", function(event) {
        event.preventDefault(); // Ngăn chặn form submit mặc định

        var formData = new FormData(problemForm);

        fetch(problemForm.action, {
            method: problemForm.method,
            body: formData
        })
            .then(function(response) {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(function(data) {
                // Xử lý phản hồi từ server thành công
                console.log("Server response:", data);
                // Ví dụ: Hiển thị thông báo cho người dùng
                alert("Problem added successfully!");
                // Sau khi xử lý xong, có thể chuyển hướng hoặc làm gì đó tiếp theo
                // window.location.href = "/redirect-url"; // Chuyển hướng đến URL khác
            })
            .catch(function(error) {
                // Xử lý lỗi
                console.error('There was a problem with the fetch operation:', error);
                alert("Error: Problem could not be added.");
            });
    });
});

