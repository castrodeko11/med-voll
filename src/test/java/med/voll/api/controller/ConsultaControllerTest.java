package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Should return http status 400 when there is information available")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var agendar = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(agendar.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http status 200 when there is information available")
    @WithMockUser
    void agendar_cenario2() throws Exception {

        var data = LocalDateTime.now().plusDays(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);

        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);


        var response = mockMvc.perform(post("/consultas")
                        .contentType("application/json")
                        .content(dadosAgendamentoConsultaJacksonTester
                                .write(new DadosAgendamentoConsulta(2l, 5l, data, especialidade)).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonReturn = dadosDetalhamentoConsultaJacksonTester.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonReturn);
    }
}