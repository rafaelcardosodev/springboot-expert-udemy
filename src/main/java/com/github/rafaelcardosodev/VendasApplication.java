package com.github.rafaelcardosodev;

import com.github.rafaelcardosodev.domain.entity.Cliente;
import com.github.rafaelcardosodev.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
        return args -> {

            System.out.println("salvando...");
            clienteRepository.save(new Cliente("Rafael"));
            clienteRepository.save(new Cliente("Maria"));
            List<Cliente> clientes = clienteRepository.getAll();
            clientes.forEach(System.out::println);

            System.out.println("atualizando...");
            clientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado");
                clienteRepository.update(c);
            });
            clientes = clienteRepository.getAll();
            clientes.forEach(System.out::println);

            System.out.println("buscando por nome...");
            clienteRepository.getByNome("af").forEach(System.out::println);

            System.out.println("deletando...");
            clienteRepository.getAll().forEach(c -> {
                clienteRepository.delete(c);
            });
            clientes = clienteRepository.getAll();
            if (clientes.isEmpty()) {
                System.out.println("nenhum cliente encontrado");
            } else {
                clientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
