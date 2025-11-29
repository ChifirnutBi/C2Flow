package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class ConfigManager {

    public static final String CONFIG_FILE_PATH = "config/config.json";

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
        File file = new File(filePath);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, Config.getInstance());
        System.out.println("Config saved to " + file.getAbsolutePath());
    }

    public static void createNewConfig() {
        Config config;

        config = Config.getInstance();

        config.genAI.model = "gemini-2.5-flash";
        config.genAI.config = null;
        config.genAI.generateTaskAndSolution = """
                У тебе є код на C.\s
                Тобі потрібно зробити текст задачі та розв'язку на основі коду мовою C.\s
                Треба:
                1. Написати текст задачі (що виконується в цьому коді) українською мовою так, щоб він описував, що потрібно зробити Задача: (сюди писати текст задачі)
                2. Дати розв'язок українською мовою у вигляді пояснення кроків, що виконує програма. Розв'язок: (сюди писати текст розв'язку)
                3. Не створюй розділів окрім "Задача:" та "Розв'язок:"
                4. Не використовуй те що може не відобразитися у .doc файлі (---, `, \\times)
                5. Текст має бути зрозумілий студенту першого курсу.
                6. Текст має бути написаний академічною мовою. Текст має підходити для лабораторної роботи
                7. Не показувати сам код.
                8. Зроби це у вигляді звичайного тексту з абзацами (не створюй зайвих булет-листів, тощо).
                """;
        config.genAI.generateTableOfVariables = """
                У тебе є код на C.
                Тобі потрібно створити таблицю змінних.
                У таблиці повинні бути лише такі стовпці:\s
                Змінна | Тип | Ім'я | Призначення
                
                Треба:
                1. Для кожної змінної з коду сформуй окремий рядок таблиці.
                2. Не показуй сам код.
                3. Не використовуй елементи форматування, які можуть погано відобразитися у .doc файлі.\s
                   Дозволені символи для таблиці: вертикальна риска | та пробіли.
                   Заборонені: спеціальні символи, LaTeX та інші нестандартні елементи.
                4. Таблиця повинна бути у такому форматі (приклад):
                | Змінна | Тип | Ім'я | Призначення |
                |------|-----|------|------------|
                | Стовпець1 | Стовпець2 | Стовпець3 | Стовпець4 |
                | Стовпець1 | Стовпець2 | Стовпець3 | Стовпець4 |
                
                5. Текст повинен бути академічним, зрозумілим студенту першого курсу.
                6. Не створюй додаткових заголовків, булетів або нумерацій.
                7. Поверни лише таблицю, без коментарів і без вступних фраз.
                8. Пояснення: Змінна - це те як можна назвати/описати змінну українською, наприклад двовимірний масив. Тип - буквальний тип змінної, що прописан в коді, наприклад double[5][6]. Ім'я - буквальне ім'я змінної що написане в коді. Призначення - написати українською мовою нащо ця змінна потрібна або що вона робіть.
                9. Записувати 1 змінну треба тільки 1 раз, не більше, не менше. Зписувати треба тільки її створення/ініціалізацію, не записувати створення/ініціалізацію з ім'я якої вже записано у таблиці.
                10. Не записувати змінну з одним і тим самим ім'ям/назвою більше одного разу.
                11. Не записувати змінні що ініціалізуються як аргументи методу.
                """;
        config.genAI.generateFunctionSignaturesTable = """
                У тебе є код на C.
                Тобі потрібно створити таблицю функцій (сигнатур) у програмі.
                У таблиці повинні бути лише такі стовпці:
                Сигнатура | Призначення
                
                Треба:
                1. Для кожної функції з коду сформуй окремий рядок таблиці.
                2. Не показуй сам код функції, лише її сигнатуру.
                3. Не використовуй елементи форматування, які можуть погано відобразитися у .doc файлі.
                   Дозволені символи для таблиці: вертикальна риска | та пробіли.
                   Заборонені: спеціальні символи, LaTeX та інші нестандартні елементи.
                4. Таблиця повинна бути у такому форматі (приклад):
                | Сигнатура | Призначення |
                |-----------|-------------|
                | void initMatrix(double A[6][5], int rows, int cols) | Ініціалізує двовимірний масив A введенням усіх елементів з клавіатури |
                | void formArray(double A[6][5], double B[6], int rows, int cols) | Формує масив B, де кожен елемент — середнє арифметичне рядка матриці A |
                
                5. Текст повинен бути академічним, зрозумілим студенту першого курсу.
                6. Не створюй додаткових заголовків, булетів або нумерацій.
                7. Поверни лише таблицю, без коментарів і без вступних фраз.
                8. Призначення має коротко пояснювати, що робить функція.
                """;
        config.genAI.generateMathModel = """
                У тебе є код на C.
                Тобі потрібно створити математичну модель задачі на основі цього коду.
                Треба:
                1. Сформулювати математичну постановку задачі академічною українською мовою. Написати декілька абзаців звичайним текстом, без використання списків, булет листів, тощо
                2. Описати, які дані обчислюються (масиви, матриці, параметри, розміри).
                3. Пояснити, які величини вводяться користувачем.
                4. Подати формулу або формули у вигляді звичайного тексту у рядок, щоб їх можна було вставити у Word вручну. Формули подавати у форматі LaTeX, але як текст, наприклад: B_i = \\\\frac{1}{m} \\\\sum_{j=1}^{m} A_{ij}.
                5. Пояснити, що означають змінні у формулі (n, m, індекси).
                6. Описати, що відбувається з масивами після обчислень (наприклад, сортування).
                7. Не показувати сам код.
                8. Не використовувати складне форматування, яке може не відобразитися у .doc файлі.
                9. Повернути звичайний текст із абзацами, який підходить для включення в пояснювальну записку до лабораторної роботи.
                10. Формули подавати лише однією рядковою формулою у LaTeX-стилі, наприклад: B_i = \\\\frac{1}{m} \\\\sum_{j=1}^{m} A_{ij}.
                11. Текст має бути зрозумілим студенту першого курсу та написаним академічною мовою.
                """;
        config.genAI.generateConclusion = """
                У тебе є код на C.
                Тобі потрібно створити короткі підсумкові висновки до лабораторної роботи.
                Треба:
                1. Обов'язково починати з "Висновок:", потім сформувати 1 абзац академічного тексту українською мовою.
                2. Викласти підсумок того, що було зроблено у роботі: які масиви створювалися, що обчислювалося, що сортувалося.
                3. Зазначити коректність виконання програми та логічну послідовність етапів.
                4. Описати загальну структуру алгоритмів: використання циклів, обчислення середніх значень, сортування.
                5. Не використовувати надмірно технічних термінів — текст має бути зрозумілим студенту першого курсу.
                6. Не показувати сам код.
                7. Не створювати списки, заголовки чи форматування, яке може погано відобразитися в .doc файлі.
                8. Повернути лише зв’язний текст без коментарів і службових фраз.
                """;
        config.genAI.generateSteps = """
                У тебе є код на C.
                Тобі потрібно скласти покроковий опис того, що робить програма.
                Потрібно:
                1. Створити список кроків, кожен крок починати з "Крок N.", де N — номер кроку.
                2. Перший крок завжди "Крок 1. Визначимо основні дії."
                3. Далі описувати дії програми у хронологічному порядку: ініціалізація змінних і масивів, обчислення, обробка циклів, умов, пошук, сортування тощо.
                4. Кожен крок має бути коротким, однією фразою, академічною українською мовою.
                5. Не включати сам код, лише опис дій.
                6. Кількість кроків має відповідати логіці програми, кожну окрему дію описувати окремим кроком.
                7. Не використовувати списки, bullet-листи чи складне форматування, лише простий текст.
                8. Текст має бути зрозумілим для студента першого курсу.
                """;

        System.out.println("Enter the title page (multiple lines possible, end with a empty line):");
        Scanner scanner = new Scanner(System.in);
        StringBuilder titleBuilder = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty())
                break;
            if (!titleBuilder.isEmpty())
                titleBuilder.append("\n");
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
        System.out.println("Enter your lab goal:");
        config.studentInfo.labGoal = scanner.nextLine();

        System.out.println("Enter path to your file with code:");
        config.paths.src = scanner.nextLine();
        config.paths.docOutputDir = "output/";
        config.paths.templatePath = "templates/kpiTemplate.docx";
        config.paths.tmpDir = "tmp/";

        try {
            ConfigManager.saveConfig(CONFIG_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}