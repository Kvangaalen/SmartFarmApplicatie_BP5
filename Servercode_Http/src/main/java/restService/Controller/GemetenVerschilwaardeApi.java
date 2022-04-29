package restService.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import restService.Model.Gemetenverschilwaarde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static restService.Database.getDB;

@RestController
public class GemetenVerschilwaardeApi {

    static Connection connection;

    @PostMapping("/gemetenverschikwaardes")
    @ResponseBody
    public ArrayList getKassen(@RequestParam(name = "productId") String productId) {
        try {
            System.out.println(productId);
            ArrayList ArrayListGemetenVerschilwaardes = new ArrayList<Gemetenverschilwaarde>();


            String query2 = "SELECT * FROM gemetenverschilwaarde where productid = ? ORDER BY to_timestamp(concat(datum,' ',tijdstip), 'dd-mm-yyyy hh24:mi') asc";

            connection = getDB();


            // create the preparedstatement and add the criteria
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, productId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String productid =  rs.getString("productid");
                String datum =  rs.getString("datum");
                String tijdstip =  rs.getString("tijdstip");
                int gemetentempratuur = Integer.parseInt(rs.getString("gemetentempratuur"));
                int gemetenluchtvochtigheid   = Integer.parseInt(rs.getString("gemetenluchtvochtigheid"));
                int gemetenbodemvochtigheid	 = Integer.parseInt(rs.getString("gemetenbodemvochtigheid"));
                int gemetenlux	 = Integer.parseInt(rs.getString("gemetenlux"));
                System.out.println(productid + datum + tijdstip + gemetentempratuur + gemetenluchtvochtigheid + gemetenbodemvochtigheid + gemetenlux);
                ArrayListGemetenVerschilwaardes.add(new Gemetenverschilwaarde(productid, datum, tijdstip, gemetentempratuur, gemetenluchtvochtigheid, gemetenbodemvochtigheid,gemetenlux));
            }
            rs.close();
            preparedStatement.close();
            return ArrayListGemetenVerschilwaardes;
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

}
