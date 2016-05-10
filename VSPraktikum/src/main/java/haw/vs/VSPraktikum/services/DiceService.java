package haw.vs.VSPraktikum.services;

import static spark.Spark.get;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;
import org.json.JSONObject;
import haw.vs.VSPraktikum.util.YellowpagesData;
import static haw.vs.VSPraktikum.util.YellowServiceRegistration.*;

public class DiceService {
	
	public static void main(String[] args) {
		registerService("jenny_marc_vsp_dice", "give a single dice roll", "dice", "http://172.18.0.71:4567/dice");
		
		get("/dice", (request, response) -> {
			response.status(200);
			response.type("application/json");
			
			String player = request.queryParams("player");
			String game = request.queryParams("game");
			
			createEvent(player, game);
			
			Integer randomNumber = (Integer)ThreadLocalRandom.current().nextInt(1, 7);
			
			JSONObject json = new JSONObject();
			json.put("number", randomNumber);
			json.put("player", player);
			json.put("game", game);
			return json.toString();
		});
	}
	
	private static String createEvent(String player, String game) {
		HttpURLConnection connection = null;
		
		try {
			YellowpagesData eventService = EventServiceProvider.getService("232");
			URL url = new URL(eventService.getUri() + "events");
			
			String requestBody = "{ " + "\"game\":\"" + game + "\", " + "\"player\":\"" + player + "\"" + "}";
			
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", "" + requestBody.getBytes().length);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(requestBody);
			wr.flush();
			wr.close();
			
			connection.getResponseCode();
			
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			
			reader.close();
			return response.toString();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
		
		return "";
	};
}
