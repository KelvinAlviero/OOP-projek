import java.util.List;

//Level stuff
public class Level {
    private String name;
    private String backgroundImagePath;
    private List<BoundingBox> boundingBoxes;
    private BoundingBox transitionPoint;

    //Takes level info from Gamepanel and does some stuff
    public Level(String name, String backgroundImagePath, List<BoundingBox> boundingBoxes) {
        this.name = name;
        this.backgroundImagePath = backgroundImagePath;
        this.boundingBoxes = boundingBoxes;
        this.transitionPoint = transitionPoint;
    }

    public List<BoundingBox> getBoundingBoxes() {
        return boundingBoxes;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public String getName() {
        return name;
    }

    public BoundingBox getTransition(){
        return transitionPoint;
    }
}
