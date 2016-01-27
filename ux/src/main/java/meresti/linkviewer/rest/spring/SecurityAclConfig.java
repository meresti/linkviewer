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

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.acls.mongodb.dao.AclClassRepository;
import org.springframework.security.acls.mongodb.dao.AclEntryRepository;
import org.springframework.security.acls.mongodb.dao.AclObjectIdentityRepository;
import org.springframework.security.acls.mongodb.dao.AclSidRepository;
import org.springframework.security.acls.mongodb.service.AclClassService;
import org.springframework.security.acls.mongodb.service.MongodbMutableAclService;
import org.springframework.security.acls.mongodb.service.SimpleCacheAclClassService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
@EnableMongoRepositories("org.springframework.security.acls.mongodb.dao")
public class SecurityAclConfig {

    @Autowired
    private AclSidRepository aclSidRepository;

    @Autowired
    private AclEntryRepository aclEntryRepository;

    @Autowired
    private AclObjectIdentityRepository aclObjectIdentityRepository;

    @Autowired
    private AclClassRepository aclClassRepository;

    @Bean
    public AclCache aclCache() {
        final EhCacheFactoryBean factoryBean = new EhCacheFactoryBean();

        final EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
        cacheManager.setAcceptExisting(true);
        cacheManager.setCacheManagerName(CacheManager.getInstance().getName());
        cacheManager.afterPropertiesSet();

        factoryBean.setName("aclCache");
        factoryBean.setCacheManager(cacheManager.getObject());
        factoryBean.setMaxBytesLocalHeap("16M");
        factoryBean.setMaxEntriesLocalHeap(0L);
        factoryBean.afterPropertiesSet();

        final AclAuthorizationStrategyImpl aclAuthorizationStrategy = aclAuthorizationStrategy();

        return new EhCacheBasedAclCache(factoryBean.getObject(), permissionGrantingStrategy(), aclAuthorizationStrategy);
    }

    @Bean
    public AclAuthorizationStrategyImpl aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public MutableAclService aclService() {
        return new MongodbMutableAclService(aclEntryRepository, aclObjectIdentityRepository, aclSidRepository, aclClassService(), aclCache(), aclAuthorizationStrategy(), permissionFactory(), permissionGrantingStrategy());
    }

    @Bean
    public AclClassService aclClassService() {
        return new SimpleCacheAclClassService(aclClassRepository);
    }

    @Bean
    public PermissionFactory permissionFactory() {
        return new DefaultPermissionFactory();
    }

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new AclPermissionEvaluator(aclService());
    }
}
