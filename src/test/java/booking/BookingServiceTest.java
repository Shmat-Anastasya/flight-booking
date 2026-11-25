package booking;

import booking.model.Booking;
import booking.model.Flight;
import booking.model.Passenger;
import booking.repository.InMemorySeatRepository;
import booking.repository.SeatRepository;
import booking.service.BookingService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingServiceTest {
    @Test
    void bookSucceedsWhenSeatIsFree() {
        SeatRepository repository = new InMemorySeatRepository();
        BookingService service = new BookingService(repository);
        Flight flight = flight();
        Passenger anna = new Passenger(99, "Анна Смирнова");

        Booking booking = service.book(flight, anna, "12A");

        assertEquals(1, booking.getId());
        assertSame(flight, booking.getFlight());
        assertSame(anna, booking.getPassenger());
        assertEquals("12A", booking.getSeat());
        assertSame(booking, repository.findById(1));
    }

    @Test
    void bookFailsWhenSeatIsEmpty() {
        SeatRepository repository = new InMemorySeatRepository();
        BookingService service = new BookingService(repository);

        assertThrows(IllegalArgumentException.class, () ->
                service.book(flight(), new Passenger(1, "Анна Смирнова"), "   ")
        );
        assertNull(repository.findById(1));
    }

    @Test
    void bookFailsWhenSeatIsAlreadyTaken() {
        SeatRepository repository = new InMemorySeatRepository();
        BookingService service = new BookingService(repository);
        Flight flight = flight();
        Flight otherFlight = new Flight(2, "SU200", "Москва — Казань");
        Passenger anna = new Passenger(1, "Анна Смирнова");
        Passenger boris = new Passenger(2, "Борис Козлов");

        service.book(flight, anna, "12A");

        assertThrows(IllegalStateException.class, () -> service.book(flight, boris, "12A"));
        assertNull(repository.findById(2));

        Booking otherSeat = service.book(flight, boris, "14C");
        assertEquals(2, otherSeat.getId());

        Booking sameSeatOtherFlight = service.book(otherFlight, boris, "12A");
        assertEquals(3, sameSeatOtherFlight.getId());
    }

    @Test
    void cancelFreesSeatForNewBooking() {
        SeatRepository repository = new InMemorySeatRepository();
        BookingService service = new BookingService(repository);
        Flight flight = flight();
        Passenger anna = new Passenger(1, "Анна Смирнова");
        Passenger boris = new Passenger(2, "Борис Козлов");

        Booking booking = service.book(flight, anna, "12A");
        service.cancel(booking);

        assertNull(repository.findById(1));
        assertNull(repository.findByFlightAndSeat(flight.getId(), "12A"));

        Booking again = service.book(flight, boris, "12A");
        assertEquals("12A", again.getSeat());
        assertSame(boris, again.getPassenger());
    }

    private Flight flight() {
        return new Flight(1, "SU100", "Москва — Сочи");
    }
}