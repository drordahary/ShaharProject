public class Item {
    private final int id;
    private String description;
    private float price;
    private int amount;

    public Item(int id, String description, float price, int amount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public float getPrice() {
        return this.price;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Item ID: %d\nDescription: %s\nPrice: %.2f\nAmount: %d\n\n",
                this.id, this.description, this.price, this.amount);
    }
}
