package org.sonatype.sitemap.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.sonatype.sitemap.Backend;
import org.sonatype.sitemap.Contributor;
import org.sonatype.sitemap.Manager;
import org.sonatype.sitemap.Sitemap;
import org.sonatype.sitemap.record.CombinationalKey;
import org.sonatype.sitemap.record.Key;

public class DefaultManager
    implements Manager
{
    private final Backend backend;

    private final Map<Key, Contributor> contributors;

    public DefaultManager( Backend backend )
    {
        this( backend, (Contributor[]) null );
    }

    public DefaultManager( Backend backend, Contributor... contributors )
    {
        this.backend = backend;

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

    public boolean hasSitemap( Key key )
    {
        return backend.hasMap( key );
    }

    public boolean createSitemap( Key key )
    {
        backend.createMap( key );

        for ( Contributor c : contributors.values() )
        {
            backend.createMap( new CombinationalKey( key, c.getKey() ) );
        }

        return true;
    }

    public Sitemap getSitemap( Key key )
    {
        return new DefaultSitemap( backend, contributors, key );
    }

    public boolean removeSitemap( Key key )
    {
        for ( Contributor c : contributors.values() )
        {
            backend.removeMap( new CombinationalKey( key, c.getKey() ) );
        }

        return backend.removeMap( key );
    }
}
