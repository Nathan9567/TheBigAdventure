package fr.uge.thebigadventure.skin.background;

public enum Decoration implements ElementMap {
    ALGAE, CLOUD, FLOWER, FOLIAGE, GRASS, LADDER, LILY, PLANK, REED, ROAD, SPROUT, TILE, TRACK, VINE;

    private final String name = this.toString().toUpperCase();
    private String encoding = null;

    @Override
    public String getEncoding() {
        return encoding;
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String getName() {
        return name;
    }
}
