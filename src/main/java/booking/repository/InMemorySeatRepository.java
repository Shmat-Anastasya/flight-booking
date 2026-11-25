package booking.repository;

import booking.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class InMemorySeatRepository implements SeatRepository {
    private final List<Booking> bookings = new ArrayList<>();

    @Override
    public void save(Booking booking) {
        bookings.add(booking);
    }

    @Override
    public Booking findById(int id) {
        for (Booking booking : bookings) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

    @Override
    public Booking findByFlightAndSeat(int flightId, String seat) {
        for (Booking booking : bookings) {
            if (booking.getFlight().getId() == flightId && booking.getSeat().equals(seat)) {
                return booking;
            }
        }
        return null;
    }

    @Override
    public void delete(Booking booking) {
        bookings.remove(booking);
    }
}