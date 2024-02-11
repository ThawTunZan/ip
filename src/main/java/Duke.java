import java.util.Scanner;
public class Duke {
    static final int MAX_SIZE = 100;
    public static void main(String[] args) {
        printsGreeting();
        mimicMessage();

    }

    private static void printsGreeting() {
        String greetingMessage = "Hello! I'm ThawBot!\nWhat can I do for you?\n";
        System.out.println(greetingMessage);
    }

    private static void printGoodByeMessage() {
        String goodbyeMessage = "Bye. Hope to see you again soon!";
        System.out.println(goodbyeMessage);
    }

    private static void mimicMessage() {
        Scanner input = new Scanner(System.in);
        Task[] list = new Task[MAX_SIZE];
        int currentIteration = 0;
        boolean canExit = false;

        while (!canExit) {
            String usersInput = input.nextLine();

            if (usersInput.equals("bye")) {
                canExit = true;
                printGoodByeMessage();
            } else if (usersInput.equals("list")) {
                printList(currentIteration, list);
            } else if (usersInput.startsWith("unmark")) {
                markOrUnmarkTask(usersInput, false, list);
            } else if (usersInput.startsWith("mark")) {
                markOrUnmarkTask(usersInput, true, list);
            }
            else {
                try {
                    addTask(usersInput, list, currentIteration);
                    currentIteration++;
                } catch(ThawException e) {
                    if (e.getMessage().equals("Empty command")) {
                        System.out.println("OOPS!!! The description of a todo cannot be empty.");
                    }
                    else if (e.getMessage().equals("Invalid command")) {
                        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
                }
            }
        }
    }

    private static void markOrUnmarkTask (String usersInput, boolean isDone, Task[] task) {
        int taskIndex;

        if (isDone) {
            taskIndex = Integer.parseInt(usersInput.substring(5)) - 1 ;
            System.out.println("Nice! I've marked this task as done:");
        }
        else {
            taskIndex = Integer.parseInt(usersInput.substring(7)) - 1;
            System.out.println("OK, I've marked this task as not done yet:");
        }
        task[taskIndex].isDone = isDone;
        System.out.println(task[taskIndex].getStatusIcon());
    }

    private static void printList(int index, Task[] task) {
        for (int i = 0; i < index; i ++) {
            System.out.println((i + 1) + ". " + task[i].getStatusIcon());
        }
    }

    private static void printAcknowledgementMessage(Task[] task, int index) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task[index].getStatusIcon());
        System.out.print("Now you have " + (index + 1) + " task in the list.");
    }

    private static void addTask(String usersInput, Task[] list, int currentIteration) throws ThawException {
        if (usersInput.strip().equals("todo") || usersInput.strip().equals("deadline") || usersInput.strip().equals("event")) {
            throw new ThawException("Empty command");
        }
        else if (usersInput.startsWith("todo")) {
            list[currentIteration] = new Todo(usersInput.substring(5), usersInput);
        }
        else if (usersInput.startsWith("deadline")) {
            int startingIndex = usersInput.indexOf("/by");
            list[currentIteration] = new Deadline(usersInput.substring(9, startingIndex - 1),
                    usersInput.substring(startingIndex + 4));
        }
        else if (usersInput.startsWith("event")) {
            int startIndex = usersInput.indexOf("from");
            int secondIndex = usersInput.indexOf("to");
            list[currentIteration] = new Event(usersInput.substring(6, startIndex - 2),
                    "from: " + usersInput.substring(startIndex+ 5, secondIndex - 2) + " " + "to: " + usersInput.substring(secondIndex + 3));
        }
        else {
            throw new ThawException("Invalid command");
        }
        printAcknowledgementMessage(list, currentIteration);
    }
}
