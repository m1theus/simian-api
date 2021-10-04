package io.github.m1theus.simianapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.m1theus.simianapi.AbstractBaseTestContainer;
import io.github.m1theus.simianapi.dataprovider.database.entity.DnaEntity;
import io.github.m1theus.simianapi.entrypoint.rest.DnaRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class DnaControllerTest extends AbstractBaseTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void cleanUp() {
        mongoTemplate.dropCollection("simian_api.dna");
        mongoTemplate.createCollection("simian_api.dna");
    }

    @ParameterizedTest
    @MethodSource("simianDnaSequenceProviderHelper")
    void simianDnaSequence_shouldReturn200(String[] dna) throws Exception {
        final var request = new DnaRequest(dna);

        simianPost(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("simian").value("true"));
    }

    @ParameterizedTest
    @MethodSource("humanDnaSequenceProviderHelper")
    void humanDnaSequence_ShouldReturn403(String[] dna) throws Exception {
        final var request = new DnaRequest(dna);

        simianPost(request)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("simian").value("false"));
    }

    @ParameterizedTest
    @MethodSource("invalidSequenceProviderHelper")
    void invalidDnaSequence_ShouldReturn400(String[] dna) throws Exception {
        final var request = new DnaRequest(dna);

        simianPost(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("statusCode").value("400"))
                .andExpect(jsonPath("errors").isArray());
    }

    @Test
    void dnaStats_shouldReturnInvalidData() throws Exception {
        dnaStatsGet()
                .andExpect(status().isOk())
                .andExpect(jsonPath("count_mutant_dna").value("0"))
                .andExpect(jsonPath("count_human_dna").value("0"))
                .andExpect(jsonPath("ratio").value("0"));
    }

    @Test
    void dnaStats_shouldReturnWithData() throws Exception {
        mongoTemplate.save(new DnaEntity("ATAAAAGGGGATGGGAGGGGCGCTG", true));
        mongoTemplate.save(new DnaEntity("ATAAAAAGGGGCATATGGAGAAGGCCCCTGTCACTG", true));

        mongoTemplate.save(new DnaEntity("AAATAAGGATG", false));
        mongoTemplate.save(new DnaEntity("ATAGAGGCATGTCGAA", false));
        mongoTemplate.save(new DnaEntity("ATGAAAAGAAAGCAGATGACATGACCTGCCTTGATA", false));

        var entities = mongoTemplate.findAll(DnaEntity.class);
        var simianCount = entities.stream().filter(DnaEntity::isSimian).count();
        var humanCount = entities.stream().filter(DnaEntity::isHuman).count();
        var ratio = (double) simianCount / (double) humanCount;

        dnaStatsGet()
                .andExpect(status().isOk())
                .andExpect(jsonPath("count_mutant_dna").value(simianCount))
                .andExpect(jsonPath("count_human_dna").value(humanCount))
                .andExpect(jsonPath("ratio").value(ratio));
    }

    protected static Stream<Arguments> simianDnaSequenceProviderHelper() {
        return Stream.of(
                Arguments.of((Object) new String[]{"AAAA", "AAAT", "AAAG", "ACCA"}),
                Arguments.of((Object) new String[] {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"}),
                Arguments.of((Object) new String[]{"ATAAA", "AGGGG", "ATGGG", "AGGGG", "CGCTG"}),
                Arguments.of((Object) new String[]{"ATAAAA", "AGGGGC", "ATATGG", "AGAAGG", "CCCCTG", "TCACTG"}),
                Arguments.of((Object) new String[]{"ATGAAA", "AGGGGC", "CTATGG", "ACAAGG", "CCCCTG", "TCACTG"}),
                Arguments.of((Object) new String[]{"ATAAAA", "AGGGGC", "ATATGG", "AGAAGG", "CCCGTG", "TCGATG"}),
                Arguments.of((Object) new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"}),
                Arguments.of((Object) new String[]{"TAAATCGG", "AAAATTTT", "TCAGTAGT", "GAATTGAT", "CCCCGGTT", "AAAGTGAG", "GAAAAGGG", "TCTTTTAG"}),
                Arguments.of((Object) new String[]{"ATGGGATAG", "TAGGATGAA", "GGCGGTGGA", "AACCAATAA", "AAAACGGTA", "TCTACCGCA", "GCACACATG", "GCTGTAGTG", "CCGGGGGCG"}),
                Arguments.of((Object) new String[]{"ATGAAAGTAC", "TACTGTTGTT", "GGACACCCGA", "CCTGTAAACG", "AAGTGGCCAA", "TACAACCGGT", "GTTGAAACAC", "CCACTGATCG", "AGTAGTGAGC", "AACTCAAGCA"})
        );
    }

    protected static Stream<Arguments> humanDnaSequenceProviderHelper() {
        return Stream.of(
                Arguments.of((Object) new String[]{"AAATA", "TGGTG", "ATGGA", "TTATA", "AGGTG"}),
                Arguments.of((Object) new String[]{"ATAG", "AGGC", "ATGT", "CGAA"})
        );
    }

    protected static Stream<Arguments> invalidSequenceProviderHelper() {
        return Stream.of(
                Arguments.of((Object) new String[]{"ATAAR", "AGGGG", "ATGGG", "AGGGG", "CGCGG"}),
                Arguments.of((Object) new String[]{"ATAAR", "AGGGG", "ATXVZ", "AXGG", "CGXVG"})
        );
    }

    private ResultActions dnaStatsGet() throws Exception {
        return mockMvc.perform(get("/dna/stats")
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions simianPost(DnaRequest request) throws Exception {
        return mockMvc.perform(post("/dna/simian")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
    }

}
