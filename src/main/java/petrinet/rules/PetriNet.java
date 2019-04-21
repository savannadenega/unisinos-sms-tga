package petrinet.rules;

import lombok.Getter;
import petrinet.structure.Transition;
import petrinet.structure.arc.ArcTransition;
import petrinet.structure.arc.WeightArc;
import petrinet.structure.Place;
import petrinet.structure.arc.ArcPlace;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class PetriNet {

    Map<Integer, Place> placeLinkedHashMap = new LinkedHashMap<>();
    LinkedHashMap<Integer, Transition> transitionLinkedHashMap = new LinkedHashMap<>();
    Map<String, ArcPlace> arcPlaceLinkedHashMap = new LinkedHashMap<>();
    Map<String, ArcTransition> arcTransitionLinkedHashMap = new LinkedHashMap<>();
    //TODO fazer classe padrão de hash para weightArcLinkedHashMap, padrão é L:T
    Map<String, WeightArc> weightArcLinkedHashMap = new LinkedHashMap<>();

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

                default:
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

        Transition transition = transitionLinkedHashMap.get(tPosition);
        if (transition == null) {
            transition = new Transition();
            transitionLinkedHashMap.put(tPosition, transition);
        }

        Place place = placeLinkedHashMap.get(lPosition);
        if (place == null) {
            place = new Place();
            placeLinkedHashMap.put(lPosition, place);
        }

        ArcTransition arcTransition = new ArcTransition(tPosition);
        arcTransitionLinkedHashMap.put(lPosition + ":" + tPosition, arcTransition);

        place.getArcsWithTransitionsToGoList().add(arcTransitionLinkedHashMap.get(lPosition + ":" + tPosition));

        ArcPlace arcPlace = new ArcPlace(placeLinkedHashMap.get(lPosition));
        arcPlaceLinkedHashMap.put(tPosition + ":" + lPosition, arcPlace);

        transition.getArcPlaceWithPlacesBeforeList().add(arcPlaceLinkedHashMap.get(tPosition + ":" + lPosition));

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

        Transition transition = transitionLinkedHashMap.get(tPosition);
        if (transition == null) {
            transition = new Transition();
            transitionLinkedHashMap.put(tPosition, transition);
        }

        Place place = placeLinkedHashMap.get(lPosition);
        if (place == null) {
            place = new Place();
            placeLinkedHashMap.put(lPosition, place);
        }

        ArcPlace arcPlace = new ArcPlace(placeLinkedHashMap.get(lPosition));
        arcPlaceLinkedHashMap.put(tPosition + ":" + lPosition, arcPlace);

        transition.getArcPlaceWithPlacesToGoList().add(arcPlaceLinkedHashMap.get(tPosition + ":" + lPosition));

    }

    private void bindQuantidadeMarcasNoLugar(String[] linhaAtualPartes) {

        String[] lPositionArray = linhaAtualPartes[1].split(":");
        int lPosition = Integer.parseInt(lPositionArray[1]);

        int tokenAmount = Integer.parseInt(linhaAtualPartes[2]);

        Place place = placeLinkedHashMap.get(lPosition);
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

        WeightArc weightArc = weightArcLinkedHashMap.get(lPosition + ":" + tPosition);
        if (weightArc == null) {
            weightArc = new WeightArc(arcWeight);
            weightArcLinkedHashMap.put(lPosition + ":" + tPosition, weightArc);
        } else {
            weightArc.setWeight(arcWeight);
        }

        ArcPlace arcPlace = arcPlaceLinkedHashMap.get(tPosition + ":" + lPosition);
        arcPlace.setWeightArc(weightArc);

    }

    private void bindPesoDoArcoDoLugar(String[] linhaAtualPartes) {

        String[] lPositionArray = linhaAtualPartes[1].split(":");
        String lPosition = lPositionArray[1];

        String[] tPositionArray = linhaAtualPartes[3].split(":");
        String tPosition = tPositionArray[1];

        int arcWeight = Integer.parseInt(linhaAtualPartes[4]);

        WeightArc weightArc = weightArcLinkedHashMap.get(lPosition + ":" + tPosition);
        if (weightArc == null) {
            weightArc = new WeightArc(arcWeight);
            weightArcLinkedHashMap.put(lPosition + ":" + tPosition, weightArc);
        } else {
            weightArc.setWeight(arcWeight);
        }

        ArcPlace arcPlace = arcPlaceLinkedHashMap.get(tPosition + ":" + lPosition);
        arcPlace.setWeightArc(weightArc);

        ArcTransition arcTransition = arcTransitionLinkedHashMap.get(lPosition + ":" + tPosition);
        arcTransition.setWeightArc(weightArc);

    }

    public void fireOverRules() {

        for (Map.Entry<Integer, Transition> transition : transitionLinkedHashMap.entrySet()) {

            if (allPlacesHaveToken(transition)) {

                for (ArcPlace arcPlaceBefore : transition.getValue().getArcPlaceWithPlacesBeforeList()) {

                    Place placeBefore = arcPlaceBefore.getPlace();

                    int weightAmountArcs = 0;
                    for (ArcTransition arcTransition : placeBefore.getArcsWithTransitionsToGoList()) {
                        weightAmountArcs += arcTransition.getWeightArc().getWeight();
                    }

                    //regra para verificar se a quantidade de tokens no place é o bastante para a quantidade de transições, se não sortear qual receber
                    if (placeBefore.getTokenAmount() < weightAmountArcs) {

                        for (int i = 0; i < placeBefore.getTokenAmount(); i++) {
                            int random = (int) (Math.random() * (placeBefore.getArcsWithTransitionsToGoList().size()) + 0);
                            Transition transitionRandomToBeFired = getElementByIndex(transitionLinkedHashMap, random);
                            fire(transitionRandomToBeFired, arcPlaceBefore);
                        }

                    } else {
                        fire(transition.getValue(), arcPlaceBefore);
                    }
                }
            }
        }

    }

    private boolean allPlacesHaveToken(Map.Entry<Integer, Transition> transition) {
        //regra para verificar se todos os places antes da transição atual tem token, para fazer a transição
        for (ArcPlace arcPlaceBefore : transition.getValue().getArcPlaceWithPlacesBeforeList()) {
            if (arcPlaceBefore.getPlace().getTokenAmount() < 1) {
                return false;
            }
        }
        return true;
    }

    private void fire(Transition transition, ArcPlace arcPlaceBefore) {

        Place placeBefore = arcPlaceBefore.getPlace();

        int tokenAmount = placeBefore.getTokenAmount();
        int arcWeightPlaceBefore = arcPlaceBefore.getWeightArc().getWeight();

        if (tokenAmount >= arcWeightPlaceBefore) {

            placeBefore.setTokenAmount(
                placeBefore.getTokenAmount() - arcWeightPlaceBefore
            );

            for (ArcPlace arcPlaceToGo : transition.getArcPlaceWithPlacesToGoList()) {

                Place placeToGo = arcPlaceToGo.getPlace();

                int arcWeightPlaceToGo = arcPlaceToGo.getWeightArc().getWeight();

                placeToGo.setTokenAmount(
                    placeToGo.getTokenAmount() + arcWeightPlaceToGo
                );

            }
        }


    }

    public Transition getElementByIndex(LinkedHashMap map, int index) {
        return (Transition) map.get((map.keySet().toArray())[index]);
    }

}
