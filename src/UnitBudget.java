public class UnitBudget {
    private final int id;
    private final String unitName;
    private final float budget;

    public UnitBudget(int id, String unitName, float budget) {
        this.id = id;
        this.unitName = unitName;
        this.budget = budget;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nUnit Name: %s\nBudget: %.2f",
                this.id, this.unitName, this.budget);
    }
}
