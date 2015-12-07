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

package meresti.linkviewer.core.tool;

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.entities.ContentRoomLink;
import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.repositories.ContentRoomLinkRepository;
import meresti.linkviewer.core.repositories.ContentRoomRepository;
import meresti.linkviewer.core.repositories.LinkRepository;
import meresti.linkviewer.core.spring.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
public class StoreInitializer {

    @Autowired
    private ContentRoomRepository contentRoomRepository;

    @Autowired
    private ContentRoomLinkRepository contentRoomLinkRepository;

    @Autowired
    private LinkRepository linkRepository;

    public static void main(final String[] args) {
        final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, StoreInitializer.class);
        context.registerShutdownHook();

        final StoreInitializer storeInitializer = context.getBean(StoreInitializer.class);
        storeInitializer.initStore();
    }

    private void initStore() {
        final List<Link> savedLinks = linkRepository.save(DummyDataCollection.LINKS);

        final List<ContentRoom> contentRooms = DummyDataCollection.CONTENT_ROOMS.stream().map((roomName) -> new ContentRoom(null, roomName)).collect(Collectors.toList());
        final List<ContentRoom> savedContentRooms = contentRoomRepository.save(contentRooms);

        final List<BigInteger> linkIds = savedLinks.stream().map(Link::getId).collect(Collectors.toList());
        final Random random = new SecureRandom();
        final int contentRoomCount = savedContentRooms.size();
        int fromIndex = 0;
        for (int roomIndex = 0; roomIndex < contentRoomCount; roomIndex++) {
            final ContentRoom contentRoom = savedContentRooms.get(roomIndex);
            final BigInteger roomId = contentRoom.getId();

            final int toIndex = isLastRoom(contentRoomCount, roomIndex) ? savedLinks.size() : random.nextInt(savedLinks.size() - fromIndex) + fromIndex;
            final List<BigInteger> linkIdSublist = linkIds.subList(fromIndex, toIndex);

            final List<ContentRoomLink> contentRoomLinks = linkIdSublist.stream().map(linkId -> new ContentRoomLink(null, roomId, linkId)).collect(Collectors.toList());
            contentRoomLinkRepository.save(contentRoomLinks);

            fromIndex = toIndex;
        }
    }

    private static boolean isLastRoom(final int count, final int index) {
        return index == count - 1;
    }
}
