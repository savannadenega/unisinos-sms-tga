package petrinet;

import petrinet.rules.PetriNet;

import java.util.Scanner;

public class App {

    private App() {
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PetriNet petriNet = new PetriNet();

        petriNet.buildPetriNetfromTxtFile(System.getProperty("user.dir") + "/src/test/resources/petriNet.txt");
        //OU
        //TODO se não achar o arquivo, adicionar leitura do teclado de forma iterativa, usando métodos da PetriNet
        //petriNet.buildPetriNetFromInteractiveInput();

        //esperando clicar ENTER para rodar a transicao
        //cada vez que rodar a transição, passar tokens (de acordo com o peso de cada arco) de cada place para transitions (em todos os places)

        int cycle = 1;
        petriNet.printCurrentStatus(cycle);

        while (true) {
            cycle++;
            String input = scanner.nextLine();
            if (!input.equals("S")) {

                //TODO perguntar se deseja salvar a rede em um arquivo externo txt

            } else {

                petriNet.fireOverRules();

                petriNet.printCurrentStatus(cycle);

            }
        }

    }



}
