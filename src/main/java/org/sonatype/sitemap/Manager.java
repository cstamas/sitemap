package org.sonatype.sitemap;

import org.sonatype.sitemap.record.Key;

/**
 * Sitemap manager for managing Sitemaps, by adding then, removing them, creating them, and also registering and
 * unregistering the Contributors.
 * 
 * @author cstamas
 */
public interface Manager
{
    void registerContributor( Contributor pc );

    void unregisterContributor( Contributor pc );

    // ==

    boolean hasSitemap( Key key );

    boolean createSitemap( Key key );

    Sitemap getSitemap( Key key );

    boolean removeSitemap( Key key );
}
