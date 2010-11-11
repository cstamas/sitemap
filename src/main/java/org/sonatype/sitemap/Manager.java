package org.sonatype.sitemap;

import org.sonatype.sitemap.record.Key;

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
