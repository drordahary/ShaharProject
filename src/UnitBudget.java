import java.util.ArrayList;

public class UnitBudget {
    private final int id;
    private String unitName;
    private float budget;
    private final ArrayList<Order> orders;

    public UnitBudget(int id, String unitName, float budget) {
        this.id = id;
        this.unitName = unitName;
        this.budget = budget;
        this.orders = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public float getBudget() {
        return this.budget;
    }

    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(int orderId) {
        this.orders.removeIf(order -> order.getId() == orderId);
    }

    @Override
    public String toString() {
        return String.format("Unit Budget ID: %d\nUnit Name: %s\nBudget: %.2f\n",
                this.id, this.unitName, this.budget);
    }
}
