package filetest;

/**
 * Used to create objects in the form of a pair
 */
public class Pair {

    /**
     * The name of this Pair.
     */
    private final String name;

    /**
     * The value of this Pair.
     */
    private final String value;

    /**
     * Gets the name of this Pair.
     *
     * @return this Pair's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of this Pair.
     *
     * @return this Pair's value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Creates a new Pair with the given name
     * and value.
     *
     * @param name  this Pair's name
     * @param value this Pair's value
     */
    public Pair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) o;
        return this.name.equals(pair.getName()) &&
                this.value.equals(pair.getValue());
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ value.hashCode();
    }
}
