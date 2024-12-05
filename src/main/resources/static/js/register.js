document.addEventListener("DOMContentLoaded", function () {
    const phoneInput = document.getElementById("phone");

    // Добавляем "+7 " при загрузке страницы
    phoneInput.value = "+7 ";

    // Обработка ввода
    phoneInput.addEventListener("input", function () {
        // Если пользователь удаляет "+7", добавляем его обратно
        if (!phoneInput.value.startsWith("+7 ")) {
            phoneInput.value = "+7 ";
        }
    });

    // Блокируем удаление "+7"
    phoneInput.addEventListener("keydown", function (event) {
        if (phoneInput.selectionStart <= 3 && (event.key === "Backspace" || event.key === "Delete")) {
            event.preventDefault();
        }
    });

    // Если поле остаётся пустым, возвращаем "+7 "
    phoneInput.addEventListener("blur", function () {
        if (phoneInput.value === "+7 " || phoneInput.value.trim() === "") {
            phoneInput.value = "+7 ";
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirm_password");
    const errorMessage = document.getElementById("error-message");
    const submitButton = document.getElementById("submit-button");

    function validatePassword() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        // Проверка на минимальную длину и наличие хотя бы одной цифры
        const isPasswordValid = password.length >= 8 && /\d/.test(password);

        // Проверка на совпадение паролей
        const doPasswordsMatch = password === confirmPassword;

        // Если пароли не совпадают или не соответствуют требованиям
        if (!isPasswordValid || !doPasswordsMatch) {
            errorMessage.style.display = "block";
            errorMessage.textContent = !isPasswordValid
                ? "Пароль должен быть не менее 8 символов и содержать хотя бы одну цифру."
                : "Пароли не совпадают.";
            submitButton.disabled = true;
        } else {
            errorMessage.style.display = "none";
            submitButton.disabled = false;
        }
    }

    // Добавляем обработчики событий на ввод в оба поля
    passwordInput.addEventListener("input", validatePassword);
    confirmPasswordInput.addEventListener("input", validatePassword);
});
