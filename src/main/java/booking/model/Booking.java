package booking.model;

public class Booking {
    private final int id;
    private final Flight flight;
    private final Passenger passenger;
    private final String seat;

    public Booking(int id, Flight flight, Passenger passenger, String seat) {
        this.id = id;
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Бронь №" + id
                + ": рейс " + flight.getNumber()
                + " (" + flight.getRoute() + ")"
                + ", место " + seat
                + ", пассажир " + passenger.getName();
    }
}