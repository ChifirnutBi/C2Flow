package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class ConfigManager {

    public static void loadConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config loaded = mapper.readValue(new File(filePath), Config.class);

        Config instance = Config.getInstance();
        instance.genAI = loaded.genAI;
        instance.studentInfo = loaded.studentInfo;
        instance.paths = loaded.paths;
    }

    public static void saveConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), Config.getInstance());
    }

    public static void createNewConfig() {
        Config config;

        config = Config.getInstance();

        config.genAI.model = "gemini-2.5-flash";
        config.genAI.config = null;
        config.genAI.generateTaskAndSolution = "У тебе є код на C. \n" +
                "Тобі потрібно зробити текст задачі та розв'язку на основі коду мовою C. \n" +
                "Треба:\n" +
                "1. Написати текст задачі (що виконується в цьому коді) українською мовою так, щоб він описував, що потрібно зробити Задача: (сюди писати текст задачі)\n" +
                "2. Дати розв'язок українською мовою у вигляді пояснення кроків, що виконує програма. Розв'язок: (сюди писати текст розв'язку)\n" +
                "3. Не створюй розділів окрім \"Задача:\" та \"Розв'язок:\"\n" +
                "4. Не використовуй те що може не відобразитися у .doc файлі (---, `, \\times)\n" +
                "5. Текст має бути зрозумілий студенту першого курсу.\n" +
                "6. Текст має бути написаний академічною мовою. Текст має підходити для лабораторної роботи\n" +
                "7. Не показувати сам код.\n" +
                "8. Зроби це у вигляді звичайного тексту з абзацами (не створюй зайвих булет-листів, тощо).\n";
        config.genAI.generateTableOfVariables = "У тебе є код на C.\n" +
                "Тобі потрібно створити таблицю змінних.\n" +
                "У таблиці повинні бути лише такі стовпці: \n" +
                "Змінна | Тип | Ім'я | Призначення\n\n" +
                "Треба:\n" +
                "1. Для кожної змінної з коду сформуй окремий рядок таблиці.\n" +
                "2. Не показуй сам код.\n" +
                "3. Не використовуй елементи форматування, які можуть погано відобразитися у .doc файлі. \n" +
                "   Дозволені символи для таблиці: вертикальна риска | та пробіли.\n" +
                "   Заборонені: спеціальні символи, LaTeX та інші нестандартні елементи.\n" +
                "4. Таблиця повинна бути у такому форматі (приклад):\n" +
                "| Змінна | Тип | Ім'я | Призначення |\n" +
                "|------|-----|------|------------|\n" +
                "| Стовпець1 | Стовпець2 | Стовпець3 | Стовпець4 |\n" +
                "| Стовпець1 | Стовпець2 | Стовпець3 | Стовпець4 |\n\n" +
                "5. Текст повинен бути академічним, зрозумілим студенту першого курсу.\n" +
                "6. Не створюй додаткових заголовків, булетів або нумерацій.\n" +
                "7. Поверни лише таблицю, без коментарів і без вступних фраз.\n" +
                "8. Пояснення: Змінна - це те як можна назвати/описати змінну українською, наприклад двовимірний масив. Тип - буквальний тип змінної, що прописан в коді, наприклад double[5][6]. Ім'я - буквальне ім'я змінної що написане в коді. Призначення - написати українською мовою нащо ця змінна потрібна або що вона робіть.\n" +
                "9. Записувати 1 змінну треба тільки 1 раз, не більше, не менше. Зписувати треба тільки її створення/ініціалізацію";
        config.genAI.generateFunctionSignaturesTable = "У тебе є код на C.\n" +
                "Тобі потрібно створити таблицю функцій (сигнатур) у програмі.\n" +
                "У таблиці повинні бути лише такі стовпці:\n" +
                "Сигнатура | Призначення\n\n" +
                "Треба:\n" +
                "1. Для кожної функції з коду сформуй окремий рядок таблиці.\n" +
                "2. Не показуй сам код функції, лише її сигнатуру.\n" +
                "3. Не використовуй елементи форматування, які можуть погано відобразитися у .doc файлі.\n" +
                "   Дозволені символи для таблиці: вертикальна риска | та пробіли.\n" +
                "   Заборонені: спеціальні символи, LaTeX та інші нестандартні елементи.\n" +
                "4. Таблиця повинна бути у такому форматі (приклад):\n" +
                "| Сигнатура | Призначення |\n" +
                "|-----------|-------------|\n" +
                "| void initMatrix(double A[6][5], int rows, int cols) | Ініціалізує двовимірний масив A введенням усіх елементів з клавіатури |\n" +
                "| void formArray(double A[6][5], double B[6], int rows, int cols) | Формує масив B, де кожен елемент — середнє арифметичне рядка матриці A |\n\n" +
                "5. Текст повинен бути академічним, зрозумілим студенту першого курсу.\n" +
                "6. Не створюй додаткових заголовків, булетів або нумерацій.\n" +
                "7. Поверни лише таблицю, без коментарів і без вступних фраз.\n" +
                "8. Призначення має коротко пояснювати, що робить функція.\n";
        config.genAI.generateMathModel = "У тебе є код на C.\n" +
                "Тобі потрібно створити математичну модель задачі на основі цього коду.\n" +
                "Треба:\n" +
                "1. Сформулювати математичну постановку задачі академічною українською мовою.\n" +
                "2. Описати, які дані обчислюються (масиви, матриці, параметри, розміри).\n" +
                "3. Пояснити, які величини вводяться користувачем.\n" +
                "4. Подати формулу або формули у вигляді звичайного тексту у рядок, щоб їх можна було вставити у Word вручну. Формули подавати у форматі LaTeX, але як текст, наприклад: B_i = \\\\frac{1}{m} \\\\sum_{j=1}^{m} A_{ij}.\n" +
                "5. Пояснити, що означають змінні у формулі (n, m, індекси).\n" +
                "6. Описати, що відбувається з масивами після обчислень (наприклад, сортування).\n" +
                "7. Не показувати сам код.\n" +
                "8. Не використовувати складне форматування, яке може не відобразитися у .doc файлі (не використовувати \\'---\\', \\'`\\', LaTeX середовища типу \\\\begin).\n" +
                "9. Повернути звичайний текст із абзацами, який підходить для включення в пояснювальну записку до лабораторної роботи.\n" +
                "10. Формули подавати лише однією рядковою формулою у LaTeX-стилі, наприклад: B_i = \\\\frac{1}{m} \\\\sum_{j=1}^{m} A_{ij}.\n" +
                "11. Текст має бути зрозумілим студенту першого курсу та написаним академічною мовою.\n";
        config.genAI.generateConclusion = "У тебе є код на C.\n" +
                "Тобі потрібно створити короткі підсумкові висновки до лабораторної роботи.\n" +
                "Треба:\n" +
                "1. Обов'язково починати з \"Висновок:\", потім сформувати 1 абзац академічного тексту українською мовою.\n" +
                "2. Викласти підсумок того, що було зроблено у роботі: які масиви створювалися, що обчислювалося, що сортувалося.\n" +
                "3. Зазначити коректність виконання програми та логічну послідовність етапів.\n" +
                "4. Описати загальну структуру алгоритмів: використання циклів, обчислення середніх значень, сортування.\n" +
                "5. Не використовувати надмірно технічних термінів — текст має бути зрозумілим студенту першого курсу.\n" +
                "6. Не показувати сам код.\n" +
                "7. Не створювати списки, заголовки чи форматування, яке може погано відобразитися в .doc файлі.\n" +
                "8. Повернути лише зв’язний текст без коментарів і службових фраз.\n";

        System.out.println("Enter the title page (multiple lines possible, end with a empty line):");
        Scanner scanner = new Scanner(System.in);
        StringBuilder titleBuilder = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            if (!titleBuilder.isEmpty()) titleBuilder.append("\n");
            titleBuilder.append(line);
        }
        config.studentInfo.title = titleBuilder.toString();
        System.out.println("Enter lab number:");
        config.studentInfo.labNumber = scanner.nextLine();
        System.out.println("Enter course name:");
        config.studentInfo.course = scanner.nextLine();
        System.out.println("Enter topic name:");
        config.studentInfo.topic = scanner.nextLine();
        System.out.println("Enter your variant:");
        config.studentInfo.variant = scanner.nextLine();
        System.out.println("Enter your group:");
        config.studentInfo.group = scanner.nextLine();
        System.out.println("Enter your name:");
        config.studentInfo.studentName = scanner.nextLine();
        System.out.println("Enter teacher`s name:");
        config.studentInfo.teacherName = scanner.nextLine();
        System.out.println("Enter your city:");
        config.studentInfo.city = scanner.nextLine();
        config.studentInfo.year = String.valueOf(LocalDate.now().getYear());

        System.out.println("Enter path to your file with code:");
        config.paths.src = scanner.nextLine();
        config.paths.docOutputDir = "output/";
        config.paths.templatePath = "template.docx";
        config.paths.tmpDir = "tmp/";
    }
}