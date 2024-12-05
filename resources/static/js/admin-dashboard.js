async function loadBookings() {
    try {
        const response = await fetch('/admin/bookings/pending-data');
        if (!response.ok) {
            throw new Error('Ошибка загрузки данных');
        }

        const bookings = await response.json();
        const tableBody = document.getElementById('booking-table-body');

        bookings.forEach(booking => {
            const row = `
                    <tr>
                        <td>${booking.id}</td>
                        <td>${booking.username}</td>
                        <td>${booking.cabin}</td>
                        <td>${booking.startDate}</td>
                        <td>${booking.endDate}</td>
                        <td>${booking.status}</td>
                        <td>
                            <button onclick="approveBooking(${booking.id})">Подтвердить</button>
                            <button onclick="rejectBooking(${booking.id})">Отклонить</button>
                        </td>
                    </tr>
                `;
            tableBody.insertAdjacentHTML('beforeend', row);
        });
    } catch (error) {
        console.error(error);
        alert('Не удалось загрузить данные.');
    }
}

async function approveBooking(id) {
    try {
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const response = await fetch('/admin/bookings/approve', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken,
            },
            body: JSON.stringify({ id: id }),
        });

        if (!response.ok) {
            throw new Error('Ошибка подтверждения брони.');
        }

        alert(`Бронь с ID ${id} успешно подтверждена.`);
    } catch (error) {
        console.error(error);
        alert('Не удалось подтвердить бронь.');
    }
}

async function rejectBooking(id) {
    try {
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const response = await fetch('/admin/bookings/reject', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken,
            },
            body: JSON.stringify({ id: id }),
        });

        if (!response.ok) {
            throw new Error('Ошибка отклонения брони.');
        }

        alert(`Бронь с ID ${id} успешно отклонена.`);
    } catch (error) {
        console.error(error);
        alert('Не удалось отклонить бронь.');
    }}

window.onload = loadBookings;