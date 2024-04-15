**Brennan Tan UOP Software Development Tools and Practices assessment 2**
To prevent *Exception in thread "AWT-EventQueue-0" java.awt.HeadlessException* (With InteliJ IDEA) :
- Navigate to Run > Edit configurations > Choose Application/SDTPApplication
- Modify options > add VM options > type -Djava.awt.headless=false
- Apply and run
