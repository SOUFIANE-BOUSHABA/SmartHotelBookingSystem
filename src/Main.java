import controller.ClientController;
import controller.HotelController;
import model.Client;
import model.Hotel;

import java.util.List;

public class Main {

    public static void main(String[] args) {

       ClientController clientController = new ClientController();

       clientController.createClient(1, "John", "testttttt@", "123456");



    }
}
