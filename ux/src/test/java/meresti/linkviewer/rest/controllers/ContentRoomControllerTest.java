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

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.services.ContentRoomService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContentRoomControllerTest {

    @InjectMocks
    private ContentRoomController contentRoomController;

    @Mock
    private ContentRoomService contentRoomService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contentRoomController).build();
    }

    @Test
    public void testCreateContentRoom() throws Exception {
        when(contentRoomService.createRoom(Matchers.any(ContentRoom.class))).thenAnswer(returnsFirstArg());

        mockMvc.perform(post("/rooms").content("{\"name\":\"dummy room\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("dummy room")));
    }

    @Test
    public void testDeleteContentRoom() throws Exception {
        when(contentRoomService.deleteRoom(Matchers.any(ContentRoom.class))).thenAnswer(returnsFirstArg());

        mockMvc.perform(delete("/rooms").content("{\"name\":\"dummy room\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("dummy room")));
    }

    @Test
    public void testGetContentRooms() throws Exception {
        final ContentRoom contentRoom = new ContentRoom(BigInteger.ONE, "dummy room", null);
        when(contentRoomService.getRooms()).thenReturn(Collections.singletonList(contentRoom));

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].roomId", is(contentRoom.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(contentRoom.getName())));
    }

    @Test
    public void testGetLinks() throws Exception {
        final Link link = createDummyLink(BigInteger.ONE);

        when(contentRoomService.findLinks(BigInteger.ONE, 0L, 10L)).thenReturn(Collections.singletonList(link));
        when(contentRoomService.findLinks(BigInteger.ONE, 10L, 10L)).thenReturn(Collections.emptyList());
        when(contentRoomService.findLinks(BigInteger.TEN, 0L, 10L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/rooms/{roomId}/links/{startIndex}/{pageSize}", BigInteger.ONE, 0L, 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].url", is(link.getUrl())))
                .andExpect(jsonPath("$[0].title", is(link.getTitle())))
                .andExpect(jsonPath("$[0].description", is(link.getDescription())))
                .andExpect(jsonPath("$[0].imageUrl", is(link.getImageUrl())));

        mockMvc.perform(get("/rooms/{roomId}/links/{startIndex}/{pageSize}", BigInteger.ONE, 10L, 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(empty())));


        mockMvc.perform(get("/rooms/{roomId}/links/{startIndex}/{pageSize}", BigInteger.TEN, 0L, 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(empty())));

    }

    @Test
    public void testGetExistingLink() throws Exception {
        final Link link = createDummyLink(BigInteger.ONE);

        when(contentRoomService.findById(link.getId())).thenReturn(link);

        mockMvc.perform(get("/rooms/{roomId}/links//{id}", BigInteger.ONE, link.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(link.getUrl())))
                .andExpect(jsonPath("$.title", is(link.getTitle())))
                .andExpect(jsonPath("$.description", is(link.getDescription())))
                .andExpect(jsonPath("$.imageUrl", is(link.getImageUrl())));
    }

    @Test
    public void testGetNonExistingLink() throws Exception {
        when(contentRoomService.findById(BigInteger.ONE)).thenReturn(null);

        mockMvc.perform(get("/rooms/{roomId}/links//{id}", BigInteger.ONE, BigInteger.ONE))
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
