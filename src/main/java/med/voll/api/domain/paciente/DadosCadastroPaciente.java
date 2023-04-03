package med.voll.api.domain.paciente;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.endereco.DadosEndereco;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @CPF
        String cpf,

        @NotNull
        @Valid
        DadosEndereco endereco) {
}