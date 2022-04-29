package restService.Controller;

import org.springframework.web.bind.annotation.*;
import restService.Database;
import restService.Model.apparaatlocatie;
import restService.Model.LocatieKas;
import restService.model.Gebruikerinformatie;
import restService.model.idealeomstandigheden;

import java.sql.*;
import java.util.ArrayList;

import static restService.Database.getDB;

@RestController
public class ApparaatApi {
    static Connection connection;
    private String gebruikersNaam, wachtwoord, email, naam, telefoonnummer, bedrijfnaam;

    @PostMapping(value = "/newPlant")
    public String addNewPlant(@RequestBody idealeomstandigheden nieuwePlant) throws Exception {

        String plantRas = nieuwePlant.getPlantras();
        String plantSoort = nieuwePlant.getPlantsoort();
        int idealetempratuur = nieuwePlant.getIdealetempratuur();
        int idealelucht = nieuwePlant.getIdealeluchtvochtigheid();
        int idealebodem = nieuwePlant.getIdealebodemvochtigheid();
        int idealelux = nieuwePlant.getIdealelux();
        System.out.println(plantRas + plantSoort + idealetempratuur + idealelucht + idealebodem + idealelux);

        idealeomstandigheden io = new idealeomstandigheden(plantRas, plantSoort, idealetempratuur, idealelucht, idealebodem, idealelux);
        String strInsert = "insert into idealeomstandigheden (plantras, plantsoort, idealetempratuur, idealeluchtvochtigheid, idealebodemvochtigheid, idealelux)"
                + "values (?, ?, ?, ?, ?, ?)";
        connection = Database.getDB();
        PreparedStatement preparedStmt = connection.prepareStatement(strInsert);
        preparedStmt.setString(1, (plantRas));
        preparedStmt.setString(2, (plantSoort));
        preparedStmt.setInt(3, (idealetempratuur));
        preparedStmt.setInt(4, (idealelucht));
        preparedStmt.setInt(5, (idealebodem));
        preparedStmt.setInt(6, (idealelux));
        preparedStmt.execute();
        preparedStmt.close();
        connection.close();
        return null;
    }

    @PostMapping("/apparaat")
    @ResponseBody
    public ArrayList getApparaat(@RequestParam(name = "gebruikersNaam") String gebruikersNaam) {
        try {
            ArrayList ArrayListApparaatlocatie = new ArrayList<apparaatlocatie>();
            connection = getDB();
            String query2 = "SELECT * FROM apparaatlocatie where gebruikersnaam = ?";
            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, gebruikersNaam);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String productid = rs.getString("productid");
                String kasnaam = rs.getString("kasnaam");
                String vaknummer = rs.getString("vaknummer");
                String gebruikersnaam = rs.getString("gebruikersnaam");
                System.out.println(productid + kasnaam + vaknummer + gebruikersnaam);
                ArrayListApparaatlocatie.add(new apparaatlocatie(productid, kasnaam, vaknummer, gebruikersnaam));
            }
            rs.close();
            preparedStatement.close();
            return ArrayListApparaatlocatie;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("retun null");
        return null;

    }

    @PostMapping(value = "/newApparaat")
    public apparaatlocatie addNewApparaat(@RequestParam(name = "kasnaam") String kasnaam, @RequestParam(name = "vaknummer") String vaknummer, @RequestParam(name = "productid") String productid, @RequestParam(name = "gebruikersNaam") String gebruikersNaam) throws Exception {
        System.out.println(kasnaam + vaknummer + productid);
        apparaatlocatie al = new apparaatlocatie(productid, kasnaam, vaknummer, null);
        String strInsert = "insert into apparaatlocatie (productid, kasnaam, vaknummer, gebruikersnaam)"
                + "values (?, ?, ?, ?)";
        connection = Database.getDB();
        PreparedStatement preparedStmt = connection.prepareStatement(strInsert);
        preparedStmt.setString(1, (productid));
        preparedStmt.setString(2, (kasnaam));
        preparedStmt.setInt(3, Integer.parseInt((vaknummer)));
        preparedStmt.setString(4, (gebruikersNaam));
        preparedStmt.execute();
        preparedStmt.close();
        connection.close();
        return al;
    }

    @PostMapping(value = "/deleteApparaat")
    public apparaatlocatie deleteApparaat(@RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "productid") String productid) throws Exception {
        System.out.println(productid + gebruikersNaam);

        String deleteSQL = "DELETE FROM apparaatlocatie WHERE gebruikersnaam = ? and productid = ?;";

        try (Connection connection = getDB();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, gebruikersNaam);
            preparedStatement.setString(2, productid);

            int rowCount = preparedStatement.executeUpdate();
            if (rowCount > 0) {
                System.out.println("verwijderd");
            } else {
                System.out.println("Mislukt");
            }

        }
        return null;
    }

    @PostMapping(value = "/editApparaat")
    public boolean addNewApparaat(@RequestParam(name = "kasnaam") String kasnaam, @RequestParam(name = "vaknummer") String vaknummer, @RequestParam(name = "productid") String productid, @RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "oldproductid") String oldproductid) throws Exception {
        try {
            connection = Database.getDB();
            PreparedStatement ps = connection.prepareStatement("UPDATE apparaatlocatie SET productid = ?, kasnaam = ?, vaknummer = ? WHERE productid = ?");
            ps.setString(1, productid);
            ps.setString(2, kasnaam);
            ps.setInt(3, Integer.parseInt(vaknummer));
            ps.setString(4, oldproductid);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
}
