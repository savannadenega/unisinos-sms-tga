package versao2.rules;

import versao2.estructure.Place;
import versao2.estructure.Transition;
import versao2.estructure.arc.ArcPlace;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;


public class PedriNetBuilder {

    List<Place> placeList;
    List<Transition> transitionList;

    public void buildPetriNet(int placesAmount, int transitionsAmount,
                              List<ArcPlaceSimple> arcPlaceSimpleList,
                              List<Integer> amountTokensPlacesList,
                              List<Integer> weightArcsList){

        generatePlaces(placesAmount);
        generateTransitions(transitionsAmount);

        bindArcsPlacesTransitions(arcPlaceSimpleList);

        //TODO
//        addTokensToPlaces(amountTokensPlacesList);

        //TODO
//        addWeightsToArcs(weightArcsList);

    }

    private void generatePlaces(int placesAmount) {
        placeList = new ArrayList<>(placesAmount);
    }

    private void generateTransitions(int transitionsAmount) {
        transitionList = new ArrayList<>(transitionsAmount);
    }

    private void bindArcsPlacesTransitions(List<ArcPlaceSimple> arcPlaceSimpleList) {

        for(int i = 1; i <= arcPlaceSimpleList.size(); i++){
            Transition actualTransition = transitionList.get(i);

            ArcPlace arcPlace = ArcPlace.builder()
                .placesToGo(Lists.newArrayList(placeList.get(i-1)))
                .placesBefore(Lists.newArrayList(placeList.get(i)))
                .build();

            //TODO
//            actualTransition = Transition.builder()
//                .arcWithPlacesToGoAndPlacesBefore()
//                .build();

        }

    }

}
