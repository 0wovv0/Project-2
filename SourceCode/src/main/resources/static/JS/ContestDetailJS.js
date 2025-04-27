function toggleMenu(check) {
    let sidebar = document.getElementById("sidebar");
    if (check) {
        sidebar.style.left = "0";
    } else {
        sidebar.style.left = "-100%";
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const data = Array.from({ length: 100 }, (_, i) => ({ id: i + 1, name: `Name ${i + 1}`, age: 20 + (i % 50) }));
    const perPage = 10;
    let currentPage = 1;

    function renderTable(page) {
        const start = (page - 1) * perPage;
        const end = start + perPage;
        const slicedData = data.slice(start, end);

        const tableBody = document.getElementById('problems-table').getElementsByTagName('tbody')[0];
        tableBody.innerHTML = '';
        slicedData.forEach(item => {
            const row = tableBody.insertRow();
            row.insertCell(0).textContent = item.id;
            row.insertCell(1).textContent = item.name;
            row.insertCell(2).textContent = item.age;
        });
    }

    function setupPagination(totalItems, itemsPerPage) {
        const pageCount = Math.ceil(totalItems / itemsPerPage);
        const wrapper = document.getElementById('pagination-wrapper');
        wrapper.innerHTML = '';

        for (let i = 1; i <= pageCount; i++) {
            const btn = document.createElement('button');
            btn.textContent = i;
            btn.className = 'pagination-btn';
            btn.addEventListener('click', () => {
                currentPage = i;
                renderTable(currentPage);
                updateActiveBtn();
            });
            wrapper.appendChild(btn);
        }
    }

    function updateActiveBtn() {
        const buttons = document.querySelectorAll('.pagination-btn');
        buttons.forEach(button => {
            if (Number(button.textContent) === currentPage) {
                button.classList.add('active');
            } else {
                button.classList.remove('active');
            }
        });
    }

    renderTable(currentPage);
    setupPagination(data.length, perPage);
    updateActiveBtn();
});

