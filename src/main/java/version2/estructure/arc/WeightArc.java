package version2.estructure.arc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeightArc {

    private int weight = 1;

    public WeightArc() {
    }

    public WeightArc(int weight) {
        this.weight = weight;
    }
}
