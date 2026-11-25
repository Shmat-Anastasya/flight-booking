//интерфейс - действия сохранить бронь, найти бронь по номеру, найти бронь по рейсу и месту
package booking.repository;

import booking.model.Booking;

public interface SeatRepository {
    void save(Booking booking);

    Booking findById(int id);

    Booking findByFlightAndSeat(int flightId, String seat);

    void delete(Booking booking);
}