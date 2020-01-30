import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {

    // Creates the arrays used to represent the memory and registers
    static Interpreter<String> memory = new InterpreterMethods<>();
    static Interpreter<Integer> stack = new InterpreterMethods<>();
    static int PC = 0;  // Program counter

    public static void main(String[] args) {
        // Formats to ge the hour and minutes
        SimpleDateFormat hoursFormatter = new SimpleDateFormat("hh");
        SimpleDateFormat minutesFormatter = new SimpleDateFormat("mm");

        // Saving hours and minutes in the respective variables
        String C = hoursFormatter.format(System.currentTimeMillis());
        String B = minutesFormatter.format(System.currentTimeMillis());

        // Input and instruction register, which stores the instruction to be executed from memory
        Scanner input = new Scanner(System.in);
        String IR = "";

        // Initializes the operands in memory
        // This represents the program loaded in memory
        memory.push(0, "MOV");
        memory.push(1, "MOV");
        memory.push(2, "ADD");
        memory.push(3, "MOV");
        memory.push(4, "MUL");
        memory.push(5, "MOV");
        memory.push(6, "GRE");
        memory.push(7, "MOV");

        // Fills the rest of memory with X's to represent unused cells
        for (int i = 8; i < 50; i++) {
            memory.push(i, "X");
        }

        // Adds the END instruction
        memory.push(24,"END");
        memory.remove(25);

        // Saves the needed variables in memory, and initializes the other ones
        memory.push(25, "A");
        memory.push(26, B);
        memory.push(27, C);
        memory.push(28, "D");
        memory.push(29, "E");

        // Removes the cells that exceed memory size
        for(int i = 54; i > 49; i--) {
            memory.remove(i);
        }

        // Initializes the current state of the stack
        for (int i = 0; i < 8; i++) {
            stack.push(i, 0);
        }

    // Main program code

        // The IR gets the instruction from memory
        IR = memory.peek(PC) + " " + "MEM " + memory.getIndex(B) + ", R0";
        System.out.println("IR = " + IR);

        // Program counter increases
        PC += 1;
        System.out.println("PC = " + PC);

        // Moves the needed values to the registers
        stack.push(0, Integer.parseInt(B));
        stack.remove(8);

        // Displays the current state of the registers
        System.out.println("Registers R0 --> R7");

        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        // Waits for input to run the next line
        System.out.println();
        System.out.println("Press enter to run the next line");
        input.nextLine();

        // IR gets the instruction. It is decoded and finds the location of the needed cells (C)
        IR = memory.peek(PC) + " " + "MEM " + memory.getIndex(C) + ", R1";
        System.out.println("IR = " + IR);

        PC += 1;
        System.out.println("PC = " + PC);

        stack.push(1, Integer.parseInt(C));
        stack.remove(8);

        System.out.println("Registers R0 --> R7");
        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        System.out.println();
        System.out.println("Press enter to run the next line");
        input.nextLine();

        // Saves A = B + C in register 2
        IR = "IR = " + memory.peek(PC) + " " + "R2, " + "R0" + ", R1";
        System.out.println(IR);

        // PC increases, and instruction is executed; it saves the result on register 2
        operationResult(memory.peek(PC), stack.peek(0), stack.peek(1), 2);
        stack.remove(8);
        System.out.println("PC = " + PC);

        System.out.println("Registers R0 --> R7");
        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        System.out.println();
        System.out.println("Press enter to execute the next line");
        input.nextLine();

        // Saves A in its reserved memory. The instruction automatically finds the cell location
        IR = "IR = " + memory.peek(PC) + " R2, " + "MEM" + memory.getIndex("A");
        System.out.println(IR);

        // PC increases, and data is moved to memory
        PC += 1;
        MOV(2, memory.getIndex("A"));
        memory.remove(memory.getIndex("A"));
        System.out.println("PC = " + PC);

        System.out.println("Registers R0 --> R7");
        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        System.out.println();
        System.out.println("Press enter to execute the next line");
        input.nextLine();

        // Saves D in register 3 D = 2 * B
        IR = "IR = " + memory.peek(PC) + " R3, " + "2, " + "R0";
        System.out.println(IR);

        //Increases PC and performs the operation
        operationResult(memory.peek(PC), 2, stack.peek(0), 3);
        stack.remove(8);
        System.out.println("PC = " + PC);

        System.out.println("Registers R0 --> R7");
        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        System.out.println();
        System.out.println("Press enter to execute the next line");
        input.nextLine();

        // Saves D in its reserved memory
        IR = "IR = " + memory.peek(PC) + " R3, " + "MEM" + memory.getIndex("D");
        System.out.println(IR);

        PC += 1;
        MOV(3, memory.getIndex("D"));
        memory.remove(memory.getIndex("D"));
        System.out.println("PC = " + PC);

        System.out.println("Registers R0 --> R7");
        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        System.out.println();
        System.out.println("Press enter to execute the next line");
        input.nextLine();

        // Saves the result D > A in register 4 operation > is implemented in the ISA (it allows a direct result)
        IR = "IR = " + memory.peek(PC) + " R4, " + "R3, " + "R2";
        System.out.println(IR);

        //Increases PC, performs the operation, and saves it on register 4
        operationResult(memory.peek(PC), stack.peek(3), stack.peek(2), 4);
        stack.remove(8);
        System.out.println("PC = " + PC);

        System.out.println("Registers R0 --> R7");
        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        System.out.println();
        System.out.println("Press enter to execute the next line");
        input.nextLine();


        // Saves E in its reserved memory
        IR = "IR = " + memory.peek(PC) + " R4, " + "MEM" + memory.getIndex("E");
        System.out.println(IR);

        PC += 1;
        MOV(4, memory.getIndex("E"));
        memory.remove(memory.getIndex("E"));
        System.out.println("PC = " + PC);

        System.out.println("Registers R0 --> R7");
        for(int i = 0; i < stack.size(); i++) {
            System.out.print(stack.peek(i) + " ");
        }

        System.out.println();
        System.out.println();

        // Displays the final state of the memory
        System.out.println("The final state of the memory is: ");
        for(int i = 0; i < memory.size(); i ++) {
            System.out.print(memory.peek(i) + " ");
        }

        System.out.println();
        System.out.println("Press enter to end the program");
        input.nextLine();

        // PC jumps to the final instruction
        END();  // Jumps to the end of the program
        System.out.println("IR = " + memory.peek(PC));
        System.out.println("PC = " + PC);
        System.out.println();

        // Displays the final state of the variables
        System.out.println("Final state of the variables:");
        System.out.println("A = " + memory.peek(25));
        System.out.println("B = " + memory.peek(26));
        System.out.println("C = " + memory.peek(27));
        System.out.println("D = " + memory.peek(28));
        System.out.println("E = " + memory.peek(29));

        System.out.println("Press enter to exit");
        input.nextLine();
        System.out.println("Thank you for using the assembler simulator :)");
    }

    // Theoretical MOV, doesn't move the contents of stack, so that they are still loaded in memory and can be operated
    public static void MOV(int register, int memoryCell) {
        String registerContent = Integer.toString(stack.peek(register));
        memory.push(memoryCell, registerContent);
    }

    // Ends the program by going to the end of the instructions
    public static void END() {
        PC = 24;
    }

    // Decodes the operation according to what is stored in memory
    public static void operationResult(String memoryCell, int firstNumber, int secondNumber, int register) {
        int result = 0;

        // Executes the operation according to the case
        switch (memoryCell) {

            case "ADD":
                PC += 1;
                result =  firstNumber + secondNumber;
                break;

            case "MUL":
                PC += 1;
                result =  firstNumber * secondNumber;
                break;

            case "GRE":
                PC += 1;
                if(firstNumber > secondNumber) {
                    result =  1;
                } else {
                    result =  0;
                }
                break;

            default:
                result =  0;
                break;

        }
        // Saves the result in the desired register
        stack.push(register, result);
    }
}


