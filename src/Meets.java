import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Meets {
    private static String url_base = "https://www.milesplit.com/api/v1/meets/";


    /**
     * Returns the JSON result of a GET request to the url
     *
     * @param url complete URL used for the HTTP Request
     * @return a JSONObject containing the response
     */
    private static JSONObject FromURL(String url) {
        String content = HTTPRequest.Get(url);

        try {

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(content);

            jsonObject.remove("_meta");
            jsonObject.remove("_links");

            return jsonObject;

        } catch (ParseException e) {

            e.printStackTrace();
            return null;

        }
    }


    /**
     * Returns the results for the given meetId.
     * Default fields are id, meetId, place, units, millimeters, teamName,
     * firstName, lastName, eventType, meetName, and videoId. Also contains the meet
     * information accessible through Meets.Meet()
     *
     * @param meetId an integer identifying the meet
     * @return a JSONArray containing an array of race results.
     */
    public static JSONArray Performances(int meetId) {
        String url = url_base + meetId + "/performances";

        return (JSONArray) FromURL(url).get("data");
    }


    /**
     * Returns the results for the given meetId.
     * Returns the desired fields for each individual instead of the default fields.
     * @param meetId an integer identifying the meet
     * @param fields a list of the desired fields for each individual
     * @return a JSONArray containing an array of race results.
     */
    public static JSONArray Performances(int meetId, List<String> fields) {
        StringBuilder url = new StringBuilder();

        url.append(url_base).append(meetId).append("/performances?fields=");

        for (String field : fields) {
            url.append(field).append("%2C");
        }

        return (JSONArray) FromURL(url.toString()).get("data");
    }


    /**
     * Returns a list of values related to the meet.
     * Default fields are id, name, dateStart, dateEnd, season, seasonYear,
     * venueCity, venueState, venueCountry, registrationActive.
     *
     * @param meetId an integer identifying the meet
     * @return a JSONObject containing data related to the meet
     */
    public static JSONObject Meet(int meetId) {
        String url = url_base + meetId;

        return FromURL(url);
    }


    /**
     *
     * @return a JSONObject containing a list of meets
     */
    public static JSONObject ListMeets() {
        return FromURL(url_base);
    }


    /**
     * Returns a list of meets based on
     *
     * @param query Map containing fields and values
     * @return a JSONObject containing a list of meets
     */
    public static JSONObject ListMeets(Map<String, String> query) {
        StringBuilder url = new StringBuilder();

        url.append(url_base).append("?");

        for (Map.Entry<String, String> entry : query.entrySet()) {
            url.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return FromURL(url.toString());
    }

    public static void main(String[] args) {
        List<String> fields = new ArrayList<>() {{
            add("mark");
            add("firstName");
        }};
        System.out.println(Performances(373799));
    }
}
