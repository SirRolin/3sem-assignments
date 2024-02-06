package week02.DTO;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class Movie implements DTO{
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

    @Override
    public void finalizeLoading() {
        if(!release_date.isBlank()){
            release_year = release_date.split("-",2)[0];
            release_dateLD = LocalDate.parse(release_date);
        }
    }
}
