import java.util.Random;

/**
     * The Main class has three function that use the manager.
     */
public class Main {

    /**
     * a main function that creates a Manager object, and calls the
     * insertFivePatient, simulateOnlyByPriority, simulateOnlyByCreation functions.
     */
    public void main(String[] args){
        Manager manger = new Manager();
        this.insertFivePatient(manger);
        this.simulateOnlyByPriority(manger);
        this.simulateOnlyByCreation(manger);
    }


    /**
     * a function that adds 5 different Patients with different
     * priorities into a Manager object it gets as input
     * @param manager - Manager class for insert users.
     */
    public void insertFivePatient(Manager manager) {
        for(int i = 1; i <= 5; i++ ){
            Random r = new Random();
            int num = r.nextInt(1000);
            boolean isVip = false;
            if((num % 2) == 0)
                isVip = true;
            Patient p = new Patient(num, isVip);
            manager.add(p);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e){}
        }
    }

    /**
     * a function simulateOnlyByPriority that gets as input a Manager
     * object, removes all patients by priority and prints their details.
     * @param manager - Manager class to simulate.
     */
    public static void simulateOnlyByPriority(Manager manager) {
        int size = manager.getHeap().getSize();
        for (int i = 0; i < size; i++){
            System.out.println(manager.getByPriority());
        }
    }

    /**
     * a function simulateOnlyByCreation that gets as input a Manager
     * object, removes all patients by creation time and prints their details.
     * @param manager - Manager class to simulate.
     */
    public static void simulateOnlyByCreation(Manager manager) {
        int length = manager.getQueue().getLength();
        for (int i = 0; i < length; i++){
            System.out.println(manager.getByCreationTime());
        }
    }

}