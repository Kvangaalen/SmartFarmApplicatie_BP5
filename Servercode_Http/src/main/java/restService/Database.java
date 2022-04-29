package restService;

import restService.model.Sensorwaarde;
import restService.model.VerwachteWeersOmstandigheden;
import restService.model.idealeomstandigheden;
import restService.Model.LocatieKas;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Database {
     static Connection connection;

    public Database(String productId, int luchtVochtigheid, int bodemVochtigheid, int tempratuur, int lichtSterkte, VerwachteWeersOmstandigheden verwachteWeersOmstandigheden, String postcode) throws SQLException {
        // ophalen van server tijd en datum
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
        time_format.setTimeZone(timeZone);
        Date date = new Date();
        String datum = date_format.format(date);
        String tijd = time_format.format(date);
        System.out.println("Datum(GMT) : " + datum);
        System.out.println("Tijd(GMT) : " + tijd);
        System.out.println("------------------------");

        try {
//            // idealewaarden - gemetenwaarden = verschilwaarde
//            idealeomstandigheden idealeomstandigheden = Database.getIdealomstandigheden(productId);
//
//            int verschilTempratuur = idealeomstandigheden.getIdealetempratuur() - tempratuur;
//            System.out.println(idealeomstandigheden.getIdealetempratuur() + "-" + tempratuur + " =" + verschilTempratuur);
//
//            int verschilLux = idealeomstandigheden.getIdealelux() - lichtSterkte;
//            System.out.println(idealeomstandigheden.getIdealelux() + "-" + lichtSterkte + " =" + verschilLux);
//
//            int verschilLuchtvochtigheid = idealeomstandigheden.getIdealeluchtvochtigheid() - luchtVochtigheid;
//            System.out.println(idealeomstandigheden.getIdealeluchtvochtigheid() + "-" + luchtVochtigheid + " =" + verschilLuchtvochtigheid);
//
//            int verschilBodemvochtigheid = idealeomstandigheden.getIdealebodemvochtigheid() - bodemVochtigheid;
//            System.out.println(idealeomstandigheden.getIdealebodemvochtigheid() + "-" + bodemVochtigheid + " =" + verschilBodemvochtigheid);

            //  insert gemetenverschilwaarde
            String strInsertGemetenverschilwaarde = "insert into gemetenverschilwaarde (productid, datum, tijdstip, gemetenTempratuur, gemetenLuchtvochtigheid, gemetenBodemvochtigheid, gemetenLux )"
                    + "values (?, ?, ?, ?, ?, ?, ?)";
            connection = getDB();
            PreparedStatement preparedStmt = connection.prepareStatement(strInsertGemetenverschilwaarde);
            preparedStmt.setString(1, (productId)); // prouductId
            preparedStmt.setString(2, (datum)); // datum
            preparedStmt.setString(3, (tijd)); // tijd
            preparedStmt.setInt(4, tempratuur); // gemetenTempratuur
            preparedStmt.setInt(5, luchtVochtigheid); // gemetenLuchtvochtigheid
            preparedStmt.setInt(6, bodemVochtigheid); // gemetenBodemvochtigheid
            preparedStmt.setInt(7, lichtSterkte); // gemetenLux
//            preparedStmt.setInt(8, verschilTempratuur); // gemetenTempratuur
//            preparedStmt.setInt(9, verschilLuchtvochtigheid); // gemetenLuchtvochtigheid
//            preparedStmt.setInt(10, verschilBodemvochtigheid); // gemetenBodemvochtigheid
//            preparedStmt.setInt(11, verschilLux); // gemetenLux
            preparedStmt.execute();
            preparedStmt.close();

            // insert verwachteWeersOmstandigheden
            String strInsertLiveWeer = "insert into verwachteWeersOmstandigheden  (datum, tijdstip, postcode, buitenTempratuur, buitenVochtigheid, weertype)"
                    + "values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmtLiveweer = connection.prepareStatement(strInsertLiveWeer);
            preparedStmtLiveweer.setString(1, (datum)); // datum
            preparedStmtLiveweer.setString(2, (tijd)); // tijdstip
            preparedStmtLiveweer.setString(3, (postcode)); // postcode
            preparedStmtLiveweer.setInt(4, verwachteWeersOmstandigheden.getBuitenTempratuur()); // buitenTempratuur
            preparedStmtLiveweer.setInt(5, verwachteWeersOmstandigheden.getBuitenVochtigheid()); // buitenVochtigheid
            preparedStmtLiveweer.setString(6, verwachteWeersOmstandigheden.getWeertype()); // weertype
            preparedStmtLiveweer.execute();
            preparedStmtLiveweer.close();

            Sensorwaarde sensorwaarde = new Sensorwaarde(tempratuur, luchtVochtigheid, bodemVochtigheid, lichtSterkte);
            // update thingsspeak dashboard
            updateThingspeak(sensorwaarde);
        } catch (Exception e) {
            System.err.println("exp 1");
            System.err.println(e.getMessage());
        } finally {
            connection.close();
            System.out.println("DB close 3 strInsertGemetenverschilwaarde and verwachteWeersOmstandigheden");
        }
    }

    public static Connection getDB() {
        try {

            //String url = "jdbc:postgresql://ella.db.elephantsql.com/cjnvbyay?user=cjnvbyay&password=NaWmzBLlSuU9FUBMWIGBdAgtBsAuRu6S";
            Connection conn;
            //conn = DriverManager.getConnection("jdbc:postgresql://ella.db.elephantsql.com/cjnvbyay", "cjnvbyay","NaWmzBLlSuU9FUBMWIGBdAgtBsAuRu6S");
            conn = DriverManager.getConnection("jdbc:postgresql://tai.db.elephantsql.com/acfpytqw", "acfpytqw","yMHb4zp0ChHLpb_pv7CHuj-TI1q9jdMf");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static idealeomstandigheden getIdealomstandigheden(String productId) throws SQLException {
        try {
            connection = getDB();
            String query2 = "Select * From apparaatlocatie Inner Join kasplant On kasplant.kasNaam = apparaatlocatie.kasNaam And kasplant.vaknummer = apparaatlocatie.vakNummer Inner Join idealeomstandigheden On kasplant.plantRas = idealeomstandigheden.plantRas And kasplant.plantSoort = idealeomstandigheden.plantSoort Where apparaatlocatie.productId = ?";
            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, productId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String plantsoort = rs.getString("plantsoort");
                String plantras = rs.getString("plantras");
                int idealetempratuur = rs.getInt("idealetempratuur");
                int idealeluchtvochtigheid = rs.getInt("idealeluchtvochtigheid");
                int idealebodemvochtigheid = rs.getInt("idealebodemvochtigheid");
                int idealelux = rs.getInt("idealelux");
                idealeomstandigheden ioh = new idealeomstandigheden(plantsoort, plantras, idealetempratuur, idealeluchtvochtigheid, idealebodemvochtigheid, idealelux);
                return ioh;
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.close();
            System.out.println("DB close 2 ophalen idealeomstandighecen plant");
        }
        return null;
    }

    private void updateThingspeak(Sensorwaarde sensorwaarde) {
        try {
            URL ur1 = new URL("https://api.thingspeak.com/update?api_key=FX7ZSL5OIM1G4JW1&field1=" + sensorwaarde.getLuchtVochtigheid() + "&field2=" + sensorwaarde.getBodemVochtigheid() + "&field3=" + sensorwaarde.getTempratuur() + "&field4=" + sensorwaarde.getLichtSterkte() + "");
            HttpURLConnection conn = (HttpURLConnection) ur1.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LocatieKas getLocatiekas(String productId) throws SQLException {
        connection = getDB();

        String query2 = "Select locatiekas.* From locatiekas Inner Join apparaatlocatie On locatiekas.kasNaam = apparaatlocatie.kasNaam Where apparaatlocatie.productId = ?";
        try {
            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
            preparedStatement1.setString(1, productId);
            ResultSet rs2 = preparedStatement1.executeQuery();
            while (rs2.next()) {
                String kasnaam = rs2.getString("kasnaam");
                String straatnaam = rs2.getString("straatnaam");
                String huisnummer = rs2.getString("huisnummer");
                String plaats = rs2.getString("plaats");
                String postcode = rs2.getString("postcode");
                LocatieKas locatieKas = new LocatieKas(null, kasnaam, straatnaam, huisnummer, plaats, postcode);
                return locatieKas;
            }
            rs2.close();
            preparedStatement1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.close();
            System.out.println("DB close ophalen Locatiekas");
        }
        return null;
    }

}