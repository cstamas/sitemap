package org.sonatype.sitemap.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.sonatype.sitemap.Backend;
import org.sonatype.sitemap.Contributor;
import org.sonatype.sitemap.ContributorKeyProvider;
import org.sonatype.sitemap.Manager;
import org.sonatype.sitemap.Sitemap;
import org.sonatype.sitemap.record.Key;

public class DefaultManager
    implements Manager
{
    private final Backend backend;

    private final Map<Key, Contributor> contributors;

    private final ContributorKeyProvider contributorKeyProvider;

    public DefaultManager( Backend backend )
    {
        this( backend, new DefaultContributorKeyProvider(), (Contributor[]) null );
    }

    public DefaultManager( Backend backend, ContributorKeyProvider contributorKeyProvider, Contributor... contributors )
    {
        this.backend = backend;

        this.contributorKeyProvider = contributorKeyProvider;

        this.contributors = new ConcurrentHashMap<Key, Contributor>();

        if ( contributors != null )
        {
            for ( Contributor c : contributors )
            {
                registerContributor( c );
            }
        }
    }

    public void registerContributor( Contributor pc )
    {
        contributors.put( pc.getKey(), pc );
    }

    public void unregisterContributor( Contributor pc )
    {
        contributors.remove( pc.getKey() );
    }

    public ContributorKeyProvider getContributorKeyProvider()
    {
        return contributorKeyProvider;
    }

    public boolean hasSitemap( Key key )
    {
        return backend.hasMap( key );
    }

    public boolean createSitemap( Key key )
    {
        backend.createMap( key );

        for ( Contributor c : contributors.values() )
        {
            backend.createMap( getContributorKeyProvider().getContributorKey( key, c ) );
        }

        return true;
    }

    public Sitemap getSitemap( Key key )
    {
        return new DefaultSitemap( backend, getContributorKeyProvider(), contributors, key );
    }

    public boolean removeSitemap( Key key )
    {
        for ( Contributor c : contributors.values() )
        {
            backend.removeMap( getContributorKeyProvider().getContributorKey( key, c ) );
        }

        return backend.removeMap( key );
    }
}
