package med.voll.api.paciente;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.medico.Especialidade;
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