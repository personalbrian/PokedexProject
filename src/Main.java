import org.json.JSONObject;


public class Main {




    public static void main(String[] args) {
        API api = API.getInstance();
        JSONObject obj = api.Request("https://pokeapi.co/api/v2/pokemon?limit=151&offset=0");
        api.parseInitial(obj);
        try {
            Pokemon p = api.searchRequest("magmar");
            System.out.println(p.getId());
            Pokemon next = api.nextRequest(p);
            System.out.println(next.getName());
            Pokemon prev = api.prevRequest(p);
            System.out.println(prev.getName());
        } catch (PokemonNotFoundException e) {
            e.printStackTrace();
        }



    }

}
