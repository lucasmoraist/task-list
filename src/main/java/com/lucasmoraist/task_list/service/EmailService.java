package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.model.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmailToCreatingTask(String title, String description, StatusType status, LocalDateTime date) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("to");
        message.setSubject("Nova tarefa criada!");
        message.setText(
                """
                Olá,
                
                Tarefa foi criada com sucesso!
                
                Detalhes da tarefa:
                Título: %s
                Descrição: %s
                Status: %s
                Data de criação: %s
                
                Atenciosamente,
                Equipe de Gerenciamento de Tarefas
                """.formatted(title, description, status, date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
        );

        message.setFrom(from);

        mailSender.send(message);
    }

    public void sendEmailToUpdatingTask(String title, String description, StatusType status, LocalDateTime date) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("to");
        message.setSubject("Tarefa atualizada!");
        message.setText(
                """
                Olá,
                
                Tarefa foi atualizada com sucesso!
                
                Novos detalhes da tarefa:
                Título: %s
                Descrição: %s
                Status: %s
                Data de atualização: %s
                
                Atenciosamente,
                Equipe de Gerenciamento de Tarefas
                """.formatted(title, description, status, date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
        );

        message.setFrom(from);

        mailSender.send(message);
    }

    public void sendEmailToDeletingTask(String title, String description, StatusType status, LocalDateTime date) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("to");
        message.setSubject("Tarefa Excluída!");
        message.setText(
                """
                Olá,
                
                Tarefa foi excluída com sucesso!
                
                Detalhes da tarefa excluída:
                Título: %s
                Descrição: %s
                Status: %s
                Data de exclusão: %s
                
                Atenciosamente,
                Equipe de Gerenciamento de Tarefas
                """.formatted(title, description, status, date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
        );

        message.setFrom(from);

        mailSender.send(message);
    }

}
