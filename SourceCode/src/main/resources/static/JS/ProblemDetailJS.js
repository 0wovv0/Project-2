document.addEventListener('DOMContentLoaded', function () {
    const resizeBar = document.getElementById('resize-bar');
    const leftPanel = document.querySelector('.split.left');
    const rightPanel = document.querySelector('.split.right');
    let isResizing = false;

    resizeBar.addEventListener('mousedown', function (e) {
        e.preventDefault();
        isResizing = true;
        document.addEventListener('mousemove', handleMouseMove);
        document.addEventListener('mouseup', stopResize);
    });

    function handleMouseMove(e) {
        if (!isResizing) {
            return;
        }
        const leftPanelWidth = e.clientX - leftPanel.getBoundingClientRect().left;
        const rightPanelWidth = rightPanel.getBoundingClientRect().right - e.clientX;
        leftPanel.style.width = `${leftPanelWidth}px`;
        rightPanel.style.width = `${rightPanelWidth}px`;
    }

    function stopResize() {
        if (isResizing) {
            document.removeEventListener('mousemove', handleMouseMove);
            document.removeEventListener('mouseup', stopResize);
            isResizing = false;
        }
    }
});

let timerInterval;
let timerRunning = false;
let seconds = 0;
let minutes = 0;
let hours = 0;

function updateTime() {
    seconds++;
    if (seconds === 60) {
        seconds = 0;
        minutes++;
        if (minutes === 60) {
            minutes = 0;
            hours++;
        }
    }
    document.getElementById('timer').textContent =
        (hours < 10 ? '0' : '') + hours + ':' +
        (minutes < 10 ? '0' : '') + minutes + ':' +
        (seconds < 10 ? '0' : '') + seconds;
}

function toggleTimer() {
    const timerBtn = document.getElementById('timer-btn');
    const timerIcon = document.getElementById('timer-icon');
    if (timerInterval) {
        clearInterval(timerInterval);
        timerInterval = null;
        timerIcon.classList.remove('fa-pause'); // Loại bỏ biểu tượng Pause
        timerIcon.classList.add('fa-play'); // Thêm biểu tượng Play
        timerBtn.title = 'Start'; // Thay đổi tiêu đề cho tiện tham chiếu
    } else {
        timerInterval = setInterval(updateTime, 1000);
        timerIcon.classList.remove('fa-play'); // Loại bỏ biểu tượng Play
        timerIcon.classList.add('fa-pause'); // Thêm biểu tượng Pause
        timerBtn.title = 'Pause'; // Thay đổi tiêu đề cho tiện tham chiếu
    }
}


function startTimer() {
    timerRunning = true;
    timerInterval = setInterval(updateTime, 1000);
}

function pauseTimer() {
    timerRunning = false;
    clearInterval(timerInterval);
}

document.addEventListener('DOMContentLoaded', function() {
    toggleTimer(); // Tự động bắt đầu đồng hồ khi trang được tải
});

function resetTimer() {
    clearInterval(timerInterval);
    timerRunning = false;
    seconds = 0;
    minutes = 0;
    hours = 0;
    document.getElementById("timer").innerText = '00:00:00';
    const timerIcon = document.getElementById('timer-icon');
    timerIcon.classList.remove('fa-pause');
    timerIcon.classList.add('fa-play');
}

function loadProblemData(problemId) {
    fetch(`/api/problem/${problemId}`)
        .then(response => {
            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Không tồn tại');
                } else {
                    throw new Error('Có lỗi xảy ra');
                }
            }
            return response.json();
        })
        .then(data => {
            document.querySelector('.description p').innerText = 'Description: ' + data.problemEntity.description;

            let examplesHtml = '';
            data.problemEntity.examples.forEach((example, index) => {
                examplesHtml += `
                    <div class="section">
                        <h3>Example ${index + 1}:</h3>
                        <pre>
                            <div><strong class="example-label">Input:</strong></div>
                            <div class="example-value">${example.input}</div><br>
                            <div><strong class="example-label">Output:</strong></div>
                            <div class="example-value">${example.output}</div>
                        </pre>
                    </div>
                `;
            });
            document.querySelector('.examples-container').innerHTML = examplesHtml;

            let constraintsHtml = '';
            data.problemEntity.constraints.split('\n').forEach(line => {
                constraintsHtml += `<li><span>${line}</span></li>`;
            });
            document.querySelector('.constraints-list').innerHTML = constraintsHtml;
        })
        .catch(error => {
            document.querySelector('.description p').innerText = error.message;
            document.querySelector('.examples-container').innerHTML = '';
            document.querySelector('.constraints-list').innerHTML = '';
        });
}

document.getElementById('fetchDataButton').addEventListener('click', function() {
    fetch(`/api/getSubmition?classID=${classID}&problemID=${problemID}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Chuyển đổi dữ liệu nhận được thành đối tượng JSON
        })
        .then(data => {
            // Xử lý dữ liệu ở đây
            console.log('Data from backend:', data);

            // Ví dụ: hiển thị dữ liệu lên giao diện
            displayData(data);
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
});


function displayData(data) {
    // Lấy thẻ div có ID là 'content' và 'new-content'
    var contentElement = document.getElementById('content');
    var newContentElement = document.getElementById('new-content');

    // Ẩn 'content', hiện 'new-content'
    if (contentElement) {
        contentElement.classList.remove('visible');
        contentElement.classList.add('hidden');
    }
    if (newContentElement) {
        newContentElement.classList.remove('hidden');
        newContentElement.classList.add('visible');
    }

    // Tạo thẻ div để chứa bảng
    var tableContainer = document.createElement('div');
    tableContainer.classList.add('table-container');

    // Xóa nội dung cũ của 'new-content'
    if (newContentElement) {
        newContentElement.innerHTML = '';
    }

    // Tạo bảng
    var table = document.createElement('table');
    var thead = document.createElement('thead');
    var tbody = document.createElement('tbody');

    // Tạo header của bảng
    var headerRow = document.createElement('tr');
    var headers = ['ID', 'Language', 'Accepted', 'Submitted At', 'Running Time'];

    headers.forEach(headerText => {
        var th = document.createElement('th');
        th.textContent = headerText;
        headerRow.appendChild(th);
    });

    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Tạo các hàng dữ liệu
    data.forEach(item => {
        var row = document.createElement('tr');

        // Ô ID
        var idCell = document.createElement('td');
        idCell.textContent = item.id;
        idCell.style.cursor = 'pointer'; // Đặt con trỏ chuột thành dấu nhọn khi di chuột qua idCell
        idCell.addEventListener('click', function() {
            handleClickAndFetchData(item.id);
        });
        row.appendChild(idCell);

        var languageCell = document.createElement('td');
        languageCell.textContent = item.language;
        row.appendChild(languageCell);

        var acceptedCell = document.createElement('td');
        acceptedCell.textContent = item.accepted;
        if (item.accepted === 'ACCEPTED') {
            acceptedCell.style.color = 'green'; // Đổi màu xanh lá cho ACCEPTED
        } else {
            acceptedCell.style.color = 'red'; // Đổi màu đỏ cho các giá trị khác
        }
        row.appendChild(acceptedCell);

        var submittedAtCell = document.createElement('td');
        submittedAtCell.textContent = item.submitted_at;
        row.appendChild(submittedAtCell);

        var runningTimeCell = document.createElement('td');
        runningTimeCell.textContent = item.running_time;
        row.appendChild(runningTimeCell);

        tbody.appendChild(row);
    });

    table.appendChild(tbody);
    tableContainer.appendChild(table);

    // Thêm tableContainer vào newContentElement
    if (newContentElement) {
        newContentElement.appendChild(tableContainer);
    }
}


function showOldContent() {
    document.getElementById('content').classList.remove('hidden');
    document.getElementById('content').classList.add('visible');
    document.getElementById('new-content').classList.remove('visible');
    document.getElementById('new-content').classList.add('hidden');
}

document.getElementById('restore').addEventListener('click', function() {
    showOldContent();
});

function handleClickAndFetchData(itemId) {
    // Gọi API để lấy dữ liệu chi tiết với id tương ứng
    fetch(`/api/getSubmition/${itemId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Data from API:', data);
            editor.setValue(data.source);
            if (data.results != null) {
                displayProblemDetail(data.results);
            }
        })
        .catch(error => {
            editor.setValue("#include <iostream>\nusing namespace std;\n\nint main() {\n\t// Write your code here\n\treturn 0;\n}");
            alert('Error fetching data. Please try again later.');
        });

}

function displayProblemDetail(data) {
    var newContentElement = document.getElementById('new-content');
    if (!newContentElement) return;

    // Xóa nội dung cũ của 'new-content'
    newContentElement.innerHTML = '';

    // Tạo bảng
    var tableContainer = document.createElement('div');
    tableContainer.classList.add('table-container');

    var table = document.createElement('table');
    var thead = document.createElement('thead');
    var tbody = document.createElement('tbody');

    // Tạo header của bảng
    var headerRow = document.createElement('tr');
    var headers = ['Show detail', 'Accepted', 'Running Time'];

    headers.forEach(headerText => {
        var th = document.createElement('th');
        th.textContent = headerText;
        headerRow.appendChild(th);
    });

    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Tạo các hàng dữ liệu
    data.forEach(item => {
        var row = document.createElement('tr');

        // Ô ID
        var idCell = document.createElement('td');
        idCell.textContent = "Details";
        idCell.style.cursor = 'pointer';
        idCell.addEventListener('click', function() {
            openPopup(item.input, item.output, item.expectedOutput);
        });
        row.appendChild(idCell);

        var acceptedCell = document.createElement('td');
        acceptedCell.textContent = item.accepted;
        if (item.accepted === 'ACCEPTED') {
            acceptedCell.style.color = 'green';
        } else {
            acceptedCell.style.color = 'red';
        }
        row.appendChild(acceptedCell);

        var languageCell = document.createElement('td');
        languageCell.textContent = item.runningTime;
        row.appendChild(languageCell);

        tbody.appendChild(row);

        // Thêm đường kẻ ngang cho hàng
        row.style.borderBottom = '1px solid #ddd';
    });

    table.appendChild(tbody);
    tableContainer.appendChild(table);

    // Thêm tableContainer vào newContentElement
    newContentElement.appendChild(tableContainer);
}


function openPopup(input, output, expectedOutput) {
    // Insert content into the popup
    document.getElementById('inputContent').textContent = input;
    document.getElementById('outputContent').textContent = output;
    document.getElementById('expectedOutputContent').textContent = expectedOutput;

    // Show the popup
    var popup = document.getElementById('popup');
    popup.style.display = 'block';
}

// Đóng pop-up
function closePopup() {
    var popup = document.getElementById('popup');
    popup.style.display = 'none';
}
function copyInput() {
    var inputContent = document.getElementById('inputContent').innerText;
    navigator.clipboard.writeText(inputContent).then(function() {
        console.log('Text copied to clipboard');
    }, function(err) {
        console.error('Unable to copy text to clipboard: ', err);
    });
}