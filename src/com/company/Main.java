package com.company;

import java.io.*;
import java.util.Scanner;

//its main class
public class Main {
//region utils methods
    static String inputString(String message) {
        boolean isValidInput;
        String output = "";
        do {
            try {
                isValidInput = true;
                Scanner scanner = new Scanner(System.in);

                System.out.print(message);
                output = scanner.nextLine();
            } catch (Exception e) {
                isValidInput = false;
                System.out.println("Ошибка ввода. Пожалуйста повторите ввод");
            }

        } while (isValidInput == false);

        return output;
    }

    static int inputInt(String message, int min, int max) {
        boolean isValidInput;
        int output = 0;
        do {
            try {
                isValidInput = true;
                Scanner scanner = new Scanner(System.in);

                System.out.print(message);
                output = scanner.nextInt();

                if (output < min || output > max) {
                    System.out.println("Ошибка ввода. Вы вышли за границы диапазона от " + min + " до " + max);
                    throw new Exception();
                }

            } catch (Exception e) {
                isValidInput = false;
                System.out.println("Ошибка ввода. Пожалуйста повторите ввод");
            }

        } while (isValidInput == false);

        return output;
    }
//endregion

//region global variables
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
//endregion

//region support phones methods
    static Phone getPhoneById(Phone[] phones, int id) {
        for (int i = 0; i < phones.length; i++) {
            if (phones[i].id == id) {
                return phones[i];
            }
        }
        return null;
    }

    static int getIndexPhoneById(Phone[] phones, int id) {
        for (int i = 0; i < phones.length; i++) {
            if (phones[i].id == id) {
                return i;
            }
        }
        return -1;
    }
//endregion

//region main phones methods
    static Phone createPhone() {
        String model = inputString("Введите модель телефона: ");

        System.out.print("Введите цену телефона: ");
        int price = inputInt("Введите цену телефона: ", 1, 1000000);

        System.out.print("Введите количество телефонов на складе: ");
        int amount = inputInt("Введите количество телефонов на складе: ", 1, 10000);

        return new Phone(0, model, price, amount);
    }

    static Phone[] createEmptyPhonesArray() {
        return new Phone[0];
    }

    static Phone[] addPhoneToEndOfArray(Phone[] phones, Phone insertedPhone) {
        lastPhoneId++;
        insertedPhone.id = lastPhoneId;

        Phone[] tempPhones = new Phone[phones.length + 1];

        for (int i = 0; i < phones.length; i++) {
            tempPhones[i] = phones[i];
        }

        tempPhones[tempPhones.length - 1] = insertedPhone;

        System.out.println("Телефон добавлен успешно!");

        return tempPhones;
    }

    static void updatePhoneById(Phone[] phones, int updatedIdPhone) {
        Phone foundPhone = getPhoneById(phones, updatedIdPhone);

        if (foundPhone == null) {
            System.out.println("Ошибка. Телефон с таким ID не найден.");
            return;
        }

        System.out.println("Введите данные телефона для обновления");
        Phone updatedPhone = createPhone();

        foundPhone.model = updatedPhone.model;
        foundPhone.price = updatedPhone.price;
        foundPhone.amount = updatedPhone.amount;

        System.out.println("Телефон обновлён успешно!");
    }

    static Phone[] deletePhoneById(Phone[] phones, int deletedIdPhone) {
        int foundPhoneIndex = getIndexPhoneById(phones, deletedIdPhone);

        if (foundPhoneIndex == -1) {
            System.out.println("Ошибка. Телефон с таким ID не найден. Удаление не возможно");
            return phones;
        }

        Phone[] tempPhones = new Phone[phones.length - 1];
        int tempPhonesIndex = 0;

        for (int i = 0; i < phones.length; i++) {
            if (i != foundPhoneIndex) {
                tempPhones[tempPhonesIndex] = phones[i];
                tempPhonesIndex++;
            }
        }

        System.out.println("Телефон удалён успешно!");

        return tempPhones;
    }

    static Phone[] sellPhone(Phone[] phones, int soldIdPhone) {
        Phone foundPhone = getPhoneById(phones, soldIdPhone);

        if (foundPhone == null) {
            System.out.println("Ошибка. Телефон с таким ID не найден. Продажа не возможна");
            return phones;
        }

        int soldAmount = inputInt("Введите кол-во телефонов для покупки: ", 1, foundPhone.amount);

        foundPhone.amount -= soldAmount;

        if (foundPhone.amount == 0) {
            phones = deletePhoneById(phones, foundPhone.id);
        }

        System.out.println("Покупка осуществлена успешно!");

        return phones;
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

        System.out.println("Сохранение совершено успешно!");
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

        System.out.println("Загрузка совершена успешно!");

        return phones;
    }

    static void printPhonesToTxtFile(String filename, Phone[] phones) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(String.format("%-3s%-12s%-8s%-18s", "ИД", "Модель", "Цена", "Остаток на складе"));
        bufferedWriter.newLine();

        if (phones.length == 0) {
            bufferedWriter.write("Список телефонов пуст");
            bufferedWriter.newLine();
        } else {
            for (int i = 0; i < phones.length; i++) {
                bufferedWriter.write(String.format("%-3d%-12s%-8d%-18d", phones[i].id, phones[i].model, phones[i].price, phones[i].amount));
                bufferedWriter.newLine();
            }
            bufferedWriter.write("Список телефонов насчитывает " + phones.length + " элем.");
        }

        bufferedWriter.close();
        fileWriter.close();

        System.out.println("Печать в файл совершена успешно!");
    }
//endregion

//region ui methods
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
        System.out.println("4. Осуществить продажу телефона");
        System.out.println("5. Удалить телефон из списка");
        System.out.println("6. Распечатать список телефонов в файл");
        System.out.println("7. Обновить данные о телефоне");
        System.out.println("0. Выйти из программы");
    }
//endregion

    public static void main(String[] args) throws IOException {
        Phone[] phones = createEmptyPhonesArray();

        while (true) {
            printlnTableHeader();
            printlnPhones(phones);
            printlnSeparator();
            printlnMenu();
            int menuPoint = inputInt("Введите номер нужного пункта меню: ", 0, 7);

            switch (menuPoint) {
                case 1: {
                    Phone insertPhone = createPhone();
                    phones = addPhoneToEndOfArray(phones, insertPhone);
                }
                break;

                case 2: {
                    String filename = inputString("Введите имя файла: ");

                    savePhonesToTxtFile(filename, phones);
                }
                break;

                case 3: {
                    String filename = inputString("Введите имя файла: ");

                    phones = loadPhonesFromTxtFile(filename);
                }
                break;

                case 4: {
                    int sellIdPhone = inputInt("Введите ID телефона для покупки: ", 1, lastPhoneId);
                    phones = sellPhone(phones, sellIdPhone);
                }
                break;

                case 5: {
                    int deleteIdPhone = inputInt("Введите ID телефона для удаления : ", 1, lastPhoneId);
                    phones = deletePhoneById(phones, deleteIdPhone);
                }
                break;

                case 6: {
                    String filename = inputString("Введите имя файла: ");

                    printPhonesToTxtFile(filename, phones);
                }
                break;

                case 7: {
                    int updateIdPhone = inputInt("Введите ID телефона для обновления : ", 1, lastPhoneId);


                    updatePhoneById(phones, updateIdPhone);
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
