package restService.Controller;

import org.springframework.web.bind.annotation.*;
import restService.Database;
import restService.Model.KasPlant;
import restService.Model.apparaatlocatie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static restService.Database.getDB;
@RestController
public class KasPlantApi {
    Connection connection;

    //Ophalen data kassen
    @PostMapping("/getKasPlant")
    @ResponseBody
    public ArrayList getKasPlanten(@RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "kasnaam") String kasnaam) {
        try {
            ArrayList ArrayListKasPlanten = new ArrayList<KasPlant>();
            connection = getDB();
            String query2 = "SELECT * from kasplant where gebruikersnaam = ? and kasnaam = ?";
            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, gebruikersNaam);
            preparedStatement.setString(2, kasnaam);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer plantid = rs.getInt("plantid");
                String plantsoort = rs.getString("plantsoort");
                String plantras = rs.getString("plantras");
                Integer vaknummer = rs.getInt("vaknummer");

                System.out.println(plantid + kasnaam + plantsoort + plantras + vaknummer + gebruikersNaam);
                System.out.println("test");
                ArrayListKasPlanten.add(new KasPlant(plantid, kasnaam, plantsoort, plantras, vaknummer, gebruikersNaam));
            }
            rs.close();
            preparedStatement.close();
            return ArrayListKasPlanten;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    //plantToKas insert
    @PostMapping(value = "/plantToKas")



        public boolean plantToKas(@RequestParam(name = "kasnaam") String kasnaam, @RequestParam(name = "plantsoort") String plantsoort, @RequestParam(name = "plantras") String plantras,@RequestParam(name = "vaknummer") int vaknummer, @RequestParam(name = "gebruikersNaam") String gebruikersnaam) throws Exception {

        try {


            KasPlant kp = new KasPlant(null, kasnaam, plantsoort, plantras, vaknummer, gebruikersnaam);
            String insert = "insert into kasplant (kasnaam, plantsoort, plantras,vaknummer, gebruikersnaam)"
                    + "values (?, ?, ?, ?, ?)";
            connection = Database.getDB();
            PreparedStatement preparedStmt = connection.prepareStatement(insert);
            //preparedStmt.setInt(1, (plantid));
            preparedStmt.setString(1, (kasnaam));
            preparedStmt.setString(2, (plantsoort));
            preparedStmt.setString(3, (plantras));
            preparedStmt.setInt(4, vaknummer);
            preparedStmt.setString(5, (gebruikersnaam));
            preparedStmt.execute();
            preparedStmt.close();




            return true;

        } catch (Exception e){
            return false;
        }

        finally {
            connection.close();
        }

    }


//Controleren vaknummer
    @PostMapping("/checkVaknummer")
    @ResponseBody
    public Boolean checkVaknummer(@RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "kasnaam") String kasnaam, @RequestParam(name = "vaknummer") String vakNummer) throws SQLException {

        try {
            connection = Database.getDB();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM kasplant WHERE kasnaam = ? AND vaknummer = ? AND gebruikersnaam = ?");
            ps.setString(1, kasnaam);
            ps.setInt(2, Integer.parseInt(vakNummer));
            ps.setString(3, gebruikersNaam);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                return true;
            }
            rs.close();
            ps.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            connection.close();
        }
        return false;
    }
    // verwijderen plant uit kas
    @PostMapping(value = "/deleteKasPlant")
    public apparaatlocatie deleteKas(@RequestParam(name = "gebruikersNaam") String gebruikersNaam, @RequestParam(name = "kasnaam") String kasnaam, @RequestParam(name = "vaknummer") String vaknummer) throws Exception {
        System.out.println(kasnaam + gebruikersNaam + vaknummer);

        String deleteSQL = "DELETE FROM KasPlant WHERE gebruikersnaam = ? and kasnaam = ? and vaknummer = ?;";

        try (Connection connection = getDB();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, gebruikersNaam);
            preparedStatement.setString(2, kasnaam);
            int intVaknummer = Integer.parseInt(vaknummer);
            preparedStatement.setInt(3, intVaknummer );

            int rowCount = preparedStatement.executeUpdate();
            if (rowCount > 0) {
                System.out.println("verwijderd");
            } else {
                System.out.println("Mislukt");
            }

        }
        return null;
    }
}

