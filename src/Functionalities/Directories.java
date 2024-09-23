package Functionalities;

public enum Directories {
    LOCATIONS_DIR("Locations\\"),
    SPHERES_DIR("Spheres\\"),
    ITEMS_DIR("Items\\");

    private final String dirName;

    Directories(String s) {
        this.dirName=s;
    }

    public String getDirName() {
        return dirName;
    }

    /**
     * Имя директории без \\ указателя вложенности
     * @return string dirName
     */
    public String getJustDirName() {
        return dirName;
    }
}
