package com.backend.tasks.web.rest;

import com.backend.tasks.Application;
import com.backend.tasks.domain.dto.view.OrganizationViewDto;
import com.backend.tasks.domain.entity.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import com.backend.tasks.rules.ResourceRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@Transactional
public class OrganizationControllerTest {

    @Rule
    public ResourceRule resources = new ResourceRule();

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    @DatabaseSetup(value = "/com/backend/tasks/web/rest/orgs/getAll/dataset.xml")
    public void getAll() throws Exception {

        // check rest response
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/orgs"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONAssert.assertEquals(
                resources.get("/com/backend/tasks/web/rest/orgs/getAll/result.json"),
                response,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }

    @Test
    @DatabaseSetup(value = "/com/backend/tasks/web/rest/orgs/get/dataset.xml")
    public void get() throws Exception {

        String result = resources.get("/com/backend/tasks/web/rest/orgs/get/result.json");
        OrganizationViewDto organizationViewDto = objectMapper.readValue(result, OrganizationViewDto.class);

        // check rest response
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/orgs/{orgId}", organizationViewDto.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONAssert.assertEquals(
                result,
                response,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }

    @Test
    public void save() throws Exception {

        // check rest response
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/orgs")
                .content(resources.get("/com/backend/tasks/web/rest/orgs/save/in.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONAssert.assertEquals(
                resources.get("/com/backend/tasks/web/rest/orgs/save/result.json"),
                response,
                JSONCompareMode.NON_EXTENSIBLE
        );

        //check data in db
        OrganizationViewDto organizationViewDto = objectMapper.readValue(response, OrganizationViewDto.class);
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations.size(), is(1));
        assertThat(organizations.get(0).getId(), is(organizationViewDto.getId()));
        assertThat(organizations.get(0).getName(), is(organizationViewDto.getName()));
    }

    @Test
    @DatabaseSetup(value = "/com/backend/tasks/web/rest/orgs/update/dataset.xml")
    public void update() throws Exception {

        Long idBefore = 1L;
        String nameBefore = "org1";
        //pre-check
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations.size(), is(1));
        assertThat(organizations.get(0).getId(), is(idBefore));
        assertThat(organizations.get(0).getName(), is(nameBefore));

        // check rest response
        String response = mockMvc.perform(MockMvcRequestBuilders.put("/orgs/{orgId}", idBefore)
                .content(resources.get("/com/backend/tasks/web/rest/orgs/update/in.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONAssert.assertEquals(
                resources.get("/com/backend/tasks/web/rest/orgs/update/result.json"),
                response,
                JSONCompareMode.NON_EXTENSIBLE
        );

        //check data in db
        OrganizationViewDto organizationViewDto = objectMapper.readValue(response, OrganizationViewDto.class);
        organizations = organizationRepository.findAll();
        assertThat(organizations.size(), is(1));
        assertThat(organizations.get(0).getId(), is(organizationViewDto.getId()));
        assertThat(organizations.get(0).getName(), is(organizationViewDto.getName()));
        assertThat(organizations.get(0).getId(), is(idBefore));
        assertThat(organizations.get(0).getName(), not(nameBefore));
    }

    @Test
    @DatabaseSetup(value = "/com/backend/tasks/web/rest/orgs/delete/dataset.xml")
    public void delete() throws Exception {

        Long idBefore = 1L;
        String nameBefore = "org1";
        //pre-check
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations.size(), is(1));
        assertThat(organizations.get(0).getId(), is(idBefore));
        assertThat(organizations.get(0).getName(), is(nameBefore));

        // rest request-response
        mockMvc.perform(MockMvcRequestBuilders.delete("/orgs/{orgId}", idBefore))
                .andExpect(status().isNoContent());

        //check data in db
        organizations = organizationRepository.findAll();
        assertThat(organizations.size(), is(0));
    }

}
