package booking.service;

import booking.model.Booking;
import booking.model.Flight;
import booking.model.Passenger;
import booking.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingService {
    private final SeatRepository repository;
    private int nextId = 1;
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

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
            log.warn("Место {} уже занято", normalizedSeat);
            throw new IllegalStateException("Место " + normalizedSeat + " уже занято");
        }

        Booking booking = new Booking(nextId, flight, passenger, normalizedSeat);
        nextId = nextId + 1;
        repository.save(booking);
        log.info("Место {} забронировано", normalizedSeat);
        return booking;
    }

    public void cancel(Booking booking) {
        if (booking == null || repository.findById(booking.getId()) == null) {
            log.error("Бронь не найдена");
            throw new IllegalArgumentException("Такая бронь не найдена");
        }
        log.info("Бронь {} отменена", booking.getId());
        repository.delete(booking);
    }
}
// Эта строка специально длиннее ста символов, чтобы Checkstyle показал нарушение правила LineLength в файле BookingService.