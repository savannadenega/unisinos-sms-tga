package petrinet.estructure.arc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ArcTransition extends Arc {

    private Integer placeName;

    private List<Integer> transitionsToGoList = new ArrayList<>();

    public ArcTransition(Integer placeName) {
        this.placeName = placeName;
    }

}
