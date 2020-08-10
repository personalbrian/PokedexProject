import org.json.JSONObject;



//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://pokeapi.co/api/v2/pokemon/1/")).build();
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(Main::parse).join();
//"https://pokeapi.co/api/v2/pokemon?limit=151&offset=0"

public class Main {




    public static void main(String[] args) {
        API api = API.getInstance();
        JSONObject obj = api.Request("https://pokeapi.co/api/v2/pokemon?limit=151&offset=0");
        api.parseInitial(obj);
        try {
            Pokemon p = api.searchRequest("magmar");
            System.out.println(p.getId());
            Pokemon prev_p = api.nextRequest(p);
            System.out.println(prev_p.getName());
        } catch (PokemonNotFoundException e) {
            e.printStackTrace();
        }



    }

}
