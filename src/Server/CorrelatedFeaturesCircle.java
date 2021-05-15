package Server;


public class CorrelatedFeaturesCircle {
    public final String feature1,feature2;
    public final float corrlation;
    public final Circle smallCircle;


    public CorrelatedFeaturesCircle(String feature1, String feature2, float corrlation, Circle smallCircle) {
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.corrlation = corrlation;
        this.smallCircle = smallCircle;
    }
}
