public class Item {
    private final int id;
    private final String description;
    private final float price;
    private final int amount;

    public Item(int id, String description, float price, int amount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nDescription: %s\nPrice: %.2f\nAmount: %d",
                this.id, this.description, this.price, this.amount);
    }
}
