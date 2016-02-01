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

import meresti.linkviewer.core.RelevanceRateCalculator;
import meresti.linkviewer.core.SpecialContentRooms;
import meresti.linkviewer.core.entities.*;
import meresti.linkviewer.core.repositories.ContentRoomLinkRepository;
import meresti.linkviewer.core.repositories.LinkRepository;
import meresti.linkviewer.core.services.ContentRoomService;
import meresti.linkviewer.core.services.UserService;
import meresti.linkviewer.core.spring.AppConfig;
import meresti.linkviewer.rest.spring.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class StoreInitializer {

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Autowired
    private ContentRoomService contentRoomService;

    @Autowired
    private ContentRoomLinkRepository contentRoomLinkRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LinkRepository linkRepository;

    public static void main(final String[] args) {
        final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, StoreInitializer.class, SecurityConfig.class);
        context.registerShutdownHook();

        final StoreInitializer storeInitializer = context.getBean(StoreInitializer.class);
        storeInitializer.dropStore();
        storeInitializer.initStore();
    }

    private void dropStore() {
        mongoDbFactory.getDb().dropDatabase();
    }

    @Transactional
    private void initStore() {
        final List<User> savedUsers = userService.createUsers(DummyDataCollection.USERS);
        setUserWhoOwnsAllCreatedData(savedUsers.get(1));

        final List<Link> savedLinks = linkRepository.save(DummyDataCollection.LINKS);

        final List<ContentRoom> contentRooms = DummyDataCollection.CONTENT_ROOMS.stream().map((roomName) -> new ContentRoom(null, roomName)).collect(Collectors.toList());
        final List<ContentRoom> savedContentRooms = contentRoomService.createRooms(contentRooms);

        saveContentRoomLinks(savedLinks, savedContentRooms);

        contentRoomService.addPermission(new ObjectIdentityImpl(ContentRoom.class, SpecialContentRooms.ALL_ROOMS.getId().toString()), new GrantedAuthoritySid("ROLE_USER"), BasePermission.READ);
    }

    private static void setUserWhoOwnsAllCreatedData(final User user) {
        final Authentication authRequest = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(authRequest);
    }

    private void saveContentRoomLinks(final List<Link> links, final List<ContentRoom> contentRooms) {
        final Random random = new SecureRandom();
        final int contentRoomCount = contentRooms.size();
        int fromIndex = 0;
        for (int roomIndex = 0; roomIndex < contentRoomCount; roomIndex++) {
            final ContentRoom contentRoom = contentRooms.get(roomIndex);

            final int toIndex = isLastRoom(contentRoomCount, roomIndex) ? links.size() : random.nextInt(links.size() - fromIndex) + fromIndex;
            final List<Link> linkSubList = links.subList(fromIndex, toIndex);

            final List<ContentRoomLink> contentRoomLinks = linkSubList.stream().map(link -> createContentRoomLink(contentRoom, link, random)).collect(Collectors.toList());
            contentRoomLinkRepository.save(contentRoomLinks);

            fromIndex = toIndex;
        }
    }

    private static ContentRoomLink createContentRoomLink(final ContentRoom room, final Link link, final Random random) {
        final ContentRoomLink contentRoomLink = new ContentRoomLink(null, room, link);
        contentRoomLink.setRelevance(getPersonalOpinionatedRelevance(random));
        final Map<Relevance, Long> relevanceCounts = getRelevanceCounts(random);
        if (relevanceCounts != null) {
            contentRoomLink.setRelevanceCounts(relevanceCounts);

            final BigDecimal relevanceRate = new RelevanceRateCalculator().calculateFrom(relevanceCounts);
            contentRoomLink.setRelevanceRate(relevanceRate);
        }

        return contentRoomLink;
    }

    private static Relevance getPersonalOpinionatedRelevance(final Random random) {
        final Relevance relevance;
        switch (random.nextInt(50)) {
            case 1:
                relevance = Relevance.ABSOLUTELY_IRRELEVANT;
                break;
            case 2:
                relevance = Relevance.MOSTLY_IRRELEVANT;
                break;
            case 3:
                relevance = Relevance.SOMEWHAT_IRRELEVANT;
                break;
            case 4:
                relevance = Relevance.NEITHER_IRRELEVANT_NOR_RELEVANT;
                break;
            case 5:
                relevance = Relevance.SOMEWHAT_RELEVANT;
                break;
            case 6:
                relevance = Relevance.MOSTLY_RELEVANT;
                break;
            case 7:
                relevance = Relevance.ABSOLUTELY_RELEVANT;
                break;
            default:
                relevance = null;
        }

        return relevance;
    }

    private static Map<Relevance, Long> getRelevanceCounts(final Random random) {
        final Map<Relevance, Long> counts = new EnumMap<>(Relevance.class);
        for (final Relevance relevance : Relevance.values()) {
            final int randomValue = random.nextInt(500);
            if (randomValue < 50) {
                counts.put(relevance, (long) randomValue);
            }
        }

        return counts.isEmpty() ? null : counts;
    }

    private static boolean isLastRoom(final int count, final int index) {
        return index == count - 1;
    }
}
