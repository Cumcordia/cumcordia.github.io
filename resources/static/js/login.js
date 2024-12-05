function showPopup(message) {
    document.getElementById('popup-message').textContent = message;
    document.getElementById('popup').style.display = 'flex';
}

    // Функция для закрытия всплывающего окна
    function closePopup() {
    document.getElementById('popup').style.display = 'none';
}

    // Закрытие окна по нажатию на крестик
    document.querySelector('.close-popup').addEventListener('click', closePopup);