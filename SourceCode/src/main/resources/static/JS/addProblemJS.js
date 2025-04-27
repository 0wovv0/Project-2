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


// document.addEventListener("DOMContentLoaded", function() {
//     var problemForm = document.getElementById("problemForm");
//
//     problemForm.addEventListener("submit", function(event) {
//         event.preventDefault(); // Ngăn chặn form submit mặc định
//
//         var formData = new FormData(problemForm);
//
//         fetch(problemForm.action, {
//             method: problemForm.method,
//             body: formData
//         })
//             .then(function(response) {
//                 if (!response.ok) {
//                     throw new Error('Network response was not ok');
//                 }
//                 return response.text();
//             })
//             .then(function(data) {
//                 // Xử lý phản hồi từ server thành công
//                 console.log("Server response:", data);
//                 // Ví dụ: Hiển thị thông báo cho người dùng
//                 alert("Problem added successfully!");
//                 // Sau khi xử lý xong, có thể chuyển hướng hoặc làm gì đó tiếp theo
//                 // window.location.href = "/redirect-url"; // Chuyển hướng đến URL khác
//             })
//             .catch(function(error) {
//                 // Xử lý lỗi
//                 console.error('There was a problem with the fetch operation:', error);
//                 alert("Error: Problem could not be added.");
//             });
//     });
// });

// function addProblem() {
//     window.location.href = "/addNewProblem";
// }

document.addEventListener("DOMContentLoaded", function() {
    const searchBox = document.getElementById("problem-search-box");

    searchBox.addEventListener("keyup", function() {
        const filter = searchBox.value.toLowerCase();
        const table = document.querySelector("table tbody");
        const rows = table.getElementsByTagName("tr");

        for (let i = 0; i < rows.length; i++) {
            let cells = rows[i].getElementsByTagName("td");
            let match = false;

            for (let j = 0; j < cells.length; j++) {
                if (cells[j]) {
                    if (cells[j].textContent.toLowerCase().indexOf(filter) > -1) {
                        match = true;
                        break;
                    }
                }
            }

            rows[i].style.display = match ? "" : "none";
        }
    });

    const getCellValue = (row, index) => row.children[index].innerText || row.children[index].textContent;

    const comparer = (index, asc) => (a, b) => ((v1, v2) =>
            v1 !== '' && v2 !== '' && !isNaN(v1) && !isNaN(v2) ? v1 - v2 : v1.toString().localeCompare(v2)
    )(getCellValue(asc ? a : b, index), getCellValue(asc ? b : a, index));

    document.querySelectorAll('th').forEach(th => th.addEventListener('click', () => {
        const table = th.closest('table');
        Array.from(table.querySelectorAll('tbody > tr'))
            .sort(comparer(Array.from(th.parentNode.children).indexOf(th), this.asc = !this.asc))
            .forEach(tr => table.querySelector('tbody').appendChild(tr) );
    }));
});

function sortTable(column) {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("problemsTable");
    switching = true;

    while (switching) {
        switching = false;
        rows = table.rows;

        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[column];
            y = rows[i + 1].getElementsByTagName("TD")[column];

            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const problemSearchBox = document.getElementById('problem-search-box');

    // Function to handle sorting
    const sortTable = (tableId, columnIndex) => {
        const table = document.getElementById(tableId);
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.querySelectorAll('tr'));

        // Toggle sorting direction
        const asc = table.classList.contains('asc');
        rows.sort(comparer(columnIndex, asc));
        table.classList.toggle('asc');

        // Re-append sorted rows to tbody
        rows.forEach(row => tbody.appendChild(row));
    };

    // Function to compare cell values
    const getCellValue = (row, index) => {
        const cellValue = row.children[index].textContent.trim();
        return isNaN(cellValue) ? cellValue.toLowerCase() : parseFloat(cellValue);
    };

    // Function to compare rows based on column index and sort order
    const comparer = (index, asc) => (a, b) => {
        const valueA = getCellValue(a, index);
        const valueB = getCellValue(b, index);

        return asc ? (valueA > valueB ? 1 : -1) : (valueA < valueB ? 1 : -1);
    };

    // Sort table when header is clicked
    document.querySelectorAll('th').forEach((th, index) => {
        th.addEventListener('click', () => {
            const table = th.closest('table');
            const tableId = table.id;
            const columnIndex = Array.from(th.parentNode.children).indexOf(th);

            sortTable(tableId, columnIndex);
        });
    });

    // Search functionality for Problems table
    problemSearchBox.addEventListener('input', function () {
        const query = problemSearchBox.value.toLowerCase();
        const rows = document.querySelectorAll('#problems-table tbody tr');

        rows.forEach(row => {
            const problemName = row.querySelector('td:nth-child(1) a').textContent.toLowerCase();
            row.style.display = problemName.includes(query) ? '' : 'none';
        });
    });
});

