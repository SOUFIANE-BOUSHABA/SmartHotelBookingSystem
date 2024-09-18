package repository;
import model.Reservation;
import model.Room;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationRepository {
    void create(Reservation reservation);
    void cancel(int reservationId);
    List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate);
    boolean checkDateAvailability(int roomId, LocalDate startDate, LocalDate endDate);
    List<Reservation> getAllReservations();

    Map<Integer, Room> findAvailableRoomsForMiseENSItuation(LocalDate startDate, LocalDate endDate);

    boolean isUserReservation(int reservationId, int clientId);
}
