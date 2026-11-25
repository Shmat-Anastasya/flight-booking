package booking;

import booking.model.Booking;
import booking.model.Flight;
import booking.model.Passenger;
import booking.repository.InMemorySeatRepository;
import booking.repository.SeatRepository;
import booking.service.BookingService;

public class Main {
    public static void main(String[] args) {
        SeatRepository repository = new InMemorySeatRepository();
        BookingService service = new BookingService(repository);

        Flight flight = new Flight(1, "SU100", "Москва — Сочи");
        Passenger anna = new Passenger(1, "Анна Смирнова");
        Passenger boris = new Passenger(2, "Борис Козлов");

        Booking first = service.book(flight, anna, "12A");
        System.out.println("Оформлено: " + first);

        try {
            service.book(flight, boris, "12A");
        } catch (IllegalStateException exception) {
            System.out.println("Отклонено: " + exception.getMessage());
        }

        Booking second = service.book(flight, boris, "14C");
        System.out.println("Оформлено: " + second);
    }
}