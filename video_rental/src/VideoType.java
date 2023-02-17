public enum VideoType {
    VHS(1, 1, 5),
    CD(2, 2, 3),
    DVD(3, 3, 2);

    private final int value;
    private final int penalty;
    private final int limit;

    VideoType(int value, int penalty, int limit) {
        this.value = value;
        this.penalty = penalty;
        this.limit = limit;
    }

    public int getValue() {
        return value;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getLimit() {
        return limit;
    }

    public static VideoType fromValue(int value) {
        for (VideoType e : VideoType.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
