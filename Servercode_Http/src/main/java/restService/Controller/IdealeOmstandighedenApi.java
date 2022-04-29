package restService.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static restService.Database.getDB;

@RestController
public class IdealeOmstandighedenApi {
    static Connection connection;



    @PostMapping("/idealeWaarde")
    @ResponseBody
    public ArrayList getIdealWaarde() {
        try {
            ArrayList alIdealeWaarde = new ArrayList<restService.model.idealeomstandigheden>();
            connection = getDB();
            String query = "SELECT * FROM idealeomstandigheden";
            PreparedStatement prepStat = connection.prepareStatement(query);
            ResultSet rsIdealeWaardes = prepStat.executeQuery();
            while (rsIdealeWaardes.next()) {
                String plantRas = rsIdealeWaardes.getString("plantras");
                String plantSoort = rsIdealeWaardes.getString("plantsoort");
                int idealeTemp = rsIdealeWaardes.getInt("idealetempratuur");
                int idealeLucht = rsIdealeWaardes.getInt("idealeluchtvochtigheid");
                int idealeBodem = rsIdealeWaardes.getInt("idealebodemvochtigheid");
                int idealeLux = rsIdealeWaardes.getInt("idealeLux");
                alIdealeWaarde.add(new restService.model.idealeomstandigheden(plantRas, plantSoort, idealeTemp, idealeBodem, idealeLucht, idealeLux));
                System.out.println(plantRas + " " + plantSoort + " " + idealeTemp + " " + idealeLucht + " " + idealeBodem + " " + idealeLux);
            }
            rsIdealeWaardes.close();
            return alIdealeWaarde;
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

    // Volley Terbehoeve van het vullen van een spinner aan Front-end zijde.

    @PostMapping("/spinnerfiller")
    @ResponseBody
    public ArrayList fillSpinner(@RequestParam(name = "selectedplantsoort") String plantsoort) {
        try {
            ArrayList alIdealeWaarde = new ArrayList<restService.model.idealeomstandigheden>();
            connection = getDB();
            String query = "SELECT * FROM idealeomstandigheden WHERE plantsoort = ?";
            PreparedStatement prepStat = connection.prepareStatement(query);
            prepStat.setString(1, plantsoort);
            ResultSet rsIdealeWaardes = prepStat.executeQuery();
            while (rsIdealeWaardes.next()) {
                String plantRas = rsIdealeWaardes.getString("plantras");
                String plantSoort = rsIdealeWaardes.getString("plantsoort");
                int idealeTemp = rsIdealeWaardes.getInt("idealetempratuur");
                int idealeLucht = rsIdealeWaardes.getInt("idealeluchtvochtigheid");
                int idealeBodem = rsIdealeWaardes.getInt("idealebodemvochtigheid");
                int idealeLux = rsIdealeWaardes.getInt("idealeLux");
                alIdealeWaarde.add(new restService.model.idealeomstandigheden(plantRas, plantSoort, idealeTemp, idealeBodem, idealeLucht, idealeLux));
                System.out.println(plantRas + " " + plantSoort + " " + idealeTemp + " " + idealeLucht + " " + idealeBodem + " " + idealeLux);
            }
            rsIdealeWaardes.close();
            return alIdealeWaarde;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("return null");
        return null;
    }


}
