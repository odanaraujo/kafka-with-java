package br.com.alura.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Email {
    private final String subject, body;
}