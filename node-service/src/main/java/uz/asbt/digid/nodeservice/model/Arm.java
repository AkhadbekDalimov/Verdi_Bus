package uz.asbt.digid.nodeservice.model;

public class Arm {
    private String jar;
    private String title;
    private String version;
    private String description;
    private int id;

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Arm{" +
                "jar='" + jar + '\'' +
                ", title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
