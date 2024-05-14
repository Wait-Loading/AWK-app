// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
// import java.util.LinkedList;
// import java.util.List;

// @SpringBootApplication
// public class AwkInterpreterApplication {
//     public static void main(String[] args) {
//         SpringApplication.run(AwkInterpreterApplication.class, args);
//     }
// }

// @RestController
// class AwkInterpreterController {
//     @PostMapping("/interpret")
//     public String interpretAwkProgram(@RequestBody String document) {
//         try {
//             Lexer lexer = new Lexer(document);
//             List<Token> tokens = lexer.Lex();
//             Parser parser = new Parser((LinkedList<Token>) tokens);
//             ProgramNode programNode = parser.Parse();
//             Interpreter interpreter = new Interpreter(programNode, null); // Pass file as null for direct document processing
//             return interpreter.interpret(); // Return result of interpretation
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "Error occurred: " + e.getMessage();
//         }
//     }
// }
