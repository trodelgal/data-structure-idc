# system for handling patients waiting in line in a clinic.

When a patient arrives at the clinic she should be added to the system.   
Patients are handled either by creation time or by medical priority. Thus, the system should be
able to report and remove from the line either the patient with the first creation time or the
one with the highest priority.  
A patient is represented by the Patient class, which implements the Comparable interface. This
class is provided to you in Patient.java. You should read the code and make sure you
understand how patients are compared. We intentionally do not provide tons for any of the
other components of this assignment because it is important that you gain independence in
writing complete standalone projects.

You should implement a Manager class (Generic class) that maintains comparable elements
(patients in our case) in two private data structures:

1. A max heap, storing all elements according to priority.
2. A FIFO queue storing all elements by order of insertion.
   The signature of Manager is: public class Manager <T extends Comparable<T>>
   It must implement the following methods:   
   public void add(T t) - adds element t to the system.  
   public T getByCreationTime() – returns the element with the earliest creation time and
   removes it from the system.   
   public T getByPriority() – returns the element with the highest priority and removes it from
   the system.

You should implement a generic max heap class Heap and a generic FIFO queue class Queue
(each in a separate java file), with the following signatures:  
public class Heap<T extends Comparable<T>>    
public class Queue <T>

Both classes should implement the following three methods:  
public void add(T t)  - Adds t to the data structure  
public T get()  - Returns and removes the first / highest priority element.
public void remove(T t) – Removes an element equal to t from the data structure, if exists. if
multiple elements exist, remove an arbitrary one.

Queue.java should implement a FIFO queue using a node-based linked list.  
Heap.java should implement a max heap using an array as learned in class. Priorities should be
determined according to CompareTo.

Make sure of the following:
• Adhere exactly to the above specifications as components of your solution will
be tested against our implementations of Heap and Queue.
• Handle all edge cases, including overflows (doubling the array size if required).
• Your implementation must be generic; No class should “know” what Patient is.
• Each class should reside its own .java file.
• Include sufficient comments, and documentation, and adhere to coding
conventions you had learned last semester.
• Use short and precise functions and avoid repetitive code.

Write a Main.java file containing the following:

1. a function that adds at least 5 different Patients with different
   priorities into a Manager object it gets as input.
   Use Thread.sleep function between your patient’s creation to make sure that
   their enqueue times are well separated.
2. a function simulateOnlyByPriority that gets as input a Manager
   object, removes all patients by priority and prints their details.
3. a function simulateOnlyByCreation that gets as input a Manager
   object, removes all patients by creation time and prints their details.
4. a main function that creates a Manager object, and calls the
   above functions.
