# Calculator

Completed the calculator project in accordance to all the requirements:
Requirements:
1. Operations: Deﬁne an enum named Operation that includes basic operations like ADD, SUBTRACT, MULTIPLY, and DIVIDE.
2. Basic Calculation Method: Implement a method calculate(Operation op, Numbernum1, Number num2) in the Calculator class that performs a single operation between two numbers and returns the result.
3. Chaining Operations: Implement a method that allows chaining multiple operations on a single value, similar to how basic calculators work. This should enable users to start with an initial value and perform a series of operations sequentially.
4. Extensibility: The Calculator class should allow new operations to be added without requiring changes to its existing code.
5. IoC Compatibility: Ensure the design is compatible with an Inversion of Control (IoC)environment, allowing for external management of dependencies to enable easy testing and swapping of implementations.
6. Error Handling: The solution should handle invalid operations gracefully (e.g., operations not supported by the calculator).7. Testing: Write unit tests to verify your solution, including both normal cases and edge cases

Technologies Used:
1. Java 17
2. Spring Boot 3.4.4
3. Maven
4. JUnit and Mockito
