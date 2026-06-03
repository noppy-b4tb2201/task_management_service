package com.example.batch_service.batch;

import com.example.batch_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchRunner implements CommandLineRunner {

    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) {

        System.out.println("==========");
        System.out.println("Batch Start");
        System.out.println("==========");

        Long count = taskRepository.count();
        System.out.println("total Task Count = " + count);

        System.out.println("==========");
        System.out.println("Batch Finished");
        System.out.println("==========");
    }
}