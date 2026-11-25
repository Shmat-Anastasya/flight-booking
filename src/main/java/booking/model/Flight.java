package booking.model;

public class Flight {
    private final int id;
    private final String number;
    private final String route;

    public Flight(int id, String number, String route) {
        this.id = id;
        this.number = number;
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getRoute() {
        return route;
    }
}