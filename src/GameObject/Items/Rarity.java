package GameObject.Items;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Rarity {
    COMMON("Обыкновенный",  0.5D),
    UNUSUAL("Необычный",  0.8D),
    RARE("Редкий",  1.0D),
    LEGENDARY("Легендарный",  4D),
    MYTHICAL("Мифический",  4.5D),
    CELESTIAL("Звёздный",  6D),
    TRANSCENDENT("Трансцендентный",  8D),
    DIVINE("Божественный",  12D),
    ETERNAL("Вечный",  25D);

    @JsonProperty("name")
    private final String name;
    @JsonProperty("quality")
    private final Double quality;

    Rarity(String name, Double quality) {
        this.name = name;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public Double getQuality() {
        return quality;
    }
}
