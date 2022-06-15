package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    static int lastPhoneId = 0;

    static class Phone {
        public int id;
        public String model;
        public int price;
        public int amount;

        public Phone(int id, String model, int price, int amount) {
            this.id = id;
            this.model = model;
            this.price = price;
            this.amount = amount;
        }
    }

    static Phone createPhone() {
        Scanner scanner = new Scanner(System.in);

        lastPhoneId++;

        System.out.print("Введите модель телефона: ");
        String model = scanner.nextLine();

        System.out.print("Введите цену телефона: ");
        int price = scanner.nextInt();

        System.out.print("Введите количество телефонов на складе: ");
        int amount = scanner.nextInt();

        return new Phone(lastPhoneId, model, price, amount);
    }

    static Phone[] createEmptyPhonesArray() {
        return new Phone[0];
    }

    static Phone[] addPhoneToEndOfArray(Phone[] phones, Phone insertPhone) {
        Phone[] tempPhones = new Phone[phones.length + 1];

        for (int i = 0; i < phones.length; i++) {
            tempPhones[i] = phones[i];
        }

        tempPhones[tempPhones.length - 1] = insertPhone;

        return tempPhones;
    }

    static void printlnTableHeader() {
        System.out.println(String.format("%-3s%-12s%-8s%-18s", "ИД", "Модель", "Цена", "Остаток на складе"));
    }

    static void printlnPhone(Phone phone) {
        System.out.println(String.format("%-3d%-12s%-8d%-18d", phone.id, phone.model, phone.price, phone.amount));
    }

    static void printlnPhones(Phone[] phones) {
        if (phones.length == 0) {
            System.out.println("Список телефонов пуст");
            return;
        }

        for (int i = 0; i < phones.length; i++) {
            printlnPhone(phones[i]);
        }
        System.out.println("Список телефонов насчитывает " + phones.length + " элем.");
    }

    static void printlnSeparator() {
        System.out.println("-".repeat(15));
    }

    static void printlnMenu() {
        System.out.println("Меню");
        System.out.println("1. Добавить новый телефон");
        System.out.println("2. Сохранить телефоны в текстовый файл");
        System.out.println("3. Загрузить телефоны из текстового файла");
        System.out.println("0. Выйти из программы");
    }

    static int getChosenMenuPoint() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер нужного пункта меню: ");
        return scanner.nextInt();
    }

    static void savePhonesToTxtFile(String filename, Phone[] phones) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(Integer.toString(phones.length));
        bufferedWriter.newLine();

        for (int i = 0; i < phones.length; i++) {
            bufferedWriter.write(Integer.toString(phones[i].id));
            bufferedWriter.newLine();

            bufferedWriter.write(phones[i].model);
            bufferedWriter.newLine();

            bufferedWriter.write(Integer.toString(phones[i].price));
            bufferedWriter.newLine();

            bufferedWriter.write(Integer.toString(phones[i].amount));
            bufferedWriter.newLine();
        }
        bufferedWriter.write(Integer.toString(lastPhoneId));
        bufferedWriter.newLine();


        bufferedWriter.close();
        fileWriter.close();
    }

    static Phone[] loadPhonesFromTxtFile(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int phonesLength = Integer.parseInt(bufferedReader.readLine());
        Phone[] phones = new Phone[phonesLength];

        for (int i = 0; i < phones.length; i++) {
            int id = Integer.parseInt(bufferedReader.readLine());
            String model = bufferedReader.readLine();
            int price = Integer.parseInt(bufferedReader.readLine());
            int amount = Integer.parseInt(bufferedReader.readLine());

            phones[i] = new Phone(id, model, price, amount);
        }

        lastPhoneId = Integer.parseInt(bufferedReader.readLine());

        bufferedReader.close();
        fileReader.close();

        return phones;
    }

    public static void main(String[] args) throws IOException {
        Phone[] phones = createEmptyPhonesArray();

        while (true) {
            printlnTableHeader();
            printlnPhones(phones);
            printlnSeparator();
            printlnMenu();
            int menuPoint = getChosenMenuPoint();

            switch (menuPoint) {
                case 1: {
                    Phone insertPhone = createPhone();
                    phones = addPhoneToEndOfArray(phones, insertPhone);
                }
                break;

                case 2: {
                    Scanner scanner = new Scanner(System.in);

                    System.out.print("Введите имя файла: ");
                    String filename = scanner.nextLine();

                    savePhonesToTxtFile(filename, phones);
                }
                break;

                case 3: {
                    Scanner scanner = new Scanner(System.in);

                    System.out.print("Введите имя файла: ");
                    String filename = scanner.nextLine();

                    phones = loadPhonesFromTxtFile(filename);
                }
                break;

                case 0: {
                    System.exit(0);
                }
                break;
            }
        }
    }
}
