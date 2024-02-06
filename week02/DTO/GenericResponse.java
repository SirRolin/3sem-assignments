package week02.DTO;

@SuppressWarnings("unused")
public abstract class GenericResponse<T> {
    public T[] results;
    public int page;
    public int total_pages;
    public int total_results;
}
