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
public class KassenApi {
        static Connection connection;

  //Ophalen data kassen
    @PostMapping("/kassen")
    @ResponseBody
    public ArrayList getKassen(@RequestParam(name = "gebruikersNaam") String gebruikersNaam) {
        try {
            ArrayList ArrayListKassen = new ArrayList<LocatieKas>();
            connection = getDB();
            String query2 = "SELECT * from locatiekas where gebruikersnaam = ?";
            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, gebruikersNaam);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String kasnaam = rs.getString("kasnaam");
                String straatnaam = rs.getString("straatnaam");
                String huisnummer = rs.getString("huisnummer");
                String plaats = rs.getString("plaats");
                String postcode = rs.getString("postcode");
                System.out.println(kasnaam + kasnaam + straatnaam + huisnummer + plaats + postcode);
                ArrayListKassen.add(new LocatieKas(null, kasnaam, straatnaam, huisnummer, plaats, postcode));
            }
            rs.close();
            preparedStatement.close();
            return ArrayListKassen;
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

    // nieuwe kas toevoegen aan database

    @PostMapping(value = "/newKas")
    public String addNewKas(@RequestBody LocatieKas locatieKas) throws Exception {

        String kasnaam = locatieKas.getKasnaam();
        String gebruikersnaam = locatieKas.getGebruikersnaam();
        String straatnaam = locatieKas.getStraatnaam();
        String huisnummer = locatieKas.getHuisnummer();
        String plaats = locatieKas.getPlaats();
        String postcode = locatieKas.getPostcode();
        System.out.println(kasnaam + gebruikersnaam + straatnaam + huisnummer + plaats + postcode);

        LocatieKas lk = new LocatieKas(kasnaam, gebruikersnaam, straatnaam, huisnummer, plaats, postcode);
        String insertNewKas = "insert into locatiekas (kasnaam, gebruikersnaam, straatnaam, huisnummer, plaats, postcode)"
                + "values (?, ?, ?, ?, ?, ?)";
        connection = Database.getDB();
        PreparedStatement preparedStmt = connection.prepareStatement(insertNewKas);
        preparedStmt.setString(1, (kasnaam));
        preparedStmt.setString(2, (gebruikersnaam));
        preparedStmt.setString(3, (straatnaam));
        preparedStmt.setString(4, (huisnummer));
        preparedStmt.setString(5, (plaats));
        preparedStmt.setString(6, (postcode));
        preparedStmt.execute();
        preparedStmt.close();
        connection.close();
        return null;
    }

    // Verwijderen kas uit database
    @PostMapping(value = "/deleteKas")
    public apparaatlocatie deleteKas(@RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "kasnaam") String kasnaam) throws Exception {
        System.out.println(kasnaam + gebruikersNaam);

        String deleteSQL = "DELETE FROM locatiekas WHERE gebruikersnaam = ? and kasnaam = ?;";

        try (Connection connection = getDB();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, gebruikersNaam);
            preparedStatement.setString(2, kasnaam);

            int rowCount = preparedStatement.executeUpdate();
            if (rowCount > 0) {
                System.out.println("verwijderd");
            } else {
                System.out.println("Mislukt");
            }

        }
        return null;
    }

    // Updaten kassen
    @PostMapping(value = "/updateKas")
    public Boolean updateKas (@RequestParam(name = "kasnaam") String kasnaam, @RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam (name = "straatnaam") String straatnaam, @RequestParam (name = "huisnummer")String huisnummer, @RequestParam (name = "postcode")String postcode) throws Exception{
        System.out.println("De naam van de kas van : " + gebruikersNaam + " " + straatnaam + " " + huisnummer + " " + postcode + " wordt aangepast");

        try {
            connection = Database.getDB();
            PreparedStatement ps = connection.prepareStatement("UPDATE locatiekas SET kasnaam = ? WHERE straatnaam = ? AND huisnummer = ? AND postcode = ? ");
            ps.setString(1, kasnaam);
            ps.setString(2, straatnaam);
            ps.setString(3,huisnummer);
            ps.setString(4,postcode);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            connection.close();
        }
        return false;
    }

}
