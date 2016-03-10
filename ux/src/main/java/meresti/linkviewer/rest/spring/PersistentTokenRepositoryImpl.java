/*
 * Copyright (c) 2016. meresti
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

package meresti.linkviewer.rest.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {

    private static final String COLLECTION_NAME = "RememberMeToken";

    private final MongoOperations mongoOperations;

    @Autowired
    public PersistentTokenRepositoryImpl(final MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void createNewToken(final PersistentRememberMeToken token) {
        mongoOperations.save(token, COLLECTION_NAME);
    }

    @Override
    public void updateToken(final String series, final String tokenValue, final Date lastUsed) {
        final PersistentRememberMeToken token = mongoOperations.findAndRemove(query(where("series").is(series)), PersistentRememberMeToken.class, COLLECTION_NAME);
        if (token != null) {
            final PersistentRememberMeToken updatedToken = new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed);
            mongoOperations.save(updatedToken, COLLECTION_NAME);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(final String seriesId) {
        return mongoOperations.findOne(query(where("series").is(seriesId)), PersistentRememberMeToken.class, COLLECTION_NAME);
    }

    @Override
    public void removeUserTokens(final String username) {
        mongoOperations.remove(query(where("username").is(username)), PersistentRememberMeToken.class, COLLECTION_NAME);
    }
}
