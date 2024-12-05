document.addEventListener('DOMContentLoaded', function () {
    const checkinInput = document.getElementById('date-checkin');
    const checkoutInput = document.getElementById('date-checkout');
    const totalPriceElement = document.getElementById('total-price');

    function calculateTotalPrice() {
        const startDate = new Date(checkinInput.value);
        const endDate = new Date(checkoutInput.value);

        if (checkinInput.value && checkoutInput.value) {
            const days = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));
            const basePrice = 15000; // Base price per day
            const total = days * basePrice;

            totalPriceElement.textContent = `${total.toLocaleString()}₸`;
        }
    }

    flatpickr(checkinInput, {
        onChange: calculateTotalPrice,
        minDate: "today"
    });

    flatpickr(checkoutInput, {
        onChange: calculateTotalPrice,
        minDate: "today"
    });
});

function showPopup(message) {
    const popup = document.getElementById('popup');
    const popupMessage = document.getElementById('popup-message');
    popupMessage.textContent = message;
    popup.style.display = 'block';
    popup.querySelector('.popup-content').classList.add('show-popup');
}

function closePopup() {
    const popup = document.getElementById('popup');
    popup.querySelector('.popup-content').classList.remove('show-popup');
    setTimeout(() => {
        popup.style.display = 'none';
    }, 300);
}

document.querySelector('.close-popup').addEventListener('click', closePopup);
document.querySelector('.ok-button').addEventListener('click', closePopup);

document.querySelector(".submit-button").addEventListener("click", async function (event) {
    event.preventDefault();

    const startDate = document.getElementById("date-checkin").value;
    const endDate = document.getElementById("date-checkout").value;
    const adults = parseInt(document.getElementById("adults").value) || 0;
    const children = parseInt(document.getElementById("children").value) || 0;
    const totalPeople = adults + children;

    // Проверка на превышение лимита
    if (totalPeople > 10) {
        showPopup("Ошибка! Максимальное количество людей в одной брони — 10.");
        return;
    }

    const bookingRequest = {
        startDate: startDate,
        endDate: endDate,
        adults: adults,
        children: children,
        totalPeople: totalPeople,
    };

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    try {
        const response = await fetch("/bookings/book", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(bookingRequest)
        });

        if (response.ok) {
            const result = await response.json();
            showPopup("Успех! " + result.message);
        } else {
            const error = await response.json();
            showPopup("Ошибка: " + error.message);
        }
    } catch (error) {
        console.error("Ошибка при создании брони:", error);
        showPopup("Произошла ошибка при создании брони.");
    }
});
