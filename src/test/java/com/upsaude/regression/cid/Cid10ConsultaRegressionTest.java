package com.upsaude.regression.cid;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testes de Regressão - Consulta CID-10")
class Cid10ConsultaRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Listar capítulos deve retornar status 200")
    void listarCapitulosDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/v1/cid/cid10/capitulos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Obter capítulo por número deve retornar status 200")
    void obterCapituloPorNumeroDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/v1/cid/cid10/capitulos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Pesquisar categorias deve retornar status 200")
    void pesquisarCategoriasDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/v1/cid/cid10/categorias")
                .param("q", "A00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Obter categoria por código deve retornar status 200 ou 404")
    void obterCategoriaPorCodigoDeveRetornarStatusValido() throws Exception {
        try {
            mockMvc.perform(get("/v1/cid/cid10/categorias/A00")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (AssertionError e) {
            // Se não houver dados seedados, 404 é aceitável
            mockMvc.perform(get("/v1/cid/cid10/categorias/A00")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    @DisplayName("Listar categorias por intervalo deve retornar status 200")
    void listarCategoriasPorIntervaloDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/v1/cid/cid10/categorias/intervalo")
                .param("catinic", "A00")
                .param("catfim", "A09")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Pesquisar subcategorias deve retornar status 200")
    void pesquisarSubcategoriasDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/v1/cid/cid10/subcategorias")
                .param("q", "A000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Obter subcategoria por código deve retornar status 200 ou 404")
    void obterSubcategoriaPorCodigoDeveRetornarStatusValido() throws Exception {
        try {
            mockMvc.perform(get("/v1/cid/cid10/subcategorias/A000")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (AssertionError e) {
            // Se não houver dados seedados, 404 é aceitável
            mockMvc.perform(get("/v1/cid/cid10/subcategorias/A000")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    @DisplayName("Listar subcategorias por categoria deve retornar status 200")
    void listarSubcategoriasPorCategoriaDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/v1/cid/cid10/subcategorias/categoria/A00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Listar grupos deve retornar status 200")
    void listarGruposDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/v1/cid/cid10/grupos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
