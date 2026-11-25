package booking.service;

import booking.model.Booking;
import booking.model.Flight;
import booking.model.Passenger;
import booking.repository.SeatRepository;

public class BookingService {
    private final SeatRepository repository;
    private int nextId = 1;

    public BookingService(SeatRepository repository) {
        this.repository = repository;
    }

    public Booking book(Flight flight, Passenger passenger, String seat) {
        if (flight == null || passenger == null) {
            //рейс и пассажир указаны
            throw new IllegalArgumentException("Нужно указать рейс и пассажира");
        }
        if (seat == null || seat.trim().isEmpty()) {
            //номер места не пустой
            throw new IllegalArgumentException("Нужно указать место");
        }

        String normalizedSeat = seat.trim();
        Booking existing = repository.findByFlightAndSeat(flight.getId(), normalizedSeat);
        if (existing != null) {
            //проверяет есть ли уже бронь, если занято - ошиба, нет - бронь
            throw new IllegalStateException("Место " + normalizedSeat + " уже занято");
        }

        Booking booking = new Booking(nextId, flight, passenger, normalizedSeat);
        nextId = nextId + 1;
        repository.save(booking);
        return booking;
    }

    public void cancel(Booking booking) {
        if (booking == null || repository.findById(booking.getId()) == null) {
            throw new IllegalArgumentException("Бронь не найдена");
        }
        repository.delete(booking);
    }
}