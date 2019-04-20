package petrinet.rules;

import lombok.Getter;
import petrinet.structure.Transition;
import petrinet.structure.arc.WeightArc;
import petrinet.structure.Place;
import petrinet.structure.arc.ArcPlace;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import petrinet.structure.arc.ArcTransition;

@Getter
public class PetriNet {

    Map<Integer, Place> placeHashMap = new HashMap<>();
    Map<Integer, Transition> transitionHashMap = new HashMap<>();
    Map<String, ArcPlace> arcPlaceHashMap = new HashMap<>();
    Map<String, ArcTransition> arcTransitionHashMap = new HashMap<>();
    //TODO fazer padrão de hash para weightArcHashMap, padrão é L:T
    Map<String, WeightArc> weightArcHashMap = new HashMap<>();

    public PetriNet() {
    }

    public void buildPetriNetfromTxtFile(String path) {

        FileReader arq = null;
        BufferedReader read = null;
        String line = "";
        try {

            arq = new FileReader(path);
            read = new BufferedReader(arq);
            line = read.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (line != null) {

            String[] linhaAtualPartes = line.split("-");

            //compara o primeiro item da quebra
            //TODO fazer uma pattern factory, para cada montagem uma strategy

            switch (linhaAtualPartes[0]) {

                case "Lugares de entrada na transição":
                    bindLugaresDeEntradaNaTransicao(linhaAtualPartes);
                    break;

                case "Lugares de saída da transição":
                    bindLugaresDeSaidaNaTransicao(linhaAtualPartes);
                    break;

                case "Quantidade marcas no lugar":
                    bindQuantidadeMarcasNoLugar(linhaAtualPartes);
                    break;

                case "Peso do arco da transição":
                    bindPesoDoArcoDaTransicao(linhaAtualPartes);
                    break;

                case "Peso do arco do lugar":
                    bindPesoDoArcoDoLugar(linhaAtualPartes);
                    break;
            }

            try {
                line = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void buildPetriNetFromInteractiveInput(String input) {
        //TODO extrair esses dois métodos de input para a classe input
        //TODO para o método de leitura do TXT, para cada método de bind dessa classe aqui, extrair o trecho de código split e fazer um método intermediador com o split, depois chamar o bind dessa classe aqui

        //TODO para esse método aqui, fazer chamadas a modo de ir passando os parâmetros necessários para os métodos bind
//        Scanner ler = new Scanner(System.in);

//        System.out.printf("Informe o nome de arquivo texto:\n");
//        String nome = ler.nextLine();

//        System.out.printf("\nConteúdo do arquivo texto:\n");

    }

    protected void bindLugaresDeEntradaNaTransicao(String[] linhaAtualPartes) {

        String[] tArray;
        Integer tPosition;
        String[] lArray;
        Integer lPosition;

        tArray = linhaAtualPartes[1].split(":");
        tPosition = Integer.parseInt(tArray[1]);

        lArray = linhaAtualPartes[2].split(":");
        lPosition = Integer.parseInt(lArray[1]);

        Transition transition = transitionHashMap.get(tPosition);
        if (transition == null) {
            transition = new Transition();
            transitionHashMap.put(tPosition, transition);
        }

        Place place = placeHashMap.get(lPosition);
        if (place == null) {
            place = new Place();
            placeHashMap.put(lPosition, place);
        }

        ArcTransition arcTransition = new ArcTransition(lPosition);
        arcTransition.getTransitionsToGoList().add(tPosition);
        arcTransitionHashMap.put(lPosition + ":" + tPosition, arcTransition);

        place.getArcsWithTransitionsToGoList().add(arcTransitionHashMap.get(lPosition + ":" + tPosition));

        ArcPlace arcPlace = new ArcPlace(tPosition);
        arcPlace.getPlaceList().add(placeHashMap.get(lPosition));
        arcPlaceHashMap.put(tPosition + ":" + lPosition, arcPlace);

        transition.getArcPlaceWithPlacesBeforeList().add(arcPlaceHashMap.get(tPosition + ":" + lPosition));

    }

    private void bindLugaresDeSaidaNaTransicao(String[] linhaAtualPartes) {

        String[] tArray;
        Integer tPosition;
        String[] lArray;
        Integer lPosition;

        tArray = linhaAtualPartes[1].split(":");
        tPosition = Integer.parseInt(tArray[1]);

        lArray = linhaAtualPartes[2].split(":");
        lPosition = Integer.parseInt(lArray[1]);

        Transition transition = transitionHashMap.get(tPosition);
        if (transition == null) {
            transition = new Transition();
            transitionHashMap.put(tPosition, transition);
        }

        Place place = placeHashMap.get(lPosition);
        if (place == null) {
            place = new Place();
            placeHashMap.put(lPosition, place);
        }

        ArcPlace arcPlace = new ArcPlace(tPosition);
        arcPlace.getPlaceList().add(placeHashMap.get(lPosition));
        arcPlaceHashMap.put(tPosition + ":" + lPosition, arcPlace);

        transition.getArcPlaceWithPlacesToGoList().add(arcPlaceHashMap.get(tPosition + ":" + lPosition));

    }

    private void bindQuantidadeMarcasNoLugar(String[] linhaAtualPartes) {

        String[] lPositionArray = linhaAtualPartes[1].split(":");
        int lPosition = Integer.parseInt(lPositionArray[1]);

        int tokenAmount = Integer.parseInt(linhaAtualPartes[2]);

        Place place = placeHashMap.get(lPosition);
        if (place != null) {
            place.setTokenAmount(tokenAmount);
        }

    }

    private void bindPesoDoArcoDaTransicao(String[] linhaAtualPartes) {

        String[] tPositionArray = linhaAtualPartes[1].split(":");
        String tPosition = tPositionArray[1];

        String[] lPositionArray = linhaAtualPartes[3].split(":");
        String lPosition = lPositionArray[1];

        int arcWeight = Integer.parseInt(linhaAtualPartes[4]);

        WeightArc weightArc = weightArcHashMap.get(lPosition + ":" + tPosition);
        if (weightArc == null) {
            weightArc = new WeightArc(arcWeight);
            weightArcHashMap.put(lPosition + ":" + tPosition, weightArc);
        } else {
            weightArc.setWeight(arcWeight);
        }

        ArcPlace arcPlace = arcPlaceHashMap.get(tPosition + ":" + lPosition);
        arcPlace.setWeightArc(weightArc);

    }

    private void bindPesoDoArcoDoLugar(String[] linhaAtualPartes) {

        String[] lPositionArray = linhaAtualPartes[1].split(":");
        String lPosition = lPositionArray[1];

        String[] tPositionArray = linhaAtualPartes[3].split(":");
        String tPosition = tPositionArray[1];

        int arcWeight = Integer.parseInt(linhaAtualPartes[4]);

        WeightArc weightArc = weightArcHashMap.get(lPosition + ":" + tPosition);
        if (weightArc == null) {
            weightArc = new WeightArc(arcWeight);
            weightArcHashMap.put(lPosition + ":" + tPosition, weightArc);
        } else {
            weightArc.setWeight(arcWeight);
        }

        ArcPlace arcPlace = arcPlaceHashMap.get(tPosition + ":" + lPosition);
        arcPlace.setWeightArc(weightArc);

        ArcTransition arcTransition = arcTransitionHashMap.get(lPosition + ":" + tPosition);
        arcTransition.setWeightArc(weightArc);

    }

    public void fire() {

        boolean stop = false;

        for (Map.Entry<Integer, Transition> transition : transitionHashMap.entrySet()) {
            for (ArcPlace arcPlaceBefore : transition.getValue().getArcPlaceWithPlacesBeforeList()) {

                for (Place placeBefore : arcPlaceBefore.getPlaceList()) {
                    if (placeBefore.getTokenAmount() < 1) {
                        stop = true;
                        break;
                    }
                }

                if (stop) {
                    break;
                }

                for (Place placeBefore : arcPlaceBefore.getPlaceList()) {

                    int tokenAmount = placeBefore.getTokenAmount();
                    int arcWeightPlaceBefore = arcPlaceBefore.getWeightArc().getWeight();

                    if (tokenAmount >= arcWeightPlaceBefore) {
                        placeBefore.setTokenAmount(
                            placeBefore.getTokenAmount() - arcWeightPlaceBefore
                        );

                        for (ArcPlace arcPlaceToGo : transition.getValue().getArcPlaceWithPlacesToGoList()) {
                            for (Place placeToGo : arcPlaceToGo.getPlaceList()) {

                                int arcWeightPlaceToGo = arcPlaceToGo.getWeightArc().getWeight();

                                placeToGo.setTokenAmount(
                                    placeToGo.getTokenAmount() + arcWeightPlaceToGo
                                );
                            }
                        }
                    }

                }
            }

        }

    }

}
