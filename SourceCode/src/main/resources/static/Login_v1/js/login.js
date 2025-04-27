function login(event) {
    event.preventDefault(); // Ngăn chặn việc submit mặc định của form
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/login", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Xử lý phản hồi từ máy chủ sau khi đăng nhập thành công
            console.log(xhr.responseText);
        }
    };
    // Gửi dữ liệu biểu mẫu đến máy chủ
    xhr.send("email=" + encodeURIComponent(email) + "&password=" + encodeURIComponent(password));
    console.log("email4: " + email + " password: " + password);
}

$('form').submit(function(event) {
    event.preventDefault(); // Ngăn chặn việc submit mặc định của form
    var formData = $(this).serialize(); // Lấy dữ liệu từ form
    $.post('/login', formData, function(response) {
        // Xử lý kết quả từ server (nếu cần)
    });
});
