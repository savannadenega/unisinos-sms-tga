package version2;

import version2.rules.PetriNet;

import java.util.Scanner;

public class App {



    private App() {
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PetriNet petriNet = new PetriNet();

        petriNet.buildPetriNetfromTxtFile(System.getProperty("user.dir") + "/src/test/resources/fileReadExample.txt");
        //OU
        //TODO se não achar o arquivo, adicionar leitura do teclado de forma iterativa, usando métodos da PetriNet
        //petriNet.buildPetriNetFromInteractiveInput();

        //esperando clicar ENTER para rodar a transicao
        //cada vez que rodar a transição, passar tokens (de acordo com o peso de cada arco) de cada place para transitions (em todos os places)
        while (true) {
            String input = scanner.nextLine();
            if (!input.equals("")) {

                //TODO perguntar se deseja salvar a rede em um arquivo externo txt

            } else {

                petriNet.fire();
                //TODO imprimir transição no log

                break;
            }
        }

    }



}
