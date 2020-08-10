import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class API {
    private static API single_api_instance = null;
    public HashMap<String, String> pokemon_bank;
    private static final int max_num_pokemon = 151;


    private API() {
        this.pokemon_bank = new HashMap<>();
    }

    public static API getInstance() {
        if (single_api_instance == null) {
            single_api_instance = new API();
        }
        return single_api_instance;
    }

    public HashMap<String, String> getPokemon_bank() {
        return pokemon_bank;
    }

    public JSONObject Request(String str) {
        HttpURLConnection connection = null;
        String str_url = str;
        String line;
        BufferedReader reader;
        StringBuffer responsecontent = new StringBuffer();

        try {
            URL url = new URL(str_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responsecontent.append(line);
            }
            reader.close();
            JSONObject obj = new JSONObject(responsecontent.toString());
            connection.disconnect();
            return obj;


        } catch (MalformedURLException e) {
            System.out.println("User Unable to manipulate address (MalformedURLException)");
            connection.disconnect();
            return null;
        } catch (IOException e) {
            System.out.println("User Unable to manipulate address (IOException)");
            connection.disconnect();
            return null;
        }

    }


    public void parseInitial(JSONObject data) {
        JSONArray array = data.getJSONArray("results");
        for (int i = 0; i < array.length(); i++) {
            JSONObject pokemon = array.getJSONObject(i);
            pokemon_bank.put(pokemon.getString("name"), pokemon.getString("url"));
        }
    }

    public Pokemon prevRequest(Pokemon pokemon) {
        Pokemon new_pokemon;
        JSONObject obj;
        String str_url;
        String name;
        int curr_id = pokemon.getId();
        if (curr_id != 1) {
            int new_id = curr_id - 1;
            str_url = "https://pokeapi.co/api/v2/pokemon/" + new_id + "/";
        } else {
            str_url = "https://pokeapi.co/api/v2/pokemon/" + max_num_pokemon + "/";
        }
        name = Request(str_url).getJSONArray("forms").getJSONObject(0).getString("name");
        obj = Request(str_url);
        new_pokemon = producePokemon(obj, name);
        return new_pokemon;
    }

    public Pokemon nextRequest(Pokemon pokemon) {
        Pokemon new_pokemon;
        JSONObject obj;
        String str_url;
        String name;
        int curr_id = pokemon.getId();
        if (curr_id != max_num_pokemon) {
            int new_id = curr_id + 1;
            str_url = "https://pokeapi.co/api/v2/pokemon/" + new_id + "/";
        } else {
            str_url = "https://pokeapi.co/api/v2/pokemon/" + 1 + "/";
        }
        name = Request(str_url).getJSONArray("forms").getJSONObject(0).getString("name");
        obj = Request(str_url);
        new_pokemon = producePokemon(obj, name);
        return new_pokemon;
    }


    public Pokemon searchRequest(String str) throws PokemonNotFoundException {
        if (pokemon_bank.containsKey(str)) {
            JSONObject obj = Request(pokemon_bank.get(str));
            Pokemon pokemon = producePokemon(obj, str);
            return pokemon;
        } else {
            throw new PokemonNotFoundException();
        }

    }


    public Pokemon producePokemon(JSONObject obj, String str) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(str.toUpperCase());
        pokemon.setId(obj.getInt("id"));
        JSONArray array = obj.getJSONArray("stats");
        pokemon.setHp_stat(array.getJSONObject(0).getInt("base_stat"));
        pokemon.setAtk_stat(array.getJSONObject(1).getInt("base_stat"));
        pokemon.setDef_stat(array.getJSONObject(2).getInt("base_stat"));
        pokemon.setSpatk_stat(array.getJSONObject(3).getInt("base_stat"));
        pokemon.setSpdef_stat(array.getJSONObject(4).getInt("base_stat"));
        pokemon.setSpe_stat(array.getJSONObject(5).getInt("base_stat"));
        String str_img = obj.getJSONObject("sprites").getString("front_default");
        pokemon.setUrl_image(str_img);
        JSONArray types_array = obj.getJSONArray("types");
        for (int i = 0; i < types_array.length(); i++) {
            String str_type = types_array.getJSONObject(i).getJSONObject("type").getString("name");
            pokemon.getTypes().add(str_type);
        }
        String flavour_string = obj.getJSONObject("species").getString("url");
        JSONObject species_obj = Request(flavour_string);
        pokemon.setEntry(species_obj.getJSONArray("flavor_text_entries").getJSONObject(20).getString("flavor_text"));
        return pokemon;
    }
}
