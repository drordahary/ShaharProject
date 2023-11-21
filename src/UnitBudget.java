import java.util.ArrayList;

public class UnitBudget {
    private final int id;
    private final String unitName;
    private final float budget;
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

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(int orderId) {
        this.orders.removeIf(order -> order.getId() == orderId);
    }

    @Override
    public String toString() {
        return String.format("Unit Budget ID: %d\nUnit Name: %s\nBudget: %.2f",
                this.id, this.unitName, this.budget);
    }
}
