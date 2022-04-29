package restService.Controller;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import restService.Database;
import restService.model.Payload;
import restService.model.VerwachteWeersOmstandigheden;
import restService.Model.LocatieKas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;

@RestController
public class DataCollector {
    Connection conn = null;

    @RequestMapping(value = "/payload", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String strPl(@RequestBody ArrayList<Payload> alPayloads) throws Exception {
        //Ophalen Data uit ArrayList
        String productId = alPayloads.get(0).getBn();
        String payload = alPayloads.get(1).getVs();
        System.out.println("------------------------");
        System.out.println("Device : " + productId);
        System.out.println("Payload : " + payload);
        System.out.println("------------------------");

        //Afkomstig van sensoren Koen
        String hexLuchtvochtigheid = payload.substring(0, 2);
        System.out.println("Luchtvochtigheid hex : " + hexLuchtvochtigheid);
        String hexBodemvochtigheid = payload.substring(2, 4);
        System.out.println("Bodemvochtigheid hex : " + hexBodemvochtigheid);
        String hexTempratuur = payload.substring(4, 6);
        System.out.println("Tempratuur hex : " + hexTempratuur);

        //Afkomstig van sensor Remco
        String hexLichtSterkte = payload.substring(6);
        System.out.println("Lichtsterkte hex : " + hexLichtSterkte);
        System.out.println("------------------------");

        // Omrekenen Hexadecimalen naar Decimalen
        int luchtVochtigheid = Integer.parseInt(hexLuchtvochtigheid, 16);
        System.out.println("Luchtvochtigheid : " + luchtVochtigheid);

        int bodemVochtigheid = Integer.parseInt(hexBodemvochtigheid, 16);
        System.out.println("Bodemvochtigheid : " + bodemVochtigheid);

        int tempratuur = Integer.parseInt(hexTempratuur, 16);
        System.out.println("Tempratuur : " + tempratuur);


        int lichtSterkte = Integer.parseInt(hexLichtSterkte, 16);
        System.out.println("Lichtsterkte : " + lichtSterkte);

        System.out.println("------------------------");
        LocatieKas locatieKas = Database.getLocatiekas(productId);

        new Database(productId, luchtVochtigheid, bodemVochtigheid, tempratuur, lichtSterkte, getLiveWeer(locatieKas.getPlaats()), locatieKas.getPostcode());
        return "productId: " + productId + " | luchtVochtigheid: " + luchtVochtigheid + " | bodemVochtigheid: " + bodemVochtigheid + " | tempratuur: " + tempratuur + " | lichtSterkte " + lichtSterkte + " | " ;

    }

    private VerwachteWeersOmstandigheden getLiveWeer(String plaats) throws IOException, Exception {
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        URL url = new URL("https://weerlive.nl/api/json-data-10min.php?key=1583a0437b&locatie=" + plaats);
        URLConnection con = url.openConnection();
        in = con.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        JSONObject liveweer = null;
        try {
            JSONObject jsonObject = new JSONObject(sb.toString());
            liveweer = jsonObject.getJSONArray("liveweer").getJSONObject(0);

        } catch (Exception e) {
            System.out.println("Error 1 : " + e.getMessage());
        }
        int buitenTempratuur = (int) Math.round(Float.parseFloat((String) liveweer.get("temp")));
        int buitenVochtigheid = (int) Math.round(Float.parseFloat((String) liveweer.get("lv")));
        String weertype = (String) liveweer.get("samenv");
        VerwachteWeersOmstandigheden VerwachteWeersOmstandigheden = new VerwachteWeersOmstandigheden(buitenTempratuur, buitenVochtigheid, weertype);
        return VerwachteWeersOmstandigheden;
    }

}


