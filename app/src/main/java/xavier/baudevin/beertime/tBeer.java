/**
 * @autor:      Xavier Baudevin <xavier.bau@hotmail.fr>
 * @date:       29/01/16
 * @project:    BeerTime
 * @desciption: object with the data for a beer
 */

package xavier.baudevin.beertime;

public class tBeer {
    private int id;
    private String name;
    private String type;
    private int rating;
    private int ml;
    private Double alcool;
    private String comment;

    /**
     * Init
     */
    public tBeer (String name, String type, int ml, Double alcool, int rating, String comment){
        this.name = name;
        this.type = type;
        this.ml = ml;
        this.alcool = alcool;
        this.rating = rating;
        this.comment = comment;
    }

    public tBeer(){};

    /**
     * Setter
     */

    public void setIdBeer(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setMl(int ml) {
        this.ml = ml;
    }

    public void setAlcool(Double alcool) {
        this.alcool = alcool;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     * Getter
     */

    public int getIdBeer() {
        return id;
    }

    public String getName() { return name; }

    public String getType() {
        return type;
    }

    public int getRating() {
        return rating;
    }

    public int getMl() {
        return ml;
    }

    public Double getAlcool() {
        return alcool;
    }

    public String getComment() {
        return comment;
    }

}
