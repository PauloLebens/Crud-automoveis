package br.com.empresa.automoveis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AutomovelCRUD {

    private static List<Automovel> repositorio = new ArrayList<>();
    private static int proximoId = 1; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        cadastrarAutomovel(new Automovel(proximoId++, "Honda", "Moto X", 2022));
        cadastrarAutomovel(new Carro(proximoId++, "Ford", "Ka", 2018, 4));

        do {
            exibirMenu();
            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número para a opção.");
                scanner.nextLine();
                opcao = -1;
                continue;
            }
            
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    operacaoCreate(scanner);
                    break;
                case 2:
                    operacaoRetrieve();
                    break;
                case 3:
                    operacaoUpdate(scanner);
                    break;
                case 4:
                    operacaoDelete(scanner);
                    break;
                case 0:
                    System.out.println("Programa encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n CRUD de Automoveis ---");
        System.out.println("1. Cadastrar Automovel (CREATE)");
        System.out.println("2. Listar Todos (RETRIEVE)");
        System.out.println("3. Atualizar Automovel (UPDATE)");
        System.out.println("4. Excluir Automovel (DELETE)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static void operacaoCreate(Scanner scanner) {
        System.out.println("\n  Cadastrar Novo Automóvel ---");
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        System.out.print("E um Carro? (S/N): ");
        String tipo = scanner.nextLine().toUpperCase();

        Automovel novoAutomovel;
        if (tipo.equals("S")) {
            System.out.print("Numero de Portas: ");
            int portas = scanner.nextInt();
            scanner.nextLine();
            novoAutomovel = new Carro(proximoId++, marca, modelo, ano, portas);
        } else {
            novoAutomovel = new Automovel(proximoId++, marca, modelo, ano);
        }

        cadastrarAutomovel(novoAutomovel);
        System.out.println("Automovel cadastrado com sucesso! ID: " + novoAutomovel.getId());
    }

    private static void cadastrarAutomovel(Automovel automovel) {
        repositorio.add(automovel); 
    }

    private static void operacaoRetrieve() {
        System.out.println("\n Automoveis Cadastrados ---");
        if (repositorio.isEmpty()) {
            System.out.println("Nenhum automovel cadastrado.");
            return;
        }

        
        for (Automovel a : repositorio) {
            System.out.println(a.getDados());
        }
    }

    private static void operacaoUpdate(Scanner scanner) {
        System.out.println("\n Atualizar Automovel ---");
        System.out.print("Digite o ID do automovel para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Automovel autoParaAtualizar = buscarPorId(id);

        if (autoParaAtualizar != null) {
            System.out.print("Novo Modelo (" + autoParaAtualizar.getModelo() + "): ");
            String novoModelo = scanner.nextLine();
            if (!novoModelo.isEmpty()) {
                autoParaAtualizar.setModelo(novoModelo);
            }

            if (autoParaAtualizar instanceof Carro) {
                Carro carro = (Carro) autoParaAtualizar; 
                System.out.print("Novo Numero de Portas (atual: " + carro.getNumeroPortas() + "): ");
                int novoPortas = scanner.nextInt();
                scanner.nextLine();
                carro.setNumeroPortas(novoPortas);
            }

            System.out.println("Automovel ID " + id + " atualizado com sucesso!");
        } else {
            System.out.println("Automovel com ID " + id + " nao encontrado.");
        }
    }

    private static Automovel buscarPorId(int id) {
        for (Automovel a : repositorio) {
            if (a.getId() == id) { 
                return a;
            }
        }
        return null;
    }

    private static void operacaoDelete(Scanner scanner) {
        System.out.println("\n Excluir Automovel ---");
        System.out.print("Digite o ID do automovel para excluir: ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("ID invalido. Por favor, digite um numero inteiro.");
            scanner.nextLine();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        Automovel autoParaExcluir = buscarPorId(id);

        if (autoParaExcluir != null) {
            repositorio.remove(autoParaExcluir); 
            System.out.println("Automovel ID " + id + " excluido com sucesso!");
        } else {
            System.out.println("Automovel com ID " + id + " nao encontrado.");
        }
    }
}
