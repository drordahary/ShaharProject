public class Item {
    private int id;
    private String description;
    private int amount;

    public Item(int id, String description, int amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nDescription: %s\nAmount: %d",
                this.id, this.description, this.amount);
    }
}
