package org.sonatype.sitemap.space4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.sonatype.sitemap.Backend;
import org.sonatype.sitemap.record.Key;
import org.sonatype.sitemap.record.Keyed;
import org.sonatype.sitemap.record.Record;
import org.space4j.Command;
import org.space4j.Space;
import org.space4j.Space4J;
import org.space4j.command.CreateMapCmd;
import org.space4j.command.PutCmd;
import org.space4j.command.RemoveCmd;
import org.space4j.command.RemoveObjectCmd;

public class Space4jBackend
    implements Backend
{
    private static final String RECORD_SEQ_NAME = "record_seq";

    private final Space4J space4j;

    private final Space space;

    public Space4jBackend( final Space4J space4j )
        throws Exception
    {
        this.space4j = space4j;

        space4j.start();

        space = space4j.getSpace();

        if ( !space.check( RECORD_SEQ_NAME ) )
        {
            space4j.exec( new CreateLongSequenceCmd( RECORD_SEQ_NAME ) );
        }
    }

    public long getNextId( final Key key )
    {
        IncrementLongSeqCmd cmd = new IncrementLongSeqCmd( RECORD_SEQ_NAME );

        exec( cmd, -1 );

        return cmd.getSequence();
    }

    public boolean hasMap( final Key key )
    {
        return space.check( key );
    }

    public boolean createMap( Key key )
    {
        return exec( new CreateMapCmd<Key, Record>( key ), 1 );
    }

    public Map<Key, Keyed> getMap( final Key key )
    {
        if ( !hasMap( key ) )
        {
            // kaboom
            throw new IllegalStateException( "No map with key=\"" + key.toString() + "\" exists!" );
        }

        return space.get( key );
    }

    public boolean removeMap( final Key key )
    {
        return exec( new RemoveObjectCmd( key ), 1 );
    }

    // ==

    public boolean put( final Key sid, final Keyed cr )
    {
        return exec( new PutCmd( sid, cr.getKey(), cr ), 1 );
    }

    public <T extends Keyed> int putAll( Key sid, Collection<T> records )
    {
        Map<Object, Object> toPut = new HashMap<Object, Object>( records.size() );

        for ( Keyed r : records )
        {
            toPut.put( r.getKey(), r );
        }

        return exec( new PutAllCmd( sid, toPut ) );
    }

    public boolean remove( final Key sid, final Key key )
    {
        return exec( new RemoveCmd( sid, key ), 1 );
    }

    public <T extends Key> int removeAll( final Key sid, final Collection<T> keys )
    {
        ArrayList<Object> toRemove = new ArrayList<Object>( keys.size() );

        toRemove.addAll( keys );

        return exec( new RemoveAllCmd( sid, toRemove ) );
    }

    // ==

    protected boolean exec( Command cmd, int expected )
        throws IllegalStateException
    {
        if ( expected > 0 )
        {
            // we want to match the affected object count
            return exec( cmd ) == expected;
        }
        else
        {
            // we do not care about affected object count
            exec( cmd );

            return true;
        }
    }

    protected int exec( Command cmd )
        throws IllegalStateException
    {
        try
        {
            return space4j.exec( cmd );
        }
        catch ( Exception e )
        {
            throw new IllegalStateException( "Could not execute command: " + cmd.toString(), e );
        }
    }

}
