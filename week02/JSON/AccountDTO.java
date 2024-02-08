package week02.JSON;

public class AccountDTO {
    public String id;
    public String balance;
    public boolean isActive;

    @Override
    public String toString() {
        return "Account: id=" + id + " - at " + balance + " active=" + isActive;
    }
}
