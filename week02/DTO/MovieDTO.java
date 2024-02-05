package week02.DTO;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.*;

public class MovieDTO {
    public String title;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public String release_date;
    public boolean video;
    public double vote_average;
    public int vote_count;
    public boolean adult;
    public String backdrop_path;
    public int[] genre_ids;
    public int id;
    public String original_language;
    transient String release_year;
    transient LocalDate release_dateLD;

    protected void finalizeLoading() {
        if(release_date!=null){
            release_year = release_date.split("-",2)[0];
            release_dateLD = LocalDate.parse(release_date);
        }
    }
}
