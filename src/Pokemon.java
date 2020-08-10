import java.util.HashSet;

public class Pokemon {

    public String url_image;
    public String name; //done
    public HashSet<String> types;
    public String entry;
    public int atk_stat;
    public int def_stat;
    public int hp_stat;
    public int spe_stat;
    public int spatk_stat;
    public int spdef_stat;
    public int id;

    public Pokemon() {
        types = new HashSet<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getTypes() {
        return types;
    }


    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public int getAtk_stat() {
        return atk_stat;
    }

    public void setAtk_stat(int atk_stat) {
        this.atk_stat = atk_stat;
    }

    public int getDef_stat() {
        return def_stat;
    }

    public void setDef_stat(int def_stat) {
        this.def_stat = def_stat;
    }

    public int getHp_stat() {
        return hp_stat;
    }

    public void setHp_stat(int hp_stat) {
        this.hp_stat = hp_stat;
    }

    public int getSpe_stat() {
        return spe_stat;
    }

    public void setSpe_stat(int spe_stat) {
        this.spe_stat = spe_stat;
    }

    public int getSpatk_stat() {
        return spatk_stat;
    }

    public void setSpatk_stat(int spatk_stat) {
        this.spatk_stat = spatk_stat;
    }

    public int getSpdef_stat() {
        return spdef_stat;
    }

    public void setSpdef_stat(int spdef_stat) {
        this.spdef_stat = spdef_stat;
    }
}
