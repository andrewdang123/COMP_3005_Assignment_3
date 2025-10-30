import java.util.Scanner;

public class Options {

    public static void placeholder() {
        System.out.println("FUBAR");
    }

    public static void exit() {
        System.out.println("Now exiting program...");
        return;
    }

    /***************************************************************
     * FunctionEnum
     ***************************************************************/
    private interface FunctionEnum {
        int getCode();

        String getDescription();

        void execute();

        static <E extends Enum<E> & FunctionEnum> E fromCode(Class<E> enumClass, int code) {
            for (E e : enumClass.getEnumConstants()) {
                if (e.getCode() == code) {
                    return e;
                }
            }
            return null;
        }

        static <E extends Enum<E> & FunctionEnum> void printMenu(Class<E> enumClass) {
            String enumName = enumClass.getSimpleName()
                    .replace("FunctionEnum", "")
                    .toUpperCase();
            System.out.println("\n=== " + enumName + " MENU OPTIONS ===");
            for (E e : enumClass.getEnumConstants()) {
                System.out.println(e.getCode() + " - " + e.getDescription());
            }
        }

    }

    /***************************************************************
     * ApplicationFunctionEnum
     ***************************************************************/
    public enum ApplicationFunctionEnum implements FunctionEnum {
        EXIT(0, "EXIT", () -> exit()),
        GET_ALL_STUDENTS(1, "Retrieves and displays all records from the students table.", () -> Main.getAllStudents()),
        ADD_STUDENT(2, "Inserts a new student record into the students table.", () -> Main.addStudent()),
        UPDATE_STUDENT_EMAIL(3, "Retrieves and displays all records from the students table.",
                () -> Main.updateStudentEmail()),
        DELETE_STUDENT(4, "Deletes the record of the student with the specified student_id",
                () -> Main.deleteStudent());

        private final int code;
        private final String description;
        private final Runnable action;

        ApplicationFunctionEnum(int code, String description, Runnable action) {
            this.code = code;
            this.description = description;
            this.action = action;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public void execute() {
            action.run();
        }

    }

    /***************************************************************
     * runView
     ***************************************************************/
    public static <E extends Enum<E> & FunctionEnum> void runView(Class<E> enumClass) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            FunctionEnum.printMenu(enumClass);
            System.out.print("Enter command number: ");
            while (!scanner.hasNextInt()) {

                scanner.next();
                System.out.print("Enter valid command number: ");
            }
            int code = scanner.nextInt();

            E function = FunctionEnum.fromCode(enumClass, code);
            if (function == null) {
                System.out.println("Invalid command.");
                continue;
            }

            function.execute();

            try {
                E exitEnum = Enum.valueOf(enumClass, "EXIT");
                if (function == exitEnum)
                    break;
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

}
