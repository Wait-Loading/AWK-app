package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    public static class MainController {

        // Endpoint to accept text input for lexing, parsing, and interpreting
        @PostMapping("/process")
public String processInput(@RequestParam("input") String input, @RequestPart("fileinput") String fileinput) {
    // Perform lexing, parsing, and interpreting on the input text and file
    try {
        Lexer lexerobj = new Lexer(input); // Create lexer object for input.
        List<Token> tokens = lexerobj.Lex();
        Parser parser = new Parser((LinkedList<Token>) tokens);
        ProgramNode programNode = parser.Parse();
        if(fileinput=="" || fileinput==null  )
        {
            Interpreter interpreter = new Interpreter(programNode, null); // Pass file content as input.
            return interpreter.mainreturn;

        }
        else{
        Interpreter interpreter = new Interpreter(programNode, fileinput); // Pass file content as input.
        return interpreter.mainreturn;

    }
        // Return the result of interpreting, you might want to adjust this based on your needs
        // return interpreter.interpret();
    } catch (Exception e) {
        // Return error message if any exception occurs during processing
        return "Error processing input: " + e.getMessage();
    }
}
        // Mapping for the root URL
        @GetMapping("/")
        public String index() {
            String htmlContent = loadHtmlFromResource("index.html");
            return htmlContent != null ? htmlContent : "Error loading HTML content.";
        }

        // Helper method to load HTML content from resource
        private String loadHtmlFromResource(String resourceName) {
            try {
                ClassPathResource resource = new ClassPathResource(resourceName);
                return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
