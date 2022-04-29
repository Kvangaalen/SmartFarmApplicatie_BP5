package restService.Controller;

import org.springframework.web.bind.annotation.*;
import restService.Database;
import restService.model.Gebruikerinformatie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static restService.Database.getDB;

@RestController
public class GebruikerGevensApi {
    static Connection connection;
    private String gebruikersNaam, wachtwoord, email, naam, telefoonnummer, bedrijfnaam;

    @PostMapping("/logins")
    @ResponseBody
    public Gebruikerinformatie loginGebruiker(@RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "wachtwoord") String wachtwoord) throws SQLException {
        System.out.println(wachtwoord);
        System.out.println(gebruikersNaam);

        try {
            connection = getDB();
            String query2 = "Select * From gebruikerinformatie where wachtwoord = ? and gebruikersnaam = LOWER(?)";
            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, wachtwoord);
            preparedStatement.setString(2, gebruikersNaam);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String gebruikersnaam = rs.getString("gebruikersnaam");
                String naameigenaar = rs.getString("naameigenaar");
                String emailadres = rs.getString("emailadres");
                String telefooneigenaar = rs.getString("telefooneigenaar");
                String bedrijfsnaam = rs.getString("bedrijfsnaam");
                Gebruikerinformatie gbrinf = new Gebruikerinformatie(gebruikersnaam, naameigenaar, emailadres, telefooneigenaar, null, bedrijfsnaam);

                System.out.println("Login gelukt!");
                return gbrinf;
            }
            rs.close();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("test");
        return null;
    }

    @PostMapping(value = "/getGebruiker")
    public Gebruikerinformatie getGebruiker(@RequestParam(name = "gebruikersNaam") String gebruikersNaam) throws SQLException {

        System.out.println(gebruikersNaam);

        try {
            connection = getDB();
            String query2 = "Select * From gebruikerinformatie where gebruikersnaam = ?";
            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, gebruikersNaam);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String gebruikersnaam = rs.getString("gebruikersnaam");
                String naameigenaar = rs.getString("naameigenaar");
                String emailadres = rs.getString("emailadres");
                String telefooneigenaar = rs.getString("telefooneigenaar");
                String bedrijfsnaam = rs.getString("bedrijfsnaam");
                restService.model.Gebruikerinformatie gbrinf = new restService.model.Gebruikerinformatie(gebruikersnaam, naameigenaar, emailadres, telefooneigenaar, null, bedrijfsnaam);

                System.out.println("Login gelukt!");
                return gbrinf;
            }
            rs.close();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("test");
        return null;
    }

    @PostMapping(value = "/editGebruiker")
    public boolean addNewApparaat(@RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "naam") String naam, @RequestParam(name = "email") String email, @RequestParam(name = "telefoonnummer") String telefoonnummer, @RequestParam(name = "bedrijfsNaam") String bedrijfsNaam) throws Exception {
        try {
            connection = Database.getDB();
            PreparedStatement ps = connection.prepareStatement("UPDATE gebruikerinformatie SET gebruikersnaam = ?, naameigenaar = ?, emailadres = ?, telefooneigenaar = ?, bedrijfsnaam = ?  WHERE gebruikersnaam = ?");
            ps.setString(1, gebruikersNaam);
            ps.setString(2, naam);
            ps.setString(3, email);
            ps.setString(4, telefoonnummer);
            ps.setString(5, bedrijfsNaam);
            ps.setString(6, gebruikersNaam);
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

    @PostMapping(value = "/gebruikers/addnew")
    public String createGebruikers(@RequestBody Gebruikerinformatie gebruiker) throws Exception {

        gebruikersNaam = gebruiker.getGebruikersNaam();
        wachtwoord = gebruiker.getWachtwoord();
        email = gebruiker.getEmailAdres();
        naam = gebruiker.getNaamEigenaar();
        telefoonnummer = gebruiker.getTelefoonEigenaar();
        bedrijfnaam = gebruiker.getBedrijfsNaam();
        Gebruikerinformatie gbri = new Gebruikerinformatie(gebruikersNaam, naam, email, telefoonnummer, wachtwoord, bedrijfnaam);
        String strInsertGemetenverschilwaarde = "insert into gebruikerinformatie (gebruikersnaam, naameigenaar, emailadres, telefooneigenaar, wachtwoord, bedrijfsnaam)"
                + "values (?, ?, ?, ?, ?, ?)";
        connection = Database.getDB();
        PreparedStatement preparedStmt = connection.prepareStatement(strInsertGemetenverschilwaarde);
        preparedStmt.setString(1, (gebruikersNaam)); // prouductId
        preparedStmt.setString(2, (naam)); // datum
        preparedStmt.setString(3, (email)); // tijd
        preparedStmt.setString(4, (telefoonnummer)); // gemetenTempratuur
        preparedStmt.setString(5, (wachtwoord)); // gemetenLuchtvochtigheid
        preparedStmt.setString(6, (bedrijfnaam)); // gemetenBodemvochtigheid
        preparedStmt.execute();
        preparedStmt.close();
        connection.close();
        return null;
    }

}
