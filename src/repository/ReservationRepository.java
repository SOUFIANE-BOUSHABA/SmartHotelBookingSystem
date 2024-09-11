package repository;
import model.Reservation;
import model.Room;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {
    void create(Reservation reservation);
    void cancel(int reservationId);
    List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate);
    boolean checkDateAvailability(int roomId, LocalDate startDate, LocalDate endDate);
    List<Reservation> getAllReservations();
}
