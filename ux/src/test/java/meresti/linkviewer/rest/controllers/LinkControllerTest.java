/*
 * Copyright (c) 2015. meresti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package meresti.linkviewer.rest.controllers;

import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.services.LinkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LinkControllerTest {

    @InjectMocks
    private LinkController linkController;

    @Mock
    private LinkService linkService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(linkController).build();
    }

    @Test
    public void testGetLinks() throws Exception {
        final Link link = createDummyLink(BigInteger.ONE);

        when(linkService.findLinks(0L, 10L)).thenReturn(Collections.singletonList(link));

        mockMvc.perform(get("/links/{startIndex}/{pageSize}", 0L, 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].url", is(link.getUrl())))
                .andExpect(jsonPath("$[0].title", is(link.getTitle())))
                .andExpect(jsonPath("$[0].description", is(link.getDescription())))
                .andExpect(jsonPath("$[0].imageUrl", is(link.getImageUrl())));

        mockMvc.perform(get("/links/{startIndex}/{pageSize}", 10L, 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(empty())));

    }

    @Test
    public void testGetExistingLink() throws Exception {
        final Link link = createDummyLink(BigInteger.ONE);

        when(linkService.findById(link.getId())).thenReturn(link);

        mockMvc.perform(get("/links//{id}", link.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(link.getUrl())))
                .andExpect(jsonPath("$.title", is(link.getTitle())))
                .andExpect(jsonPath("$.description", is(link.getDescription())))
                .andExpect(jsonPath("$.imageUrl", is(link.getImageUrl())));
    }

    @Test
    public void testGetNonExistingLink() throws Exception {
        when(linkService.findById(BigInteger.ONE)).thenReturn(null);

        mockMvc.perform(get("/links//{id}", BigInteger.ONE))
                .andExpect(status().isNotFound());
    }

    private Link createDummyLink(final BigInteger id) {
        final Link link = new Link();
        link.setId(id);
        link.setUrl("http://some.url");
        link.setTitle("A dummy link");
        link.setDescription("A dummy link about something");
        link.setImageUrl("http://some-image.url");
        return link;
    }
}
