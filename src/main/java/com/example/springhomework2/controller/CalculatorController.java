package com.example.springhomework2.controller;

import com.example.springhomework2.entity.MathObject;
import com.example.springhomework2.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class CalculatorController {

    //am verificat ca merge in postman; am facut un post cu un body in json cu niste dummy data si a creat folderul cu rezultatele
    //    http://localhost:8082/do-math
    @PostMapping("/do-math")
    public String doMath(@RequestBody List<MathObject> mathObjects) throws IOException, InterruptedException {
        // Generam un file cu nume random in care bagam rezultatele
        String filename = UUID.randomUUID().toString() + ".txt";

        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream o = new ObjectOutputStream(fileOut);

        //chemam metoda care face operatiile
        CalculatorService calculatorService = new CalculatorService();
        List<String> results = calculatorService.performOperations(mathObjects);
        for (String result : results) {
            o.writeObject(result + "\n");  //salvam fiecare operatie in fisier
        }
        o.close();
        fileOut.close();

        return filename;
    }

//    postman: http://localhost:8082/check-finished/6a283a67-e765-4c96-8050-3fbb09d826ba.txt
    @GetMapping("/check-finished/{filename}")
    public String checkFinished(@PathVariable String filename) throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            String content = builder.toString();
            if (content.isEmpty()) {
                return "file is empty";
            } else {
                return "file is processed " + content;
            }
        } else {
            return "file not found";
        }
    }

//    postman:  http://localhost:8082/results/6a283a67-e765-4c96-8050-3fbb09d826ba.txt
    @GetMapping("/results/{filename}")
    public ModelAndView getFileView(@PathVariable String filename) throws IOException, ClassNotFoundException {
        ModelAndView mv = new ModelAndView("file-view");

        // Verificam daca exista file-ul
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            mv.addObject("error", "file empty or not found");
            return mv;
        }

        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        //creem un arraylist cu rezultatele si il bagam in html
        List<String> results = new ArrayList<>();
        while (fileIn.available() > 0) {
            String result = (String) objectIn.readObject();
            results.add(result);
        }
        mv.addObject("results", results);


        return mv;
    }

}


